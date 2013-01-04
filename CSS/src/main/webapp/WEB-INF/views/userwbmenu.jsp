<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="true"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" import="war.model.WorkBoard" %>
<%@ page language="java" import="war.model.Person" %>
<%@ page language="java" import="war.model.Files" %>

<% WorkBoard wb = (WorkBoard) request.getAttribute("workboard") ; %>
<% Person p = (Person) wb.getPerson() ; %>

<div class="grid_12">
	<h2> ${workboard.workBoardName} </h2>
</div>
<div class="clear"></div>

<div class="grid_3 box">
	<p>
		<form:form method="post" action="/CSS/spring/workboard/upload?workboardid=${workboard.workBoardID}" modelAttribute="file" enctype="multipart/form-data">
			File Upload : <input type="file" name="files" id="files" /><br />
	    	<input type="submit" value="Upload File" class="btn btn-grey btn-icon btn-plus" />
		</form:form>
	</p>
	
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
						</select>
					</td>
				</tr>
			</table>
			<script type="text/javascript">
				$('select#cbbDataSource').change(function() {
					switch($('select#cbbDataSource').val())
					{
						case "CSIRO":
							$('#csiroDataForm').show();
							$('#bomDataForm').hide();
							break;
						case "BoM":
							$('#csiroDataForm').hide();
							$('#bomDataForm').show();
							break;
						default:
							$('#csiroDataForm').hide();
							$('#bomDataForm').hide();
					}
				});
			</script>
			<p>
			<div id="csiroDataForm">
				<form:form method="post" action="/CSS/spring/workboard/addCsiroData?workboardid=${workboard.workBoardID}" modelAttribute="file" enctype="multipart/form-data">
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
					<span></span>Add BoM Data Element
				</button>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$('#csiroDataForm').hide();
		$('#bomDataForm').hide();
		setupDialogBox("addDataElementModalWindow", "btnOpenAddDataElementModalWindow");
	</script>
	
	<p>
		<button type="button" class="btn btn-icon btn-blue btn-cross" onclick="location.href='/CSS/spring/workboard/deleteWB?workboardid=${workboard.workBoardID}'" >
			<span></span>Delete WorkBoard
		</button>
	</p>
	<p>
		<button type="button" class="btn btn-icon btn-blue btn-arrow-right" onclick="location.href='/CSS/spring/createus?workboardid=${workboard.workBoardID}'" >
			<span></span>Create User Story
		</button>
	</p>
</div>