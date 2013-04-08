<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ page import="war.model.DataElementCsiro" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:choose>
	<c:when test="${not empty dataelement.acornSatDataList}">
		<table class="data display datatable" id="example">
		<thead>
			<tr>
				<th>Station name</th>
				<th>Number</th>
				<th>Latitude</th>
				<th>Longitude</th>
				<th>Period</th>
				<c:if test="${dataelement.acornSatDataList[0].extreme == true}">
				<th>Highest temperature</th>
				<th>Highest daily rainfall</th>
				<th>Maximum wind gust</th>
				</c:if>
				<c:if test="${dataelement.acornSatDataList[0].extreme == false}">
				<th>Annual mean surface temperature (&#8451;)</th>
				<th>Annual mean rainfall (mm)</th>
				<th colspan="2">Relative humidity</th>
				<th colspan="2">Mean wind speed</th>
				</c:if>
			</tr>
			</thead>
			<tbody>
				<c:forEach items="${dataelement.acornSatDataList}" var="acornSatData" varStatus="dataLoopStatus">
					
					<c:if test="${prevData.acornSatStation.name != acornSatData.acornSatStation.name}">
					</tr>
					<tr class="${dataLoopStatus.index % 2 == 0 ? 'even' : 'odd'}">
						<td>${acornSatData.acornSatStation.name}</td>
						<td>${acornSatData.acornSatStation.number}</td>
						<td>${acornSatData.acornSatStation.latitude}</td>
						<td>${acornSatData.acornSatStation.longitude}</td>
						<td><fmt:formatDate value="${acornSatData.periodStart}" pattern="yyyy" />-<fmt:formatDate value="${acornSatData.periodEnd}" pattern="yyyy" /></td>
					</c:if>

					<td>
						${acornSatData.value}<br />
						<c:if test="${acornSatData.extreme == true}">
							<fmt:formatDate value="${acornSatData.dateMeasured}" pattern="dd MMM yyyy" />
						</c:if>
						<c:if test="${acornSatData.extreme == false && not empty acornSatData.dateMeasured}">
							at <fmt:formatDate value="${acornSatData.dateMeasured}" pattern="hha" />
						</c:if>
					</td>	
					<c:set var="prevData" value="${acornSatData}" />
				</c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
		<div id="warningMessage" class="message warning">
		<h5>No Data</h5>
		<p>No data corresponding to the selected settings.</p>
	</div>
	</c:otherwise>
</c:choose>