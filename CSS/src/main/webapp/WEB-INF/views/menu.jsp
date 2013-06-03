<%--
 Copyright (c) 2013, RMIT University, Australia.
 All rights reserved.
  
 This code is under the BSD license. See 'license.txt' for details.
 Project hosted at: https://code.google.com/p/climate-smart-seaports/
--%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<ul class="nav main">
	<li class="ic-home${requestScope['javax.servlet.forward.servlet_path'] == '/' ? ' current' : ''}"><a href="/CSS"><span>Home</span></a> </li>
	
	<sec:authorize access="isAuthenticated()">
	<li class="ic-workboard
		${requestScope['javax.servlet.forward.servlet_path'] == '/auth/workboard' ? ' current' : ''}">
		<a href="/CSS/auth/workboard?user=${user.username}"><span>My Workboard</span></a>
	</li>
	
	<li class="ic-story
		${requestScope['javax.servlet.forward.servlet_path'] == '/auth/userstory/list' 
		|| requestScope['javax.servlet.forward.servlet_path'] == '/auth/userstory' 
		|| requestScope['javax.servlet.forward.servlet_path'] == '/auth/userstory/view' ? ' current' : ''}">
		<a href="/CSS/auth/userstory/list?user=${user.username}"><span>My Reports</span></a>
	</li>
	</sec:authorize>
	
	<li class="ic-browse${requestScope['javax.servlet.forward.servlet_path'] == '/public/reports/list' 
		|| requestScope['javax.servlet.forward.servlet_path'] == '/public/reports/view' ? ' current' : ''}">
		<a href="/CSS/public/reports/list"><span>Published Reports</span></a>
	</li>
	
	<li class="ic-info
		${requestScope['javax.servlet.forward.servlet_path'] == '/public/guidelines' ? ' current' : ''}">
		<a href="/CSS/public/guidelines" target="_blank"><span>Guidelines</span></a>
	</li>
	
	<sec:authorize access="hasRole('ROLE_ADMIN')">
	<li class="ic-admin
		${requestScope['javax.servlet.forward.servlet_path'] == '/admin/users/list' ? ' current' : ''}">
		<a href="/CSS/admin/users/list"><span>Administration</span></a>
	</li>
	</sec:authorize>
	
</ul>