<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.io.*,java.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>STOCK APP</title>

    <c:set var="url">${pageContext.request.requestURL}</c:set>
    <base href="${fn:substring(url, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/" />

    <link href="resources/css/bootstrap-3.0.2.css" rel="stylesheet" media="screen">
    <link href="resources/css/bootstrap-theme-3.0.2.css" rel="stylesheet">
    
</head>
<body>

<div class="masthead">
  <ul class="nav nav-pills pull-right">
       <li class="active"><a href="index">Home</a></li>
       <li><a href="alert">Alerts</a></li>
       <li><a href="summary/loadStockTake">Summary</a></li>
       <li><a href="j_spring_cas_security_logout">Logout</a>
  </ul>
  <h2><img src="resources/img/logo.png"></h2>
  <h3 class="muted">STOCK APP DEMO</h3
</div>
<hr>
	<h2>Create alerts and view stocktake summary</h2>
</hr>



</body>
</html>