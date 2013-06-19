<%--
 Copyright (c) 2013, RMIT University, Australia.
 All rights reserved.
  
 This code is under the BSD license. See 'license.txt' for details.
 Project hosted at: https://code.google.com/p/climate-smart-seaports/
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ page import="war.model.DataElementCsiro" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<c:choose>
	<c:when test="${not empty dataelement.csiroDataList}">
		<c:set var="csiroData" value="${dataelement.csiroDataList[0]}" />
		<c:choose>
			<c:when test="${dataelement.displayType == 'TABLE'}">
				<p style="margin-top:10px; text-align:center; font-weight"><b>Average ${csiroData.variable.name} for the ${csiroData.parameters.region.name} NRM region under a ${csiroData.parameters.emissionScenario.description} (${csiroData.parameters.emissionScenario.name}) emissions scenario.</b></p>
				
				<table class="data display datatable">
					<thead>
						<tr>
							<th>Climate Model</th>
							<th>Baseline</th>
							<th>Change centred around ${dataelement.csiroDataList[0].year}</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${dataelement.csiroDataList}" var="csiroData" varStatus="dataLoopStatus">
							<tr class="${dataLoopStatus.index % 2 == 0 ? 'even' : 'odd'}">
								<td class="top head">${csiroData.parameters.modelName} (${csiroData.parameters.model.name})</td>
								<td class="top">${csiroData.baseline.value} ${csiroData.baseline.variable.uom}</td>
								<td class="top"><c:if test="${csiroData.value > 0}">+</c:if>${csiroData.value} ${csiroData.variable.uomVariation}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<i class="credits">Data provided by CSIRO on <fmt:formatDate value="${dataelement.csiroDataList[0].creationDate}" pattern="dd MMM yyyy" /> was the best available to date</i>
			</c:when>
			<c:when test="${dataelement.displayType == 'PICTURE'}">
					<c:set var="pictureName" value="/resources/img/data/csiro/${fn:replace(csiroData.variable.name, ' ', '-')}-${fn:replace(csiroData.parameters.emissionScenario.name, ' ', '-')}-${fn:replace(userstory.seaport.region.name, ' ', '-')}-${csiroData.year}.png" />
					<c:set var="formattedPictureName" value="${fn:toLowerCase(pictureName)}" />
					<img name="${csiroData.variable.name}" src="<c:url value="${formattedPictureName}" />" class="dataelementIllustrationPicture" style="width: 100%" />
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