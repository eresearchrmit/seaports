<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ page import="war.model.DataElementCsiro" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:choose>
	<c:when test="${not empty dataelement.csiroDataList}">
		<p style="margin-top:10px; text-align:center; font-weight"><b>${dataelement.csiroDataList[0].parameters.modelName} model (${dataelement.csiroDataList[0].parameters.model.name}) in the region ${dataelement.csiroDataList[0].parameters.region.name} for ${dataelement.csiroDataList[0].parameters.emissionScenario.description} (${dataelement.csiroDataList[0].parameters.emissionScenario.name}) emissions scenario.</b></p>
		<c:choose>
			<c:when test="${dataelement.displayType == 'TABLE'}">
				<table class="data display datatable">
					<thead>
						<tr>
							<th>Variable</th>
							<th>Baseline</th>
							<th>Change for the ${dataelement.csiroDataList[0].year}s</th>
							<%--<c:if test="${dataelement.picturesIncluded}"><th class="center">Map</th></c:if> --%>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${dataelement.csiroDataList}" var="csiroData" varStatus="dataLoopStatus">
							<tr class="${dataLoopStatus.index % 2 == 0 ? 'even' : 'odd'}">
								<td class="top head">${csiroData.variable.name}</td>
								<td class="top">${csiroData.baseline.value} ${csiroData.baseline.variable.uom}</td>
								<td class="top"><c:if test="${csiroData.value > 0}">+</c:if>${csiroData.value} ${csiroData.variable.uomVariation}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<i class="credits">Data provided by CSIRO on <fmt:formatDate value="${dataelement.csiroDataList[0].creationDate}" pattern="dd MMM yyyy" /> was the best available to date</i>
			</c:when>
			<c:when test="${dataelement.displayType == 'PICTURE'}">
				<table>
				<tr>
				<c:forEach items="${dataelement.csiroDataList}" var="csiroData" varStatus="dataLoopStatus">
					<td>
						<center><strong>${csiroData.variable.name}</strong></center><br />
						<c:choose>
							<c:when test="${not empty csiroData.picture}">
								<img style="width:100%" name="${csiroData.variable.name}" src="<c:url value="/resources/img/data/csiro/${csiroData.picture}" />" class="dataelementIllustrationPicture csiroIllustrationPicture" />
							</c:when>
							<c:otherwise>
								<center>No map available</center>
							</c:otherwise>
						</c:choose>
					</td>
				</c:forEach>
				</tr>
				</table>
			</c:when>
		</c:choose>
	</c:when>
	<c:otherwise>
		<div id="warningMessage" class="message warning">
		<h5>No Data</h5>
		<p>No data corresponding to the selected settings.</p>
	</div>
	</c:otherwise>
</c:choose>