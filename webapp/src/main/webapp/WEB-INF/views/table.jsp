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

    <h4><u>View stock levels</u></h4>
         <div class="form-group">
          <table>
				<tr>
				    <td>Date</td>
				    <td>Msisdn</td>
				</tr>
				<tr>
				    <td><input name="date" type="text" placeholder="Date" autofocus required style="width: 250px"></td>
				    <td><input name="msisdn" type="text" placeholder="msisdn" required style="width: 250px"></td>
				</tr> 
		 </table> 
        </div>
        <div>
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
            
            
            </tbody>
        </table>
        </div>
        <button class="btn btn-default" type="submit">View</button>

    <hr>

    <div class="footer">
        <p>&copy; Cell-Life (NPO) - 2013</p>
    </div>

</div>


</body>
</html>
