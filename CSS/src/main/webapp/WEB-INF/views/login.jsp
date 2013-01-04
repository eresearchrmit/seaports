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
		
		<form:form method="POST" modelAttribute="person" >
			<table class="form">
			    <tr>
			    	<td class="col1" align="right">Login:</td>
			    	<td class="col2"><form:input path="login" /></td>
			    </tr>
			    <tr>
			    	<td class="col1" align="right">Password:</td>
			    	<td class="col2"><form:password path="password" align ="right" /></td>
			    </tr>
			</table>
			<input type="submit" value="Sign In" class="btn btn-blue" />
		</form:form>
	</div>
</div>

<h2></h2>

