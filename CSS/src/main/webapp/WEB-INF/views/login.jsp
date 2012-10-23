<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>

<body>

<h3 align = "center" style="font-style: italic; color: #294052">
	     Sign In 
</h3>
<p style="color:red"> ${controllerMessage} </p>
<form:form method="POST" modelAttribute="person" style="padding:8px">
    <p align = "left" style="color: #294052" >
         Username <br/>
        <form:input path="firstName" />
    </p>
    <p align = "left" style="color: #294052" >
         Password <br/>
        <form:password path="passWord" align ="right" />
    <br/><br/>   
    
    </p>
    <input type="submit" value="Login" />
</form:form>
</body>
</html>
