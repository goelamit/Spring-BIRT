package com.agl.demo.birt.core;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 *        
 *         <p>
 *         inspired: https://spring.io/blog/2012/01/30/spring-framework-birt
 */
public class BirtEngineFactory implements FactoryBean<IReportEngine>, ApplicationContextAware, DisposableBean {

    private final Logger logger = LoggerFactory.getLogger(BirtEngineFactory.class);

    private ApplicationContext context;
    private IReportEngine birtEngine;
    @SuppressWarnings("unused")
	private Resource logDirectory;
    private File _resolvedDirectory;
    private Level logLevel;


    @Override
    public void setApplicationContext(ApplicationContext ctx) {
        context = ctx;
    }

    @Override
    public void destroy() throws Exception {
        birtEngine.destroy();
        Platform.shutdown();
    }

    public void setLogLevel(Level ll) {
        logLevel = ll;
    }

    public void setLogDirectory(Resource resource) {
        File f;
        try {
            f = resource.getFile();
            validateLogDirectory(f);
            _resolvedDirectory = f;
        } catch (IOException e) {
            logger.error("setLogDirectory e= " + e);
            throw new RuntimeException("Couldnt set the log Directory");
        }


    }

    private void validateLogDirectory(File f) {
        Assert.notNull(f, " the directory must not be null");
        Assert.isTrue(f.isDirectory(), " the path given must be a directory");
        Assert.isTrue(f.exists(), "the path specified must exist!");
    }

    public void setLogDirectory(File f) {
        validateLogDirectory(f);
        _resolvedDirectory = f;
    }

    public IReportEngine getObject() {

        EngineConfig config = new EngineConfig();

        //This line injects the Spring Context into the BIRT Context
        config.getAppContext().put("spring", context);
        config.setLogConfig(null != _resolvedDirectory ? _resolvedDirectory.getAbsolutePath() : null, logLevel);
        try {
            Platform.startup(config);
        } catch (BirtException e) {
            throw new RuntimeException("Could not start the Birt engine!", e);
        }

        IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
        IReportEngine be = factory.createReportEngine(config);
        birtEngine = be;


        return be;
    }

    @Override
    public Class<?> getObjectType() {
        return IReportEngine.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
