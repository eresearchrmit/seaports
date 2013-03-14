<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:if test="${not empty dataelements}">
 	<c:forEach items="${dataelements}" var="dataelement" varStatus="status">
 			<c:if test="${(dataelementsfilter == 'Future' && (dataelement.class.simpleName == 'DataElementCsiro' || dataelement.class.simpleName == 'DataElementCmar')) || (dataelementsfilter == 'Applications' && dataelement.class.simpleName == 'DataElementEngineeringModel') || (dataelementsfilter == 'NonClimate' && dataelement.class.simpleName == 'DataElementFile') || (dataelementsfilter == 'All')}">
	 			<c:set var="dataelement" scope="request" value="${dataelement}"/>
	 			<c:set var="dataElementLoopIndex" scope="request" value="${status.index}"/>
	 			
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
	 				
	 				<!-- CMAR Data Element -->
	 				<c:if test="${dataelement.class.simpleName == 'DataElementCmar'}">
	 					<jsp:include page="dataElementCmar.jsp" />
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
			</c:if>
	</c:forEach>
</c:if>