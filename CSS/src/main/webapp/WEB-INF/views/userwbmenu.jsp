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
<h3 style="color:#FF6600;margin-bottom: 3cm; font-style: italic; position:static;"> ${workboard.person.firstName} ${workboard.person.lastName} </h3> 

<% WorkBoard wb = (WorkBoard) request.getAttribute("workboard") ; %>
<% Person p = (Person) wb.getPerson() ; %>

<form method="post" action="views/fileupload.jsp" name="upform"  
enctype="multipart/form-data">  
  <table width="60%" border="0" cellspacing="1" cellpadding="1"  
align="center" class="style1">  
    <tr>  
      <td align="left" style="color:#294052;font-size: medium ; padding:8px"><b>Select a file to upload :</b></td>  
    </tr>  
    <tr>  
      <td align="left">  
        <input type="file" name="uploadfile" size="50">  
        </td>  
    </tr>  
    <tr>  
      <td align="left">  
  <input type="hidden" name="todo" value="upload">  
        <input type="submit" name="Submit" value="Upload">  
        <input type="reset" name="Reset" value="Cancel">  
        </td>  
    </tr>  
  </table>  
</form>  



<p style="text-align:right;margin-top: 5cm;color: purple; position:static ; ">
<a href="/CSS/spring/login/">Logout</a>
</p>
</body>
</html>