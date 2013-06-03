<%--
 Copyright (c) 2013, RMIT University, Australia.
 All rights reserved.
  
 This code is under the BSD license. See 'license.txt' for details.
 Project hosted at: https://code.google.com/p/climate-smart-seaports/
--%>
<%@ page session="false" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="grid_12">

	<c:set var="successMessage" scope="request" value="${successMessage}"/>
	<c:set var="warningMessage" scope="request" value="${warningMessage}"/>
	<c:set var="errorMessage" scope="request" value="${errorMessage}"/>
	<jsp:include page="notifications.jsp" />

	<c:if test="${not empty userProfile}">
		<h2>${userProfile.firstname} ${userProfile.lastname}'s profile</h2>
	
		<div style="font-size: 1.2em; margin-bottom: 40px">
			<strong>Username</strong>: ${userProfile.username}<br />
			<strong>E-mail address</strong>: ${userProfile.email}<br />
			<strong>NLA Number</strong>: ${not empty userProfile.nlaNumber ? userProfile.nlaNumber : 'N/A'} 
		</div>
		
		<h5>Reports published by ${userProfile.firstname} ${userProfile.lastname}:</h5>
		<c:choose> 
			<c:when test="${not empty userProfile.userStories}">
				<table class="data display datatable" id="tblUserStoryList">
					<thead>
						<tr>
							<th>Title</th>
							<th>Author</th>
							<th>Published on</th>
							<th>NRM Region</th>
							<th>View</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${userProfile.userStories}" var="story" varStatus="status">
							<c:if test="${story.mode == 'published'}">
							<tr>
								<td><c:out value="${story.name}" /></td>
								<td>${story.owner.firstname} ${story.owner.lastname}</td>
								<td><fmt:formatDate value="${story.publishDate}" pattern="dd MMM yyyy" /></td>
								<td>${story.seaport.region.name}</td>
								<td><a href="/CSS/public/reports/view?id=${story.id}" title="View this Story" target="_blank"><img src="<c:url value="/resources/img/icons/page_white.png" />" alt="View" /></a></td>
							</tr>
							</c:if>
						</c:forEach>
					</tbody>
				</table>
				<script type="text/javascript">
					$(document).ready(function () {
						$('#tblUserStoryList').dataTable();
					});
			 	</script>
			</c:when>
			<c:otherwise>
				<i>${userProfile.firstname} ${userProfile.lastname} hasn't published any report yet.</i>
			</c:otherwise>
		</c:choose>
	</c:if>
</div>