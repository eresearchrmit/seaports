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
<html>
<head>
<title>Home</title>
</head>
<body>
<table>
	<tr> 
	  <td>  
	      <font style="background-color:silver;font-size:medium ;color:red; text-align: center ; margin-bottom:3cm"> ${workboard.person.firstName} ${workboard.person.lastName} </font> 
	  </td>  
	</tr>
	<tr> 
	  <td>  
	      <font style="background-color:silver;font-size:x-large ;color:blcak; text-align: center ; margin-bottom:3cm"> ${workboard.workBoardName} </font> 
	  </td>  
	</tr>

</table>

<form:form method="post" modelAttribute="stringfiles">
	
  	<c:if test="${not empty stringfiles}"> 
	<table border=1 cellspacing=1 cellpadding=1>
	
 	<c:forEach items="${stringfiles.files}" var="file" varStatus="status"> 
 	
 		<c:set var="stringcontent">${file.filecontent}</c:set>
       	<tr>    
      		  <td> ${file.filename}.${file.type}  </td>
      		  
              <td> 
  					<input name="files[${status.index}].fileid" value= ${file.fileid} type="hidden">
					<c:choose>
				  		<c:when test="${file.type != 'jpg'}">
              				<textarea name="files[${status.index}].filecontent" cols ="100" rows="10" readonly="readonly"> 
                    			${file.filecontent}		
                    		</textarea>
                  		</c:when>
                  		
                  	    <c:otherwise>  
							<img name="files[${status.index}].filecontent" src="data:image/jpeg;charset=utf-8;base64,${file.filecontent}"  />  
                   		</c:otherwise> 
                   	</c:choose>
                   
                    <input name="files[${status.index}].filename" value= ${file.filename} type="hidden">
              </td>
                            
              <td> <a href="/CSS/spring/createus/addfile?dataelementid=${file.fileid}">add</a> </td>
       	</tr>  	
	 	<br />
	</c:forEach>
	</table>
	</c:if>	
  
</form:form>

</body>
</html>
