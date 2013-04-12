<%@ page session="true"%>
<%@ page language="java" %>
<%@ page import="java.lang.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.codec.binary.*"%>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ page import="org.apache.commons.lang.*" %>
<%@ page language="java" import="war.model.UserStory" %>
<%@ page language="java" import="war.model.User" %>
<%@ page language="java" import="war.model.DataElement" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${not empty userstory}">
<div class="grid_12">

	<!-- Titles -->
	<div style="margin-left: 20px; float:left">
		<h2>${userstory.name}</h2>
		<h4>${userstory.region.name} region</h4>
	</div>
	
	<a class="lnkConvertToUserStory" href="/CSS/auth/userstory/create?id=${userstory.id}" style="margin-right: 10px; float:right">
		<button id="btnConvertToUserStory" type="button" class="btn btn-icon btn-blue btn-arrow-right" >
			<span></span>Create User Story
		</button>
	</a>
	<a class="lnkDeleteWorkboard" href="/CSS/auth/workboard/delete?id=${userstory.id}" style="margin-right: 10px; float:right">
		<button id="btnDeleteWorkboard" type="button" class="btn btn-icon btn-blue btn-cross" >
			<span></span>Delete WorkBoard
		</button>
	</a>
	<div id="confirmWorkboardDeletionModalWindow" title="Delete your workboard ?">
		<p>Deleting your workboard will also delete all the data elements it contains. This action cannot be undone.</p> 
		<p>Are you sure you want to permanently delete your current workboard ?</p> 
	</div>
	<div id="confirmConvertToUserStoryModalWindow" title="Create a story from this workboard ?">
		<p>This will create a Story based on your Workboard. Once the Workboard becomes a Story, no more data element can be added to it.</p> 
		<p>Are you sure you want to create a Story from your Workboard now ?</p>
	</div>
	<script type="text/javascript">
		setupConfirmBox("confirmConvertToUserStoryModalWindow", "lnkConvertToUserStory");
		setupConfirmBox("confirmWorkboardDeletionModalWindow", "lnkDeleteWorkboard");
	</script>
	
	<div class="clear"></div><br />
	
	<c:set var="successMessage" scope="request" value="${successMessage}"/>
	<c:set var="warningMessage" scope="request" value="${warningMessage}"/>
	<c:set var="errorMessage" scope="request" value="${errorMessage}"/> 			
	<jsp:include page="notifications.jsp" />
		
	<script type="text/javascript">
		$(document).ready(function() {
			$("#tabs").tabs({
				select: function(event, ui) {
					window.location.replace(ui.tab.hash);
				},
				show: function(event, ui) {
					$(window).resize(); // triggered to resize the Highcharts graphs to a % of the width of the tab
				}
			});
		});
	</script>
	
	<c:set var="userstory" scope="request" value="${userstory}"/>
	<c:set var="dataelements" scope="request" value="${dataelements}"/>
	
	<div id="tabs">
		<ul>
			<li><a href="#tabs-non-climate-context" class="${dataelementsCounts[0] > 0 ? 'checked' : ''}">Non-climate context</a></li>
			<li><a href="#tabs-observed-climate" class="${dataelementsCounts[1] > 0 ? 'checked' : ''}">Observed Climate</a></li>
			<li><a href="#tabs-future-climate" class="${dataelementsCounts[2] > 0 ? 'checked' : ''}">Future Climate</a></li>
			<li><a href="#tabs-applications" class="${dataelementsCounts[3] > 0 ? 'checked' : ''}">Applications</a></li>
			<li style="float:right"><a href="#tabs-summary" class="${(dataelementsCounts[0] > 0 && dataelementsCounts[1] > 0 && dataelementsCounts[2] > 0 && dataelementsCounts[3] > 0) ? 'checked' : ''}">Summary (All)</a></li>
		</ul>
		
		<div id="tabs-non-climate-context">
			<!-- Explanation text -->
			<c:if test="${dataelementsCounts[0] <= 0}">
				<div>
					<h6><img src="<c:url value="/resources/img/icons/information.png" />"> Information</h6>
					<p><b>ABS, BITRE, custom files</b>: Proin elit arcu, rutrum commodo, vehicula tempus, commodo a, risus. Curabitur nec arcu. Donec sollicitudin mi sit amet mauris. Nam elementum quam ullamcorper ante. Etiam aliquet massa et lorem. Mauris dapibus lacus auctor risus. </p>
				</div>
			</c:if>
			
			<c:set var="dataelementsfilter" scope="request" value="NonClimate"/>
			
			<!-- Toolbox -->
			<center>
				<jsp:include page="workboardToolbox.jsp" />
			</center>
			<div class="clear"></div><br />
			
			<jsp:include page="dataElements.jsp" />
		</div>
		
		<div id="tabs-observed-climate">
			<!-- Explanation text -->
			<c:if test="${dataelementsCounts[1] <= 0}">
				<div>
					<h6><img src="<c:url value="/resources/img/icons/information.png" />"> Information</h6>
					<p><b>Past data</b>: Proin elit arcu, rutrum commodo, vehicula tempus, commodo a, risus. Curabitur nec arcu. Donec sollicitudin mi sit amet mauris. Nam elementum quam ullamcorper ante. Etiam aliquet massa et lorem. Mauris dapibus lacus auctor risus.</p>
					<p><b>Local ACORN-Sat data</b>: Proin elit arcu, rutrum commodo, vehicula tempus, commodo a, risus. Curabitur nec arcu. Donec sollicitudin mi sit amet mauris. Nam elementum quam ullamcorper ante. Etiam aliquet massa et lorem. Mauris dapibus lacus auctor risus.</p>
				</div>
			</c:if>
			
			<c:set var="dataelementsfilter" scope="request" value="ObservedClimate"/>
			
			<!-- Toolbox -->
			<center>
				<jsp:include page="workboardToolbox.jsp" />
			</center>
			<div class="clear"></div><br />
			
			<jsp:include page="dataElements.jsp" />
		</div>
		
		<div id="tabs-future-climate">
			<!-- Explanation text -->
			<c:if test="${dataelementsCounts[2] <= 0}">
			<div>
				<h6><img src="<c:url value="/resources/img/icons/information.png" />"> Information</h6>
				<p>Climate Smart Seaports assists seaport authorities and environmental managers of the Australian coast in understanding how their climate has changed and how it may change in the future.</p>
	
				<p>This web-tool has been designed to provide national climate projections for natural resource management regions across Australia. The projections have been derived for specific regions  to facilitate the generation of data for detailed impact and risk assessments.</p>
					
				<p>Within the Future Climate section, global climate models (GCMs) were selected using CSIRO's Climate Futures tool. Projections were classified using two climate variables; rainfall and temperature, and grouped into so called "climate futures" (e.g. "hotter, drier" or "cooler, wetter"). Likelihoods were then assigned according to the number of climate models that fell within each category. This application offers three GCM categories, 'hotter and drier', 'cooler and wetter' and 'most likely', where the 'most likely' category represents a likelihood of 33% and above.</p>
				
				<p>For the natural resource management regions future climate projections are available for:
				- Four climate variables: temperature, rainfall, wind speed and relative humidity
				- Two emissions scenarios: A1B (medium) and A1Fi (high)
				- Three time periods: 2030, 2055 and 2070
				</p>
				
				<p>The sea level data provided here has been derived from global and regional projections on the CMAR website (<a href="http://www.cmar.csiro.au/sealevel/sl_proj_21st.html" >global</a> and <a href="http://www.cmar.csiro.au/sealevel/sl_proj_regional.html">regional</a>).</p>
				
				<p>Data provided by CMAR was taken from the average of 17 climate model simulations for the medium (A1B) emissions scenario. This average was plotted around the Australian coastline to allow the identification of particular locations. Figures from these locations were then added to the globally averaged sea level projections for 2030 and 2070, using only the medium emissions scenario. These global projections included estimates for ice-sheet melt and were taken from the 50th percentile range.</p>
				
				<p>Both data sets are available as raw data and as map images which can be selected or deselected.</p>
				<p>For more information please refer to the User Guide/Manual.</p>
			</div>
			</c:if>
			
			<c:set var="dataelementsfilter" scope="request" value="Future"/>
			
			<!-- Toolbox -->
			<center>
				<jsp:include page="workboardToolbox.jsp" />
			</center>
			<div class="clear"></div><br />
			
			<jsp:include page="dataElements.jsp" />
		</div>
		
		<div id="tabs-applications">
			<!-- Explanation text -->
			<c:if test="${dataelementsCounts[3] <= 0}">
				<div>
					<h6><img src="<c:url value="/resources/img/icons/information.png" />"> Information</h6>
					<p><b>Concrete Deterioration</b>: ?</p>
					<p><b>Vulnerability</b>: This aims to identify your current vulnerability to particular weather events.</p>
				</div>
			</c:if>
			
			<c:set var="dataelementsfilter" scope="request" value="Applications"/>
			
			<!-- Toolbox -->
			<center>
				<jsp:include page="workboardToolbox.jsp" />
			</center>
			<div class="clear"></div><br />
			
			<jsp:include page="dataElements.jsp" />
		</div>
		
		<div id="tabs-summary">
			<c:set var="dataelements" scope="request" value="${dataelements}"/>
			<c:set var="dataelementsfilter" scope="request" value="All"/>
			<jsp:include page="dataElements.jsp" />
		</div>
	</div>
	
	<div id="confirmDataElementDeletionModalWindow" title="Delete this data element ?">
		<p>Are you sure you want to delete this data element from your Workboard ?</p> 
	</div>
	<script type="text/javascript">
		setupConfirmBox("confirmDataElementDeletionModalWindow", "lnkDeleteDataElement");
	</script>
</div>
</c:if>