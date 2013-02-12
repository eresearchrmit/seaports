<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="true"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" import="war.model.UserStory" %>
<%@ page language="java" import="war.model.User" %>
<%@ page language="java" import="war.model.DataElement" %>

<div class="grid_12">
	<h2> ${userstory.name} </h2>
</div>
<div class="clear"></div>

<div class="grid_3 box">	
	<p>
		<button id="btnOpenAddDataElementModalWindow" type="button" class="btn btn-icon btn-blue btn-plus" >
			<span></span>Add Data Element
		</button>
	</p>
	<div id="addDataElementModalWindow" class="box round first" title="New Data Element">
		<div class="block">
			<p><strong>1. Choose a Data Source:</strong></p>
			<table width="auto" height="auto" class="form">
				<tr>
					<td align="right" class="col1">Source:</td>
					<td class="col2">
						<select id="cbbDataSource" name="dataSource">
							<option value="none">- Select Data Source -</option>
							<option value="csiro">CSIRO</option>
							<option value="bom">BoM</option>
							<option value="engineering">Engineering Model</option>
							<option value="customFile">Custom file</option>
						</select>
					</td>
				</tr>
			</table>
			<script type="text/javascript">
				$('select#cbbDataSource').change(function() {
					$('.dataElementForm').hide();
					var selectedValue = $('select#cbbDataSource').val();
					if (selectedValue != "none")
						$("#" + selectedValue + "DataForm").show();
				});
			</script>
			<p>
			<div id="csiroDataForm" class="dataElementForm">
				<form:form method="post" action="/CSS/spring/workboard/addCsiroData?id=${userstory.id}" modelAttribute="climateData">
					<input type="hidden" name="userstoryid" value="${userstory.id}" />
					<p><strong>2. CSIRO Data Options:</strong></p>
					<table width="auto" height="auto" class="form">
						<tr>
							<td align="right" class="col1">Variable:</td>
							<td class="col2">
								<select id="cbbClimateVariable" name="climateVariable">
									<option value="All">All Variables</option>
									<option value="Temperature">Temperature</option>
									<option value="WindSpeed">Wind Speed</option>
									<option value="Rainfall">Rainfall</option>
									<option value="RelativeHumidity">Relative Humidity</option>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right" class="col1">Emission Scenario:</td>
							<td class="col2">
								<select id="cbbClimateEmissionScenario" name="climateEmissionScenario">
									<option value="A1B">A1B</option>
									<option value="A1FI">A1FI</option>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right" class="col1">Climate Model:</td>
							<td class="col2">
								<select id="cbbClimateClimateModel" name="climateModel">
									<option value="Most Likely">Most Likely</option>
									<option value="Hotter and Drier">Hotter and Drier</option>
									<option value="Cooler and Wetter">Cooler &amp; Wetter</option>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right" class="col1">Year:</td>
							<td class="col2">
								<select id="cbbYear" name="year">
									<option value="2030">2030</option>
									<option value="2055">2055</option>
									<option value="2070">2070</option>
								</select>
							</td>
						</tr>
					</table>
					<button type="button" class="btn btn-icon btn-blue btn-plus" onclick="submit();" >
						<span></span>Add CSIRO Data Element
					</button>
				</form:form>
			</div>
			<div id="bomDataForm" class="dataElementForm">
				<p><strong>2. BoM Data Options:</strong></p>
				<table width="auto" height="auto" class="form">
					<tr>
						<td align="right" class="col1">Year:</td>
						<td class="col2">
							<select id="cbbBomYear" name="year">
								<option value="2030">2030</option>
								<option value="2055">2055</option>
								<option value="2070">2070</option>
							</select>
						</td>
					</tr>
				</table>
				<button type="button" class="btn btn-icon btn-blue btn-plus" onclick="submit();" >
					<span></span>Upload custom file
				</button>
			</div>
			<div id="engineeringDataForm" class="dataElementForm">
				<form:form method="post" action="/CSS/spring/workboard/addEngineeringData?id=${userstory.id}" enctype="multipart/form-data">
					<p><strong>2. Engineering Model Data Element Options:</strong></p>
					<table width="auto" height="auto" class="form">
						<tr>
							<td><input type="radio" name="rdEngineeringSourceType" /></td>
							<td align="right" class="col1">Select a file to upload:</td>
							<td class="col2">
								<input type="file" name="file" id="file" /><br />
								<p style="width:300px; text-align:justify"><i>The file should be an Excel file, generated by the concrete deterioration engineering model tool available <a href="http://localhost/ccmit/" title="Go to the engineering model tool" target="blank">here</a>. Download the output from this tool and upload it here to get the data to your workboard.</i></p>
							</td>
						</tr>
						<tr>
							<td><input type="radio" name="rdEngineeringSourceType" /></td>
							<td align="right" class="col1">Or use a predefined example for this region.</td>
						</tr>
						<tr>
							<td align="right" class="col1" colspan="2">Select a variable to use for the Data Element:</td>
							<td class="col2">
								<select id="cbbEngineeringVariable" name="engVariable">
									<option value="var1">Variable 1</option>
									<option value="var2">Variable 2</option>
									<option value="var3">Variable 3</option>
								</select>								
								<a href="#" id="lnkHelpEngVariable" ><img src="<c:url value="/resources/img/icons/help.png" />" alt="Help" /></a>
								<script type="text/javascript" language="javascript">
							        $(document).ready(function () {
							            setupBubblePopup("lnkHelpEngVariable", "Each excel file and example contains data for many engineering variables. Choose one of these variables to use in the new Data Element. If you need more than one variable, add several Data Elements to your workboard.");
							        });
							    </script>
							</td>
						</tr>
					</table>
					<p id="loading">Loader here</p>
					<button type="button" id="btnAddEngineeringModelDataElement" class="btn btn-icon btn-blue btn-plus" onclick="submit();" >
						<span></span>Add Engineering Model Data Element
					</button>
					<script type="text/javascript">
						$(function() {
							$("#btnAddEngineeringModelDataElement").throbber({image: "<c:url value="/resources/img/ajax-loader.gif" />"});
						});
					</script>
				</form:form>
			</div>
			<div id="customFileDataForm" class="dataElementForm">
				<form:form method="post" action="/CSS/spring/workboard/upload?id=${userstory.id}" enctype="multipart/form-data">
					<p><strong>2. Custom Data Element Options:</strong></p>
					<table width="auto" height="auto" class="form">
						<tr>
							<td align="right" class="col1">Select a file to upload:</td>
							<td class="col2">
								<input type="file" name="file" id="file" />
							</td>
						</tr>
					</table>
					<button type="button" class="btn btn-icon btn-blue btn-plus" onclick="submit();" >
						<span></span>Add Custom Data Element
					</button>
				</form:form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$('.dataElementForm').hide();
		setupDialogBox("addDataElementModalWindow", "btnOpenAddDataElementModalWindow");
		$('input[type="radio"]').fancybutton();
	</script>
	
	<p>
		<button type="button" class="btn btn-icon btn-blue btn-cross" onclick="location.href='/CSS/spring/workboard/delete?id=${userstory.id}'" >
			<span></span>Delete WorkBoard
		</button>
	</p>
	<p>
		<button type="button" class="btn btn-icon btn-blue btn-arrow-right" onclick="location.href='/CSS/spring/userstory/create?id=${userstory.id}'" >
			<span></span>Create User Story
		</button>
	</p>
</div>