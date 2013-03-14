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

<div class="grid_12">
	<h2>${listingTitle}</h2>
	<c:if test="${not empty userStoriesList}">
		<table class="data display datatable" id="tblUserStoryList">
			<thead>
				<tr>
					<th>Title</th>
					<th>View</th>
					<th>Edit</th>
					<th>Private / Public</th>
					<th>Publish</th>
					<th>Delete</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${userStoriesList}" var="story" varStatus="status"> 
				<tr>
					<td>${story.name}</td>
					<td><a href="/CSS/auth/userstory/view?id=${story.id}" title="View this Story" target="_blank"><img src="<c:url value="/resources/img/icons/page_white.png" />" alt="View" /></a></td>
					<td><a href="/CSS/auth/userstory?id=${story.id}" title="Edit this Story"><img src="<c:url value="/resources/img/icons/pencil.png" />" alt="Edit"/></a></td>
					<c:choose>
						<c:when test="${story.access == 'private'}">
							<td><a href="/CSS/auth/userstory/lock?user=${user.username}&id=${story.id}&lock=0" class="lnkUnlockUserStory" title="Click to make public"><img src="<c:url value="/resources/img/icons/lock.png" />" alt="Private" /> Private</a></td>
	                 	</c:when>
				  		<c:when test="${story.access == 'public'}">
				  			<td><a href="/CSS/auth/userstory/lock?user=${user.username}&id=${story.id}&lock=1" class="lnkLockUserStory" title="Click to make private"><img src="<c:url value="/resources/img/icons/lock_open.png" />" alt="Public"/> Public</a></td>
	                 	</c:when>
	                 	<c:otherwise>
	                 		<td></td>
	                 	</c:otherwise>
					</c:choose>
					
					<c:choose>
						<c:when test="${story.access == 'published'}">
							<td>Published</td>
							<td></td>
	                 	</c:when>
	                 	<c:otherwise>
	                 		<td><a href="#" class="lnkPublishUserStory" ><img src="<c:url value="/resources/img/icons/world_go.png" />" alt="Publish" title="Publish" /></a></td>
							<td><a href="/CSS/auth/userstory/delete?id=${story.id}" class="lnkDeleteUserStory" title="Delete this Story"><img src="<c:url value="/resources/img/icons/delete.png" />" alt="Delete" /></a></td>
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

	<div id="confirmUserStoryPrivateModalWindow" title="Make this story private ?">
	  <p>Once private, you will be the only one able to see this story.</p>
	  <p>Are you sure you want to make this story private ?</p> 
	</div>
	<div id="confirmUserStoryPublicModalWindow" title="Make this story public ?">
	  <p>Once public, everyone will be able to see this story.</p>
	  <p>Are you sure you want to make this story public ?</p> 
	</div>
	<div id="confirmUserStoryPublishModalWindow" title="Really publish this story ?">
	  <p>Publishing this story will automatically submit it to the ANDS RIFCS. Once published, this story cannot be edited, deleted or made private again.</p>
	  <p>Are you sure you want to publish this story ?</p> 
	</div>
	<div id="confirmUserStoryDeletionModalWindow" title="Permanently delete the story ?">
	  <p>Warning: this action can't be undone ! It will also delete all the data elements contained in the story.</p>
	  <p>Are you sure you want to permanently delete this story ?</p> 
	</div>
	<script type="text/javascript">
	setupConfirmBox("confirmUserStoryPrivateModalWindow", "lnkLockUserStory");
	setupConfirmBox("confirmUserStoryPublicModalWindow", "lnkUnlockUserStory");
	setupConfirmBox("confirmUserStoryPublishModalWindow", "lnkPublishUserStory");
	setupConfirmBox("confirmUserStoryDeletionModalWindow", "lnkDeleteUserStory");
	</script>
</div>	
