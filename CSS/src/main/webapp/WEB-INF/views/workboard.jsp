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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="grid_8">
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
	<c:if test="${not empty warningMessage}">
		<div class="message warning">
			<h5>Warning</h5>
			<p>${warningMessage}.</p>
		</div>
	</c:if>
	<c:if test="${not empty errorMessage}">
		<div class="message error">
			<h5>Error</h5>
			<p>${errorMessage}.</p>
		</div>
	</c:if>

	<form:form method="post" action="/CSS/auth/workboard/save?id=${userstory.id}" modelAttribute="userstory">
	  	<c:if test="${not empty dataelements}">
		 	<c:forEach items="${dataelements}" var="dataelement" varStatus="status">
		 			
		 			<c:set var="dataelement" scope="request" value="${dataelement}"/>
		 			
		 			<div class="box round">
						<div class="box-header">
						<h5 class="floatleft">${dataelement.name}<c:if test="${dataelement.class.simpleName == 'DataElementFile'}">.${dataelement.filetype}</c:if></h5>
							<a class="lnkDeleteDataElement" href="/CSS/auth/workboard/deletedataelement?dataelementid=${dataelement.id}">
							<button type="button" class="btn btn-icon btn-blue btn-small btn-cross floatright" >
								<span></span>Delete
							</button>
							</a>
							<div class="clear"></div>
						</div>
						<input name="dataElements[${status.index}].fileid" value="${dataelement.id}" type="hidden">
						<input name="dataElements[${status.index}].filename" value="${dataelement.name}" type="hidden">
						
						<!-- CSIRO Data Element -->
		 				<c:if test="${dataelement.class.simpleName == 'DataElementCsiro'}">
		 					<jsp:include page="dataElementCsiro.jsp" />
		 				</c:if>
		 				
		 				<!-- Engineering Model Data Element -->
		 				<c:if test="${dataelement.class.simpleName == 'DataElementEngineeringModel'}">
		 					<jsp:include page="dataElementEngineeringModel.jsp" />
		 				</c:if>
		 				
		 				<!-- File Data Element, display a picture if JPEG, textarea with content otherwise -->
						<c:if test="${dataelement.class.simpleName == 'DataElementFile'}">
		 					<jsp:include page="dataElementFile.jsp" />
						</c:if>
					</div>
			</c:forEach>
			<!-- <button class="floatright btn btn-icon btn-check btn-blue btn-big" ><span></span>Save Workboard</button> -->
			<div id="confirmDataElementDeletionModalWindow" title="Delete this data element ?">
				<p>Are you sure you want to delete this data element from your Workboard ?</p> 
			</div>
			<script type="text/javascript">
				setupConfirmBox("confirmDataElementDeletionModalWindow", "lnkDeleteDataElement");
			</script>
		</c:if>
	</form:form>
</div>