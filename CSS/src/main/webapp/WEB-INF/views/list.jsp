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
<%-- <jsp:setProperty name="person" property="login" value = <% ${person.login} %>/> --%>


<h3 style="color:#FF6600;margin-bottom: 3cm; font-style: italic; position:static;"> ${person.firstname}   ${person.lastname} </h3>

<%-- <% Person p = (WorkBoard) request.getAttribute("workboard") ; %> --%>
<%-- <c:forEach items="${people}" var="v_person">
	<a href="edit?id=${v_person.id}">${v_person.id} -
	${v_person.firstname} ${v_person.lastname}</a>
	<br />
</c:forEach> --%>


<br/>
<br/>

<a href="workboard/add?login=${person.login}" style="font-size: medium;"> Create a Work Board </a>
 

 


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
