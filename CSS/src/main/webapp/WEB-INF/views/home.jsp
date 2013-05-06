<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="grid_12">

	<div class="pitch">
		<h3 class="lightblue">This application is still under development.<br />It may be put offline at any time without warning. The data provided so far is not guaranteed, all data is subject to change or be deleted at any time.</h3>
		<div class="pitch-link"><a href="#">Read more...</a></div>
	</div>
	
	<div class="box round first" style="text-align:center">
		<div class="box-content">
			<h3>Connect Now</h3>
			<div class="block">
				<input type="button" class="btn btn-blue" value="Log In" onclick="location.href='/CSS/auth/workboard/my-workboard'" />&nbsp;
				<input type="button" class="btn btn-blue" value="Sign Up" onclick="location.href='/CSS/register'" />
			</div>
		</div>
	</div>
	<h3 style="text-align: center">OR</h3>
	<div class="box round first" style="text-align:center">
		<div class="box-content">
			<h3>Search for Reports</h3>
			<div class="block">
				<input type="button" class="btn btn-blue" value="Browse Reports" onclick="location.href='/CSS/public/reports/list'" />
			</div>
		</div>
	</div>
	<p>
		<center>
			<h4>Detailed presentation of the application</h4>
			Text, pictures, etc.
		</center>
	</p>
</div>