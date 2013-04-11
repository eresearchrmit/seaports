<%@ page session="true"%>
<%@ page language="java" %>
<%@ page import="java.lang.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.codec.binary.*"%>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ page import="org.apache.commons.lang.*" %>
<%@ page language="java" import="war.model.UserStory" %>
<%@ page language="java" import="war.model.User" %>
<%@ page language="java" import="war.model.DataElement" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="grid_12">

	<c:if test="${not empty userstory}">
		<a href="javascript: window.print()" id="btnPrint" style="margin-right: 10px; float:right">
			<button class="btnAddDataElement btn btn-icon btn-blue btn-print">
				<span></span>Print
			</button>
		</a>
		
		<a href="javascript: window.close()" id="btnClosePreview" style="margin-right: 10px; float:right">
			<button class="btnAddDataElement btn btn-icon btn-blue btn-arrow-left">
				<span></span>Close Preview
			</button>
		</a>
		<div class="clear"></div>
		<center>
			<h2>${userstory.name} </h2>
			<h4>${userstory.region.name}</h4>
		</center>
	</c:if>
	
	<br />
	<c:if test="${not empty successMessage}">
		<div id="successMessage" class="message success">
			<h5>Success !</h5>
			<p>${successMessage}.</p>
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
	</c:if>
	<c:if test="${not empty errorMessage}">
		<div class="message error">
			<h5>Error</h5>
			<p>${errorMessage}</p>
		</div>
	</c:if>
	
	<c:if test="${not empty userstory}">
	<p style="text-align:left; width:90%; margin-right:auto;margin-left:auto">This report has been created by ${userstory.owner.firstname} ${userstory.owner.lastname} (${userstory.owner.email}) using the Climate Smart Seaports toolkit available at <a href="http://www.DOMAIN.com.au">http://www.DOMAIN.com.au</a></p>
	<br/><br/><br/>
	
	<c:if test="${not empty userstory.dataElements}">
	<div style="text-align:left; width:90%; margin-right:auto;margin-left:auto">	 	
	 	<!-- Iteration on every element in the User Story -->
	 	<c:forEach items="${userstory.dataElements}" var="dataelement" varStatus="status">
			
			<c:set var="dataelement" scope="request" value="${dataelement}"/>
			
			<c:if test="${dataelement.included == true}">
			
				<%-- ABS Data Element --%>
 				<c:if test="${dataelement.class.simpleName == 'DataElementAbs'}">
 					<jsp:include page="dataElementAbs.jsp" />
 				</c:if>
 				
 				<%-- BITRE Data Element --%>
 				<c:if test="${dataelement.class.simpleName == 'DataElementBitre'}">
 					<jsp:include page="dataElementBitre.jsp" />
 				</c:if>
			
				<%-- Past Data Element --%>
 				<c:if test="${dataelement.class.simpleName == 'DataElementPast'}">
 					<jsp:include page="dataElementPast.jsp" />
 				</c:if>
 				
 				<%-- Acorn-Sat Data Element --%>
 				<c:if test="${dataelement.class.simpleName == 'DataElementAcornSat'}">
 					<jsp:include page="dataElementAcornSat.jsp" />
 				</c:if>
				
				<%-- CSIRO Data Element --%>
 				<c:if test="${dataelement.class.simpleName == 'DataElementCsiro'}">
 					<jsp:include page="dataElementCsiro.jsp" />
 				</c:if>
 				
 				<%-- CMAR Data Element --%>
 				<c:if test="${dataelement.class.simpleName == 'DataElementCmar'}">
 					<jsp:include page="dataElementCmar.jsp" />
 				</c:if>
 				
 				<%-- Engineering Model Data Element --%>
 				<c:if test="${dataelement.class.simpleName == 'DataElementEngineeringModel'}">
 					<jsp:include page="dataElementEngineeringModel.jsp" />
 				</c:if>
 				
 				<%-- Vulnerability Data Element --%>
 				<c:if test="${dataelement.class.simpleName == 'DataElementVulnerability'}">
 					<jsp:include page="dataElementVulnerability.jsp" />
 				</c:if>
 				
 				<%-- File Data Element, display a picture if JPEG, textarea with content otherwise --%>
				<c:if test="${dataelement.class.simpleName == 'DataElementFile'}">
 					<jsp:include page="dataElementFile.jsp" />
				</c:if>
				
				<br /><br /><br /><br />
		</c:if>
		</c:forEach>
	</div>
	</c:if>
	</c:if>
	<div class="clearfix"></div><br />
</div>