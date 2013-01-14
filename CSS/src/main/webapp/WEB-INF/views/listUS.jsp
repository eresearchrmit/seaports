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
					<th>Name</th>
					<th>Publish</th>
					<th>Update</th>
					<th>Delete</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${userStoriesList}" var="story" varStatus="status"> 
				<tr>
					<td>${story.name}</td>
					<td class="center"><img src="<c:url value="/resources/img/icons/arrow_right_32.png" />" alt="Publish" title="Publish" /></td>
					<td class="center"><a href="/CSS/spring/userstory?id=${story.id}"><img src="<c:url value="/resources/img/icons/pencil_32.png" />" alt="Edit" title="Edit" /></a></td>
					<td class="center"><img src="<c:url value="/resources/img/icons/close_32.png" />" alt="Delete" title="Delete" /></td>
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

</div>	
