<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>



<div class="grid_10">
		<h2>Sign In</h2>
	<div class="block">
		<c:if test="${not empty controllerMessage}"> 
		<div class="message error">
			<h5>Error</h5>
			<p>${controllerMessage}</p>
		</div>
		</c:if>
		
		<form:form method="POST" modelAttribute="person" style="padding:8px">
		    <p>Username <form:input path="firstName" /></p>
		    <p>Password <form:password path="passWord" align ="right" /></p>
		    <input type="submit" value="Sign In" class="btn btn-blue" />
		</form:form>
	</div>
</div>

<h2></h2>

