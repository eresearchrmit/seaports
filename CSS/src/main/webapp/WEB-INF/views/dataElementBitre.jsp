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

<c:choose>
	<c:when test="${not empty dataelement.bitreDataList}">
		
		<c:set var="categoryName" value="${dataelement.bitreDataList[0].variable.category.name}" />
		
		<c:choose>
			<c:when test="${dataelement.displayType == 'TABLE'}">
				<h4>${dataelement.bitreDataList[0].seaport.name} - ${categoryName}</h4>
				<br />
				<table class="data display datatable">
					<thead>
						<tr>
							<th>Financial Year</th>
							<c:forEach var="i" begin="2000" end="2011" step="1" varStatus="yearLoop">
								<th>${i}-${i + 1}</th>
							</c:forEach>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${dataelement.bitreDataList}" var="bitreData" varStatus="dataLoopStatus">
						<tr class="${dataLoopStatus.index % 2 == 0 ? 'even' : 'odd'}">
							<td class="head">${bitreData.variable.name}</td>
							<c:forEach var="i" begin="2000" end="2011" step="1" varStatus="yearLoop">
								<td>
									<fmt:formatNumber type="number" value="${bitreData.values[i]}" />
								</td>
							</c:forEach>
						</tr>
						</c:forEach>
				</tbody>
			</table>
			</c:when>
			<c:when test="${dataelement.displayType == 'GRAPH'}">
				<c:choose>
					<c:when test="${categoryName == 'Freight throughput by cargo type'}">
						<script type="text/javascript">
							$(function () {
								setTimeout(function(){
								
								$('#${dataelementsfilter}-bitre-bycargo-StackedAreaGraph${dataElementLoopIndex}').highcharts({
						            chart: {
						                type: 'area',
						                zoomType: 'y'
						            },
						            title: {
						                text: '${dataelement.bitreDataList[0].seaport.name} - ${dataelement.bitreDataList[0].variable.category.name}'
						            },
						            legend: {
						                borderWidth: 0
						            },
						            xAxis: {
						                categories: ['2000-01', '2001-02', '2002-03', '2003-04', '2004-05', '2005-06', 
						                             '2006-07', '2007-08', '2008-09', '2009-10', '2010-11', '2011-12'],
						                title: {
						                    text: 'Financial Year'
						                }
						            },
						            yAxis: {
						                title: {
						                    text: '${dataelement.bitreDataList[0].variable.uom}'
						                },
						                labels: {
						                    formatter: function() {
						                        return this.value;
						                    }
						                }
						            },
						            tooltip: {
						                shared: true,
						                valueSuffix: ' ${dataelement.bitreDataList[0].variable.uom}'
						            },
						            plotOptions: {
						                area: {
						                    stacking: 'normal',
						                    lineColor: '#666666',
						                    lineWidth: 1,
						                    marker: {
						                        lineWidth: 1,
						                        lineColor: '#666666'
						                    }
						                }
						            },
						            
						            series: [
						                    <c:forEach items="${dataelement.bitreDataList}" var="bitreData" varStatus="loop">{
						                     	name: '${bitreData.variable.name}',
						                     	data: [
													<c:forEach var="i" begin="2000" end="2011" step="1" varStatus="yearLoop">
														${bitreData.values[i]}<c:if test="${!yearLoop.last}">,</c:if>
													</c:forEach>]
						     				}<c:if test="${!loop.last}">,</c:if>
						                 </c:forEach>]
								});
								}, 0);
						    });
						</script>
		
						<div id="${dataelementsfilter}-bitre-bycargo-StackedAreaGraph${dataElementLoopIndex}" class="highcharts" style="width:95%; margin-bottom:30px">
						</div>
						
					</c:when>
					<c:when test="${categoryName == 'Commercial vessel calls by type'}">
						<script type="text/javascript">
							$(function () {
								setTimeout(function(){
								
								$('#${dataelementsfilter}-bitre-byvessel-StackedColumnGraph${dataElementLoopIndex}').highcharts({
						            chart: {
						                type: 'column'
						            },
						            title: {
						                text: '${dataelement.bitreDataList[0].seaport.name} - ${dataelement.bitreDataList[0].variable.category.name}'
						            },
						            legend: {
						                borderWidth: 0
						            },
						            xAxis: {
						                categories: ['2000-01', '2001-02', '2002-03', '2003-04', '2004-05', '2005-06', 
						                             '2006-07', '2007-08', '2008-09', '2009-10', '2010-11', '2011-12'],
						                title: {
						                    text: 'Financial Year'
						                }
						            },
						            yAxis: {
						                title: {
						                    text: '${dataelement.bitreDataList[0].variable.uom}'
						                },
						                stackLabels: {
						                    enabled: true,
						                    style: {
						                        fontWeight: 'bold',
						                        color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
						                    }
						                }
						            },
						            tooltip: {
						                formatter: function() {
						                    return '<b>'+ this.x +'</b><br/>'+
						                        this.series.name +': '+ this.y +'<br/>'+
						                        'Total: '+ this.point.stackTotal;
						                }
						            },
						            plotOptions: {
						            	column: {
						                    stacking: 'normal',
						                    dataLabels: {
						                        enabled: false
						                    }
						                }
						            },
						            
						            series: [<c:forEach items="${dataelement.bitreDataList}" var="bitreData" varStatus="loop">{
							                     	name: '${bitreData.variable.name}',
							                     	data: [
														<c:forEach var="i" begin="2000" end="2011" step="1" varStatus="yearLoop">
															${bitreData.values[i]}<c:if test="${!yearLoop.last}">,</c:if>
														</c:forEach>]
							     				}<c:if test="${!loop.last}">,</c:if>
							                 </c:forEach>]
								});
								}, 0);
						    });
						</script>
		
						<div id="${dataelementsfilter}-bitre-byvessel-StackedColumnGraph${dataElementLoopIndex}" class="highcharts" style="width:95%; margin-bottom:30px">
						</div>
					</c:when>
					<c:when test="${categoryName == 'Export Commodities by type'}">
						<script type="text/javascript">
						$(function () {
							setTimeout(function(){
														
							$('#${dataelementsfilter}-bitre-bycommodities-LinesGraph${dataElementLoopIndex}').highcharts({
					            chart: {
					                type: 'line',
					                zoomType: 'y'
					            },
					            title: {
					            	text: '${dataelement.bitreDataList[0].seaport.name} - ${dataelement.bitreDataList[0].variable.category.name}'
					            },
					            xAxis: {
					                categories: ['2000-01', '2001-02', '2002-03', '2003-04', '2004-05', '2005-06', 
					                             '2006-07', '2007-08', '2008-09', '2009-10', '2010-11', '2011-12'],
					                title: {
					                    text: 'Financial Year'
					                }
					            },
					            yAxis: {
					            	title: {
					                    text: '${dataelement.bitreDataList[0].variable.uom}'
					                },
					                plotLines: [{
					                    value: 0,
					                    width: 1,
					                    color: '#808080'
					                }]
					            },
					            tooltip: {
					                valueSuffix: ' ${dataelement.bitreDataList[0].variable.uom}'
					            },
					            series: [<c:forEach items="${dataelement.bitreDataList}" var="bitreData" varStatus="loop">{
					                     	name: '${bitreData.variable.name}',
					                     	data: [
												<c:forEach var="i" begin="2000" end="2011" step="1" varStatus="yearLoop">
													${bitreData.values[i]}<c:if test="${!yearLoop.last}">,</c:if>
												</c:forEach>]
					     				}<c:if test="${!loop.last}">,</c:if>
					                 </c:forEach>]
							});
							}, 0);
					    });
						</script>
						<div id="${dataelementsfilter}-bitre-bycommodities-LinesGraph${dataElementLoopIndex}" class="highcharts" style="width:95%; margin-bottom:30px">
						</div>
					</c:when>
				</c:choose>
			</c:when>
		</c:choose>
		<br />
	</c:when>
	<c:otherwise>
		<div id="warningMessage" class="message warning">
		<h5>No Data</h5>
		<p>No data corresponding to the selected settings.</p>
	</div>
	</c:otherwise>
</c:choose>