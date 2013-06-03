<%--
 Copyright (c) 2013, RMIT University, Australia.
 All rights reserved.
  
 This code is under the BSD license. See 'license.txt' for details.
 Project hosted at: https://code.google.com/p/climate-smart-seaports/
--%>
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
		<p class="message"><span class="error"><b>Warning : Deleting your workboard will also delete all data it contains. This action cannot be undone !</b></span></p>
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
					
					// Hide all notifications when changing tab
					$(".message").hide();
					
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
			<c:choose>
				<c:when test="${dataelementsCounts[0] <= 0}">
					<br />
					<%@ include file="/WEB-INF/views/help/explanationTextNonClimate.jsp" %>
				</c:when>
				<c:otherwise>
					<a href="#" class="helpTooltip" title="<%@ include file="/WEB-INF/views/help/explanationTooltipNonClimate.jsp" %>">
						<img src="<c:url value="/resources/img/icons/help.png" />">
					</a>
				</c:otherwise>
			</c:choose>
						
			<!-- Toolbox -->
			<c:set var="dataelementsfilter" scope="request" value="NonClimate"/>
			<center>
				<jsp:include page="workboardToolbox.jsp" />
			</center>
			<div class="clear"></div><br />
			
			<jsp:include page="dataElements.jsp" />
		</div>
		
		<div id="tabs-observed-climate">
			<!-- Explanation text -->
			<c:choose>
				<c:when test="${dataelementsCounts[1] <= 0}">
					<br />
					<%@ include file="/WEB-INF/views/help/explanationTextObservedClimate.jsp" %>
				</c:when>
				<c:otherwise>
					<a href="#" class="helpTooltip" title="<%@ include file="/WEB-INF/views/help/explanationTooltipObservedClimate.jsp" %>">
						<img src="<c:url value="/resources/img/icons/help.png" />">
					</a>
				</c:otherwise>
			</c:choose>
			
			<!-- Toolbox -->
			<c:set var="dataelementsfilter" scope="request" value="ObservedClimate"/>
			<center>
				<jsp:include page="workboardToolbox.jsp" />
			</center>
			<div class="clear"></div><br />
			
			<jsp:include page="dataElements.jsp" />
		</div>
		
		<div id="tabs-future-climate">
			
			<!-- Explanation text -->
			<c:choose>
				<c:when test="${dataelementsCounts[2] <= 0}">
					<br />
					<%@ include file="/WEB-INF/views/help/explanationTextFutureClimate.jsp" %>
				</c:when>
				<c:otherwise>
					<a href="#" class="helpTooltip" title="<%@ include file="/WEB-INF/views/help/explanationTooltipFutureClimate.jsp" %>">
						<img src="<c:url value="/resources/img/icons/help.png" />">
					</a>
				</c:otherwise>
			</c:choose>
			
			<!-- Toolbox -->
			<c:set var="dataelementsfilter" scope="request" value="Future"/>
			<center>
				<jsp:include page="workboardToolbox.jsp" />
			</center>
			<div class="clear"></div><br />
			
			<jsp:include page="dataElements.jsp" />
		</div>
		
		<div id="tabs-applications">
			<!-- Explanation text -->
			<c:choose>
				<c:when test="${dataelementsCounts[3] <= 0}">
					<br />
					<%@ include file="/WEB-INF/views/help/explanationTextApplications.jsp" %>
				</c:when>
				<c:otherwise>
					<a href="#" class="helpTooltip" title="<%@ include file="/WEB-INF/views/help/explanationTooltipApplications.jsp" %>">
						<img src="<c:url value="/resources/img/icons/help.png" />">
					</a>
				</c:otherwise>
			</c:choose>
			
			<!-- Toolbox -->
			<c:set var="dataelementsfilter" scope="request" value="Applications"/>
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
			
			<center>
			<a class="lnkConvertToUserStory" href="/CSS/auth/userstory/create?id=${userstory.id}">
				<button id="btnConvertToUserStory" type="button" class="btn btn-icon btn-blue btn-arrow-right" >
					<span></span>Create Report
				</button>
			</a>
			</center>
		</div>
	</div></div>
</c:if>