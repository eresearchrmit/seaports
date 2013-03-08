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
		<h2>${userstory.name}</h2>
		<h6>${userstory.region.name}</h6>
	</div>
	
	<a class="lnkConvertToUserStory" href="/CSS/auth/userstory/create?id=${userstory.id}" style="margin-right: 10px; float:right">
		<button id="btnConvertToUserStory" type="button" class="btn btn-icon btn-blue btn-arrow-right" >
			<span></span>Create User Story
		</button>
	</a>
	<a class="lnkDeleteWorkboard" href="/CSS/auth/workboard/delete?id=${userstory.id}" style="margin-right: 10px; float:right">
		<button id="btnDeleteWorkboard" type="button" class="btn btn-icon btn-blue btn-cross" >
			<span></span>Delete WorkBoard
		</button>
	</a>
	<div id="confirmWorkboardDeletionModalWindow" title="Delete your workboard ?">
		<p>Deleting your workboard will also delete all the data elements it contains. This action cannot be undone.</p> 
		<p>Are you sure you want to permanently delete your current workboard ?</p> 
	</div>
	<div id="confirmConvertToUserStoryModalWindow" title="Create a story from this workboard ?">
		<p>This will create a Story based on your Workboard. Once the Workboard becomes a Story, no more data element can be added to it.</p> 
		<p>Are you sure you want to create a Story from your Workboard now ?</p>
	</div>
	<script type="text/javascript">
		setupConfirmBox("confirmConvertToUserStoryModalWindow", "lnkConvertToUserStory");
		setupConfirmBox("confirmWorkboardDeletionModalWindow", "lnkDeleteWorkboard");
	</script>
	
	<div class="clear"></div><br />
	
	<c:set var="successMessage" scope="request" value="${successMessage}"/>
	<c:set var="warningMessage" scope="request" value="${warningMessage}"/>
	<c:set var="errorMessage" scope="request" value="${errorMessage}"/> 			
	<jsp:include page="notifications.jsp" />
		
	<script type="text/javascript">
		$(document).ready((function() {
			$( "#tabs" ).tabs();
		}));
	</script>
	
	<div id="tabs">
		<ul>
			<li><a href="#tabs-non-climate-context">Non-climate context</a></li>
			<li><a href="#tabs-observed-climate">Observed Climate</a></li>
			<li><a href="#tabs-future-climate">Future Climate</a></li>
			<li><a href="#tabs-applications">Applications</a></li>
			<li style="float:right"><a href="#tabs-summary">Summary (All)</a></li>
		</ul>
		
		<div id="tabs-non-climate-context">
			<!-- Explanation text -->
			<p><b>ABS, BITRE, custom files</b>: Proin elit arcu, rutrum commodo, vehicula tempus, commodo a, risus. Curabitur nec arcu. Donec sollicitudin mi sit amet mauris. Nam elementum quam ullamcorper ante. Etiam aliquet massa et lorem. Mauris dapibus lacus auctor risus. </p>
			
			<!-- Toolbox -->
			<center>
				<c:set var="userstory" scope="request" value="${userstory}"/>
				<jsp:include page="workboardToolbox.jsp" />
			</center>
			<div class="clear"></div><br />
			
			<c:set var="dataelements" scope="request" value="${dataelements}"/>
			<c:set var="dataelementsfilter" scope="request" value="NonClimate"/>
			<jsp:include page="dataElements.jsp" />
		</div>
		
		<div id="tabs-observed-climate">
			<!-- Explanation text -->
			<p><b>BoM, Local ACORN-Sat, custom files</b>: Proin elit arcu, rutrum commodo, vehicula tempus, commodo a, risus. Curabitur nec arcu. Donec sollicitudin mi sit amet mauris. Nam elementum quam ullamcorper ante. Etiam aliquet massa et lorem. Mauris dapibus lacus auctor risus. </p>
			
			<!-- Toolbox -->
			<center>
				<c:set var="userstory" scope="request" value="${userstory}"/> 			
				<jsp:include page="workboardToolbox.jsp" />
			</center>
			<div class="clear"></div><br />
		</div>
		
		<div id="tabs-future-climate">
			<!-- Explanation text -->
			<p><b>CSIRO, CMAR</b>: Proin elit arcu, rutrum commodo, vehicula tempus, commodo a, risus. Curabitur nec arcu. Donec sollicitudin mi sit amet mauris. Nam elementum quam ullamcorper ante. Etiam aliquet massa et lorem. Mauris dapibus lacus auctor risus. </p>
			
			<!-- Toolbox -->
			<center>
				<c:set var="userstory" scope="request" value="${userstory}"/> 			
				<jsp:include page="workboardToolbox.jsp" />
			</center>
			<div class="clear"></div><br />
			
			<c:set var="dataelements" scope="request" value="${dataelements}"/>
			<c:set var="dataelementsfilter" scope="request" value="Future"/>
			<jsp:include page="dataElements.jsp" />
		</div>
		
		<div id="tabs-applications">
			<!-- Explanation text -->
			<p><b>Engineering Model</b>:Proin elit arcu, rutrum commodo, vehicula tempus, commodo a, risus. Curabitur nec arcu. Donec sollicitudin mi sit amet mauris. Nam elementum quam ullamcorper ante. Etiam aliquet massa et lorem. Mauris dapibus lacus auctor risus. </p>
			
			<!-- Toolbox -->
			<center>
				<c:set var="userstory" scope="request" value="${userstory}"/> 			
				<jsp:include page="workboardToolbox.jsp" />
			</center>
			<div class="clear"></div><br />
			
			<c:set var="dataelements" scope="request" value="${dataelements}"/>
			<c:set var="dataelementsfilter" scope="request" value="Applications"/>
			<jsp:include page="dataElements.jsp" />
		</div>
		
		<div id="tabs-summary">
		<c:set var="dataelements" scope="request" value="${dataelements}"/>
			<c:set var="dataelementsfilter" scope="request" value="All"/>
			<jsp:include page="dataElements.jsp" />
		</div>
	</div>
	
	<div id="confirmDataElementDeletionModalWindow" title="Delete this data element ?">
		<p>Are you sure you want to delete this data element from your Workboard ?</p> 
	</div>
	<script type="text/javascript">
		setupConfirmBox("confirmDataElementDeletionModalWindow", "lnkDeleteDataElement");
	</script>
</div>
</c:if>