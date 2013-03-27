<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="grid_12">
	<div class="box round first" style="text-align:center">
		<h2>Registration</h2>
		<div class="box-content block"> 
			<c:if test="${not empty errorMessage}"> 
			<div id="errorMessage" class="message error">
				<h5>Error</h5>
				<p id="errorMessageContent" >${errorMessage}</p>
			</div>
			</c:if>
			 
			<form:form method="POST" modelAttribute="user" >
				<table class="form">
				    <tr>
				    	<td class="col1" align="right">Username:</td>
				    	<td class="col2" align="left">
				    		<form:input id="txtLogin" path="username" onblur="checkLogin()" />*&nbsp;
				    		<span id="loginErrorMessage" style="color:red;"></span>
				    	</td>
				    </tr>
				    <tr>
				    	<td class="col1" align="right">Password:</td>
				    	<td class="col2" align="left">
				    		<form:password id="txtPassword" path="password" onblur="checkPassword()" />*&nbsp;
				    		<span id="passwordErrorMessage" style="color:red;"></span>
				    	</td>
				    </tr>
				    <tr>
				    	<td class="col1" align="right">Confirm password:</td>
				    	<td class="col2" align="left">
				    		<input id="txtConfirmPassword" type="password" onblur="checkPasswordConfirm()" />*&nbsp;
				    		<span id="passwordConfirmErrorMessage" style="color:red;"></span>
				    	</td>
				    </tr>
					<tr>
				    	<td class="col1" align="right">First name:</td>
				    	<td class="col2" align="left">
				    		<form:input id="txtFirstname" path="firstname" onblur="checkFirstname()" />*&nbsp;
				    		<span id="firstnameErrorMessage" style="color:red;"></span>
				    	</td>
				    </tr>
					<tr>
				    	<td class="col1" align="right">Last name:</td>
				    	<td class="col2" align="left">
				    		<form:input id="txtLastname" path="lastname" onblur="checkLastname()" />*&nbsp;
				    		<span id="lastnameErrorMessage" style="color:red;"></span>
				    	</td>
				    </tr>
					<tr>
				    	<td class="col1" align="right">E-mail address:</td>
				    	<td class="col2" align="left">
				    		<form:input id="txtEmail" path="email" onblur="checkEmail()" />*&nbsp;
				    		<span id="emailErrorMessage" style="color:red;"></span>
				    	</td>
				    </tr>
					<tr>
				    	<td class="col1" align="right"></td>
				    	<td class="col2" align="left">*<i> All fields are required</i></td>
				    </tr>
				</table>
				<input type="submit" value="Sign Up" id="btnSignUp" class="btn btn-blue" />
			</form:form>
			<script type="text/javascript" src="<c:url value="/resources/js/registration-validation.js" />"></script>
		</div>
	</div>
</div>

