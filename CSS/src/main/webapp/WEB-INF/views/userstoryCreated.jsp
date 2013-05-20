<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="true"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="grid_12">
	<center>
		<h3>How to edit your report:</h3>
	
		<div id="successMessage" class="message success">
			<h5>Your Report has been created from the Workboard !</h5>
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
		
		<p class="info-paragraph">This section of the Climate Smart Seaports tool enables users to create a report from the data elements they have generated. Within this section, users can re-order the elements to suit their needs, hide data elements and add further text and analysis as required.</p>

		<p class="info-paragraph">Users need to consider for what purpose they are producing the report, to determine the best structure, and how much further analysis may need to be added.</p>
		
		<img src="<c:url value="/resources/img/help/report-instructions.png" />" style="margin: 50px auto; opacity: 0.7" />
		
		<h4>Click on "Continue" when you are ready.</h4>
	</center>
	<br />
	<div align="center">
		<a href="/CSS/auth/userstory?id=${userstory.id}">
			<button id="btnContinueToReport" class="btn btn-icon btn-blue btn-arrow-right" >
				<span></span>Continue
			</button>
		</a>
	</div>
	
</div>