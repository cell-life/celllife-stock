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
					<option value="6009628332788">Abacavir 60 mg 56 tablets</option>
					<option value="6009628332856">Abacavir 600 mg/Lamivudine 300 mg 28 tablets</option>
					<option value="6004405009518">Adco-Lamivudine Solution 10mg/ml 240 ml</option>
					<option value="6004405009525">Adco-Zidovudine Syrup 50 mg/ 5ml 240 ml</option>
					<option value="6001127044858">Aluvia 100 mg/25mg 56 tablets</option>
					<option value="6001390116696">Aspen Abacavir Oral Suspension 20mg/ml</option>
					<option value="6009695242683">Auro-Stavuddine  20mg  60 capsules</option>
					<option value="6006352024804">Cipla-Efavirenz 600 mg 28 tablets</option>
					<option value="6006352003052">Cipla-Lamivudine 150 mg 60 tablets</option>
					<option value="6006352008583">Cipla-Lamivudine 300 mg 150 tablets</option>
					<option value="6006352019459">Cipla-Zidovudine 100 mg 100 capsules</option>
					<option value="6009695243703">Deladex 250 mg capsules</option>
					<option value="6001137100797">Diflucan 35 ml powder for oral suspension</option>
					<option value="6009695242614">Erige 50 mg 30 capsules</option>
					<option value="6005317012900">Ethatyl 250 mg 84 tablets</option>
					<option value="6009695243024">Legram 10mg/ml oral solution 240 ml</option>
					<option value="6001127043738">Lopinavir/Ritonavir oral solution 5X60 ml</option>
					<option value="6006673871552">Mycobutin 150 mg 30 capsules</option>
					<option value="6009609471031">Norstan-isoniazid 100 mg 84 tablets</option>
					<option value="6001127043325">Norvir 80 mg/ml 1X90ml</option>
					<option value="6001127043318">Norvir Sec 100 mg  84 capsules</option>
					<option value="6009657981742">R-CIN 600 mg 100 capsules</option>
					<option value="6001390115637">R-Cin suspension 180 ml</option>
					<option value="6005317017103">Rifafour e-275 100 tablets</option>
					<option value="6006498008003">Rimactane 150 mg 100 capsules</option>
					<option value="6006498009178">Rimactane Vial dry powder</option>
					<option value="6006498012703">Rimactazid Paed 56 tablets</option>
					<option value="6006498009505">Sandoz Ethambutol HCI 400 mg 100 tablets</option>
					<option value="6006498011638">Sandoz Pyrazinamide 500 mg 84 tablets</option>
					<option value="6005317007357">Terivalidin 250 mg capsules</option>
					<option value="6009695242591">Viropon 50mg/ 5ml Oral Suspension 240 ml</option>
					<option value="6001076209209">Ziagen  60 tablets</option>
					<option value="6009628332801">Zidomat 300 mg 56 tablets</option>
				</select>
			</div>
			
			<div class="form-group">
				<label for="message">Message</label>
				<input class="form-control" type="text" name="message" id="message" value="Abacavir 60 mg 56 tablets" required>
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