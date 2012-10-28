<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true"%>
<%@ page import="java.lang.*" %>
<%@ page language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" import="war.model.WorkBoard" %>
<%@ page language="java" import="war.model.Person" %>
<%@ page language="java" import="war.model.Files" %>
<html>
<head>
<title>Home</title>
</head>
<body>
<h3 style="background-color:silver; color:black; text-align: center ; margin-bottom:3cm;"> ${workboard.workBoardName} </h3>

<form action="/CSS/spring/createwb/update?workboardid=${workboard.workBoardID}" method=POST>
     	<% 
     	     String fileid ;
     		 String filename, filecontent ;
     	  
     	%>
 <table border=1 cellspacing=1 cellpadding=1>
 	<c:forEach items="${files}" var="filevar">
 		<c:set var="filecontent"> ${filevar.filecontent} </c:set> 
     	<c:set var="fileid"> ${filevar.fileid} </c:set>
     	<c:set var="filename"> ${filevar.filename}.${filevar.type} </c:set>
     	<% 
     		fileid   = (String) pageContext.getAttribute("fileid") ;
     		filename = (String) pageContext.getAttribute("filename") ;
     		filecontent = (String) pageContext.getAttribute("filecontent") ;
     	%>
       <tr>    
      		  <td> <%=filename%> </td>
      		  
              <td> 
              		<textarea name=<%=fileid%> cols ="100" rows="10"> 
                    		<%= filecontent %>
                    </textarea>
              </td>
              
              <td> <a href="/CSS/spring/createwb/delete?dataelementid=<%=fileid%>">delete</a> </td>
       </tr>
	 <br />
</c:forEach>
</table>

	<br />
	<input type=submit value=Save align="left" style="text-align: left">
</form>

<a href="/CSS/spring/createwb/delete?workboardid=${workboard.workBoardID}" style="padding-left: 18cm; text-align:right ;" >Delete WorkBoard</a>
</body>
</html>
