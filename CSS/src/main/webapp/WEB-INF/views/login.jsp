<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="grid_12">
	<div class="box round first" style="text-align:center">
		<h2>Sign In</h2>
		<div class="block">
			<c:if test="${not empty errorMessage}"> 
			<div class="message error">
				<h5>Error</h5>
				<p>${errorMessage}</p>
			</div>
			</c:if>
			
			<form:form method="POST" modelAttribute="user" >
				<table class="form">
				    <tr>
				    	<td class="col1" align="right">Login:</td>
				    	<td class="col2" align="left"><form:input path="login" /></td>
				    </tr>
				    <tr>
				    	<td class="col1" align="right">Password:</td>
				    	<td class="col2" align="left"><form:password path="password" /></td>
				    </tr>
				</table>
				<input type="submit" value="Sign In" class="btn btn-blue" />
			</form:form>
		</div>
	</div>
</div>

