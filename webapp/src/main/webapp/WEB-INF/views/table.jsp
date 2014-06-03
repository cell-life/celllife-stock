<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="refresh" content="60">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <title>Stock App</title>

    <c:set var="url">${pageContext.request.requestURL}</c:set>
    <base href="${fn:substring(url, 0, fn:length(url) - fn:length(pageContext.request.requestURI))}${pageContext.request.contextPath}/" />

    <link href="resources/css/bootstrap-3.0.2.css" rel="stylesheet" media="screen">
    <link href="resources/css/bootstrap-theme-3.0.2.css" rel="stylesheet" media="screen">
    <link href="resources/css/application.css" rel="stylesheet">
</head>
<body>
	<div class="container">

		<jsp:include page="includes/header.jsp"/>
        
    	<h4>View stock levels</h4>
    
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
            <jsp:useBean id="dateValue" class="java.util.Date" />
            <c:forEach items="${stockList}" var="stockList">
                <tr>               
                    <td>${stockList.clinicCode}</td>
                    <td>${stockList.clinicName}</td>
                    <td>${stockList.drugName}</td>
                    <jsp:setProperty name="dateValue" property="time" value="${stockList.date}" />
                    <td><fmt:formatDate type="both" dateStyle="medium" timeStyle="medium" value="${dateValue}" /></td>
                    <td><fmt:formatNumber value="${stockList.quantity}" type="number" minFractionDigits="2"/></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

		<jsp:include page="includes/footer.jsp"/>

</div>

</body>
</html>
