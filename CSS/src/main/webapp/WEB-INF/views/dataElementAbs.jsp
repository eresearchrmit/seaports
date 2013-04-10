<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ page import="war.model.DataElementCsiro" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<c:choose>
	<c:when test="${not empty dataelement.absDataList}">
		<script type="text/javascript">
			$(function () {
				var colors = Highcharts.getOptions().colors;
								
		        $('#${dataelementsfilter}-abs-LineAndBarGraph${dataElementLoopIndex}').highcharts({
		            chart: {
		                spacingRight: 20
		            },
		            title: {
		                text: '${dataelement.absDataList[0].seaport.urbanCenter} - Annual Population Change'
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
								<c:if test="${!yearLoop.first}"><c:out value="${(dataelement.absDataList[0].values[i] - previousValue) / dataelement.absDataList[0].values[i] * 100}" /></c:if>
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
		    });
		</script>
		<div id="${dataelementsfilter}-abs-LineAndBarGraph${dataElementLoopIndex}" class="highcharts" style="width:95%; margin-bottom:30px">
		</div>
		
	</c:when>
	<c:otherwise>
		<div id="warningMessage" class="message warning">
		<h5>No Data</h5>
		<p>No data corresponding to the selected settings.</p>
	</div>
	</c:otherwise>
</c:choose>