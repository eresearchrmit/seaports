<%@ page session="true"%>
<%@ page language="java" %>
<%@ page import="java.lang.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.codec.binary.*"%>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ page import="org.apache.commons.lang.*" %>
<%@ page language="java" import="war.model.WorkBoard" %>
<%@ page language="java" import="war.model.Person" %>
<%@ page language="java" import="war.model.Files" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%--      	
       <%     
     		 String filename, fileid, filecontent;
     	%> 
 --%>
<%-- <c:out value="${stringfiles}"></c:out> --%>

<div class="grid_8">
	<c:if test="${not empty controllerMessageSuccess}">
		<div class="message success">
			<h5>Success !</h5>
			<p>${controllerMessageSuccess}.</p>
		</div>
	</c:if>
	<c:if test="${not empty controllerMessageError}">
		<div class="message error">
			<h5>Error</h5>
			<p>${controllerMessageError}.</p>
		</div>
	</c:if>

	<form:form method="post" action="/CSS/spring/workboard/update?workboardid=${workboard.workBoardID}" modelAttribute="stringfiles">
		
	  	<c:if test="${not empty dataelements}">		
		 	<c:forEach items="${dataelements}" var="dataelement" varStatus="status"> 
		 	
		 		<c:set var="stringcontent">${dataelement.filecontent}</c:set>
		 		
		 			<div class="box round">
						<div class="box-header">
							<h5 class="floatleft">${dataelement.filename}<c:if test="${dataelement.type != 'data'}">.${dataelement.type}</c:if></h5>
							<button type="button" class="btn btn-icon btn-blue btn-small btn-cross floatright" onclick="location.href='/CSS/spring/workboard/deletefile?dataelementid=${dataelement.fileid}'" >
								<span></span>Delete
							</button>
							<div class="clear"></div>
						</div>
						<input name="dataelements[${status.index}].fileid" value= ${dataelement.fileid} type="hidden">
						<input name="dataelements[${status.index}].filename" value= ${dataelement.filename} type="hidden">
						
						<c:choose>
							<c:when test="${dataelement.type == 'jpg'}">
								<ul class="prettygallery clearfix">
		                        	<li>
		                        		<a href="data:image/jpeg;charset=utf-8;base64,${dataelement.filecontent}" rel="prettyPhoto[gallery2]" title="">
		                            		<img name="dataelements[${status.index}].filecontent" src="data:image/jpeg;charset=utf-8;base64,${dataelement.filecontent}" class="dataElementThumb" />
		                            	</a>
		                            </li>
								</ul>		
		                 	</c:when>
						
					  		<c:when test="${dataelement.type == 'data'}">
								${dataelement.filecontent}
		                 	</c:when>
		                 		
		                 	<c:otherwise>
								<textarea name="dataelements[${status.index}].filecontent" rows="12">${dataelement.filecontent}</textarea>
							</c:otherwise>
						</c:choose>
					</div>
			</c:forEach>
			<button class="floatright btn btn-icon btn-check btn-blue btn-big" ><span></span>Save Workboard</button>
		</c:if>
	</form:form>
</div>