<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="true"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="grid_12">
	<center>
		<div id="successMessage" class="message success">
			<h5>Your Workboard has been created !</h5>
		</div>
		<!-- Makes the success messages fade out after 3 seconds -->
		<script type="text/javascript">
			$(document).ready(function(){
				setTimeout(function(){
					$("#successMessage").fadeOut("slow", function () {
						$("#successMessage").remove();
					});
				}, 3000);
			});
		</script>
		
		<h3>How to use your workboard:</h3>
		<img src="<c:url value="/resources/img/help/workboard-instructions.jpg" />" style="margin: 50px auto" />
		
		<h4>Click on "Continue" when you are ready to start.</h4>
	</center>
	<br />
	<div align="center">
		<a href="/CSS/auth/workboard/my-workboard">
			<button id="btnContinueToWorkboard" class="btn btn-icon btn-blue btn-arrow-right" >
				<span></span>Continue
			</button>
		</a>
	</div>
	
</div>