<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page session="true"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page language="java" import="war.model.WorkBoard" %>
<%@ page language="java" import="war.model.Person" %>

<html>
<head>
    <title>Work Board Page</title>
</head>
<body>
<h3 style="color:green;"> User ${workboard.person.firstName} ${workboard.person.lastName} </h3> 
<%-- <% Person p = workboard.person ; %> --%>
<%-- <jsp:useBean id="person" class="war.model.Person" scope="page" /> --%>
<%-- <h3 style="color:green;"> ${workboard.WorkBoardID} </h3>  --%>
<% WorkBoard wb = (WorkBoard) request.getAttribute("workboard") ; %>
<% Person p = (Person) wb.getPerson() ; %>
<br/>
<br/>
<br/>

<h4 align="center">Enter WorkBoard Title</h4>
<form:form method="POST" modelAttribute="workboard" style="padding:8px" >

  <table align="left">
    <tr>
        <td><form:input path="WorkBoardName"/></td>
    </tr>
    <tr>
<%--         <td><form:hidden path="person" value = "<%= p  %>"  /></td> --%>
    </tr>
    
    <tr>
        <td colspan="2">
            <input type="submit" value="Create"/>
        </td>
    </tr>
</table> 
</form:form>


<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<a href="login">Logout</a>
</body>
</html>