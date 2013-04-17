<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ page import="war.model.DataElementCsiro" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:choose>
	<c:when test="${not empty dataelement.acornSatDataList}">
		<h6>
		<c:if test="${dataelement.acornSatDataList[0].extreme == true}">
			Extreme measurements by Acorn-Sat stations in the ${userstory.region.name} region within the period <fmt:formatDate value="${dataelement.acornSatDataList[0].periodStart}" pattern="yyyy" />-<fmt:formatDate value="${dataelement.acornSatDataList[0].periodEnd}" pattern="yyyy" />
		</c:if>
		<c:if test="${dataelement.acornSatDataList[0].extreme == false}">
			Annual mean measurements by Acorn-Sat stations in ${userstory.region.name} within the period <fmt:formatDate value="${dataelement.acornSatDataList[0].periodStart}" pattern="yyyy" />-<fmt:formatDate value="${dataelement.acornSatDataList[0].periodEnd}" pattern="yyyy" />
		</c:if>
		</h6>
		<br />
		<table class="data display datatable" id="example">
		<thead>
			<tr>
				<th>Station name</th>
				<th>Number</th>
				<th>Latitude</th>
				<th>Longitude</th>
				<c:if test="${dataelement.acornSatDataList[0].extreme == true}">
				<th>Highest temperature</th>
				<th>Highest daily rainfall</th>
				<th>Maximum wind gust</th>
				</c:if>
				<c:if test="${dataelement.acornSatDataList[0].extreme == false}">
				<th>Annual mean surface <br />temperature</th>
				<th>Annual mean <br />rainfall</th>
				<th colspan="2">Annual mean <br />Relative humidity</th>
				<th colspan="2">Annual Mean <br />wind speed</th>
				</c:if>
			</tr>
			</thead>
			<tbody>
				<c:if test="${dataelement.acornSatDataList[0].extreme == false}">
				<tr>
				<td colspan="6"></td>
					<td><b>9am</b></td>
					<td><b>3pm</b></td>
					<td><b>9am</b></td>
					<td><b>3pm</b></td>
				</tr>
				</c:if>
				<c:forEach items="${dataelement.acornSatDataList}" var="acornSatData" varStatus="dataLoopStatus">
					<c:if test="${prevData.acornSatStation.name != acornSatData.acornSatStation.name}">
					<td></td>
					</tr>
					<tr class="${dataLoopStatus.index % 2 == 0 ? 'even' : 'odd'}">
						<td>${acornSatData.acornSatStation.name}</td>
						<td><fmt:formatNumber minIntegerDigits="6" groupingUsed="false" value="${acornSatData.acornSatStation.number}" /></td>
						<td>${acornSatData.acornSatStation.latitude}</td>
						<td>${acornSatData.acornSatStation.longitude}</td>
					</c:if>

					<td>
						<c:out value="${acornSatData.value}" /> ${acornSatData.variable.uom}<br />
						<c:if test="${acornSatData.extreme == true}">
							<fmt:formatDate value="${acornSatData.dateMeasured}" pattern="dd MMM yyyy" />
						</c:if>
						<%--
						<c:if test="${acornSatData.extreme == false && not empty acornSatData.dateMeasured}">
							at <fmt:formatDate value="${acornSatData.dateMeasured}" pattern="hha" />
						</c:if>
						--%>
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