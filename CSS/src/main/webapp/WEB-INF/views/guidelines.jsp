<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="grid_12">
	<a href="javascript: history.back();" class="floatleft" title="Go back to previous page">
		<button class="btnDeleteStory btn-icon btn-blue btn-arrow-left" >
			<span></span>Back
		</button>
	</a>
	
	<a href="<c:url value="/resources/docs/climate-smart-seaports-guidelines.pdf" />" title="Download these guidelines as a PDF document" target="_blank" style="margin-right: 10px; float:right">
		<button class="btn-icon btn-blue btn-arrow-down" >
			<span></span>Download Guidelines PDF
		</button>
	</a>

	<center>
		<h2>Guidance document</h2>
	</center>
	<p style="text-align:center; margin: 25px 0; font-size: 12pt">
		<strong>These are the guidelines for the seaports application</strong>
	</p>
	
	<!-- BEGINNNING OF GUIDELINES CONTENT -->
	
	<h3>Table of Contents</h3>
	<ul style="list-style-type: none; font-size: 12pt">
		<li><a href="#acknowledgements">Acknowledgements</a></li>
		<li><a href="#introduction">1.	Introduction</a></li>
		<li><a href="#tool-overview">2.	Tool Overview</a></li>
		<li><a href="#css-workboard">3.	The Climate Smart Seaports Workboard</a>
			<ul style="list-style-type: none; margin-bottom:0px">
				<li><a href="#non-climate">3.1 Non-Climate Context</a></li>
				<li><a href="#observed-climate">3.2 Observed Climate</a></li>
				<li><a href="#future-climate">3.3 Future Climate Context</a></li>
				<li><a href="#applications">3.4 Applications</a></li>
				<li><a href="#data-summary">3.5 Data Summary</a></li>
			</ul>
		</li>
		<li><a href="#producing-reports">4. Producing a Report</a></li>
		<li><a href="#next-steps">5. Next Steps</a></li>
		<li><a href="#limitations">6. Limitations</a></li>
		<li><a href="#acronyms">Acronyms</a></li>
		<li><a href="#glossary">Glossary</a></li>
	</ul>
	
	<div id="acknowledgments" style="font-size: 10pt; text-align: justify; margin-top: 50px">
		<h3>Acknowledgements</h3>
		
		<p>The Climate Smart Seaports Tool was created by RMIT University eResearch in partnership with the Climate Change Adaptation Program, with funding provided by the Australian National Data Service (ANDS).  ANDS aims to lead the creation of a cohesive national collection of research resources that makes better use of Australian research outputs, enables Australian researchers to easily publish, discover, access and use data, and enable new and more efficient research.</p>

		<p>RMIT would particularly like to thank Gippsland Ports and Sydney Ports Corporation for their support in the development of the Climate Smart Seaports Tool.</p>
	</div>
	
	<div id="introduction" style="font-size: 10pt; text-align: justify; margin-top: 50px">
		<h3>1. Introduction</h3>
		
		<p>The potential impact of climate change on ports differs according to the location, function and business model of the ports.  Climate Smart ports in Australia want to understand the relevant climate impacts and risks for their particular operation; only then can they determine what adaptation measures may be appropriate.</p>

		<p>The Climate Smart Seaports web-tool is designed primarily for port personnel who make (or influence) decisions around long-term port planning for infrastructure, assets and management systems. However, it will also be of value to port owners and related businesses, government departments and local authorities concerned with ports and infrastructure, and researchers.</p>
		
		<p>The main purpose of the tool is to facilitate the collection of information so that ports are able to identify their climate risks and adaptation options.   Although it is designed to generate information for the scope and context setting stages of the ISO 31000 risk management standard, (which is widely used by ports around Australia), it is also compatible with the initial stages of other processes such as Engineers Australia's "<a href="http://www.engineersaustralia.org.au/sites/default/files/shado/Learned%20Groups/National%20Committees%20and%20Panels/Coastal%20and%20Ocean%20Engineering/vol_1_web.pdf" target="_blank">Guidelines for Responding to the Effects of Climate Change in Coastal and Ocean Engineering</a>" and the Australian Government's "<a href="http://www.climatechange.gov.au/community/~/media/publications/local-govt/risk-management.ashx" target="_blank">Climate Change Impacts and Risk Management: Guide for Business and Government</a>".</p>
		
		<p>The climate change that Australian ports will experience varies around the country. The Future Climate Context section provides more detail.  For ports, impacts can be directly climate related, such as infrastructure damage caused by storms or workflow interruptions due to extreme temperatures on site. Alternatively, impacts can be indirect such as interruptions to the supply chain caused by damaged rail or road infrastructure or disruptions to production at suppliers' sites.  These impacts can initially be felt in Australia or overseas.  For example, the flooding in Bangkok in 2011 affected the port traffic in Australia for cars in particular; while the heavy rains experienced in Queensland in 2010-11 interrupted the supply lines of coal to the ports, taking them several months to return to full operating strength.</p> 
		
		<p>For some ports, existing procedures and management systems may adequately address climate impacts, and the impacts may prove insignificant. For other ports, these impacts may cost hundreds of thousands of dollars in damages, may lead to insurance cost increases, lost business, or changed port trade. Understanding the impacts and risks allows port decision-makers to put in place appropriate measures, rather than being caught by surprise, or being left behind by competitors. The Climate Smart Seaports web-based tool enables ports to undertake some of the relevant data gathering and context-setting steps to help assess their climate change exposure and risk.</p>  
		
		<p>This guidance document supports the web-based tool, providing more in-depth discussion of issues, as well as technical assistance for some elements of the tool. After providing a broad overview of the tool, it looks at the function of the "Workboard" space, then takes users through each of the relevant data tabs, from non-climate data, current climate and future climate data, as well as applications that are designed to elicit information on specific aspects of port functions. Finally, it explains how the user can create and manipulate their own data summary report, and publish the report for public viewing (if desired).</p> 
		
		<p>It should be stressed, this is the first step in undertaking a climate risk assessment. Direction is provided for subsequent steps that ports can take to complete a risk assessment, however, the web-based tool does not perform this complete function.</p>
		
		<p>Adapting to climate change is still in its infancy and all organisations are learning how to manage the risks posed by the changing climate. The tool supports users to publish their learnings via reports to the Climate Smart Seaports site, promoting peer-to-peer learning and the spread of knowledge. Climate change poses a risk to all areas of a business. This tool attempts to bring the thinking about climate risks in different areas of the business into one report, beginning the creation of an integrated climate risk assessment.
	</div>
	
	<div id="tool-overview" style="font-size: 10pt; text-align: justify; margin-top: 50px">
		<h3>2. Tool Overview</h3>
		
		<div id="fig-user-workflow" class="hint" style="margin: 25px auto;">
			[PICTURE]<br />
			Figure 1: Climate Smart Seaports Tool - User Workflow
		</div>
		<br />
		
		<p>The Climate Smart Seaports web-based tool may be used by ports as part of their ongoing risk assessment process. It can assist to frame the scope and context for a port's future climate risk assessment. The tool is divided into seven core elements. These are:</p>
		<ul>
			<li>The Workboard</li>
			<li>Non-climate context</li>
			<li>Observed climate</li>
			<li>Future climate context</li>
			<li>Applications (Concrete deterioration modelling application, and Current vulnerability assessment application)</li>
			<li>Data Summary and</li>
			<li>Report</li>
		</ul>
		
		
		<p>The tool allows the user to progress through each of these elements, selecting data parameters that are of particular relevance to their port.  Users can also choose to add further port-specific data as required, and add additional analysis if wanted.<p> 
		
		<p>The logical workflow for a user to progress through the tool is illustrated in <a href="#fig-user-workflow">Figure 1</a>.<p>
		
		<h6>The main elements of the tool are:</h6>
		<p>The Workboard, where data elements can be collected and reviewed.  Refer to Section 3: The Climate Smart Seaports Workboard, for a more detailed description of how this element functions). The Workboard is essentially where most of the work for the tool is undertaken.</p> 

		<p>When users first login to the tool, they will be directed to a "Create New Workboard" page, where there is a map of Australia. This is where the user defines the geographic scope and purpose of the assessment. The geographic scope of the climate risk assessment is determined by the natural resource management (NRM) regions, which are graphically represented on this map. (see <a href="fig-nrm-regions">Figure 2: NRM Regions of Australia</a>). Users select which region they wish to focus their assessment on by clicking on that region. This proof-of-concept version of the tool incorporates data for three regions, the East Coast South, the Southern Slopes Victoria East and Southern and South-Western Flatlands (WA).  Users then select which particular port in that region is their focus of enquiry from the drop-down list.  The tool also asks users to nominate the purpose for using the tool; for example, for research, or work related climate risk assessment.</p>
		
		<div id="fig-nrm-regions" class="hint" style="margin: 20px 0">
			[PICTURE]<br />
			Figure 2: NRM Regions of Australia
		</div>
		
		<p>The Workboard has a number of tabs for users to progress through, each presenting different data sets: Non-climate context; Observed Climate; Future Climate; Applications; Data Summary.</p>
		
		<p><strong>Non-climate context</strong> refers to social and trade data in the region of the port. Specifically, this section displays Australian Bureau of Statistics (ABS) urban population pressure (if ports are situated near an urban centre) and Ports Australia port trade data. Once ports have selected their NRM region, data relevant to their location is made available in this section.  Ports are also able to add their own data here, if appropriate. Refer Section 3.1 for more detail.</p>

		<p>The section of the tool labelled <strong>Observed Climate</strong> includes data publicly available from the Bureau of Meteorology (BoM) and the CSIRO. It sets the historical and current context for the chosen NRM regions for both climate (temperature, rainfall, wind speed and relative humidity) and ocean (average sea level changes over time). Refer Section 3.2 for more detail.</p>
		
		<p>The <strong>Future Climate Context</strong> section provides climate projections, selected using the CSIRO Climate Futures tool, for the different NRM regions. Users are able to select one of three different time frames (2030, 2055, 2070), they can investigate four climate variables (temperature, rainfall, wind speed and relative humidity), drawing on several global climate models representing a medium emissions scenario, and a high emissions scenario. Data related to sea level rise can also be selected.  Refer Section 3.3 for more detail.</p>
		
		<p>The <strong>Applications</strong> section has two sub-elements that require input of port-specific information: one is a <strong>Concrete deterioration</strong> element that investigates the deterioration rate of concrete used in port infrastructure, under the chosen climate conditions. The second sub-element looks at the current <strong>Vulnerability</strong> of ports to recent extreme weather events. This can then be used to analyse a port's preparedness for extreme weather events under future climate change conditions.</p> 
		
		<p>The <strong>Data Summary</strong> section is the place where users can review all their chosen data. They may decide to go back to any of the sections and add more data, or choose to delete chosen data at this stage.</p>
		
		<p><strong>Producing a Report</strong> is the final step in the process, and is where a formatted report is prepared for the user. Climate Smart Seaports Tool users can arrange their data according to their needs.  They can choose to "hide" data elements, as well as add further text and analysis. From here, users can choose to print a report for their own purposes, and/or progress to publishing their report which would make the information publicly available.</p>
		
		<p>When users select [PICTURE PUBLISH BUTTON] they can no longer edit their report, and their report becomes available to the public.</p>
	</div>
	
		
	<div id="css-workboard" style="font-size: 10pt; text-align: justify; margin-top: 50px">
		<h3>3.	The Climate Smart Seaports Workboard</h3>
	
		<p>When users first login to the Climate Smart Seaports tool, they will be directed to the "Create New Workboard" page.  The Workboard can be thought of as the data gathering platform for the tool. It is where users collate information about the non-climate context, current climate, future climate and current and future vulnerability of the port. There can only be one active Workboard at a time.</p> 

		<p>This page has a number of tabs, representing the different data types: non-climate, observed climate, future climate and applications. Users work through each of these tabs, selecting the appropriate data parameters. The outputs are displayed on the "Summary (all)" tab of the Workboard for users to review and analyse.</p> 
		
		<h6>Getting Started</h6>
		<ol>
			<li>The first step is to create a New Workboard.  In the text box provided, select a name for your Workboard.</li>
			
			<li>
				Then, select the appropriate NRM region for your port. This can be done by clicking on the selected NRM region on the map, (<a href="#fig-nrm-regions-create-wb">Figure 3</a>). In this proof-of-concept version of the tool, only three regions are available to choose from: the East Coast South and the Southern Slopes Victoria East and Southern and South-Western Flatlands (WA).
				
				<div id="fig-nrm-regions-create-wb" class="hint" style="margin: 25px auto;">
					[PICTURE]<br />
					Figure 3: NRM Regions of Australia - Creating a Workboard
				</div>
			</li>
			
			<li>Users then select which particular port in that NRM region is their focus of enquiry from the drop-down list.</li>
			<li>The tool also asks users to nominate the purpose for using the tool, for example, for research into climate impacts on ports, or work related climate risk assessment etc.</li>
			<li>Click the [PICTURE CREATE BUTTON] button to create your Workboard.</li>
		</ol>
		
		
		<h6>Adding data elements</h6>
		<ol>
			<li>
				To add data elements to the Workboard, select the appropriate tab. Choose from Non-climate context, Observed Climate, Future Climate or Applications. Although it is logical to move from left to right, you do not need to add data elements in any particular order.
			
				<div style="margin: 20px 0">
					[PICTURE TABS]
				</div>
			</li>
			
			<li>Instructions relevant to each tab are outlined in the following sections.</li>
			<li>There can only be one active Workboard at a time. To create another Workboard, users must first create a Report from their first Workboard. Although data can be hidden in the Report, new data elements cannot be added once data is in the Report stage.</li>
		</ol>
		
		<h6>Outputs</h6>
		<p>Outputs from each of the data tabs are displayed on both the individual tab, as well as collated in the Summary (All) tab, to the right of the screen.  How the data is displayed generally depends on the data element itself. It may be in a graph, table, map or text form.</p> 

		<p>Users are able to add text to round out descriptions at the "Report" stage.</p>
	
		<div id="non-climate" style="font-size: 10pt; text-align: justify; margin-top: 50px">
			<h4>3.1 Non-Climate Context</h4>
			
			<p>This section identifies some of the non-climate-related elements for the nominated port, and its region.  It considers trade data for particular ports and population data. Note that only limited data may be available for some ports. Recognising that non-climate information will be gathered by ports themselves, this section also allows for ports to upload port-specific files and information.</p>
			
			
			<h6>Ports and Non-Climate Data</h6>
			<p>Non-climate data helps to set the operational context of ports. It also provides a starting point for consideration of possible impacts of non-climate factors into the future.</p> 

			<p>Port authorities generally have extensive data regarding their organisation's characteristics, throughput and local context. Therefore, this section includes the ability to upload custom data files. These files may include graphic depictions of the port system and its assets (see <a href="#fig-generic-diagram-bulk-export">Figure 4</a> and <a href="#fig-generic-diagram-container-import">Figure 5</a>), information regarding core organisational objectives and/or current risks, or data on throughput volume or the types of activity that characterise the port.</p>
			
			<div id="fig-generic-diagram-bulk-export" class="hint" style="margin: 25px auto;">
				[PICTURE]<br />
				Figure 4: Generic diagram of a bulk export<br />
				<i>Source: Infrastructure Australia and the National Transport Commission (2010)</i>
			</div>
			
			<div id="fig-generic-diagram-container-import" class="hint" style="margin: 25px auto;">
				[PICTURE]<br />
				Figure 5: Generic diagram of a Metropolitan container import port<br />
				<i>Source: Infrastructure Australia and the National Transport Commission (2010)</i> 
			</div>
			
			<p>Two basic, publicly available non-climate data sets are available within this section. These are derived from the Australian Bureau of Statistics (ABS), Ports Australia and ports' published annual reports. Refer to <a href="#limitations">Section 6: Limitations</a> for a discussion of data limitations.</p> 

			<p>Data on Urban Pressure was extracted from the ABS Regional Population Growth Catalogue (3218.0), which provides decadal population estimates by Significant Urban Area from 2001 to 2011. These allow identification of population change over this period, which can impact on the ability of urban ports to retreat from slow-onset sea-level rise impacts.</p> 
			 
			<p>Freight data for most Australian ports is collated by Ports Australia - the peak national body representing ports and marine authorities across Australia. A range of freight statistics are available across all regions for the last decade; financial year 2000/01 through to 2011/12. Data that is available within the Climate Smart Seaports tool includes:</p>
			
			<ul>
				<li>Decadal trends in freight throughput by cargo type;</li>
				<li>Decadal trends in port calls by commercial vessel type; and</li>
				<li>Decadal trends in exports by commodity type.</li> 
			</ul>
			
			<p>Over the last decade, the most prominent population increase has been in coastal areas. In the future, population growth will affect the ports in direct and indirect ways. The most obvious of the direct impacts will be an increased throughput of goods through container ports to meet the increased demands of a growing population.</p>
			
			<p>As urban populations increase, ports in urban centres will find expansion capacity limited. Capacity constraints will also be felt through increasing pressure on transport infrastructure. Additionally, population growth may create pressure on other forms of critical infrastructure such as energy generation and distribution and water resources, which may then have cascading impacts on port operations under changes to extreme climate events. Port congestion and container traffic are seen as an increasing challenge for the sector, and it has been recommended that long term reservation of maritime and land access and connecting rail corridors is necessary (<a href="http://www.ntc.gov.au/filemedia/general/GHDMeyIANTCNPSBackground4.pdf">GHD Meyrick, 2010e</a>).</p> 
			
			<h6>Steps to add this data element</h6>
			
			<ol>
				<li>Click the [PICTURE ADD DATA ELEMENT BUTTON] button</li>
				<li>
					Select the data source from the pop-up screen. Users can choose from "ABS data" (population data), "Ports Australia data" (trade data) or to add their own "Custom file".<br />
					[PICTURE POPUP SOURCE SELECTION]
				</li>
				<li>
					Once you have selected the data source, a new pop-up screen appears.  It requires users to select the particular data variable. For example, for Ports Australia trade data, users can select "Freight throughput by cargo type", "Commercial vessel calls by type", or "Export commodities by type".<br />
					[PICTURE POPUP PORTS AUSTRALIA DATA]<br />
					If selecting to add a custom file,<br />
					[PICTURE POPUP CUSTOM FILE]<br />
					Choose a file to upload from your own system.  Acceptable formats are Text (.txt) or Image (.jpeg or .jpg) formats.
				</li>
				<li>
					Users should then re-select the port of interest, and select how they want the data displayed. 
				</li>
				<li>
					Finally, click the [PICTURE ADD ABS DATA BUTTON]or [PICTURE ADD BITRE DATA] or the [ADD CUSTOM DATA] button, whichever is appropriate. 
				</li>
				<li>
					Users can progress through this process multiple times, adding as many data sets and files as are relevant.
				</li>
			</ol>
			
			<h6>Outputs</h6>
			<p>Outputs are displayed on the Non-Climate context tab, as well as the Summary (All) tab to the right of the screen.</p>

			<p>Urban pressure data is available in table, graph and mapped formats, however ABS-classified areas of urban pressure are not available for the Port of Eden, Port of Mallacoota, Port Welshpool and Esperance Ports.</p>

			<p>The Climate Smart Seaports tool allows the trade data to be displayed in either table or graph form. Trade data is not currently available for port areas in the Gippsland Ports Region.</p> 
		</div> <!-- End of 3.1 Non-climate context -->
		
		
		<div id="observed-climate" style="font-size: 10pt; text-align: justify; margin-top: 50px">
			<h4>3.2 Observed Climate</h4>
			
			<p>Extreme events occur naturally and weather records are broken from time to time. However, climate change is influencing these events and record-breaking weather is becoming more common. This section sets the historical and current context of climate and marine observations for ports. It draws on data publicly available from the Bureau of Meteorology (BoM) and the CSIRO.</p>

			<h6>Current and historical weather trends and extreme weather events</h6>
			<p>As the basis for undertaking a climate risk assessment, ports should understand their current and historical exposure to changing weather patterns, and extreme weather events.  Three types of data have been selected to assist with this understanding:</p> 
			<ul>
				<li>national trends for temperature and rainfall;</li>
				<li>global and national trends for sea-level rise; and</li>
				<li>specific weather station data (including examples of extreme weather events).</li>
			</ul>
			
			<p>All data has been chosen to support the data sourced for the Future Climate section, however please note that the Future Climate is based on modelled data and is not directly comparable to the actual data sourced in this section.</p>
			
			<p>Data is not presented for specific ports. It is presented either on the National scale, or according to the location of the weather station.  Weather stations chosen for this application are part of the Australian Climate Observations Reference Network - Surface Air Temperature (<a href="http://www.bom.gov.au/climate/change/acorn-sat/">ACORN-SAT</a>) dataset.</p>
			
			<ol>
				<li><span style="font-size: 1.1em"><i>National climate trends</i></span>
					<p>The trend maps show how temperature or rainfall has changed over time. They need to be interpreted with caution. Trend values are evened out, but the actual change indicated may not have been gradual. The trend values use past observations and cannot show the rate of future change.</p>
	
					<p>The data covers national trends in temperature and rainfall for the period 1970 to 2012. There are other variables and different ways of presenting the data that can be sourced from the <a href="http://www.bom.gov.au/cgi-bin/climate/change/trendmaps.cgi">BoM's site</a>.</p>
					
					<p>This data is useful for ports to understand their current operating context, before considering how the operating context may alter under changed climate conditions in the future.</p>	
				</li>
				
				<li><span style="font-size: 1.1em"><i>Global and National marine trends</i></span>
					<p>As with the trend maps for temperature and rainfall, the graphs and maps depicting sea-level change show how sea levels have changed over time. Past trend observations cannot be used as a predictor of the rate of future change.</p>
	
					<p>The sea-level data includes a graph of sea-level rise measurements from 1880 - 2012 and a map of more recent trends around Australia covering the period 1993 to 2011. Globally, sea levels have risen by about 20cm since 1870. Tide gauge measurements available since the late 19th century indicate that global sea levels have risen by 1.7 ± 0.3mm/year since 1950. This figure increases from 1993 to 2011 to 3.1 ± 0.4 mm/year suggesting that the rate of global sea level rise is accelerating. The observed global mean sea-level rise since 1990 is near the high end of projections from the 2007 Intergovernmental Panel on Climate Change (IPCC) Fourth Assessment Report (see Future Climate Section 3.3 for a discussion of the IPCC).</p> 
					
					<p>Across Australia, sea levels have risen 7 to 11mm per year in the north and northwest, two to three times the global average, whereas rates of sea-level rise on the central east and southern coasts of the continent are mostly similar to the global average. These regional variations are largely due to differences in the warming of the ocean waters around the coastline. Each port may have its own records of sea levels over time. This information would be useful to add to this report at the analysis stage.</p>
					
					<p>More information can be found at the <a href="http://www.cmar.csiro.au/sealevel/index.html">CSIRO Marine and Atmospheric Research website</a> on sea level rise.</p>
				</li>
				
				<li><span style="font-size: 1.1em"><i>Location specific weather station data</i></span>
					<p>The local weather stations chosen for this application are part of the Australian Climate Observations Reference Network - Surface Air Temperature (ACORN-SAT) dataset. ACORN-SAT has been developed for monitoring climate variability and change in Australia, and employs the latest analysis techniques. More information can be found at the <a href="http://www.bom.gov.au/climate/change/acorn-sat/">ACORN-SAT website</a>.</p>  

					<p>The ACORN-SAT dataset presents data for temperature, rainfall, relative humidity and wind speed at particular weather stations.  These are for the period 1981 - 2010. Additionally, the ACORN-SAT data presents measurements of extremes in the local area. These include the highest temperature recorded, highest rainfall and highest wind gust, along with the date of these occurrences.</p> 
					
					<p>While Australia is known for the variability of its climate, over the past five years there have been extreme events that have exceeded previous records.  More information can be found at: <a href="http://www.bom.gov.au/cgi-bin/climate/extremes/extreme_maps.cgi">http://www.bom.gov.au/cgi-bin/climate/extremes/extreme_maps.cgi</a></p>
					
					<p>The following extreme events have had an impact on ports ranging from minor to significant:</p>
					<ol>
						<li>2009: there was an 'exceptional January-February 2009 heatwave in south-eastern Australia' (see <a href="http://www.bom.gov.au/climate/current/statements/scs17d.pdf">BoM Special Climate Statement 17</a>).</li>
						<li>2010-11: Australia's wettest two-year period on record; 2010-2011 (see <a href="http://www.bom.gov.au/climate/current/statements/scs38.pdf">BoM Special Climate Statement 38</a>).</li>
					</ol>
					
					<p>Further reading about extreme weather in Australia can be found at: <a href="http://climatecommission.gov.au/report/extreme-weather/">http://climatecommission.gov.au/report/extreme-weather/</a></p>

					<p>Each port will have knowledge of weather events that have affected operations or infrastructure. This information would be useful to add to this report at the analysis stage. Identifying a threshold beyond which a climate event will cause disruption is a useful way to think about climate risk. For example, at many workplaces personnel are required to stop working outside once the temperature reaches a certain number of degrees, and cranes cannot operate beyond a certain wind force. If the number of hot days increases or the number of windy days decrease, this will affect the port operations.</p>
				</li>
			</ol>
			
			<h6>Steps to add this data element</h6>				
			<ol>
				<li>Click the [PICTURE ADD DATA ELEMENT BUTTON] button</li>
				
				<li>Select the data source from the pop-up screen. Users can choose from "CSIRO and BoM trend data" (national trends) or "ACORN-SAT" data (local weather station trends and extreme events).</li>
				
				<li>
					Once you have selected your data source, a new pop-up screen appears. It requires you to select the particular data variable. For example, for "CSIRO and BoM trend data", users can select "Trend in mean temperatures", "Trend in maximum temperatures", "Trend in total annual rainfall", "Long-term sea level rise measurements" and "Shorter term changes in sea level".<br />
					[PICTURE POPUP PAST DATA]<br />
					For "ACORN-SAT data", users can select from "Mean measurements" or "Extreme measurements".
				</li>
				
				<li>Click the [ADD CSIRO AND BOM TREND DATA BUTTON] or [ADD ACORN-SAT DATA BUTTON] button, whichever is appropriate for your chosen data set.</li>
			</ol>
			
			<h6>Outputs</h6>
			
			<p>Outputs are displayed on the Observed Climate context tab, as well as the Summary (All) tab to the right of the screen.</p>
			
			<p>Users cannot select the format of the output data for this section; it is pre-determined as map, graph or table.</p>
		</div> <!-- End of 3.2 Observed Climate -->
		
		<div id="future-climate" style="font-size: 10pt; text-align: justify; margin-top: 50px">
			<h4>3.3 Future Climate Context</h4>
		
			<p>This section assists ports to identify some future climate projections relevant for their region.  It provides information on the Intergovernmental Panel on Climate Change (IPCC) Scenarios, which underpin scientific modelling of future climate. Additionally, it explains what global climate models are, and how they are used in this tool.</p>
			
			<h6>IPCC Scenarios and Global Climate Models</h6>
			<p>Modelling future climate change requires an estimation of the concentrations of greenhouse gases and other substances in the atmosphere, in the years to come. The IPCC Emissions scenarios describe these possible future releases of gases that are the product of complex, dynamic systems, determined by factors such as population change, socio-economic development, and technological advances. The two emissions scenarios selected for the Climate Smart Seaports tool are both from the "A1 storyline". These scenarios are labelled based on their relative greenhouse gas emissions levels, High (SRES A1FI) and Medium (SRES A1B). For in-depth reading, see the <a href="http://www.ipcc.ch/pdf/special-reports/spm/sres-en.pdf">IPCC Special reports</a>.</p> 
			<p>Global climate models (GCMs) are mathematical representations of the behaviour of the planet's climate system through time. Modelling of the climate system has become possible due to increased computer power and advances are continually being made that increase the models' accuracy.</p>
			
			<h6>Climate variables</h6>
			<p>Within the Future Climate section of this tool, global GCMs were selected using CSIRO's Climate Futures tool. The tool allows the selection of GCMs using a matrix of two climate variables, in this case rainfall and temperature. Likelihoods are then assigned according to the number of climate models that fall within each category.</p> 
			<p>The Climate Smart Seaports application offers three categories, 'hotter and drier', 'cooler and wetter' and 'most likely', where the 'most likely' category represents a likelihood of 33% and above.</p>
			For each of the NRM regions, future climate projections are available for: 
			<ul>
				<li>Four climate variables: temperature, rainfall, wind speed and relative humidity</li>
				<li>Two emissions scenarios: A1B (medium) and A1FI (high)</li>
				<li>Three time periods: 2030, 2055 and 2070</li>
			</ul>
			
			<h6>Climate variables</h6>
			<p>The sea level data was derived from projections using the CSIRO Marine and Atmospheric Research (CMAR) website (both global and regional). Data provided by CMAR was taken from the average of 17 climate model simulations for the medium (A1B) emissions scenario. This average was plotted around the Australian coastline to allow the identification of particular locations. Figures from these locations were then added to the globally averaged sea level projections for 2030 and 2070, using only the medium emissions scenario. These global projections included estimates for ice-sheet melt and were taken from the 50th percentile range.</p>
			<p>Understanding potential future climate impacts allows ports to assess their future planning options, and accommodate the widest range of variables.</p>
			
			<h6>Steps to add this data element</h6>
			<ol>
				<li>Click the [PICTURE ADD DATA ELEMENT BUTTON] button</li>
				<li>Select the data source from the pop-up screen. Users can choose from "CSIRO" (which covers temperature, wind speed, rainfall or relative humidity) or "CMAR" data (sea-level rise).</li>
				<li>
					<p>Once you have selected your data source, a new pop-up screen appears.  It requires you to select the particular data variable. For example, for "CSIRO", users can select "Temperature", "Wind Speed", "Rainfall" or "Relative Humidity", OR, they can select "All Variables". For "CMAR" data source - no variable choice is given, as this data source only provides information on one variable, "Sea level rise".</p>
					<p>Users are able to go through this process multiple times, so that they can select as many combinations of variables that suit their assessment needs.</p>
					[PICTURE POPUP DATA ELEMENT CSIRO]
				</li>
				
				<li>Users are then required to select an appropriate "Emission scenario". There is a choice of two, "Medium" or "High" (Refer to the explanation of emission scenarios in "Background - IPCC Scenarios and Global Climate Models").</li>
				<li>Next, users need to select the climate model they wish to apply to their assessment: "Most likely", "Hotter &amp; drier" or "Cooler &amp; wetter". If uncertain which climate model to select, the best one to choose is the "Most likely" model.  You may then wish to compare the different variables for different models, and select one of the other options.  For example, if your port is already in an area of heavy rainfall, and subject to flooding, you may wish to view what a "cooler &amp; wetter" model predicts for your region. Alternatively, if your area is dry and prone to drought, you may want to see what a "hotter &amp; drier" model predicts.</li> 
				<li>Users are requested to select the time period which best suits their needs.  The shorter time period of 2030 may be suitable for short-term planning horizons. The time-frames of 2055 and 2070 show a greater change in climate from today, and will be particularly relevant for longer planning horizons.</li> 
				<li>Finally, users are able to select whether the data is displayed as a map, or a table.</li> 
				<li>Click the [ADD CSIRO DE BUTTON] or [ADD CMAR DE BUTTON] button, whichever is appropriate for the data element being added.</li> 
			</ol>
			
			<h6>Outputs</h6>
			<p>Outputs are displayed on the Future Climate context tab, as well as the Summary (All) tab to the right of the screen.</p> 
			<p>Data is able to be output as a map, and/or a table format, whichever suits the users' needs. </p>
			<p>Future data related to extreme events is not yet available.</p>
			
		</div> <!-- End of 3.3 Future Climate Context -->
		
		
		<div id="applications" style="font-size: 10pt; text-align: justify; margin-top: 50px">
			<h4>3.4 Applications</h4>
			
			<p>Two applications are available which enable the user to explore specific port impacts in more detail.  The Concrete deterioration tool looks at the impacts of long term climate change on port concrete infrastructure. Specifically, it analyses the deterioration rates of concrete using different climate change scenarios (which the user selects in the <a href="#future-climate">Section 3.3 "Future climate"</a>).  The Vulnerability application focuses more on extreme climate events. It requires users to enter information about recent past climate events that have impacted the port. This understanding of current vulnerabilities to extreme climate-related events can help a port assess their risk of future climate impacts.</p>
			
			<h5>Concrete deterioration tool</h5>
			
			<p>Climate change will affect the rate of deterioration of materials such as concrete, timber and steel. The main construction material at ports is concrete and the rate of its deterioration will affect maintenance schedules, budgets and long term plans for refurbishment and replacement.</p>

			<p>This section provides access to a tool designed by engineers that models rates of concrete deterioration under conditions of climate change in a port environment.</p>
			
			The section provides: 
			<ol style="list-style-type: lower-roman">
				<li>a set of example outputs for those who are not engineers, and</li> 
				<li>the tool for the engineers that have the knowledge to use it</li>
			</ol>
			
			
			<h6>Information about the Concrete deterioration tool</h6>
			<p>The concrete deterioration tool is a mathematical representation of how long it takes either chlorine or carbon dioxide to penetrate the concrete and reach the steel bar and then how long it takes for the steel bar to corrode. The creation of this tool was funded by the National Climate Change Adaptation Research Facility (NCCARF). For the full report see "<a href="http://www.nccarf.edu.au/publications/enhancing-resilience-seaports-structural-port-infrastructure">Structural Resilience of Core Port Infrastructure in a Changing Climate</a>", on the NCCARF website.</p> 

			The tool enables the user to define:
			<ul>
				<li>The concrete	eg: distance from coast, zone of activity (atmosphere, tidal, splash, submerged), size of structure,  depth of cover, diameter of rebar, water-to-cement ratio, cement content, depth of carbonation from maintenance records etc.;</li>
				<li>The potential climate influences (following the range provided in the Future climate tab), and;</li>
				<li>A date range for all years 2013 - 2070</li>
			</ul>
			
			<p>Data is not currently available for port areas in the South South West Flatlands West (SSWFW) Region.</p> 
			
			<h6>Information about the set of examples:</h6>
			<p>The examples provide a set of outputs for concrete structures that could conceivably have been built in the NRM region that you are investigating.</p>
			
			
			<h6>Steps to add this data element</h6>
			<ol>
				<li>Choose the "Concrete deterioration model" from the dropdown data source box.</li>
				<li>
					<p>Select the variable that you want to display from the list of 16 variables (8 for chloride ingress and 8 for carbonation)</p>

					<p>These variables chart the progress of concrete deterioration for two main chemical processes; chloride ingress and carbonation. Chlorine is present in sea water and carbon is present in the atmosphere. Chloride and carbon dioxide are both able to penetrate concrete, ultimately reaching the steel reinforcing bar in the concrete, which then begins corroding.</p> 

					<p>Thus the choices for each chemical vary from the initial probability of a change in the concentration of chloride or carbon in the concrete to the final reduction or loss of the reinforcing steel bar.</p> 
				</li>
			</ol>
			
			<h6>Outputs</h6>
			<p>The outputs are produced as either a graph or a table and cover a range of eight impacts for both chloride ingress and carbonation; ranging from (no.1) probability of corrosion initiation, through  to (no.5) crack propagation time, to finally (no.8) reduction or loss of rebar.</p>
			
			
			
			<h5>Vulnerability Assessment</h5>

			<p>This section aims to identify the nominated port's current vulnerability to particular disruptive climate-related events. It presents a series of questions, exploring how recent climate-related events have disrupted operations at the port, and what this has meant for the port's business.</p>
			
			<h6>Vulnerability and ports</h6>
			<p>When considering impacts of climate change, the term vulnerability represents exposure to a particular climate variable combined with the level of sensitivity to that variable, or the degree of impact. The "Observed Climate" tab provides the context for ports to consider their current exposure to extreme events, while the "Future Climate" tab looks at potential future exposure.  Ports are a combination of 1) hard infrastructure, 2) management systems and procedures and 3) the workforce. It could be any one, or all three, of these elements that are impacted by climate change. The following <a href="#fig-vulnerable-ports-components">Figure 6</a> represents the common components of a port system, and relevant climate variables likely to impact the port.</p>
			
			<div id="fig-vulnerable-ports-components" class="hint" style="margin: 25px auto;">
				[PICTURE]<br />
				Figure 6: Components of a port system vulnerable to different climate impacts
			</div>
			
			<p>This portal looks at vulnerability to CURRENT extreme weather events. How a port copes with, and responds to current extreme weather events, can be an indication of how well it will cope with future climate change. If its infrastructure does not cope with extreme events now (for example, the drainage system cannot manage heavy rainfall and flooding events), then it will not be able to cope in the future, when these events may become more intense. If management systems and processes are lacking now, then changes need to be made to the system to integrate future climate impacts, and the workforce (management and frontline staff) need to be cognisant of climate change and its consequences, in order to affect change.</p>
			
			<h6>Steps to add this data element</h6>
			<p>You can only select one climate-related event for each time you complete this page, however, you can complete it multiple times, to cover multiple events.</p>
			<p>When considering the questions in this section, think of the impact on port assets (machinery, buildings, equipment), infrastructure (drainage, rail, road, berths), and people (injuries, work disruptions).</p>
			
			<ol>
				<li>
					<p>Firstly, select one climate-related event from the drop down menu.</p> 
					[PICTURE WEATHER EVENT CHOICE]
					<p>You can select from "heatwave", "strong wind", "heavy rain", "electrical storm", "storm (wind and rain combined)", "cyclone", "hail", "storm surge", "sea level rise", "fog", "drought" or "flood".</p> 
				</li>
				<li>Then select the year that this disruptive event occurred.</li> 
				<li>Next, consider whether this climate-related event affected the port specifically, so could be considered a direct impact. Alternatively, consider whether it affected the supply chain for the port, so was therefore an indirect impact, that caused flow-on impacts to the port's business. Select either Direct, or Indirect, whichever caused the greater level of disruption to the port.</li> 
				<li>In the text box provided, describe the impact, in a few sentences. For example, "rain caused onsite flooding, damaging storage sheds" or "the cyclone damaged rail-lines from suppliers to the port, cutting supplies for over 2 weeks."</li>
				<li>
					These impacts will then have business-related consequences for your port. Listed are a broad selection of common consequences.  For each listed consequence, rate its impact from "no impact" through to "extreme". The table below provides guidance to interpret these ratings. If you are aware of your port's risk consequence scale, you may prefer to use its consequence levels as guidance.
					
					<div id="table-consequence-rating" class="hint" style="margin: 25px auto;">
						[TABLE]<br />
						Table 1: suggested consequence rating
					</div>
				</li> 
				<li>In the text box, please provide a brief sentence summarising your response to these impacts.</li>
				<li>Finally, consider whether your response was adequate, or whether you have implemented changed procedures, or machinery, even infrastructure as a result of the disruptive event.</li>
				<li>Click [ADD VULNERABILITY DATA BUTTON] to add this data element to your Workboard.</li>
			</ol>
			
			<h6>Outputs</h6>
			<p>The output is some text and a spider graph that depicts the port's areas of vulnerability, to each of the identified extreme weather events.</p>
			<p>If ports have experienced multiple extreme climate-related events, a series of spider graphs may show different areas of vulnerability, or, the same area may be highlighted as particularly vulnerable across different climate-related impacts.</p>
		</div> <!-- End of 3.4 Applications -->
		
		<div id="data-summary" style="font-size: 10pt; text-align: justify; margin-top: 50px">
			<h4>3.5 Data Summary</h4>
			<p>The "Summary (All)" tab to the right of the screen is where all the data elements are collected for the user to review before going to Report stage. Here the user can choose to delete particular data elements, or go back to the individual data tabs and add more information.</p>
		</div> <!-- End of 3.5 Data Summary -->
		
	</div> <!-- End of 3 Climate Smart Seaports Workboard -->
	
	
	
	<div id="producing-reports" style="font-size: 10pt; text-align: justify; margin-top: 50px">
		<h3>4. Producing a report</h3>
		
		<p>This section of the Climate Smart Seaports tool enables users to create a Report from the data they have generated. Within this section, users can re-order the elements to suit their needs, hide data elements and add further text and analysis as required.</p>
		
		<p>Users need to consider for what purpose they are producing the report, to assist determine the best structure, and how much further analysis may need to be added.</p> 
		
		<p>If the report is being used to provide the context to a climate risk assessment, it is suggested the following format be used.</p>
		
		[PICTURE REPORT FORMAT SUGGESTION]
		
		<h6>Steps to create a report</h6>
		
		<ol>
			<li>Click on the [CREATE REPORT BUTTON] button on the top right corner of the screen.</li>  
			<li>All your selected data elements are collated in this one space.  Here, you can:
				<ul>
					<li>Reorder your data elements</li>
					<li>Hide data elements so they do not appear in the final report</li>
					<li>Add text boxes that provide further analysis or interpretation of the data elements.</li>
				</ul>
			</li>
			<li><span style="font-size: 1.1em"><i>Re-ordering data elements</i></span>
				<ul style="margin-bottom: 0">
					<li>By default, the data elements are organised in the order the user created them. To move a data element</li>
					<li>Hover the mouse over the selected data element</li>
					<li>Click and drag the element to the desired position</li>
				</ul>
			</li>
			<li><span style="font-size: 1.1em"><i>Hide a data element</i></span>
			A user may wish to not display a particular data element in the final report, but does not want to delete it from the Workboard. To hide a data element, click the [MINUS EXCLUDE BUTTON] button in the top right corner of the data element box.
			</li>
			<li><span style="font-size: 1.1em"><i>Adding text boxes</i></span>
			The data present part of the picture for a climate risk assessment. However, users may wish to add some explanatory text and analysis to make their Report more complete. To add a text box, click the [ADD TEXT TO REPORT] button. The text box will appear at the bottom of the screen, the last element of your report. 
			Type your desired text.  To relocate your text box, click and drag it to the position you wish to display the text. You can also delete and hide text boxes from the Report. 
			You can add as many text boxes as you like. 
			</li>
			<li>When you are satisfied with the format of the Report, click the [SAVE CHANGES BUTTON] button to save.</li> 
			<li>To preview the Report before printing, go to the top right hand corner of the screen, and click the   button.  From here, you can either close the preview session and return to editing your Report, by clicking the   button. Or you can print your Report by clicking the   button on the top right corner of the screen.</li> 
		</ol>
		
		<h6>Publishing a report</h6>
		<p>ANDS' vision is to have "more researchers reusing research data more often".  To this end, publishing your report enables it to be viewed publicly by researchers and others. Users are able to print a version of their report that contains sensitive information, then "hide" (or remove) these elements from the publishable report. RMIT encourages users to publish their work, so that knowledge can be shared and built upon.</p>
		<p>To publish a report, simply click the [PUBLISH REPORT BUTTON] button.  A warning message will appear, asking if you are certain you wish to Publish. After agreeing to publish, your Report is published and publicly available. You can still preview and print this version of the Report for your own purposes.</p>
		<p>The Climate Smart Seaports tool enables users to search for Reports by clicking [MY REPORTS MENU] in the top left corner of the screen. A new tab will open with a list of your reports. From here, you can view reports, then print them. If the report has not yet been published, the user can also delete reports.</p> 
			
	</div> <!-- End of 4 Producing a report -->
	
	<div id="next-steps" style="font-size: 10pt; text-align: justify; margin-top: 50px">
		<h3>5. Next Steps</h3>
		
		<p>The data collected through the Climate Smart Seaports Tool provides some of the information to inform a climate risk assessment for a port, however, it does not complete the risk assessment. A risk assessment process for ports in Australia is outlined in "<a href="http://www.nccarf.edu.au/publications/enhancing-resilience-seaports-climate-change-adaptation-guidelines">Climate change adaptation guidelines for ports</a>".</p>

		<p>
		The guidelines suggest there are six main stages involved in undertaking a climate risk assessment.<br />
		Stage 0: Getting started - executive support.<br />
		Stage 1: Establish the port context
		Stage 2: Identify current vulnerabilities and future risks<br />
		Stage 3: Analyse and evaluate risks<br />
		Stage 4: Identify and prioritise adaptation options<br />
		Stage 5: Monitoring and evaluation<br />
		</p>
		<p>The Climate Smart Seaports Tool can assist ports with Stages 0, 1 and part of 2.</p> 
		
		<p>Stage 0: Getting started - executive support<br />
		Producing an initial report using the Climate Smart Seaports Tool may assist risk managers at ports highlight potential climate risks, and open the discussions with executive management about completing a full risk assessment.
		</p>
		
		<p>Stage 1: Establish the port context<br />
		The Climate Smart Seaports Tool enables ports to establish geographic boundaries for the scope of their risk assessment, and to collect data on their current operating context, and their future operating context.  This includes both climate and non-climate elements.  Ports will need to analyse this data for what it means to their particular situation, and add data elements that are particular to the port, however the Climate Smart Seaports Tool provides the backbone for establishing the context.
		</p>
		
		<p>Stage 2: Identify current vulnerabilities and future risks<br />
		Identifying current vulnerabilities and future risks is a process best done in a collaborative manner with staff and management at a port. However, the tool enables ports to document their current vulnerability to extreme weather events, and provides space for consideration of what this may mean under changed climate conditions. 
		</p>
		<p>Additionally, the Climate Smart Seaports Tool provides access to a very specific Concrete deterioration application, that can inform ports of their risk in relation to concrete deterioration on site, and what this may mean for ongoing maintenance and capital works under climate change conditions.</p> 
		
		<p>Stage 3 to Stage 5<br />
		The Climate Smart Seaports Tool does not address these areas of a climate risk assessment. Users need to take the information from the tool and use it to progress through these stages.
		</p> 
	</div> <!-- End of 5 Next Steps -->
	
	
	
	<div id="limitations" style="font-size: 10pt; text-align: justify; margin-top: 50px">
		<h3>6. Limitations</h3>
		
		<p>The proof-of-concept version of the Climate Smart Seaports tool only incorporates data for three NRM regions, the East Coast South, the Southern Slopes Vic East, and the Southern and South-Western Flatlands. Data availability differs across the regions.</p>

		<h6>Non-climate data</h6>
		<p>The ABS data looks at urban population pressure.  However, not all ports are located near urban centres, so for some ports, this data is not available. ABS-classified areas of urban pressure are not available for the Port of Eden, Port of Mallacoota, Port Welshpool and Esperance Ports.</p>
		
		<p>The trade data is based on publicly reported information.  Over time, some ports change their methodology for reporting, they may report on different indicators and may not have publicly available information spanning the timeframe required. Additionally, different ports choose to report different information, so comparability between ports can be difficult. For this reason, there needs to be some caution when interpreting the trade data.</p> 
		
		<p>Trade data is not currently available for port areas in the Gippsland Ports Region.</p>
		
		<h6>Observed climate</h6>
		<p>The national climate trend data is taken from the BoM website. This data is updated every year. The current data is correct for 2013 and will change in 2014...</p>
		
		<p>All data has been chosen to support the data sourced for the Future Climate section, however please note that the Future Climate is based on modelled data and is not directly comparable to the <strong>actual</strong> data sourced in this section.</p>
		
		<h6>Future climate</h6>
		<p>The future climate modelling is based on global climate models that are being refined by climate scientists all the time. The information provided by the models is the best possible at the time this application was created.  In late 2013, the use of scenarios as a way of describing possible futures is likely to be replaced by a description purely related to the amount of CO2 (or CO2 equivalent gasses in the atmosphere). This may lead to some differences in the way projections are described in 2014.</p>  
		
		<h6>Concrete deterioration application.</h6>
		<p>The Concrete deterioration application relies on users working through a separate application, and uploading this to the Climate Smart Seaports Tool.  This process is prone to human error, so users should check data carefully in this element.</p>
		
		<p>The Concrete deterioration application is not available for the Southern and South-Western Flatlands NRM.</p>
		
		<h6>Vulnerability application</h6>
		<p>Users need to be aware that this is not a robust vulnerability assessment. It serves only to indicate areas that may need further investigation.</p> 
				
	</div> <!-- End of 6 Limitations -->
	<!-- END OF GUIDELINES CONTENT -->
	
	
	<div id="acronyms" style="font-size: 10pt; text-align: justify; margin-top: 50px">
		<h3>Acronyms</h3>
		
		<strong>ABS</strong>: Australian Bureau of Statistics<br />
		<strong>ACORN-SAT</strong>: Australian Climate Observations Reference Network - Surface Air Temperature<br />
		<strong>BoM</strong>: Bureau of Meteorology<br />
		<strong>CMAR</strong>: CSIRO Marine and Atmospheric Research<br />
		<strong>CSIRO</strong>: Commonwealth Scientific and Industrial Research Organisation<br />
		<strong>GCM</strong>: Global Climate Model<br />
		<strong>IPCC</strong>: Intergovernmental Panel on Climate Change<br />
		<strong>NRM</strong>: Natural Resource Management<br />
	</div>
	
	
	<div id="glossary" style="font-size: 10pt; text-align: justify; margin-top: 50px">
		<h3>Glossary</h3>
		
		<strong>ACORN-SAT data</strong>: Data taken from local weather stations in the Australian Climate Observations Reference Network - Surface Air Temperature (ACORN-SAT). ACORN-SAT has been developed for monitoring climate variability and change in Australia. The dataset employs the latest analysis techniques and has had the data checked and adjusted to remove inaccuracies.<br />
		<strong>Administrator</strong>: A <i>User</i> with administration rights for the Climate Smart Seaports application. An <i>Administrator</i> can access a list of all the users, remove users and block their account. He/she can also access, any user's <i>Workboard</i>.<br />
		<strong>Category</strong>: A pre-defined category of <i>Data</i>. Categories appear as tabs in the <i>Workboard</i> page and allow filtering <i>Data</i> based on their Data Source. The Categories are Non-climate, Observed climate, Future climate and Applications.<br />
		<strong>Comment</strong>: Text added to a <i>Report</i> during the Analysis Stage containing analysis produced by the <i>User</i>.<br />
		<strong>Climate Model</strong>: see <i>Global Climate Model</i><br />
		<strong>CSIRO Climate Futures tool</strong>: The <a href="http://www.mssanz.org.au/modsim2011/F5/clarke.pdf">Climate Futures</a> tool was created to enable an assessment of the likelihood of combined changes in two climate variables (usually temperature and precipitation), and provides a sub-set of global climate models to work with. The development of the 'Climate Futures' tool assists the tailoring of scenarios to the specific needs of end-user applications. The software produces a matrix for the user which shows the number of models (the likelihood) in each category. These groups of models and their associated likelihood are then labelled as "most likely", "worst case" or "best case", or, in the case of this project, "most likely", "hot/dry" and "cool/wet".<br />
		<strong>Concrete Deterioration Model</strong>: Refers to the concrete deterioration engineering model (NCCARF-funded) which allows Users to compute the deterioration of concrete port Assets. This Model is created from a set of Excel template files which perform the computation and generate <i>Concrete Deterioration Model Output</i>.<br />
		<strong>Concrete Deterioration Model Asset</strong>: A port Asset is one that the <i>Concrete Deterioration Model</i> is able to compute the future deterioration e.g.: a concrete pile or section of a berth within a port's infrastructure.<br />
		<strong>Concrete Model Pre-defined Example</strong>: An example of <i>Concrete Deterioration Model Output</i> already loaded into the Climate Smart Seaports Tool in order to allow a User to use example data instead of going through the process of the <i>Concrete Deterioration Model Tool</i>.<br />
		<strong>Concrete Deterioration Model Tool</strong>: Web application developed in .NET in order to provide a web interface to the Concrete Deterioration Model. One can provide input for the Concrete Deterioration Model, and get it to generate <i>Concrete Deterioration Model Output</i> that can be downloaded.<br />
		<strong>Concrete Deterioration Model Output</strong>: Excel file produced by the <i>Concrete Deterioration Model</i> containing all the computed concrete deterioration data for a period of 70 years (2000 to 2070). This <i>Concrete Deterioration Model Output</i> can be uploaded into the Seaports application as part of the "Concrete Deterioration Model" Data Source.<br />
		<strong>Data</strong>: A piece of information from a <i>Data Source</i> that is added to a <i>Workboard</i> as an independent entity. Data can be added or removed from a <i>Workboard</i>, and later can be re-ordered, included or excluded from a Report.<br />
		<strong>Data Format</strong>: The way a piece of data is displayed. The <i>Data Format</i> is usually directly related to the <i>Data Source</i> (for example, data from the <i>Engineering Model</i> is formatted as a Graph followed by a table).<br />
		<strong>Data Source</strong>: An available provider of data. The available <i>Data Sources</i> are: CSIRO, CMAR, ACORN-SAT, BoM, Ports Australia, ABS, Concrete Deterioration Model, and custom files provided by the <i>User</i>.<br />
		<strong>Emission Scenario</strong>: A possible future greenhouse gas emission scenario. This is used as a parameter to compute the future evolution of climate variables. Two possible scenarios are taken into account in the Climate Smart Seaports Tool: a "High emissions" (corresponding to the IPCC's A1FI scenario) and a "Medium emissions" (corresponding to the IPCC's A1B scenario).<br /> 
		<strong>Global Climate Models</strong>: Mathematical representations of the behaviour of the planet's climate system through time. Global climate models interpret climate data to forecast the values of climate variables such as temperature, wind speed, sea level, etc. Each <i>NRM Region</i> has 3 references to <i>Climate Models</i> that in the application correspond to a future described as:  "Hotter &amp; Drier",  "Most Likely" or "Cooler &amp; Wetter". Note that for different <i>NRM Regions</i>, the same reference name can correspond to different <i>Climate Models</i> (example: the "Hotter &amp; Drier" reference may lead to the <i>Climate Model</i> X for Region A and to the <i>Climate Model</i> Y for Region B). For further reading, see: <a href="http://www.csiro.au/en/Outcomes/Climate/Reliability-Climate-Models.aspx">http://www.csiro.au/en/Outcomes/Climate/Reliability-Climate-Models.aspx</a><br />
		<strong>Private/Public</strong>: Relates to the privacy status of the <i>Workboard</i> and <i>Reports</i>. <i>Private</i> means that the <i>Workboard</i> or <i>Report</i> can only be viewed by the <i>User</i> who created it (and by the <i>Administrators</i>). <i>Public</i> means that any <i>User</i> can view the <i>Report</i> and that it appears in the results of searches and listings of <i>Reports</i> in the Climate Smart Seaports application. A Workboard and Drafts are always Private, and a Report becomes Public when it is <i>Published</i>.<br />
		<strong>Publish</strong>: An action that finalises a <i>Report</i> to become a <i>Public Report</i>. Once published the <i>Report</i> is stored permanently on the Climate Smart Seaports site and is accessible to all. The metadata from the <i>Report</i> is published to <a href="http://www.ands.org.au/resource/rif-cs.html">ANDS</a>. Publishing prevents a Report from being further edited or deleted, and makes it Public in the Climate Smart Seaports application.<br />
		<strong>Region</strong>: Refers to a Natural Resources Management (NRM) region of Australia. Three NRM Regions are taken into account in the Climate Smart Seaports application: "East Coast South", "Southern Slopes Vic East", and "Southern and Southwestern Flatlands".<br />
		<strong>Report</strong>: A document which is able to be saved and printed by the <i>User</i>, and which can then be <i>Published</i> and submitted to ANDS and is <i>Public</i> within the Climate Smart Seaport application.<br />
		<strong>Storyline</strong>: combination of <i>Climate Model</i>, <i>Emission Scenario</i> and <i>Region</i> which is used as a parameter for the different forecast of future climate data (CSIRO) or future concrete deterioration (<i>Engineering Model</i>). Different <i>storylines</i> produce different data for a same year.<br />
		<strong>User</strong>: An operator of the Climate Smart Seaports application, typically from port authorities or researchers.<br />
		<strong>Workboard</strong>: The <i>Data</i> gathering platform for the tool.<br />
		<strong>Workflow</strong>: The process followed by the User from starting a new <i>Workboard</i> to <i>Publishing a Report to ANDS</i>. The stages of the <i>Workflow</i> are: <i>Workboard creation - Setting scope and purpose</i>, <i>Data selection</i>, <i>Analysis Stage</i>, <i>Review &amp; Publish</i>.<br />
	</div>
</div>