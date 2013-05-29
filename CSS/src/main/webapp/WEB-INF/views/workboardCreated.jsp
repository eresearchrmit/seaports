<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="true"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="grid_12">
	<center>
		<h3>How to use your Workboard</h3>
	
		<div id="successMessage" class="message success">
			<h5>Your Workboard has been created !</h5>
		</div>
		
		<p class="info-paragraph">The Workboard is where data can be collected and reviewed.</p>  

		<p class="info-paragraph">It has a number of tabs for users to progress through, each presenting different data sets: Non-climate context; Observed Climate; Future Climate; Applications (Concrete deterioration model and vulnerability assessment).  Users add relevant data from each tab, and then review their collected data in the "Summary" tab.</p> 
		
		<img src="<c:url value="/resources/img/help/workboard-instructions.png" />" style="margin: 50px auto; opacity: 0.7" />
		
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