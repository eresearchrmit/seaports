<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ page import="war.model.DataElementEngineeringModel" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:if test="${dataelement.displayType == 'GRAPH'}">
	<script type="text/javascript">
		$(function () {
			setTimeout(function(){
				
	        $('#${dataelementsfilter}-engineeringmodel-LineGraph${dataElementLoopIndex}').highcharts({
	            chart: {
	                zoomType: 'x',
	                spacingRight: 20
	            },
	            title: {
	                text: '<c:out value="${dataelement.engineeringModelDataList[0].variable.name} over Time" />'
	            },
	            subtitle: {
	                text: document.ontouchstart === undefined ?
	                    'Click and drag in the plot area to zoom in' :
	                    'Drag your finger over the plot to zoom in'
	            },
	            xAxis: {
	                type: 'datetime',
	                maxZoom: 5 * 365 * 24 * 3600 * 1000, // 5 years
	                title: {
	                    text: null
	                }
	            },
	            yAxis: {
	                title: {
	                	text: "${dataelement.engineeringModelDataList[0].variable.shortName} (${dataelement.engineeringModelDataList[0].variable.uom})"
	                }
	            },
	            tooltip: {
	                shared: true,
	                valueSuffix: "${dataelement.engineeringModelDataList[0].variable.uom}"
	            },
	            legend: {
	                enabled: false
	            },
	            plotOptions: {
	                area: {
	                    fillColor: {
	                        linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
	                        stops: [
	                            [0, Highcharts.getOptions().colors[0]],
	                            [1, Highcharts.Color(Highcharts.getOptions().colors[0]).setOpacity(0).get('rgba')]
	                        ]
	                    },
	                    lineWidth: 1,
	                    marker: {
	                        enabled: false
	                    },
	                    shadow: false,
	                    states: {
	                        hover: {
	                            lineWidth: 1
	                        }
	                    },
	                    threshold: null
	                }
	            },
	            
	            series: [
	                <c:forEach items="${dataelement.engineeringModelDataList}" var="engModelData" varStatus="loop">{
	                	name: "<c:out value="${engModelData.parameters.emissionScenario.name} ${engModelData.parameters.model.name}" />",
	                	pointInterval: 365 * 24 * 3600 * 1000, // 1 year
	                    pointStart: Date.UTC(2000, 0, 01),
	                    data: [
							<c:forEach var="i" begin="2000" end="2070" step="1" varStatus="yearLoop">
								${engModelData.values[i]}<c:if test="${!yearLoop.last}">,</c:if>
							</c:forEach>]
					}<c:if test="${!loop.last}">,</c:if>
	            </c:forEach>]
	        });
			},3);
	    });
	</script>
	<div id="${dataelementsfilter}-engineeringmodel-LineGraph${dataElementLoopIndex}" class="highcharts" style="width:95%; margin-bottom:30px">
	</div>
</c:if>

<center>
	<h6>ASSET: ${dataelement.engineeringModelDataList[0].asset.assetCode} (${dataelement.engineeringModelDataList[0].asset.description})</h6> 
	<p>Built in ${dataelement.engineeringModelDataList[0].asset.yearBuilt}.</p>
</center>

<table class="data display datatable">
	<tbody>
		<tr>
			<th>Zone</th>
			<td>${dataelement.engineeringModelDataList[0].asset.zone}</td>

			<th>Distance from coast</th>
			<td>${dataelement.engineeringModelDataList[0].asset.distanceFromCoast} km</td>
		</tr>
		<tr>
			<th>Exposure class</th>
			<td>${dataelement.engineeringModelDataList[0].asset.exposureClass}</td>
			
			<th>Carbonation class</th>
			<td>${dataelement.engineeringModelDataList[0].asset.carbonationClass}</td>

			<th>Chloride class</th>
			<td>${dataelement.engineeringModelDataList[0].asset.chlorideClass}</td>
		</tr>
		<tr>
			<th>Concrete cover</th>
			<td>${dataelement.engineeringModelDataList[0].asset.cover} mm</td>

			<th>Size of concrete element</th>
			<td>${dataelement.engineeringModelDataList[0].asset.dmember} mm</td>

			<th>Design strength</th>
			<td>${dataelement.engineeringModelDataList[0].asset.fprimec} MPa</td>

		</tr>
		<tr>
			<th>Water to cement ratio</th>
			<td>${dataelement.engineeringModelDataList[0].asset.wc}</td>
			
			<th>Cement content</th>
			<td>${dataelement.engineeringModelDataList[0].asset.ce} kg/m3</td>

			<th>Diameter of rebar</th>
			<td>${dataelement.engineeringModelDataList[0].asset.dbar} mm</td>
		</tr>
	</tbody>
</table>

<c:if test="${dataelement.displayType == 'TABLE'}">
	<br />
	<p>
	<center><h5>${dataelement.engineeringModelDataList[0].variable.name} over Time</h5></center>
	</p>
	<table class="data display datatable">
		<thead>
			<tr>
				<th>Year</th>
				<c:forEach items="${dataelement.engineeringModelDataList}" var="engModelData" varStatus="loop">
					<th>${engModelData.parameters.emissionScenario.name} ${engModelData.parameters.model.name}</th>
				</c:forEach>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="i" begin="2000" end="2070" step="1" varStatus="yearLoop">
				<tr class="${yearLoop.index % 2 == 0 ? 'even' : 'odd'}">
					<td class="head">${i}</td>
					<c:forEach items="${dataelement.engineeringModelDataList}" var="engModelData" varStatus="loop">
					<td class="center"><fmt:formatNumber type="number" maxFractionDigits="3" value="${engModelData.values[i]}" /></td>
					</c:forEach>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</c:if>