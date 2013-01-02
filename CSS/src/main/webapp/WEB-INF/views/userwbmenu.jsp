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
		<form:form method="post" action="/CSS/spring/createwb/upload?workboardid=${workboard.workBoardID}" modelAttribute="file" enctype="multipart/form-data">
			File Upload : <input type="file" name="files" id="files" /><br />
	    	<input type="submit" value="Upload File" class="btn btn-grey btn-icon btn-plus" />
		</form:form>
	</p>
	<p>
		Choose a Data Source:
		<select id="cbbDataSource" name="dataSource">
			<option value="1">CSIRO</option>
			<option value="2">BoM</option>
		</select>
	</p>

	<form:form method="post" action="/CSS/spring/createwb/addCsiroData?workboardid=${workboard.workBoardID}" modelAttribute="file" enctype="multipart/form-data">
	<p>
		Variable:
		<select id="cbbCsiroVariable" name="csiroVariable">
			<option value="All">All Variables</option>
			<option value="Temperature">Temperature</option>
			<option value="WindSpeed">Wind Speed</option>
			<option value="Rainfall">Rainfall</option>
			<option value="RelativeHumidity">Relative Humidity</option>
		</select>
	</p>
	<p>
		Emission Scenario:
		<select id="cbbCsiroEmissionScenario" name="csiroEmissionScenario">
			<option value="A1B">A1B</option>
			<option value="A1FI">A1FI</option>
		</select>
	</p>
	<p>
		Climate Model:
		<select id="cbbCsiroClimateModel" name="csiroClimateModel">
			<option value="csiro_mk3_5">csiro_mk3_5</option>
			<option value="ipsl_cm4">ipsl_cm4</option>
			<option value="miroc_3_2_medres">miroc_3_2_medres</option>
		</select>
	</p>
	<p>
		Year:
		<select id="cbbassessmentYear" name="assessmentYear">
			<option value="2030">2030</option>
			<option value="2055">2055</option>
			<option value="2070">2070</option>
		</select>
	</p>
	<p>
		<button type="button" class="btn btn-icon btn-blue btn-plus" onclick="submit();" >
			<span></span>Add Data Element
		</button>
	</p>
	</form:form>
	<p>
		<button type="button" class="btn btn-icon btn-blue btn-cross" onclick="location.href='/CSS/spring/createwb/deleteWB?workboardid=${workboard.workBoardID}'" >
			<span></span>Delete WorkBoard
		</button>
	</p>
	<p>
		<button type="button" class="btn btn-icon btn-blue btn-arrow-right" onclick="location.href='/CSS/spring/createus?workboardid=${workboard.workBoardID}'" >
			<span></span>Create User Story
		</button>
	</p>
</div>