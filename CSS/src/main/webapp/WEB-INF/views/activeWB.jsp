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
	<form:form method="post" action="/CSS/spring/createwb/update?workboardid=${workboard.workBoardID}" modelAttribute="stringfiles">
		
	  	<c:if test="${not empty stringfiles}">		
		 	<c:forEach items="${stringfiles.files}" var="file" varStatus="status"> 
		 	
		 		<c:set var="stringcontent">${file.filecontent}</c:set>
		 		
		 			<div class="box round">
						<div class="box-header">
							<h5 class="floatleft">${file.filename}<c:if test="${file.type != 'data'}">.${file.type}</c:if></h5>
							<button type="button" class="btn btn-icon btn-blue btn-small btn-cross floatright" onclick="location.href='/CSS/spring/createwb/deletefile?dataelementid=${file.fileid}'" >
								<span></span>Delete
							</button>
							<div class="clear"></div>
						</div>
						<input name="files[${status.index}].fileid" value= ${file.fileid} type="hidden">
						<input name="files[${status.index}].filename" value= ${file.filename} type="hidden">
						
						<c:choose>
							<c:when test="${file.type == 'jpg'}">
								<%--  <%  
			     					String s = (String) pageContext.getAttribute("stringcontent") ;
			                 	    byte [] imgbyte = s.getBytes() ; 
									/*Byte [] imgByte = (Byte []) pageContext.getAttribute("stringcontent") ;
			                 	    byte [] imgbyte = ArrayUtils.toPrimitive(imgByte) ;  */   
			                 	    String img = Base64.encodeBase64URLSafeString(imgbyte) ; 
									// out.println(imgByte) ;
									// out.println(imgbyte) ; */
									// out.println(s) ;
			                 	    out.println("This is the test") ;
								%> --%>
			                 	<!-- byte Array - convert that into Base64 String otherwise regular String -   -->
								<%--   <img name="files[${status.index}].filecontent" src="data:image/jpg;base64,<%=img %>" />  --%>
								<%-- <input name="files[${status.index}].filecontent" src="data:image/jpeg;charset=utf-8;base64,${file.filecontent}" type="hidden"> --%>
								<img name="files[${status.index}].filecontent" src="data:image/jpeg;charset=utf-8;base64,${file.filecontent}"  />
		                 	</c:when>
						
					  		<c:when test="${file.type == 'data'}">
								${file.filecontent}
		                 	</c:when>
		                 		
		                 	<c:otherwise>
								<textarea name="files[${status.index}].filecontent" rows="12"> 
		                   			${file.filecontent}		
		                   		</textarea>
							</c:otherwise> 
						</c:choose>
					</div>
			</c:forEach>
			<button class="floatright btn btn-icon btn-check btn-blue btn-big" ><span></span>Save Workboard</button>
		</c:if>
	</form:form>
</div>