<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="true" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" import="war.model.UserStory" %>
<%@ page language="java" import="war.model.User" %>
<%@ page language="java" import="war.model.DataElement" %>

<c:if test="${not empty userstory}">
<p>
	<button id="btnOpenAdd${dataelementsfilter}DataElementModalWindow" type="button" class="btn btn-icon btn-blue btn-plus" >
		<span></span>Add Data Element
	</button>
</p>
<div id="add${dataelementsfilter}DataElementModalWindow" class="box round first" title="New Data Element" style="display:none">
	<div class="block">
		<p><strong>1. Data Source:</strong></p>
		<table width="auto" height="auto" class="form">
			<tr>
				<td>Choose a data source:</td>
				<td class="col2">
					<select id="cbb${dataelementsfilter}DataSource" name="dataSource">
						<option value="none">- Select Data Source -</option>
						<c:if test="${dataelementsfilter == 'NonClimate'}"><option value="abs">ABS data</option></c:if>
						<c:if test="${dataelementsfilter == 'NonClimate'}"><option value="bitre">Ports Australia data</option></c:if>
						<c:if test="${dataelementsfilter == 'NonClimate'}"><option value="customFile">Custom file</option></c:if>
						<c:if test="${dataelementsfilter == 'ObservedClimate'}"><option value="past">CSIRO and BoM trend data</option></c:if>
						<c:if test="${dataelementsfilter == 'ObservedClimate'}"><option value="acornSat">ACORN-SAT data</option></c:if>
						<c:if test="${dataelementsfilter == 'Future'}"><option value="csiro">CSIRO</option></c:if>
						<c:if test="${dataelementsfilter == 'Future'}"><option value="cmar">CMAR</option></c:if>
						<c:if test="${dataelementsfilter == 'Applications'}"><option value="engineering">Concrete Deterioration Model</option></c:if>
						<c:if test="${dataelementsfilter == 'Applications'}"><option value="vulnerability">Vulnerability Assessment</option></c:if>
					</select>
				</td>
			</tr>
		</table>
		<p>
		
		<c:if test="${dataelementsfilter == 'NonClimate'}">
		<div id="absDataForm" class="dataElementForm">
			<form:form method="post" action="/CSS/auth/workboard/addAbsData#tabs-non-climate-context">
				<input type="hidden" name="userstoryid" value="${userstory.id}" />
				<p><strong>2. ABS Data Element Options:</strong></p>
				<table width="auto" height="auto" class="form">
					<tr>
						<td>
							Variable&nbsp;
							<a href="#" class="helpTooltip" title="<p>Data on Urban Pressure was extracted from the <i>ABS Regional Population Growth Catalogue (3218.0)</i>, which provides decadal population estimates by Significant Urban Area from 2001 to 2011. The data presented in this element is annual percentage urban population change and total urban population for the urban area closest to the nominated port</p>"><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<select id="cbbAbsDataVariable" name="variable">
								<option value="1">Population change data for urban areas</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							Seaport&nbsp;
							<a href="#" class="helpTooltip" title="Re-select your port"><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<c:if test="${not empty regionSeaports}">
							<select id="cbbAbsDataSeaport" name="seaport">
								<c:forEach items="${regionSeaports}" var="regionSeaport" varStatus="regionSeaportLoopStatus">
									<option value="${regionSeaport.code}" <c:if test="${empty regionSeaport.urbanCenter}">disabled</c:if>>
										<c:choose>
											<c:when test="${not empty regionSeaport.urbanCenter}">
												${regionSeaport.name} (${regionSeaport.urbanCenter})
											</c:when>
											<c:otherwise>
												${regionSeaport.name} (No Data Available)
											</c:otherwise>
										</c:choose>
									</option>
								</c:forEach>
							</select>
							</c:if>
						</td>
					</tr>
				</table>
				<p><strong>3. Display Options:</strong></p>
				<table width="auto" height="auto" class="form">
					<tr>
						<td>Display data as:</td>
						<td class="col2">
							<input type="radio" name="displayType" value="graph" checked="checked" /> Graph 
							<input type="radio" name="displayType" value="picture" /> Map image
							<input type="radio" name="displayType" value="table" /> Table
						</td>
					</tr>
				</table>
				<button type="button" class="btn btn-icon btn-blue btn-plus" onclick="submit();" >
					<span></span>Add ABS Data
				</button>
			</form:form>
		</div>
		</c:if>
		
				
		<c:if test="${dataelementsfilter == 'NonClimate'}">
		<div id="bitreDataForm" class="dataElementForm">
			<form:form method="post" action="/CSS/auth/workboard/addBitreData#tabs-non-climate-context">
				<input type="hidden" name="userstoryid" value="${userstory.id}" />
				<p><strong>2. Ports Australia Data Element Options:</strong></p>
				<table width="auto" height="auto" class="form">
					<tr>
						<td>
							Variable&nbsp;
							<a href="#" class="helpTooltip" title="<p>Freight data for most Australian ports is collated by Ports Australia - the peak national body representing ports and marine authorities across Australia. A range of freight statistics are available across all regions for the last decade; financial year 2000/01 through to 2011/12.<br /><br />Data that is available within the Climate Smart Seaports tool includes:<ul><li>Decadal trends in freight throughput by cargo type;</li><li>Decadal trends in port calls by commercial vessel type; and</li><li>Decadal trends in exports by commodity type</li></ul></p>"><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<select id="cbbBitreDataVariable" name="variableCategory">
								<option value="1">Freight throughput by cargo type</option>
								<option value="2">Commercial vessels calls by type</option>
								<option value="3">Export commodities by type</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							Seaport&nbsp;
							<a href="#" class="helpTooltip" title="Re-select your port"><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<c:if test="${not empty regionSeaports}">
							<select id="cbbbitreDataSeaport" name="seaport">
								<c:forEach items="${regionSeaports}" var="regionSeaport" varStatus="regionSeaportLoopStatus">
									<option value="${regionSeaport.code}">${regionSeaport.name}</option>
								</c:forEach>
							</select>
							</c:if>
						</td>
					</tr>
				</table>
				<p><strong>3. Display Options:</strong></p>
				<table width="auto" height="auto" class="form">
					<tr>
						<td>Display data as:</td>
						<td class="col2">
							<input type="radio" name="displayType" value="graph" checked="checked" /> Graph
							<input type="radio" name="displayType" value="table" /> Table
						</td>
					</tr>
				</table>
				<button type="button" class="btn btn-icon btn-blue btn-plus" onclick="submit();" >
					<span></span>Add Ports Australia Data
				</button>
			</form:form>
		</div>
		</c:if>
		
		<c:if test="${dataelementsfilter == 'NonClimate'}">
		<div id="customFileDataForm" class="dataElementForm">
			<form:form method="post" action="/CSS/auth/workboard/upload?id=${userstory.id}#tabs-non-climate-context" enctype="multipart/form-data">
				<p><strong>2. Custom Data Element Options:</strong></p>
				<table width="auto" height="auto" class="form">
					<tr>
						<td>Select a file to upload&nbsp;
							<a href="#" class="helpTooltip" title="Port authorities have extensive data regarding their organisation's characteristics, throughput and local context. Therefore, this section allows the user to upload custom data files.<br /><br />These files may include graphic depictions of the port system and its assets, information regarding organisational objectives and/or current risks, or data on throughput volume or the types of activity that characterise the port.<br /><br />Acceptable formats are Text (.txt) or Image (.jpeg or .jpg) formats."><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<input type="file" name="file" id="customFileUpload" />
							<p class="hint">
								<i>Accepted formats: Text (txt), table (csv) or Image (jpeg)</i>
							</p>
						</td>
					</tr>
				</table>
				<button type="button" class="btn btn-icon btn-blue btn-plus" onclick="submit();" >
					<span></span>Add Custom Data
				</button>
			</form:form>
		</div>
		</c:if>
		
		<c:if test="${dataelementsfilter == 'ObservedClimate'}">
		<div id="pastDataForm" class="dataElementForm">
			<form:form method="post" action="/CSS/auth/workboard/addPastData#tabs-observed-climate">
			<input type="hidden" name="userstoryid" value="${userstory.id}" />
			<p><strong>2. CSIRO and BoM trend Data Options:</strong></p>
			<table width="auto" height="auto" class="form">
				<tr>
					<td>
						Variable&nbsp;
						<a href="#" class="helpTooltip" title="<p>The data shows how temperature or rainfall has changed over time. It covers national trends in temperature and rainfall for the period 1970 to 2012.</p><p>There are other variables and different ways of presenting the data that can be sourced from the <a href=&quot;http://www.bom.gov.au/cgi-bin/climate/change/trendmaps.cgi&quot; target=&quot;_blank&quot;>BoM's site</a>.</p><p>The sea-level data includes a graph of sea-level rise measurements from 1880 - 2012 and a map of more recent trends around Australia covering the period 1993 to 2011.</p><p>Trend data needs to be interpreted with caution. Trend values are evened out, but the actual change indicated may not have been gradual.</p><p>The trend values use past observations and cannot show the rate of future change.</p>"><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
					</td>
					<td class="col2">
						<select id="cbbPastDataId" name="pastDataId">
							<option value="1">Trend in mean temperatures</option>
							<option value="2">Trend in maximum temperatures</option>
							<option value="3">Trend in total annual rainfall</option>
							<option value="4">Long-term sea level rise measurements</option>
							<option value="5">Shorter-term changes in sea level</option>
						</select>
					</td>
				</tr>
			</table>
			<button type="button" class="btn btn-icon btn-blue btn-plus" onclick="submit();" >
				<span></span>Add CSIRO and BoM trend Data
			</button>
			</form:form>
		</div>
		</c:if>
		
		<c:if test="${dataelementsfilter == 'ObservedClimate'}">
		<div id="acornSatDataForm" class="dataElementForm">
			<form:form method="post" action="/CSS/auth/workboard/addAcornSatData#tabs-observed-climate">
			<input type="hidden" name="userstoryid" value="${userstory.id}" />
			<p><strong>2. ACORN-SAT Data Options:</strong></p>
			<table width="auto" height="auto" class="form">
				<tr>
					<td>
						Type of measurements&nbsp;
						<a href="#" class="helpTooltip" title="<p>The local weather stations chosen for this application are part of the Australian Climate Observations Reference Network - Surface Air Temperature (ACORN-SAT) dataset. ACORN-SAT has been developed for monitoring climate variability and change in Australia, and it employs the latest analysis techniques.</p><p>The dataset presents data for temperature, rainfall, relative humidity and wind speed at particular weather stations. These are for the period 1981 - 2010.</p>Additionally, the ACORN-SAT data presents measurements of extremes in the local area. These include the highest temperature recorded, highest rainfall and highest wind gust, along with the date of these occurrences"><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
					</td>
					<td class="col2">
						<select id="cbbAcornSatDataExtreme" name="acornSatExtremeData">
							<option value="mean">Mean measurements</option>
							<option value="extreme">Extreme measurements</option>
						</select>
					</td>
				</tr>
			</table>
			<button type="button" class="btn btn-icon btn-blue btn-plus" onclick="submit();" >
				<span></span>Add ACORN-SAT Data
			</button>
			</form:form>
		</div>
		</c:if>
		
		<c:if test="${dataelementsfilter == 'Future'}">
		<div id="csiroDataForm" class="dataElementForm">
			<form:form method="post" action="/CSS/auth/workboard/addCsiroData#tabs-future-climate" modelAttribute="climateData">
				<input type="hidden" name="userstoryid" value="${userstory.id}" />
				<p><strong>2. CSIRO Data Options:</strong></p>
				<table width="auto" height="auto" class="form">
					<tr>
						<td>
							Variable&nbsp;
							<a href="#" class="helpTooltip" title="<p><h6>Mean Temperature</h6>Mean air temperature in degrees Celsius (°C) as measured at 2 m above ground. Values are given as change from modelled baseline (1981-2000) climate.</p><p><h6>Rainfall</h6>Mean rainfall in millimetres (mm). Values are given as change from modelled baseline (1981-2000) climate.</p><p><h6>Daily Relative Humidity</h6>Calculated at 2 m above ground and expressed in percent (%). Values are given as change from modelled baseline (1981-2000) climate.</p><p><h6>Wind Speed</h6>Mean wind speed, in metres per second (m/sec) as measured at 10m above the ground. Values are given as change from modelled baseline (1981-2000) climate.</p>"><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<select id="cbbClimateVariable" name="climateVariable">
								<option value="All">All Variables</option>
								<option value="Temperature">Temperature</option>
								<option value="Wind speed">Wind speed</option>
								<option value="Rainfall">Rainfall</option>
								<option value="Relative humidity">Relative humidity</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							Emission Scenario&nbsp;
							<a href="#" class="helpTooltip" title="<p>The <b>emission scenarios</b> represent the future development of greenhouse gas emissions and are based on assumptions about economic, technological and population growth. The two emissions scenarios that are available here are from the 'A1 storyline' and were developed by the IPCC Special Report on Emissions Scenarios (SRES).</p><p>As a general guide:</p><p><b>Emission Scenario A1B</b>: medium CO2 emissions, peaking around 2030</p><p><b>Emission Scenario A1FI</b>: high CO2 emissions, increasing throughout the 21st century</p>"><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<select id="cbbClimateEmissionScenario" name="climateEmissionScenario">
								<option value="A1B">Medium CO2 emissions (A1B)</option>
								<option value="A1FI">High CO2 emissions (A1FI)</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							Climate Model&nbsp;
							<a href="#" class="helpTooltip" title="<p><b>Global climate models</b> (GCMs) are mathematical representations of the behaviour of the planet's climate system through time. Each mathematical equation is the basis for complex computer programs used for simulating the atmosphere or oceans of the Earth.</p><p>For this portal, CSIRO's Climate Futures software was used to select models by considering the alignment of model results over each of the natural resource management regions. 18 climate models were sub-divided into pre-defined categories such as &quot;Hotter, Drier&quot; and then assigned a relative likelihood based on the number of climate models that fell within that category. For example, if 9 of 18 models fell into the &quot;Hotter - Drier&quot; category, it was given a relative likelihood of 50%.</p><p>Three model categories are used here:<br/>A '<b>most likely</b>'* future: hotter and little change in rainfall.<br/>A '<b>hotter and drier</b>' future: much hotter and much drier.<br/>A '<b>cooler and wetter</b>' future: cooler and wetter.<br/></p><p>*Category represented by the greatest number of models that must be greater than or equal to 33% of the total number of models.</p>"><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<select id="cbbClimateClimateModel" name="climateModel">
								<option value="Most Likely">Most Likely</option>
								<option value="Hotter &amp; Drier">Hotter &amp; Drier</option>
								<option value="Cooler &amp; Wetter">Cooler &amp; Wetter</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							Time Period&nbsp;
							<a href="#" class="helpTooltip" title="<p><b>Time periods</b> are expressed relative to the 1981-2000 baseline period and are centred on a given decade. For example, the 2030s time period refers to the period 2020-2039.</p><p>Three future time periods are available: <b>2030</b>, <b>2055</b> and <b>2070</b>.</p>"><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<select id="cbbYear" name="year">
								<option value="2030">2030</option>
								<option value="2055">2055</option>
								<option value="2070">2070</option>
							</select>
						</td>
					</tr>
				</table>
				<p><strong>3. Display Options:</strong></p>
				<table width="auto" height="auto" class="form">
					<tr>
						<td>Display data as:</td>
						<td class="col2">
							<input type="radio" name="displayType" value="picture" checked="checked" /> Map image
							<input type="radio" name="displayType" value="table" /> Table<br />
							<span class="hint"><i>Maps are available only for the 'Most Likely'<br />model and the 'Medium' emission scenario.</i></span>
						</td>
					</tr>
				</table>
				<button type="button" class="btn btn-icon btn-blue btn-plus" onclick="submit();" >
					<span></span>Add CSIRO Data
				</button>
			</form:form>
		</div>
		</c:if>
		
		<c:if test="${dataelementsfilter == 'Future'}">
		<div id="cmarDataForm" class="dataElementForm">
			<form:form method="post" action="/CSS/auth/workboard/addCmarData#tabs-future-climate" modelAttribute="climateData">
				<input type="hidden" name="userstoryid" value="${userstory.id}" />
				<p><strong>2. CMAR Data Options:</strong></p>
				<table width="auto" height="auto" class="form">
					<tr>
						<td>
							Variable&nbsp;
							<a href="#" class="helpTooltip" title="<p><h6>Sea Level Rise</h6>Sea level data was derived from the CSIRO Marine and Atmospheric Research (CMAR). Data provided by CMAR was taken from the average of 17 climate model simulations for the medium (A1B) emissions scenario. This average was plotted around the Australian coastline to allow the identification of particular locations. Figures from these locations were then added to the globally averaged sea level projections for 2030 and 2070, using only the medium emissions scenario. These global projections included estimates for ice-sheet melt and were taken from the 50th percentile range.</p>"><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<select id="cbbClimateVariable" name="climateVariable">
								<option value="Sea Level Rise">Sea Level Rise</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							Emission Scenario&nbsp;
							<a href="#" class="helpTooltip" title="<p>The <b>emission scenarios</b> represent the future development of greenhouse gas emissions and are based on assumptions about economic, technological and population growth. The two emissions scenarios that are available here are from the 'A1 storyline' and were developed by the IPCC Special Report on Emissions Scenarios (SRES).</p><p>As a general guide:</p><p><b>Emission Scenario A1B</b>: medium CO2 emissions, peaking around 2030</p><p><b>Emission Scenario A1FI</b>: high CO2 emissions, increasing throughout the 21st century</p>"><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<select id="cbbClimateEmissionScenario" name="climateEmissionScenario" disabled="disabled">
								<option value="A1B">Medium CO2 emissions (A1B)</option>
								<!-- <option value="A1FI">High CO2 emissions (A1FI)</option> -->
							</select>
						</td>
					</tr>
					<tr>
						<td>
							Climate Model&nbsp;
							<a href="#" class="helpTooltip" title="<p><b>Global climate models</b> (GCMs) are mathematical representations of the behaviour of the planet's climate system through time. Each mathematical equation is the basis for complex computer programs used for simulating the atmosphere or oceans of the Earth.</p><p>For this portal, CSIRO's Climate Futures software was used to select models by considering the alignment of model results over each of the natural resource management regions. 18 climate models were sub-divided into pre-defined categories such as &quot;Hotter, Drier&quot; and then assigned a relative likelihood based on the number of climate models that fell within that category. For example, if 9 of 18 models fell into the &quot;Warmer - Drier&quot; category, it was given a relative likelihood of 50%.</p><p>Three model categories are used here:<br/>A '<b>most likely</b>'* future: hotter and little change in rainfall.<br/>A '<b>hotter and drier</b>' future: much hotter and much drier.<br/>A '<b>cooler and wetter</b>' future: cooler and wetter.<br/></p><p>*Category represented by the greatest number of models that must be greater than or equal to 33% of the total number of models.</p>"><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<select id="cbbClimateClimateModel" name="climateModel" disabled="disabled">
								<option value="Most Likely">Most Likely</option>
								<!-- <option value="Hotter &amp; Drier">Hotter &amp; Drier</option> -->
								<!-- <option value="Cooler &amp; Wetter">Cooler &amp; Wetter</option> -->
							</select>
						</td>
					</tr>
					<tr>
						<td>
							Time Period&nbsp;
							<a href="#" class="helpTooltip" title="<p><b>Time periods</b> are expressed relative to the 1981-2000 baseline period and are centred on a given decade. For example, the 2030s time period refers to the period 2020-2039.</p><p>Three future time periods are available: <b>2030</b>, <b>2055</b> and <b>2070</b>.</p>"><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<select id="cbbYear" name="year">
								<option value="2030">2030</option>
								<!-- <option value="2055">2055</option> -->
								<option value="2070">2070</option>
							</select>
						</td>
					</tr>
				</table>
				<p><strong>3. Display Options:</strong></p>
				<table width="auto" height="auto" class="form">
					<tr>
						<td>Include map image:</td>
						<td class="col2">
							<input type="checkbox" id="cbIncludePictures" name="includePictures" />
							<span class="hint"><i>Check this if you want to include pictures<br />of the region map corresponding to the data.</i></span>  
						</td>
					</tr>
				</table>
				<button type="button" class="btn btn-icon btn-blue btn-plus" onclick="submit();" >
					<span></span>Add CMAR Data
				</button>
			</form:form>
		</div>
		</c:if>
		
		<c:if test="${dataelementsfilter == 'Applications'}">
		<div id="engineeringDataForm" class="dataElementForm">
			<form:form id="formEngineeringData" method="post" action="/CSS/auth/workboard/addEngineeringData?id=${userstory.id}#tabs-applications" enctype="multipart/form-data">
				<p><strong>2. Concrete Deterioration Model Data:</strong></p>
				
				<input type="hidden" name="sourceType" id="hdnEngineeringSourceType" value="upload" />
				
				<table width="auto" height="auto" class="form">
					<tr>
						<td>
							<input type="radio" name="rdEngineeringSourceType" value="example" /> 
							Use a predefined example for this region
						</td>
					</tr>
					<tr>
						<td>
							<input type="radio" name="rdEngineeringSourceType" value="upload" checked="checked" /> 
							Or select a file to upload&nbsp;
							<a href="#" class="helpTooltip" title="Please use the <b><a href=&quot;http://10.118.96.11/ccimt&quot; title=&quot;Go to the concrete deterioration model tool&quot; target=&quot;blank&quot;>concrete deterioration modelling tool</a></b> to import your port's assets data. That tool will verify each entry and provide a forecast of the concrete deterioration for each asset, under the form of Excel files.</p><p>You can upload these Excel files here to use the forecast data in you workboard.</p>"><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<input type="file" name="file" id="file" />
							<p class="hint" style="text-align:right">
								<i>Select an Excel file (.xls) generated by the <a href="http://10.118.96.11/ccimt" title="Go to the concrete deterioration model tool" target="blank">concrete deterioration tool</a>.</i>
							</p>
						</td>
					</tr>
					<tr>
						<td style="padding-top:50px">
							Select a variable to use for the Data Element&nbsp;
							<a href="#" class="helpTooltip" title="Each excel file and example contains data for many engineering variables. Choose one of these variables to use in the new Data Element. If you need more than one variable, add several Data Elements to your workboard."><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<input type="hidden" id="hdnEngVariableCategory" name="engVariableCategory" value="${chlorideEngineeringModelVariables[0].category}" />
							<select id="cbbEngineeringVariable" name="engVariable">
								<option disabled class="dropDownTitle">${chlorideEngineeringModelVariables[0].category}</option>
								<c:forEach items="${chlorideEngineeringModelVariables}" var="engineeringModelVariable" varStatus="loop">
									<option value="${engineeringModelVariable.name}" title="${engineeringModelVariable.category}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${engineeringModelVariable.name}</option>
								</c:forEach>
								<option disabled class="dropDownTitle">${carbonationEngineeringModelVariables[0].category}</option>
								<c:forEach items="${carbonationEngineeringModelVariables}" var="engineeringModelVariable" varStatus="loop">
									<option value="${engineeringModelVariable.name}" title="${engineeringModelVariable.category}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${engineeringModelVariable.name}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</table>
				<p><strong>3. Display Options:</strong></p>
				<table width="auto" height="auto" class="form">
					<tr>
						<td>Display data as:</td>
						<td class="col2">
							<input type="radio" name="displayType" value="graph" checked="checked" /> Graph
							<input type="radio" name="displayType" value="table" /> Table
						</td>
					</tr>
				</table>
				<button type="button" id="btnAddEngineeringModelDataElement" class="btn btn-icon btn-blue btn-plus" onclick="submit();" >
					<span></span>Add Concrete Deterioration Model Data
				</button><br />
				<div id="loading" class="center">
					<p>Extracting data and preparing your data element...</p>
						<img src="<c:url value="/resources/img/theme/ajax-loader.gif" />" alt="Loading..." title="Loading..." /><br />
					<p>Please don't refresh the page.</p>
				</div>
			</form:form>
		</div>
		</c:if>

		<c:if test="${dataelementsfilter == 'Applications'}">
		<div id="vulnerabilityDataForm" class="dataElementForm">
			<form:form id="formVulnerabilityData" method="post" action="/CSS/auth/workboard/addVulnerability#tabs-applications" enctype="multipart/form-data">
				<input type="hidden" name="userstoryid" value="${userstory.id}" />
				
				<p><strong>2. Current Vulnerability Assessment Options:</strong></p>
				
				<p class="hint"><i>This section identifies your current vulnerability to particular climate related events. When considering the questions below, think of the impact on Port assets (machinery, buildings, equipment), infrastructure (drainage, rail, road, berths), and people (injuries, work disruptions). Please complete this page for each different climate related event that has impacted the port in recent years.</i></p>
				
				<table width="auto" height="auto" class="form">
					<tr>
						<td class="top">
							Weather event&nbsp;
							<a href="#" class="helpTooltip" title="Disruptive climate-related events are those that caused a significant alteration to the &quot;normal&quot; functioning of the port, whether this was for a few hours, or a few weeks."><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<select id="cbbWeatherEventType" name="weatherEventType">
								<option value="Heatwave">Heatwave</option>
								<option value="Strong wind">Strong wind</option>
								<option value="Heavy rain">Heavy rain</option>
								<option value="Electrical storm">Electrical storm</option>
								<option value="Storm (wind and rain combined)">Storm (wind and rain combined)</option>
								<option value="Cyclone">Cyclone</option>
								<option value="Hail">Hail</option>
								<option value="Storm surge">Storm surge</option>
								<option value="Sea level rise">Sea level rise</option>
								<option value="Fog">Fog</option>
								<option value="Drought">Drought</option>
								<option value="Flood">Flood</option>
								<option value="Other">Other</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="top">
							Date&nbsp;
							<a href="#" class="helpTooltip" title="The year the disruptive event occured"><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<select id="cbbWeatherEventYear" name="weatherEventYear">					
								<c:forEach var="i" begin="2003" end="2013" step="1" varStatus ="status">
									<option value="<c:out value="${i}" />"><c:out value="${i}" /></option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<td class="top">
							Direct or indirect impact&nbsp;
							<a href="#" class="helpTooltip" title="Direct impacts are those that specifically impacted the port, for example, heavy rain on site. Indirect impacts are those that impacted the supply chain to the port, causing a flow-on impact to the port business. Select <b>either</b> Direct or Indirect. If the event caused both Direct and Indirect impacts, select the one that was <b>MORE</b> significant."><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<input type="radio" name="weatherEventDirect" value="direct" checked="checked" /> Direct
							<input type="radio" name="weatherEventDirect" value="indirect" /> Indirect
						</td>
					</tr>
					<tr style="margin-bottom: 10px">
						<td class="top">Impact&nbsp;
							<a href="#" class="helpTooltip" title="Describe how the climate related event impacted your business. E.g.: Rain caused onsite flooding; the cyclone damaged rail-lines from suppliers to the port."><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<textarea id="txtWeatherEventImpact" name="weatherEventImpact" rows="5" class="small"></textarea>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<p>
								Identify and rate the business consequences of these impacts. Please complete the whole table&nbsp; 
								<a href="#" class="helpTooltip" title="<p>The impacts identified in the previous question will have business-related consequences for the port.</p><p>Listed is a broad selection of common consequences.  For each listed consequence, rate its impact from &quot;no impact&quot; through to &quot;extreme&quot;. E.g: Flooding may have led to waste flow from the site - causing a Moderate Environmental Impact; and a Major Interruption to logistics operations.</p><p>Rating of &quot;No impact&quot; through to &quot;Extreme&quot; should align with your organisational risk consequence table. If you do not have access to this, refer to the <a href=&quot;/CSS/public/guidelines&quot; title=&quot;View the Guideline page&quot; target=&quot;_blan&quot;>Guidance Document</a>.</p>"><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
							</p>
						</td>
					</tr>
					<tr>
						<td class="top">
							Lost time due to staff or contractor injuries&nbsp;
							<a href="#" class="helpTooltip" title="A lost-time injury is one that resulted in a fatality, permanent disability or time lost from work of one day/shift or more"><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<input type="radio" name="weatherEventConsequence1" value="0" checked="checked" /> No impact
							<input type="radio" name="weatherEventConsequence1" value="1" /> Insignificant
							<input type="radio" name="weatherEventConsequence1" value="2" /> Moderate
							<input type="radio" name="weatherEventConsequence1" value="3" /> Major
							<input type="radio" name="weatherEventConsequence1" value="4" /> Extreme
						</td>
					</tr>
					<tr>
						<td class="top">
							Staff not able to attend work&nbsp;
							<a href="#" class="helpTooltip" title="Events outside the port may restrict staff access, E.g.: staff are in the local CFA, and fighting fires during heatwave"><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<input type="radio" name="weatherEventConsequence2" value="0" checked="checked" /> No impact
							<input type="radio" name="weatherEventConsequence2" value="1" /> Insignificant
							<input type="radio" name="weatherEventConsequence2" value="2" /> Moderate
							<input type="radio" name="weatherEventConsequence2" value="3" /> Major
							<input type="radio" name="weatherEventConsequence2" value="4" /> Extreme
						</td>
					</tr>
					<tr>
						<td class="top">Increased annual Workcover costs:</td>
						<td class="col2">
							<input type="radio" name="weatherEventConsequence3" value="0" checked="checked" /> No impact
							<input type="radio" name="weatherEventConsequence3" value="1" /> Insignificant
							<input type="radio" name="weatherEventConsequence3" value="2" /> Moderate
							<input type="radio" name="weatherEventConsequence3" value="3" /> Major
							<input type="radio" name="weatherEventConsequence3" value="4" /> Extreme
						</td>
					</tr>
					<tr>
						<td class="top">Increased maintenance costs:</td>
						<td class="col2">
							<input type="radio" name="weatherEventConsequence4" value="0" checked="checked" /> No impact
							<input type="radio" name="weatherEventConsequence4" value="1" /> Insignificant
							<input type="radio" name="weatherEventConsequence4" value="2" /> Moderate
							<input type="radio" name="weatherEventConsequence4" value="3" /> Major
							<input type="radio" name="weatherEventConsequence4" value="4" /> Extreme
						</td>
					</tr>
					<tr>
						<td class="top">Deferment of capital expenditure:</td>
						<td class="col2">
							<input type="radio" name="weatherEventConsequence5" value="0" checked="checked" /> No impact
							<input type="radio" name="weatherEventConsequence5" value="1" /> Insignificant
							<input type="radio" name="weatherEventConsequence5" value="2" /> Moderate
							<input type="radio" name="weatherEventConsequence5" value="3" /> Major
							<input type="radio" name="weatherEventConsequence5" value="4" /> Extreme
						</td>
					</tr>
					<tr>
						<td class="top">
							Increased insurance costs&nbsp;
							<a href="#" class="helpTooltip" title="Insurance premiums may increase due to direct impacts on the port, or due to the perceived increased risk to the area"><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<input type="radio" name="weatherEventConsequence6" value="0" checked="checked" /> No impact
							<input type="radio" name="weatherEventConsequence6" value="1" /> Insignificant
							<input type="radio" name="weatherEventConsequence6" value="2" /> Moderate
							<input type="radio" name="weatherEventConsequence6" value="3" /> Major
							<input type="radio" name="weatherEventConsequence6" value="4" /> Extreme
						</td>
					</tr>
					<tr>
						<td class="top">
							Adverse reputational impact&nbsp;
							<a href="#" class="helpTooltip" title="Reputation may be affected amongst the local community, or clients and other stakeholders, E.g.: negative media reports; or perceived unreliability of port due to disruption caused by climate event."><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<input type="radio" name="weatherEventConsequence7" value="0" checked="checked" /> No impact
							<input type="radio" name="weatherEventConsequence7" value="1" /> Insignificant
							<input type="radio" name="weatherEventConsequence7" value="2" /> Moderate
							<input type="radio" name="weatherEventConsequence7" value="3" /> Major
							<input type="radio" name="weatherEventConsequence7" value="4" /> Extreme
						</td>
					</tr>
					<tr>
						<td class="top">
							Environmental impact&nbsp;
							<a href="#" class="helpTooltip" title="Any impact on the natural environment caused by port operations as a result of the climate related event, this may be excessive dust due to dry, windy conditions; contaminated water from the site; excessive fumes."><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<input type="radio" name="weatherEventConsequence8" value="0" checked="checked" /> No impact
							<input type="radio" name="weatherEventConsequence8" value="1" /> Insignificant
							<input type="radio" name="weatherEventConsequence8" value="2" /> Moderate
							<input type="radio" name="weatherEventConsequence8" value="3" /> Major
							<input type="radio" name="weatherEventConsequence8" value="4" /> Extreme
						</td>
					</tr>
					<tr>
						<td class="top">
							Regulatory impact&nbsp;
							<a href="#" class="helpTooltip" title="Regulatory impact refers to a contravention of a regulation or legal requirement, E.g.; caused by environmental contravention as above, or workforce safety contravention."><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<input type="radio" name="weatherEventConsequence9" value="0" checked="checked" /> No impact
							<input type="radio" name="weatherEventConsequence9" value="1" /> Insignificant
							<input type="radio" name="weatherEventConsequence9" value="2" /> Moderate
							<input type="radio" name="weatherEventConsequence9" value="3" /> Major
							<input type="radio" name="weatherEventConsequence9" value="4" /> Extreme
						</td>
					</tr>
					<tr>
						<td class="top">
							Interruption/halt to logistics operations&nbsp;
							<a href="#" class="helpTooltip" title="A halt to the moving of goods to and from boats, and across the port; also an impact to storage on site."><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<input type="radio" name="weatherEventConsequence10" value="0" checked="checked" /> No impact
							<input type="radio" name="weatherEventConsequence10" value="1" /> Insignificant
							<input type="radio" name="weatherEventConsequence10" value="2" /> Moderate
							<input type="radio" name="weatherEventConsequence10" value="3" /> Major
							<input type="radio" name="weatherEventConsequence10" value="4" /> Extreme
						</td>
					</tr>
					<tr>
						<td class="top">Interruption to boat movements&nbsp;
							<a href="#" class="helpTooltip" title="A change in scheduled berthing or departing from berth due to impacts of weather or forecast storms/cyclones."><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<input type="radio" name="weatherEventConsequence11" value="0" checked="checked" /> No impact
							<input type="radio" name="weatherEventConsequence11" value="1" /> Insignificant
							<input type="radio" name="weatherEventConsequence11" value="2" /> Moderate
							<input type="radio" name="weatherEventConsequence11" value="3" /> Major
							<input type="radio" name="weatherEventConsequence11" value="4" /> Extreme
						</td>
					</tr>
					<tr>
						<td class="top">
							Altered dredging schedule&nbsp;
							<a href="#" class="helpTooltip" title="Dredging activity may increase or decrease after storms or flooding or other weather events."><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<input type="radio" name="weatherEventConsequence12" value="0" checked="checked" /> No impact
							<input type="radio" name="weatherEventConsequence12" value="1" /> Insignificant
							<input type="radio" name="weatherEventConsequence12" value="2" /> Moderate
							<input type="radio" name="weatherEventConsequence12" value="3" /> Major
							<input type="radio" name="weatherEventConsequence12" value="4" /> Extreme
						</td>
					</tr>
					<tr>
						<td class="top">
							Other business consequences&nbsp;
							<a href="#" class="helpTooltip" title="Specify in the text box below any other business consequences not listed above."><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>:
						</td>
						<td class="col2">
							<textarea id="txtWeatherEventConsequencesOther" name="weatherEventConsequencesOther" rows="5" class="small"></textarea>
						</td>
					</tr>
					<tr>
						<td class="top">Would you say your response was adequate?</td>
						<td class="col2">
							<input type="radio" name="weatherEventResponseAdequate" value="adequate" checked="checked" /> Yes
							<input type="radio" name="weatherEventResponseAdequate" value="no" /> No
						</td>
					</tr>
					<tr>
						<td class="top">
							What were the changes implemented as a result of this event?&nbsp;
							<a href="#" class="helpTooltip" title="Changes may be to management systems, to safety protocols, maintenance processes, communication protocols and so forth."><img src="<c:url value="/resources/img/icons/information.png" />" alt="Help" /></a>
						</td>
						<td class="col2">
							<textarea id="txtWeatherEventChanges" name="weatherEventChanges" rows="5" class="small"></textarea>
						</td>
					</tr>
				</table>
				<button type="button" id="btnAddVulnerabilityMatrixDataElement" class="btn btn-icon btn-blue btn-plus" onclick="submit();" >
					<span></span>Add Vulnerability Assessment Data
				</button><br />
			</form:form>
		</div>
		</c:if>
	</div>
