<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>

<sec:authorize access="isAuthenticated()">
<div id="userInformations">
	<div class="floatleft">
		<img src="<c:url value="/resources/img/theme/icon-profile.png" />" alt="Profile Picture" />
	</div>
	<div class="floatleft marginleft10">
		<ul class="inline-ul floatleft">
			<li><a href="/CSS/auth/user/${user.username}">${user.firstname} ${user.lastname} (<sec:authentication property="principal.username" />)</a></li>
			<li><a href="/CSS/j_spring_security_logout">Logout</a></li>
		</ul>
		<br />
	    <!-- <span class="small">Last Login: 3 hours ago</span> -->
	</div>
</div>
</sec:authorize>
<sec:authorize access="!isAuthenticated()">
	<a href="/CSS/auth/workboard/my-workboard">Log in</a>
</sec:authorize>