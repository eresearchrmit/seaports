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
	<c:if test="${not empty errorMessage}">
		<div class="message error">
			<h5>Error</h5>
			<p>${errorMessage}.</p>
		</div>
	</c:if>

	<form:form method="post" action="/CSS/spring/workboard/save?id=${userstory.id}" modelAttribute="userstory">
		
	  	<c:if test="${not empty dataelements}">
		 	<c:forEach items="${dataelements}" var="dataelement" varStatus="status"> 		 		
		 			<div class="box round">
						<div class="box-header">
							<h5 class="floatleft">${dataelement.name}<c:if test="${dataelement.type != 'data'}">.${dataelement.type}</c:if></h5>
							<button type="button" class="btn btn-icon btn-blue btn-small btn-cross floatright" onclick="location.href='/CSS/spring/workboard/deletedataelement?dataelementid=${dataelement.id}'" >
								<span></span>Delete
							</button>
							<div class="clear"></div>
						</div>
						<input name="dataElements[${status.index}].fileid" value="${dataelement.id}" type="hidden">
						<input name="dataElements[${status.index}].filename" value="${dataelement.name}" type="hidden">
						
						<c:choose>
							<c:when test="${dataelement.type == 'jpg'}">
								<ul class="prettygallery clearfix">
		                        	<li>
		                        		<a href="data:image/jpeg;charset=utf-8;base64,${dataelement.stringContent}" rel="prettyPhoto[gallery2]" title="">
		                            		<img name="dataelement.name" src="data:image/jpeg;charset=utf-8;base64,${dataelement.stringContent}" class="dataElementThumb" />
		                            	</a>
		                            </li>
								</ul>		
		                 	</c:when>
						
					  		<c:when test="${dataelement.type == 'data'}">
								${dataelement.stringContent}
		                 	</c:when>
		                 		
		                 	<c:otherwise>
								<textarea name="dataelements[${status.index}].name" rows="12">${dataelement.stringContent}</textarea>
							</c:otherwise>
						</c:choose>
					</div>
			</c:forEach>
			<!-- <button class="floatright btn btn-icon btn-check btn-blue btn-big" ><span></span>Save Workboard</button> -->
		</c:if>
	</form:form>
</div>