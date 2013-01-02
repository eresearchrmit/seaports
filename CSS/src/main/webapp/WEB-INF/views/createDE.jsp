<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="true"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" import="war.model.CsiroData" %> 
<%@ page language="java" import="war.model.Region" %>
<%@ page language="java" import="war.model.CsiroParams" %>
<%@ page language="java" import="war.model.CsiroVariable" %>

<div class="grid_10">
	<h2>Data Element</h2>
	<p>Region: ${csiroData.region.name}</p>
	<p>Climate Model: ${csiroData.parameters.modelName}</p>
	<p>Emission Scenario: ${csiroData.parameters.emissionScenario}</p>
	<p>Year: ${csiroData.parameters.assessmentYear}</p>
	<p>${csiroData.variable.name}: ${csiroData.value} ${csiroData.variable.uom}</p>	

	<p>According to the <b>${csiroData.parameters.modelName}</b> climate model, if the <b>${csiroData.parameters.emissionScenario}</b> emissions scenario happens, the <b>${csiroData.variable.name}</b> will increase of <b>${csiroData.value} ${csiroData.variable.uom}</b> by <b>${csiroData.parameters.assessmentYear}</b> in the <b>${csiroData.region.name}</b> region.</p>
</div>