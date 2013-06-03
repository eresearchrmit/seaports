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
	<%-- Picture common for an NRM region --%>
	<c:when test="${dataelement.displayType == 'PICTURE'}">
		<c:set var="pictureName" value="${fn:replace(dataelement.absDataList[0].variable.name, ' ', '-')}-${fn:replace(userstory.seaport.region.name, ' ', '-')}"/>
		<c:set var="formatedPictureName" value="${fn:toLowerCase(pictureName)}" />
		<img name="${dataelement.absDataList[0].seaport.urbanCenter}" src="<c:url value="/resources/img/data/abs/${formatedPictureName}.png" />" class="illustrationPicture" />
	</c:when>
	
	<c:when test="${not empty dataelement.absDataList}">
		<c:choose>
			<c:when test="${dataelement.displayType == 'GRAPH'}">
				<script type="text/javascript">
					$(function () {
						setTimeout(function(){
						var colors = Highcharts.getOptions().colors;
						
				        $('#${dataelementsfilter}-abs-LineAndBarGraph${dataElementLoopIndex}').highcharts({
				            chart: {
				                spacingRight: 20
				            },
				            title: {
				                text: '${dataelement.absDataList[0].seaport.urbanCenter} - Annual Population Change in Urban Areas'
				            },
				            xAxis: {
				            	categories: ['2001', '2002', '2003', '2004', '2005', '2006',
				                             '2007', '2008', '2009', '2010', '2011'],
		                        labels: {
									rotation: -45,
									align: 'right',
		                        }
				            },
				            yAxis: [{ // Primary yAxis
				                labels: {
				                    format: '{value}'
				                },
				                title: {
				                    text: 'Total ${dataelement.absDataList[0].variable.name}'
				                }
				            }, { // Secondary yAxis
				                title: {
				                    text: 'Annual % of ${dataelement.absDataList[0].variable.name} change'
				                },
				                labels: {
				                    format: '{value} ${dataelement.absDataList[0].variable.uomVariation}',
				                },
				                opposite: true
				            }],
				            tooltip: {
				                shared: true
				            },
				            
				            series: [{
				                	name: 'Annual % of ${dataelement.absDataList[0].variable.name} change',
				                	type: 'column',
				                	yAxis: 1,
				                    data: [
										<c:forEach var="i" begin="2001" end="2011" step="1" varStatus="yearLoop">
										<c:if test="${yearLoop.first}">0</c:if>
										<c:if test="${!yearLoop.first}"><c:out value="${(dataelement.absDataList[0].values[i] - previousValue) / previousValue * 100}" /></c:if>
										<c:if test="${!yearLoop.last}">,</c:if>
										<c:set var="previousValue" value="${dataelement.absDataList[0].values[i]}" />
										</c:forEach>],
									valueSuffix: '${dataelement.absDataList[0].variable.uomVariation}',
									color: colors[3]
								}, 
								{
				                	name: 'Total ${dataelement.absDataList[0].variable.name}',
				                    data: [
										<c:forEach var="i" begin="2001" end="2011" step="1" varStatus="yearLoop">
											${dataelement.absDataList[0].values[i]}<c:if test="${!yearLoop.last}">,</c:if>
										</c:forEach>],
				                    valueSuffix: '${dataelement.absDataList[0].variable.uom}'
								}]
				        });
						}, 0);
					});
				</script>
				<div id="${dataelementsfilter}-abs-LineAndBarGraph${dataElementLoopIndex}" class="highcharts ${dataelementsfilter}-abs" style="width:95%; margin-bottom:30px">
				</div>
			</c:when>
			<c:when test="${dataelement.displayType == 'TABLE'}">
				<h4>${dataelement.absDataList[0].variable.name} - ${dataelement.absDataList[0].seaport.urbanCenter}</h4>
				<br />
				<table class="data display datatable">
					<thead>
						<tr>
							<th></th>
							<c:forEach var="i" begin="2001" end="2011" step="1" varStatus="yearLoop">
								<th>${i}</th>
							</c:forEach>
							<th>Growth (%)</th>
							<th>Total Growth (people)</th>
							<th>Average Growth (%)</th>
						</tr>
					</thead>
					<tbody>
						<tr class="even">
							<td></td>
							<c:forEach var="i" begin="2001" end="2011" step="1" varStatus="yearLoop">
								<td>
									<fmt:formatNumber type="number" value="${dataelement.absDataList[0].values[i]}" />
									<c:if test="${yearLoop.first}"><c:set var="firstValue" value="${dataelement.absDataList[0].values[i]}" /></c:if>
									<c:if test="${yearLoop.last}">
									<c:set var="lastValue" value="${dataelement.absDataList[0].values[i]}" />
									<c:set var="totalYears" value="${yearLoop.count}" />
									</c:if>
								</td>
							</c:forEach>
							<td><b><fmt:formatNumber type="number" value="${(lastValue - firstValue) / firstValue * 100}" /></b></td>
							<td><b><fmt:formatNumber type="number" value="${lastValue - firstValue}" /></b></td>
							<td><b><fmt:formatNumber type="number" value="${((lastValue - firstValue) / firstValue * 100) / totalYears}" /></b></td>
						</tr>
				</tbody>
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