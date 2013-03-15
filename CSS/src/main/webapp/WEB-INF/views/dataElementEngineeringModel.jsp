<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ page import="war.model.DataElementEngineeringModel" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div id="points-chart-${dataElementLoopIndex}" style="width:95%; margin-bottom:30px">
</div>

<script type="text/javascript" language="javascript">
	var series = new Array();
	<c:forEach items="${dataelement.engineeringModelDataList}" var="engModelData" varStatus="loop">
	    series[${loop.index}] = new Array();
	    <c:forEach items="${engModelData.values}" var="values">
	    	series[${loop.index}].push([${values.key}, ${values.value}]);
	    </c:forEach>
	</c:forEach>
	

	var graphTitle = "${dataelement.engineeringModelDataList[0].variable.name} over time";
	var yAxisTitle = "${dataelement.engineeringModelDataList[0].variable.shortName}";
	
    var plot = $.jqplot('points-chart-${dataElementLoopIndex}', 
    		[<c:forEach items="${dataelement.engineeringModelDataList}" var="engModelData" varStatus="loop">
				series[${loop.index}]<c:if test="${!loop.last}">,</c:if>
			</c:forEach>],
	{
    	title: graphTitle,
    	series: [<c:forEach items="${dataelement.engineeringModelDataList}" var="engModelData" varStatus="loop">{
                	label: "${engModelData.parameters.emissionScenario.name} ${engModelData.parameters.model.name}", 
                	showMarker: false,
                	lineWidth: 1
                }<c:if test="${!loop.last}">,</c:if>
    			</c:forEach>],
    	axes: {
            xaxis: {
              label: "Time",
              pad: 0
            },
            yaxis: {
              label: yAxisTitle
            }
    	},
        legend: {
            show: true,
            location: "se"
        }
	});
</script>
	<center>
	<h6>ASSET: ${dataelement.engineeringModelDataList[0].asset.assetCode} (${dataelement.engineeringModelDataList[0].asset.description})</h6> 
	<p>Built in ${dataelement.engineeringModelDataList[0].asset.yearBuilt}.</p>
	</center>
<table class="data display datatable" id="example" >
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