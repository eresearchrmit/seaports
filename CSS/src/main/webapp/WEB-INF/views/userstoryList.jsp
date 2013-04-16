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
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="grid_12">
	<h2>${listingTitle}</h2>
	<c:if test="${not empty userStoriesList}">
		<table class="data display datatable" id="tblUserStoryList">
			<thead>
				<tr>
					<th>Title</th>
					<th>View</th>
					<th>Edit</th>
					<th>Publish</th>
					<th>Delete</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${userStoriesList}" var="story" varStatus="status"> 
				<tr>
					<td>${story.name}</td>
					<td><a href="/CSS/auth/userstory/view?id=${story.id}" title="View this Report" target="_blank"><img src="<c:url value="/resources/img/icons/page_white.png" />" alt="View" /></a></td>
					<c:choose>
						<c:when test="${story.mode == 'published'}">
							<td></td>
							<td>Published (<fmt:formatDate value="${story.publishDate}" pattern="dd MMM yyyy" />) <img src="<c:url value="/resources/img/icons/accept.png" />" /></td>
							<td></td>
	                 	</c:when>
	                 	<c:otherwise>
	                 		<td><a href="/CSS/auth/userstory?id=${story.id}" title="Edit this Report"><img src="<c:url value="/resources/img/icons/pencil.png" />" alt="Edit"/></a></td>
	                 		<td><a href="/CSS/auth/userstory/publish?id=${story.id}" class="lnkPublishUserStory" title="Publish this Report"><img src="<c:url value="/resources/img/icons/world_go.png" />" alt="Publish"/></a></td>
							<td><a href="/CSS/auth/userstory/delete?id=${story.id}" class="lnkDeleteUserStory" title="Delete this Report"><img src="<c:url value="/resources/img/icons/delete.png" />" alt="Delete" /></a></td>
	                 	</c:otherwise>
					</c:choose>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<script type="text/javascript">
			$(document).ready(function () {
				$('#tblUserStoryList').dataTable();
			});
	 	</script>
	</c:if>

	<div id="confirmUserStoryPrivateModalWindow" title="Make this report private ?" style="display:none">
	  <p>Once private, you will be the only one able to see this report.</p>
	  <p>Are you sure you want to make this report private ?</p> 
	</div>
	<div id="confirmUserStoryPublicModalWindow" title="Make this report public ?" style="display:none">
	  <p>Once public, everyone will be able to see this report.</p>
	  <p>Are you sure you want to make this report public ?</p> 
	</div>
	<div id="confirmUserStoryPublishModalWindow" title="Really publish this report ?" style="display:none">
	  <p>Publishing this story will automatically submit it to the ANDS RIFCS. Once published, this report cannot be edited, deleted or made private again.</p>
	  <p>Are you sure you want to publish this report ?</p> 
	</div>
	<div id="confirmUserStoryDeletionModalWindow" title="Permanently delete the report ?" style="display:none">
<p class="message"><span class="error"><b>Warning: this will also delete all the data elements and texts contained in this report. This action cannot be undone !</b></span></p>
	  <p>Are you sure you want to permanently delete this report ?</p> 
	</div>
	<script type="text/javascript">
	setupConfirmBox("confirmUserStoryPrivateModalWindow", "lnkLockUserStory");
	setupConfirmBox("confirmUserStoryPublicModalWindow", "lnkUnlockUserStory");
	setupConfirmBox("confirmUserStoryPublishModalWindow", "lnkPublishUserStory");
	setupConfirmBox("confirmUserStoryDeletionModalWindow", "lnkDeleteUserStory");
	</script>
</div>	
