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
		<h2><c:out value="${userstory.name}" /></h2>
		<h4><c:out value="${userstory.seaport.region.name}" /> region - <c:out value="${userstory.seaport.name}" /></h4>
	</div>
	
	<a class="lnkConvertToUserStory" href="/CSS/auth/userstory/create?id=${userstory.id}" style="margin-right: 10px; float:right">
		<button id="btnConvertToUserStory" type="button" class="btn btn-icon btn-blue btn-arrow-right" >
			<span></span>Create Report
		</button>
	</a>
	<a class="lnkDeleteWorkboard" href="/CSS/auth/workboard/delete?id=${userstory.id}" style="margin-right: 10px; float:right">
		<button id="btnDeleteWorkboard" type="button" class="btn btn-icon btn-blue btn-cross" >
			<span></span>Delete WorkBoard
		</button>
	</a>
	<div style="display:none" id="confirmWorkboardDeletionModalWindow" title="Delete your workboard ?">
		<p class="message"><span class="error"><b>Warning : Deleting your workboard will also delete all the data elements it contains. This action cannot be undone !</b></span></p>
		<p>Are you sure you want to permanently delete your current workboard ?</p> 
	</div>
	<div style="display:none" id="confirmConvertToUserStoryModalWindow" title="Create a report from this workboard ?">
		<p>This will create a Report based on your Workboard. Once the Workboard becomes a Report, no more data can be added to it and only text can be typed.</p> 
		<p>Are you sure you want to create a Report from your Workboard now ?</p>
	</div>
	<div style="display:none" id="confirmDataElementDeletionModalWindow" title="Delete this data element ?">
		<p>Are you sure you want to delete this data element from your Workboard ?</p> 
	</div>
	<div class="clear"></div><br />
	
	<c:set var="successMessage" scope="request" value="${successMessage}"/>
	<c:set var="warningMessage" scope="request" value="${warningMessage}"/>
	<c:set var="errorMessage" scope="request" value="${errorMessage}"/>
	<jsp:include page="notifications.jsp" />
	
	<script type="text/javascript">
		$(function() {
			$("#tabs").tabs({
				activate: function(event, ui) {
					
					// Change the hash of the webpage to the activated Tab, without scrolling to that hash
					// This is just to return to the right tab if a form is posted or page refreshed
					var active = $("#tabs").tabs("option", "active");
					var tabHash = $("#tabs ul>li a").eq(active).attr('href');
					if(history.pushState)
					    history.pushState(null, null, tabHash);
					else
					    location.hash = tabHash;

					// Triggered to resize the Highcharts graphs to a % of the width of the tab
					$(window).resize(); 
				}
			});

			setupConfirmBox("confirmConvertToUserStoryModalWindow", "lnkConvertToUserStory");
			setupConfirmBox("confirmWorkboardDeletionModalWindow", "lnkDeleteWorkboard");
			setupConfirmBox("confirmDataElementDeletionModalWindow", "lnkDeleteDataElement");
			
			// Help tooltips activation
			setupTooltips();
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
				
					<br />
					<p class="justified">Non-climate data helps set the operational context of ports. It also provides a starting point for consideration of possible impacts of non-climate factors into the future. For example, population growth along the coast can constrain a port's ability to expand in the future, and to retreat as sea level rise and climatic conditions change. National population growth can also be a driver of increased activity at container import ports, which may lead to congestion problems. 
	
					<p class="justified">This tab identifies particular non-climate-related information.  It includes trade and population data. Note that only limited data may be available for some ports. 
					
					<p class="justified">Two publicly available data sets are offered within this section. These are urban pressure data derived from the Australian Bureau of Statistics (ABS), and freight data from Ports Australia and individual ports' published annual reports.
					
					<p class="justified">Recognising that non-climate information will be gathered by ports themselves, this section allows for ports to upload port-specific files and information regarding organisational objectives, current risks, or data on throughput volume or the types of activity that characterise the port. 
					
					<p class="justified">Users can select from three main data sources on this tab:</p>
					<p class="justified">"<b>ABS</b>" data, which provides Population change data;</p>
					
					<p class="justified">"<b>Ports Australia data</b>" which provides data on:</p>
						<ul>
							<li>Freight throughput by cargo type,</li>
							<li>Commercial vessel calls by type, and</li> 
							<li>Export commodities by type</li>
						</ul>
					<p class="justified">"<b>Custom file</b>" data which can be text and/or images provided by the port, relating to their current, non-climate context. 
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
					<br />
					<p class="justified">Climate change is already increasing the intensity and frequency of many extreme weather events. Extreme events occur naturally, however, climate change is influencing these events and record-breaking weather is becoming more common.</p> 

					<p class="justified">This tab sets the historical and current context of climate and marine observations for ports, to assist ports understand their current climate context. It includes data publicly available from the Bureau of Meteorology and the CSIRO.</p>

					Three types of data have been selected: 
					<ul>
						<li>national trends for temperature and rainfall;</li>
						<li>global and national trends for sea-level rise; and</i>
						<li>specific weather station data (including examples of extreme weather events).</li>
					</ul>
					<p>Users select from two main data sources in this tab:</p>
					"CSIRO and BoM trend data" which provides national or global data on: 
					<ul>
						<li>Trend in mean temperatures,</li>
						<li>Trend in maximum temperatures,</li>
						<li>Trend in total annual rainfall,</li>
						<li>Long-term sea level rise measurements and</li> 
						<li>Shorter term changes in sea level.</li>
					</ul>
					"ACORN-SAT" which provides data from specific weather stations for:
					<ul>
						<li>Mean measurements (annual mean surface temperature, annual mean rainfall, annual mean relative humidity and annual mean wind speed)</li>
						<li>Extreme measurements (highest temperature, highest daily rainfall, maximum wind gust)</li>
					</ul>
					<br />
					<p>All data has been chosen to support the data in the Future Climate section. Please note, however, that the Future Climate is based on modelled data and is not directly comparable to the factual data sourced in this section.</p>
					<br />
					<p class="justified">For further information about extreme weather in Australia, head to the Bureau of Meteorology's "<a href="http://www.bom.gov.au/climate/change/index.shtml#tabs=Climate-change-tracker&tracker=trend-maps" target="_blank">Climate Change Tracker</a>" website.</p> 
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
				<br />
				<p class="justified">The future climate context faced by ports is an important factor in future planning and risk assessment processes. Direct impacts on ports, and indirect impacts on supply chains, will impact capital investment, maintenance requirements, as well as knowledge, skill and training requirements for personnel. Understanding potential future climate impacts allows ports to adequately assess their future planning options, and accommodate the most appropriate adaptation choices. 
				
				<p class="justified">This tab assists ports to identify some of the future climate projections data relevant for their region.   
				
				<p class="justified">Global climate models were selected using CSIRO's Climate Futures tool. Projections were classified using two climate variables: rainfall and temperature, and grouped into "climate futures", for example "hotter &amp; drier" or "cooler and wetter". Likelihoods were then assigned according to the number of climate models that fell within each category. In this application, three global climate models from the following categories are offered for users to choose from: "hotter &amp; drier", "cooler &amp; wetter" and "most likely"</p>
				
				<p class="justified">Users select from two data sources (CSIRO and CMAR), and then select from a choice of variables.</p>
				"CSIRO" which displays future climate projection data for:
				<ul>
					<li>Temperature, rainfall wind speed and relative humidity</li>
					<li>Two emissions scenarios: A1B (medium) and A1FI (high)</li>
					<li>Three climate models: most likely, hotter &amp; drier, and cooler &amp; wetter</li>
					<li>Three time periods: 2030, 2055 and 2070</li>
				</ul>
				
				<p class="justified">"CMAR" which only displays sea-level rise data for the "medium emissions scenario (A1B)", using a "most likely" climate model, for two time periods, "2030" and "2070".</p> 
				<br />
				<p class="justified">Further information on emissions scenarios and the CSIRO and CMAR data is provided in the <a href="/CSS/public/guidelines">Guidance Document</a>.</p>
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
					<br />
					<b>Concrete Deterioration Model</b>
					
					<p class="justified">Climate change will affect the rate of deterioration of materials such as concrete, timber and steel. The main construction material at ports is concrete and the rate of its deterioration will affect maintenance schedules, budgets and long term plans for refurbishment and replacement.</p>

					<p class="justified">This tab provides access to a tool designed by engineers that models rates of concrete deterioration under conditions of climate change in a port environment.</p>
					
					It provides:
					<ol>
						<li>a set of example outputs for those who are not engineers, and</li> 
						<li>the tool for the engineers that have the knowledge to use it</li>
					</ol>
					
					The tool enables the user to define:
					<ul>
						<li>The concrete: e.g.: distance from coast, zone of activity (atmosphere, tidal, splash, submerged), size of structure,  depth of cover, diameter of rebar, water-to-cement ratio, cement content, depth of carbonation from maintenance records etc.;</li>
						<li>The potential climate influences (following the range provided in the Future Climate tab), and;</li>
						<li>A date range for all years 2013 - 2070</li>
					</ul>
					
					<p class="justified">Data is not currently available for port areas in the South South West Flatlands West (SSWFW) Region.</p>				

					<b>Vulnerability</b>

					<p class="justified">When considering impacts of climate change, the term vulnerability represents exposure to a particular climate variable combined with the level of sensitivity to that variable, or the degree of impact.</p>

					<p class="justified">The Climate Smart Seaports Tool assists users to investigate vulnerability to current extreme climate-related events. How a port copes with, and responds to current extreme weather events, can be an indication of how well it will cope with future climate change.</p>

					<p class="justified">This tab allows users to identify the current vulnerability of a nominated port to particular climate related events. It presents a series of questions, exploring how recent climate-related events have disrupted operations at the port, and what this has meant for the port's business.</p> 

					<p class="justified">When considering the questions in this tab, think of the impact on port assets (machinery, buildings, equipment), infrastructure (drainage, rail, road, berths), and people (injuries, work disruptions).</p>
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
			<div id="msgTabSummary" class="message info">
				<h5>Information</h5>
				<p><c:out value="All your selected data is presented in this section." /></p>
			</div>
						
			<c:set var="dataelements" scope="request" value="${dataelements}"/>
			<c:set var="dataelementsfilter" scope="request" value="All"/>
			<jsp:include page="dataElements.jsp" />
		</div>
	</div></div>
</c:if>