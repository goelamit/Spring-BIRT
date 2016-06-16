<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<title>Spring-BIRT</title>
</head>
<body>


	<h1>BIRT Report - Demo</h1>
	<p>
		<a
			href="<c:url value="/reports">
            <c:param name="ReportName" value="customer_report.rptdesign"/>
            <c:param name="ReportFormat" value="html"/> 
            </c:url>">
			1. Customer BIRT Report - HTML</a><br>

	</p>
	<p>
		<a
			href="<c:url value="/reports">
            <c:param name="ReportName" value="customer_report.rptdesign"/>
            <c:param name="ReportFormat" value="pdf"/> 
            </c:url>">
			2. Customer BIRT Report - PDF</a><br>

	</p>
	<%= new java.util.Date() %>

</body>
</html>

