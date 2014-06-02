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
    
    <script type="text/javascript" src="resources/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="resources/js/jquery-ui.min.js"></script>
    <script type="text/javascript" src="resources/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="resources/js/jquery-ui-timepicker-addon.js"></script>
    <script type="text/javascript" src="resources/js/jquery.dataTables.js"></script>
    <script type="text/javascript" src="resources/js/datatables_bootstrap.js"></script>
    
</head>
<body>
 <form action='/' id='form1' method='POST'> 
<div class="container">

    <div class="masthead">
        <ul class="nav nav-pills pull-right">
            <li class="active"><a href="#">Home</a></li>
            <li><a href="table">Summary</a></li>
            <li><a href="j_spring_cas_security_logout">Logout</a>
        </ul>
         <h2><img src="resources/img/logo.png"></h2>
        <h3 class="muted">STOCK APP DEMO</h3>

    <hr>

    <h4><u>Create an alert</u></h4>
    <!-- <div class="alert level detail"> -->
		<p> Level: <select  name ="level" id="level" style="width: 250px" onchange="selectLevel()"
			<option value="">Level </option>
			<option value="level1">Level 1</option>
			<option value="level2">Level 2</option>
			<option value="level3">Level 3</option>
			></select></p></br>

			<script type="text/javascript">
				  function selectLevel(){
				  var level =  $("#level").val(); 
				 alert(level);
				} 
		
			</script>
		
	
		<p>Drug:   <select name="drug" id="drug" style="width: 250px" onchange="setBarcode()"
		<option value="">Drug </option>
		 <option  value="6001137100797">Diflucan</option>
  		 <option  value="6009628332788">Kavimun Paed</option>
         <option  value="6009695243024">Legram Oral Solution</option>
		></select></p></br>
		
		<p>Message:   <select name="message" id="message" style="width: 250px"
		<option value="">Message </option>
		 <option  value="">Diflucan 35 ml powder for oral suspension</option>
  		 <option  value="">Abacavir 60 mg 56 tablets</option>
         <option  value="">Legram 10mg/ml oral solution 240 ml</option>
		></select></p></br>
		
		<input id="bar_code" name="bar_code" type="hidden" >
		<script type="text/javascript">

			function setBarcode(){
				var barcode = $("#drug").val();
				$('#bar_code').val(barcode);
			    /* alert(barcode);  */
			}

	  </script>
	  
		<p>Msisdn: <select  name="user" id="user" style="width: 250px" onchange="selectUser()"
        <option value="">User </option>
        <option  value="1">27768198075</option>
		 <option  value="2">27741486362</option>
        ></select></p></br>
        
        <script type="text/javascript">
			function selectUser(){
			 var user =  $("#user").val();
			 /* alert(user); */
			}

	    </script>
        <script type='text/javascript'>
        function sendUser(){
			 var user =  $("#user").val();
			 /* alert(user); */
        }
      </script>
     <button class="btn btn-default" type="submit">Create Alert</button>

	 <p>
	    <ul>
	        <li><a href="/stock/map">Alert Escalation Map</a></li>
	    </ul>
	 </p>

    <hr>
    </div>

    <div class="footer">
        <p>&copy; Cell-Life (NPO) - 2013</p>
    </div>

</div>
</form>
</body>
</html>
