<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="true"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<div class="grid_12">
	<center>
		<h3>Introduction</h3>
	
		<div id="successMessage" class="message success">
			<h5>Congratulations, your account has been created !</h5>
		</div>
		
		<p class="info-paragraph">The potential impact of climate change on ports differs according to the location, function and business model of the ports.  Climate Smart ports in Australia want to understand the relevant climate impacts and risks for their particular operation; only then can they determine what adaptation measures may be appropriate.</p> 

		<p class="info-paragraph">The Climate Smart Seaports Tool is designed primarily for port personnel who make (or influence) decisions around long-term port planning for infrastructure, assets and management systems. However, it will also be of value to port owners and related businesses, government departments, local authorities concerned with ports and infrastructure, and researchers.</p> 
		
		<p class="info-paragraph">The Tool facilitates the collection of information so ports are able to identify their future climate risks. It is designed to generate material for the scope and context setting stages of the <a href="http://www.iso.org/iso/home/standards/iso31000.htm">ISO 31000 risk management standard</a>, and it is also compatible with the initial stages of other processes such as Engineers Australia's "<a href="http://www.engineersaustralia.org.au/sites/default/files/shado/Learned%20Groups/National%20Committees%20and%20Panels/Coastal%20and%20Ocean%20Engineering/vol_1_web.pdf">Guidelines for Responding to the Effects of Climate Change in Coastal and Ocean Engineering</a>" , and the Australian Government's "<a href="http://www.climatechange.gov.au/community/~/media/publications/local-govt/risk-management.ashx">Climate Change Impacts and Risk Management: Guide for Business and Government</a>".</p> 
		
		<p class="info-paragraph">Current and historical climate trends, future climate data and non-climate data for three Australian NRM regions are available in the Tool.</p> 
		
		<img src="<c:url value="/resources/img/help/user_workflow.png" />" alt="Application Workflow" width="750" />
		
		<p class="info-paragraph">Additionally, it features applications to investigate port vulnerability to extreme weather events, and concrete deterioration modelling.</p> 
		
		<p class="info-paragraph">The "publish" function in the tool stores the final report on the Climate Smart Seaports site, to provide a growing repository of information and knowledge about climate change adaptation for peer-to-peer learning.</p>   
				
		<h4>Click on "Get started" to log in and start.</h4>
	</center>
	<br />
	<div align="center">
		<a href="/CSS/auth/workboard/my-workboard">
			<button id="btnContinueToWorkboard" class="btn btn-icon btn-blue btn-arrow-right" >
				<span></span>Get started
			</button>
		</a>
	</div>
	
</div>