</div>
<script type="text/javascript">
	$(function () {		
		setTimeout(function(){
		
		// Dynamic forms display from the "Data Source" dropdownlist
		$('select#cbb${dataelementsfilter}DataSource').change(function() {
			$('.dataElementForm').hide();			
			var selectedValue = $('select#cbb${dataelementsfilter}DataSource').val();
			if (selectedValue != "none")
				$("#" + selectedValue + "DataForm").show();
			
			$("#add${dataelementsfilter}DataElementModalWindow").dialog('option', 'position', 'center');
		});
		
		// "Loading" message in the Engineering Model Data Element form
       	$("#loading").hide();
		$("#btnAddEngineeringModelDataElement").click (function () {
			$("#btnAddEngineeringModelDataElement").hide();
			$("#loading").show();
		});
		
		// Radio button to choose the source of Engineering Model Data Element
		$("input[name='rdEngineeringSourceType']").change(function(){
			var selectedRadio = $("input[name='rdEngineeringSourceType']:checked").val();
			$("#hdnEngineeringSourceType").val(selectedRadio);
		});
		
		// Variable selection updates the hidden engineering model category field
		$("#cbbEngineeringVariable").change(function(){
			var selectedCategory = $("#cbbEngineeringVariable option:selected").attr("title");
			$("#hdnEngVariableCategory").val(selectedCategory);
		});
		
		// Hiding all data element addition forms by default 
		$('.dataElementForm').hide();
		
		// Open the right modal window on click
		setupDialogBox("add${dataelementsfilter}DataElementModalWindow", "btnOpenAdd${dataelementsfilter}DataElementModalWindow");

		}, 0);
	});
</script>
</c:if>