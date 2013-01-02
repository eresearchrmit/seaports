<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>

<div class="floatleft">
	<img src="<c:url value="/resources/img/img-profile.jpg" />" alt="Profile Pic" />
</div>
<div class="floatleft marginleft10">
	<ul class="inline-ul floatleft">
		<li>${workboard.person.firstName} ${workboard.person.lastName}</li>
		<li><a href="#">My Account</a></li>
		<li><a href="CSS/spring/login">Logout</a></li>
	</ul>
	<br />
    <!-- <span class="small">Last Login: 3 hours ago</span> -->
</div>