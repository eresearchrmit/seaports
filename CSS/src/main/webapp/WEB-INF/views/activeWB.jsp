<%@ page session="true"%>
<%@ page language="java" %>
<%@ page import="java.lang.*" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ page language="java" import="war.model.WorkBoard" %>
<%@ page language="java" import="war.model.Person" %>
<%@ page language="java" import="war.model.Files" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Home</title>
</head>
<body>
<h3 style="background-color:silver; color:black; text-align: center ; margin-bottom:3cm"> ${workboard.workBoardName} </h3>
<%--      	<%     
     		 String filename, fileid, filecontent ;
     	%> --%>
<%-- <c:out value="${stringfiles}"></c:out> --%>

<form:form method="post" action="/CSS/spring/createwb/update?workboardid=${workboard.workBoardID}" modelAttribute="stringfiles">
	
  	<c:if test="${not empty stringfiles}"> 
	<table border=1 cellspacing=1 cellpadding=1>
	
 	<c:forEach items="${stringfiles.files}" var="file" varStatus="status"> 
 		
       	<tr>    
      		  <td> ${file.filename}.${file.type}  </td>
      		  
              <td> 
  					<input name="files[${status.index}].fileid" value= ${file.fileid} type="hidden">
              		<textarea name="files[${status.index}].filecontent" cols ="100" rows="10"> 
                    		${file.filecontent}
                    </textarea>
                    <input name="files[${status.index}].filename" value= ${file.filename} type="hidden">
              </td>
                            
              <td> <a href="/CSS/spring/createwb/deletefile?dataelementid=${file.fileid}">delete</a> </td>
       	</tr>  	
	 	<br />
	</c:forEach>
			<tr> <td> <input type="submit" value=Save align="left" style="text-align: left"/> </td> </tr>
	</table>
	</c:if>	
  
</form:form>


<a href="/CSS/spring/createwb/deleteWB?workboardid=${workboard.workBoardID}" style="padding-left: 18cm; text-align:right ;" >Delete WorkBoard</a>
</body>
</html>
