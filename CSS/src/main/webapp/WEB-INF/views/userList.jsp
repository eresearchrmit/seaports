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
	
	<c:set var="successMessage" scope="request" value="${successMessage}"/>
	<c:set var="warningMessage" scope="request" value="${warningMessage}"/>
	<c:set var="errorMessage" scope="request" value="${errorMessage}"/> 			
	<jsp:include page="notifications.jsp" />
	
	<c:if test="${not empty usersList}">
		<table class="data display datatable" id="tblUserList">
			<thead>
				<tr>
					<th>Name</th>
					<th>E-mail</th>
					<th>View Workboard</th>
					<th>View Stories</th>
					<th>Enable / Disable account</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${usersList}" var="user" varStatus="status"> 
				<tr>
					<td><a href="/CSS/public/user/${user.username}">${user.firstname} ${user.lastname} (${user.username})</a></td>
					<td><a href="mailto:${user.email}?Subject=Climate Smart Seaports" title="Send an e-mail to this user" target="_blank">${user.email}</a></td>
					<td class="center"><a href="/CSS/auth/workboard?user=${user.username}" title="View the Workboard of this user"><img src="<c:url value="/resources/img/icons/application_side_boxes.png" />" alt="View" /></a></td>
					<td class="center"><a href="/CSS/auth/userstory/list?user=${user.username}" title="View the Stories of this user"><img src="<c:url value="/resources/img/icons/application_view_list.png" />" alt="View" /></a></td>
					<c:choose>
						<c:when test="${user.roles != 'ROLE_ADMIN' && user.enabled}">
							<td class="center"><a href="/CSS/admin/users/list/disable?user=${user.username}" class="lnkDisableUser" title="Click to disable account"><img src="<c:url value="/resources/img/icons/user.png" />" alt="Enabled" /> Enabled</a></td>
	                 	</c:when>
				  		<c:when test="${user.roles != 'ROLE_ADMIN' && !(user.enabled)}">
				  			<td class="center"><a href="/CSS/admin/users/list/enable?user=${user.username}" class="lnkLockUserStory" title="Click to enable account"><img src="<c:url value="/resources/img/icons/lock.png" />" alt="Disabled"/> Disabled</a></td>
	                 	</c:when>
	                 	<c:when test="${user.roles == 'ROLE_ADMIN'}">
				  			<td class="center"><img src="<c:url value="/resources/img/icons/user_green.png" />" alt="Enabled"/> Administrator account (cannot be disabled)</td>
	                 	</c:when>
	                 	<c:otherwise>
	                 		<td></td>
	                 	</c:otherwise>
					</c:choose>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<script type="text/javascript">
			$(document).ready(function () {
				$('#tblUserList').dataTable();
			});
	 	</script>
	</c:if>

	<div id="confirmDisableUserModalWindow" title="Disable this user account ?">
	  <p>Once disabled, the user won't be able to connect and access his work.</p>
	  <p>Are you sure you want to disable this user account ?</p> 
	</div>
	<div id="confirmEnableUserModalWindow" title="Enable this user account ?">
	  <p>Are you sure you want to enable this user account ?</p> 
	</div>
	<script type="text/javascript">
	setupConfirmBox("confirmDisableUserModalWindow", "lnkDisableUser");
	setupConfirmBox("confirmEnableUserModalWindow", "lnkEnableUser");
	</script>
</div>	
