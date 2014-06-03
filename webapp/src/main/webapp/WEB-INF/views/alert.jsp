<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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

        <c:if test="${not empty regMessage}">
            <div class="alert alert-success">  
              <a class="close" data-dismiss="alert">×</a>  
              ${regMessage}  
            </div>
        </c:if>

        <c:if test="${not empty errorMessage}">
            <div class="alert alert-error">  
              <a class="close" data-dismiss="alert">×</a>  
              ${errorMessage}
            </div>
        </c:if>

		<h4>Create an alert</h4>
		<form action='alert' method='POST'>
		
			<div class="form-group">
				<label for="level">Level</label>
				<select class="form-control" name="level" required>
					<option value="1">Level 1</option>
					<option value="2">Level 2</option>
					<option value="3">Level 3</option>
				</select>
			</div>

			<div class="form-group">
				<label for="drug">Drug</label>
				<select class="form-control" name="drug" id="drug" required>
					<option value="6001137100797">Diflucan</option>
					<option value="6009628332788">Kavimun Paed</option>
					<option value="6009695243024">Legram Oral Solution</option>
				</select>
			</div>
			
			<div class="form-group">
				<label for="message">Message</label>
				<input class="form-control" type="text" name="message" id="message" value="Diflucan" required>
			</div>

			<script type="text/javascript" src="resources/js/bootstrap.min.js"></script>
			<script type="text/javascript" src="resources/js/jquery-1.9.1.min.js"></script>
			<script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
			<script type="text/javascript">
			    $(document).ready(function(){
			        $('#drug').on('change', function() {
			            $("#message").val($(this).find("option:selected").text());
			        });
			    });
			</script>

			<div class="form-group">
				<label for="msisdn">Msisdn</label>
				<input class="form-control" type="text" name="msisdn" placeholder="27" required>
			</div>

			<button class="btn btn-default" type="submit">Create Alert</button>
		</form>

		<jsp:include page="includes/footer.jsp"/>
	</div>

</body>
</html>