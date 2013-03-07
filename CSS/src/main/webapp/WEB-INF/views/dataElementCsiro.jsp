<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ page import="war.model.DataElementCsiro" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:choose>
	<c:when test="${not empty dataelement.csiroDataList}">
		<p style="margin-top:10px; text-align:center; font-weight"><b>${dataelement.csiroDataList[0].parameters.modelName} model (${dataelement.csiroDataList[0].parameters.model.name}) in the region ${dataelement.csiroDataList[0].parameters.region.name} for ${dataelement.csiroDataList[0].parameters.emissionScenario.description} (${dataelement.csiroDataList[0].parameters.emissionScenario.name})</b></p>
		
		<table class="data display datatable" id="example">
		<thead>
			<tr>
				<th>Variable</th>
				<th>Base value</th>
				<th>Variation in ${dataelement.csiroDataList[0].year}</th>
				<th>Picture</th>
			</tr>
		</thead>
			<tbody>
				<c:forEach items="${dataelement.csiroDataList}" var="csiroData" varStatus="dataLoopStatus">
					<tr class="${dataLoopStatus.index % 2 == 0 ? 'even' : 'odd'}">
						<td class="center">${csiroData.variable.name}</td>
						<td class="center">${csiroData.baseline.value} ${csiroData.baseline.variable.uom}</td>
						<td class="center"><c:if test="${csiroData.value > 0}">+</c:if>${csiroData.value} ${csiroData.variable.uomVariation}</td>
						<td class="center">
							<c:choose>
							<c:when test="${not empty csiroData.stringPicture}">
								<img name="${csiroData.variable.name}" src="data:image/png;charset=utf-8;base64,${csiroData.stringPicture}" />
							</c:when>
							<c:otherwise>
								No picture available
							</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<i>Data provided by CSIRO on <fmt:formatDate value="${dataelement.csiroDataList[0].creationDate}" pattern="dd MMM yyyy" /> was the best available to date</i>
	</c:when>
	<c:otherwise>
		<div id="warningMessage" class="message warning">
		<h5>No Data</h5>
		<p>No data corresponding to the selected settings.</p>
	</div>
	</c:otherwise>
</c:choose>