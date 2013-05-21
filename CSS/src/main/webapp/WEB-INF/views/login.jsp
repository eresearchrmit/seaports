<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="grid_12">
	<div class="box round first" style="text-align:center">
		<div class="box-header"><h5>Sign In</h5></div>
		<div class="box-content block">
			<c:if test="${not empty error}"> 
			<div class="message error">
				<h5>Authentication Error</h5>
				<p>${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</p>
			</div>
			</c:if>
			
			<center>
				<form:form method="POST" action="/CSS/j_spring_security_check" >
					<table class="form">
					    <tr>
					    	<td class="col1" align="right">User name:</td>
					    	<td class="col2" align="left"><input type="text" id="txtUsername" name="j_username" value=""></td>
					    </tr>
					    <tr>
					    	<td class="col1" align="right">Password:</td>
					    	<td class="col2" align="left"><input type="password" name="j_password" /></td>
					    </tr>
					</table>
					<p><a href="/CSS/register">Don't have an account ? Click here to sign up and start</a></p>
					<input name="submit" type="submit" value="Log In" class="btn btn-blue" />
				</form:form>
				<script type="text/javascript">$("#txtUsername").focus();</script>
			</center>
		</div>
	</div>
</div>

