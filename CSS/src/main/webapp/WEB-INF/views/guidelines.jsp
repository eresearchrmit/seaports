<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="grid_12">
	<a href="javascript: history.back();" class="floatleft" title="Go back to previous page">
		<button class="btnDeleteStory btn-icon btn-blue btn-arrow-left" >
			<span></span>Back
		</button>
	</a>
	
	<a href="<c:url value="/resources/docs/climate-smart-seaports-guidelines.pdf" />" title="Download these guidelines as a PDF document" target="_blank" style="margin-right: 10px; float:right">
		<button class="btn-icon btn-blue btn-arrow-down" >
			<span></span>Download Guidelines PDF
		</button>
	</a>

	<center>
		<h2>Guidelines</h2>
	</center>
	<p style="text-align:center; margin: 25px 0; font-size: 12pt">
		<strong>These are the guidelines for the seaports application</strong>
	</p>
	
	<!-- BEGINNNING OF GUIDELINES CONTENT -->
	
	
	<!-- END OF GUIDELINES CONTENT -->
</div>