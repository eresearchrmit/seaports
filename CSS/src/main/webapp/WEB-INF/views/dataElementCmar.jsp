<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ page import="war.model.DataElementCmar" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:choose>
	<c:when test="${not empty dataelement.cmarDataList}">
		<p style="margin-top:10px; text-align:center; font-weight"><b>Data shown for the ${dataelement.cmarDataList[0].parameters.modelName} model (${dataelement.cmarDataList[0].parameters.model.name}) in the ${dataelement.cmarDataList[0].parameters.region.name} region under a ${dataelement.cmarDataList[0].parameters.emissionScenario.description} (${dataelement.cmarDataList[0].parameters.emissionScenario.name}) emissions scenario.</b></p>
		
		<table class="data display datatable" id="example">
		<thead>
			<tr>
				<th>Variable</th>
				<th>Latitude</th>
				<th>Longitude</th>
				<th>Change centred around ${dataelement.cmarDataList[0].year}</th>
				<c:if test="${dataelement.picturesIncluded}"><th class="center">Map</th></c:if>
			</tr>
		</thead>
			<tbody>
				<c:forEach items="${dataelement.cmarDataList}" var="cmarData" varStatus="dataLoopStatus">
					<c:forEach items="${cmarData.values}" var="entry" varStatus="entryLoopStatus">
					<tr class="${entryLoopStatus.index % 2 == 0 ? 'even' : 'odd'}">
						<c:if test="${entryLoopStatus.index == 0}">
							<td rowspan="${fn:length(cmarData.values)}" class="top head">${cmarData.variable.name}</td>
						</c:if>
						<td class="top">
							${entry.key.x}
						</td>
						<td class="top">
							${entry.key.y}
						</td>
						<td class="top">
							${entry.value} ${cmarData.variable.uom}
						</td>
						<c:if test="${dataelement.picturesIncluded && entryLoopStatus.index == 0}">
							<td rowspan="${fn:length(cmarData.values)}" class="top center">
								<c:choose>
								<c:when test="${not empty cmarData.picture}">
									<img name="${cmarData.variable.name}" src="<c:url value="/resources/img/data/cmar/${cmarData.picture}" />" class="dataelementIllustrationPicture cmarIllustrationPicture" />
								</c:when>
								<c:otherwise>
									No picture available
								</c:otherwise>
								</c:choose>
							</td>
						</c:if>
					</tr>
					</c:forEach>
				</c:forEach>
			</tbody>
		</table>
		<i>Data provided by CSIRO Marine and Atmospheric Research on <fmt:formatDate value="${dataelement.cmarDataList[0].creationDate}" pattern="dd MMM yyyy" /> was the best available to date</i>
	</c:when>
	<c:otherwise>
		<div id="warningMessage" class="message warning">
		<h5>No Data</h5>
		<p>No data corresponding to the selected settings.</p>
	</div>
	</c:otherwise>
</c:choose>