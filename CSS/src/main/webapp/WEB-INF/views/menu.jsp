<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<ul class="nav main">
	<li class="ic-home"><a href="/CSS"><span>Home</span></a> </li>
	<sec:authorize access="isAuthenticated()">
		<li class="ic-workboard"><a href="/CSS/auth/workboard?user=${user.username}"><span>My Workboard</span></a></li>
		<li class="ic-story">
			<a href="/CSS/auth/userstory/list?user=${user.username}"><span>My Reports</span></a>
			<!-- <ul>
				<li><a href="/CSS/auth/userstory/list?user=${user.username}">My Stories</a></li>
				<li><a href="#">Stories shared with me</a></li>
			</ul> -->
		</li>
	</sec:authorize>
	<li class="ic-browse"><a href="/CSS/public/reports/list"><span>Published Reports</span></a></li>
	<sec:authorize access="isAuthenticated()">
		<li class="ic-info">
			<a href="/CSS/public/guidelines" target="_blank"><span>Guidelines</span></a>
		</li>
	</sec:authorize>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<li class="ic-admin"><a href="/CSS/admin/users/list"><span>Administration</span></a></li>
	</sec:authorize>
	<!-- <li class="ic-data-element"><a href="#" title="Manage the favorite data elements that you saved"><span>My Data Elements</span></a></li> -->	
</ul>