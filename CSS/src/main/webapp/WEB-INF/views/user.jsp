<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>

<div class="floatleft">
	<img src="<c:url value="/resources/img/icon-profile.png" />" alt="Profile Picture" />
</div>
<div class="floatleft marginleft10">
	<ul class="inline-ul floatleft">
		<li>${user.firstname} ${user.lastname}</li>
		<li><a href="#">My Account</a></li>
		<li><a href="/CSS/j_spring_security_logout">Logout</a></li>
	</ul>
	<br />
    <!-- <span class="small">Last Login: 3 hours ago</span> -->
</div>