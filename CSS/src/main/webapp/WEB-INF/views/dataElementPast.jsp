<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ page import="war.model.DataElementCsiro" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:choose>
	<c:when test="${not empty dataelement.pastDataList}">
		<c:forEach items="${dataelement.pastDataList}" var="pastData" varStatus="dataLoopStatus">
			<h6>${pastData.title} (<fmt:formatDate value="${pastData.periodStart}" pattern="yyyy" />-<fmt:formatDate value="${pastData.periodEnd}" pattern="yyyy" />)</h6>
			
						
			<ul class="prettygallery clearfix">
				<li>
					<img name="${pastData.title}" src="<c:url value="/resources/img/data/bom/${pastData.picture}" />" />
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