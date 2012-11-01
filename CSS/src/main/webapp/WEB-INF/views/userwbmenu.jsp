<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="true"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" import="war.model.WorkBoard" %>
<%@ page language="java" import="war.model.Person" %>
<%@ page language="java" import="war.model.Files" %>


<html>
<head>
    <title>Work Board Page</title>
</head>
<body>
<h3 style="color:#FF6600;margin-bottom: 3cm; font-style: italic; position:static;"> ${workboard.person.firstName} ${workboard.person.lastName} </h3> 

<% WorkBoard wb = (WorkBoard) request.getAttribute("workboard") ; %>
<% Person p = (Person) wb.getPerson() ; %>

	
<form:form method="post" action="/CSS/spring/createwb/upload?workboardid=${workboard.workBoardID}" modelAttribute="file" enctype="multipart/form-data">
    <table>
    <tr>
        <td> File Upload </td>
        <td><input type="file" name="files" id="files"></input></td>
    </tr>
    <tr>
        <td colspan="2">
            <input type="submit" value="Upload File"/>
        </td>
    </tr>
</table> 
</form:form>  


<p style="text-align:right;margin-top: 5cm;color: purple; position:static ; ">
<a href="/CSS/spring/login/">Logout</a>
</p>
</body>
</html>