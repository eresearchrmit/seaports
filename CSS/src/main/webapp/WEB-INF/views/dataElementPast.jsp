<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ page import="war.model.DataElementCsiro" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:choose>
	<c:when test="${not empty dataelement.pastDataList}">
		<c:forEach items="${dataelement.pastDataList}" var="pastData" varStatus="dataLoopStatus">
			
			<fmt:formatDate value="${pastData.periodStart}" pattern="yyyy" var="formattedPeriodStart" />
			<fmt:formatDate value="${pastData.periodEnd}" pattern="yyyy" var="formattedPeriodEnd" />
			
			<h6>${pastData.title} (${formattedPeriodStart}-${formattedPeriodEnd})</h6>
			
			
			<ul class="prettygallery clearfix">
				<li>
					<c:set var="pictureName" value="/resources/img/data/bom/${pastData.picture}-${fn:replace(userstory.seaport.region.name, ' ', '-')}-${formattedPeriodStart}-${formattedPeriodEnd}.png" />
					<c:set var="formattedPictureName" value="${fn:toLowerCase(pictureName)}" />
					<img name="${pastData.title}" src="<c:url value="${formattedPictureName}" />" class="illustrationPicture" />
		    	</li>
			</ul>
			
			
			<i class="credits">Data extracted from <a href="${pastData.sourceUrl}" target="_blank">${pastData.sourceUrl}</a> on <fmt:formatDate value="${pastData.creationDate}" pattern="dd MMM yyyy" />.</i>	
		</c:forEach>
	</c:when>
	<c:otherwise>
		<div id="warningMessage" class="message warning">
		<h5>No Data</h5>
		<p>No data corresponding to the selected settings.</p>
	</div>
	</c:otherwise>
</c:choose>