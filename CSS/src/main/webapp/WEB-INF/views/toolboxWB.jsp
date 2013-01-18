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
					<td align="right" class="col1">Variable:</td>
					<td class="col2">
						<select id="cbbDataSource" name="dataSource">
							<option value="None">- Select Data Source -</option>
							<option value="CSIRO">CSIRO</option>
							<option value="BoM">BoM</option>
							<option value="CustomFile">Custom file</option>
						</select>
					</td>
				</tr>
			</table>
			<script type="text/javascript">
				$('select#cbbDataSource').change(function() {
					$('#csiroDataForm').hide();
					$('#bomDataForm').hide();
					$('#customFileDataForm').hide();
					switch($('select#cbbDataSource').val())
					{
						case "CSIRO":
							$('#csiroDataForm').show();
							break;
						case "BoM":
							$('#bomDataForm').show();
							break;
						case "CustomFile":
							$('#customFileDataForm').show();
							break;
						default:
							$('#csiroDataForm').hide();
							$('#bomDataForm').hide();
							$('#customFileDataForm').hide();
					}
				});
			</script>
			<p>
			<div id="csiroDataForm">
				<form:form method="post" action="/CSS/spring/workboard/addCsiroData?id=${userstory.id}" modelAttribute="csiroData">
					<p><strong>2. CSIRO Data Options:</strong></p>
					<table width="auto" height="auto" class="form">
						<tr>
							<td align="right" class="col1">Variable:</td>
							<td class="col2">
								<select id="cbbCsiroVariable" name="csiroVariable">
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
								<select id="cbbCsiroEmissionScenario" name="csiroEmissionScenario">
									<option value="A1B">A1B</option>
									<option value="A1FI">A1FI</option>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right" class="col1">Climate Model:</td>
							<td class="col2">
								<select id="cbbCsiroClimateModel" name="csiroClimateModel">
									<option value="csiro_mk3_5">csiro_mk3_5</option>
									<option value="ipsl_cm4">ipsl_cm4</option>
									<option value="miroc_3_2_medres">miroc_3_2_medres</option>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right" class="col1">Year:</td>
							<td class="col2">
								<select id="cbbassessmentYear" name="assessmentYear">
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
			<div id="bomDataForm">
				<p><strong>2. BoM Data Options:</strong></p>
				<table width="auto" height="auto" class="form">
					<tr>
						<td align="right" class="col1">Year:</td>
						<td class="col2">
							<select id="cbbBomAssessmentYear" name="assessmentYear">
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
			<div id="customFileDataForm">
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
						<span></span>Add CCustom Data Element
					</button>
				</form:form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$('#csiroDataForm').hide();
		$('#bomDataForm').hide();
		$('#customFileDataForm').hide();
		setupDialogBox("addDataElementModalWindow", "btnOpenAddDataElementModalWindow");
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