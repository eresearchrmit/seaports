<%@ page session="true"%>
<%@ page language="java" %>
<%@ page import="java.lang.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.codec.binary.*"%>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ page import="org.apache.commons.lang.*" %>
<%@ page language="java" import="war.model.WorkBoard" %>
<%@ page language="java" import="war.model.Person" %>
<%@ page language="java" import="war.model.DataElement" %>
<%@ page language="java" import="war.service.DataElementService" %>
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



<form:form method="post" modelAttribute="stringdataelements">
	
	<c:out value="${stringdataelements.dataelements}"/>	 
	<table border=1 cellspacing=1 cellpadding=1>
	<c:if test="${empty stringdataelements}">
	
	<c:out value="${stringdataelements.dataelements}"/>
	
 	<c:forEach items="${stringdataelements.dataelements}" var="dataelement" varStatus="status"> 
 	
 		<c:set var="stringcontent">${dataelement.dataelementcontent}</c:set>
       	<tr>    
      		  <td> ${dataelement.dataelementname}.${dataelement.extension}  </td>
      		  
              <td> 
  					<input name="dataelements[${status.index}].dataelementid" value= ${dataelement.dataelementid} type="hidden">
  					
					<c:choose>
				  		<c:when test="${dataelement.extension != 'jpg'}">
              				<textarea name="dataelements[${status.index}].dataelementcontent" cols ="100" rows="10" readonly="readonly"> 
                    			${dataelement.dataelementcontent}		
                    		</textarea>
                  		</c:when>
                  		
                  	    <c:otherwise>  
							<img name="dataelements[${status.index}].dataelementcontent" src="data:image/jpeg;charset=utf-8;base64,${dataelement.dataelementcontent}" />  
                   		</c:otherwise> 
                   	</c:choose>
                   
                    <input name="dataelements[${status.index}].dataelementname" value= ${dataelement.dataelementname} type="hidden">
              </td>
                            
              <td> <a href="/CSS/spring/createus/deletefile?fileid=${file.fileid}&workboardid=${workboard.workBoardID}">delete</a> </td>
       	</tr>  	
	 	<br />
	</c:forEach>
	</c:if>	
	</table>

  
</form:form>

</body>
</html>
<div class="grid_5">
	<form:form method="post" modelAttribute="stringfiles">
		<p><textarea name="title" cols ="90" rows="10">Type the Title of the User Story</textarea></p> 
		<p><textarea name="picture1" cols ="90" rows="10" readonly="readonly" style="background-color: lightgrey;"></textarea></p>
		<p><textarea name="picture2" cols ="90" rows="10" readonly="readonly" style="background-color: lightgrey;"></textarea></p>
		<p><textarea name="content" cols ="90" rows="10" >Type the Content of the User Story</textarea></p>
		<p><textarea name="picture3" cols ="90" rows="10" readonly="readonly" style="background-color: lightgrey;"></textarea></p>
	</form:form>
	<p><button class="floatright btn btn-icon btn-check btn-blue btn-big" onclick="location.href='/CSS/spring/login'"><span></span>End Demo</button></p>
</div>
