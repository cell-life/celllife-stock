<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <link href="resources/css/bootstrap-theme-3.0.2.css" rel="stylesheet" media="screen">
    <link href="resources/css/application.css" rel="stylesheet">
</head>
<body>
<div class="container">

    <div class="masthead">
        <ul class="nav nav-pills pull-right">
            <li class="active"><a href="#">Home</a></li>
            <li><a href="j_spring_cas_security_logout">Logout</a>
        </ul>
         <h2><img src="resources/img/logo.png"></h2>
        <h3 class="muted">STOCK APP DEMO</h3>
    <!-- </div> -->

    <hr>

    <div class="row ohsc-border">

        
    <h4><u>View stock levels</u></h4>
    
        <table class="table table-striped table-bordered" id="myTable">
            <thead>
            <tr>
                <th>Facility code</th>
                <th>Facility name</th>
                <th>Drug name</th>
                <th>Date</th>
                <th>Quantity</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${stockList}" var="stockList">
                <tr>               
                    <td><fmt:formatNumber value="${stockList.clinicCode}" type="number" minFractionDigits="2"/></td>
                    <td><fmt:formatNumber value="${stockList.clinicName}" type="number" minFractionDigits="2"/></td>
                    <td><fmt:formatNumber value="${stockList.drugName}" type="number" minFractionDigits="2"/></td>
                    <td><fmt:formatNumber value="${stockList.date}" type="number" minFractionDigits="2"/></td>
                    <td><fmt:formatNumber value="${stockList.quantity}" type="number" minFractionDigits="2"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>


        <p><small>All facilities stock list.</small></p>
    </div>

    <div class="footer">
        <p>&copy; Cell-Life (NPO) - 2013</p>
    </div>

</div>
</div>

</body>
</html>
