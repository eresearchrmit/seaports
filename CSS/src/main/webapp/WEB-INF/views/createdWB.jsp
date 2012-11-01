<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" import="war.model.WorkBoard" %>
<%@ page language="java" import="war.model.Person" %>
<html>
<head>
<title>Home</title>
</head>
<body>
<h3 style="background-color:silver; color:black; text-align: center ; margin-bottom:11cm;"> ${workboard.workBoardName} </h3>
<% WorkBoard wb = (WorkBoard) request.getAttribute("workboard") ; %>
<% Person p = (Person) wb.getPerson() ; %>
<% int wbid = wb.getWorkBoardID() ;  %>





<a href="delete?workboardid=<%=wb.getWorkBoardID() %>" style="font-size: large; margin-top: 5cm"> Delete </a>

</body>
</html>
