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
</table>

<form:form method="post" modelAttribute="stringfiles">
	
  <table border=1 cellspacing=1 cellpadding=1>
	
       	<tr>    
      		  <td>   
      		  	<textarea name="title" cols ="90" rows="10"> 
                    			Type the Title of the User Story	
                </textarea>
      		  </td>
      	</tr>
      	
      	<tr>
       		  <td>   
      		  	<textarea name="picture1" cols ="90" rows="10" readonly="readonly" style="background-color: aqua ;"> 

				</textarea>
      		  </td>
		</tr>

      	 <tr>
       		  <td>   
      		  	<textarea name="picture2" cols ="90" rows="10" readonly="readonly" style="background-color: blue ;"> 
                    				
                </textarea>
      		  </td>     	
      	</tr>    
      	
      	<tr>    
      		  <td>   
      		  	<textarea name="content" cols ="90" rows="10" > 
                    			Type the Content of the User Story	
                </textarea>
      		  </td>
      	</tr>  
      	
      	<tr>
       		  <td>   
      		  	<textarea name="picture3" cols ="90" rows="10" readonly="readonly" style="background-color: yellow ;"> 
                    				
                </textarea>
      		  </td>     	
      	</tr>  
      	
 	</table>
  
</form:form>

</br>
</br>
</br>
</br>
</br>
</br>

<a href="/CSS/spring/login">End Demo Logout</a>

</body>
</html>
