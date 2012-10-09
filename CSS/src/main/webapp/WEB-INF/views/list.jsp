<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" import="war.model.Person" %>
<html>
<head>
<title>Home</title>
</head>
<body>
<%-- <jsp:useBean id="person" class="war.model.Person" scope="page" /> --%>
<%-- <jsp:setProperty name="person" property="firstName" value = <% ${person.firstName} %>/> --%>


<h3 style="color:green;"> ${person.firstName}   ${person.lastName} </h3>

<%-- <% Person p = (WorkBoard) request.getAttribute("workboard") ; %> --%>
<%-- <c:forEach items="${people}" var="v_person">
	<a href="edit?id=${v_person.id}">${v_person.id} -
	${v_person.firstName} ${v_person.lastName}</a>
	<br />
</c:forEach> --%>


<br/>
<br/>

<a href="createwb?firstName=${person.firstName}" style="font-size: large;"> Create a Work Board </a>
 

 


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
<br/>
<br/>
<br/>
<br/>
<a href="login">Logout</a>
</body>
</html>
