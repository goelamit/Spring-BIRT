package com.agl.demo.birt.controller;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.agl.demo.birt.core.BirtEngineFactory;
import com.agl.demo.birt.core.BirtView;

/**
 * BirtView is used to run and render BIRT reports
 *
 */
@Controller
public class BirtController {


    private final Logger logger = LoggerFactory.getLogger(BirtController.class);

    @RequestMapping(value = "/reports", method = RequestMethod.GET)
    public BirtView testRequest(HttpServletRequest request, HttpServletResponse response){

        logger.debug("BIRT response");

        return birtView(request.getSession().getServletContext());
    }

   
    public BirtView birtView(ServletContext ctx){

        logger.debug("birtView START");

        BirtView bv = new BirtView();
        bv.setReportFormatRequestParameter("ReportFormat");
        bv.setBirtEngine(engine(ctx).getObject());
        return bv;
    }

   
    protected BirtEngineFactory engine(ServletContext ctx){
        BirtEngineFactory factory = new BirtEngineFactory() ;
        factory.setApplicationContext(WebApplicationContextUtils.getWebApplicationContext(ctx));

        logger.debug("Birt Factor CALLED");

        return factory ;
    }

}
