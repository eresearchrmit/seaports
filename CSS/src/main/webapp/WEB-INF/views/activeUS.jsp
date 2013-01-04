<%@ page session="true"%>
<%@ page language="java" %>
<%@ page import="java.lang.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.codec.binary.*"%>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ page import="org.apache.commons.lang.*" %>
<%@ page language="java" import="war.model.WorkBoard" %>
<%@ page language="java" import="war.model.Person" %>
<%@ page language="java" import="war.model.Files" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="grid_5">
	<form:form method="post" modelAttribute="stringfiles">
		<p><textarea name="title" cols ="90" rows="10">Type the Title of the User Story</textarea></p> 
		<p><textarea name="picture1" cols ="90" rows="10" readonly="readonly" style="background-color: lightgrey;"></textarea></p>
		<p><textarea name="picture2" cols ="90" rows="10" readonly="readonly" style="background-color: lightgrey;"></textarea></p>
		<p><textarea name="content" cols ="90" rows="10" >Type the Content of the User Story</textarea></p>
		<p><textarea name="picture3" cols ="90" rows="10" readonly="readonly" style="background-color: lightgrey;"></textarea></p>
	</form:form>
	<p><button class="floatright btn btn-icon btn-check btn-blue btn-big" onclick="location.href='/CSS/spring/login'"><span></span>End Demo</button></p>
</div>
