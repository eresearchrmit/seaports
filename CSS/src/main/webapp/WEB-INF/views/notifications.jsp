<%--
 Copyright (c) 2013, RMIT University, Australia.
 All rights reserved.
  
 This code is under the BSD license. See 'license.txt' for details.
 Project hosted at: https://code.google.com/p/climate-smart-seaports/
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:if test="${not empty successMessage}">
	<div id="successMessage" class="message success">
		<h5>Success !</h5>
		<p><c:out value="${successMessage}."/></p>
	</div>
</c:if>
<c:if test="${not empty warningMessage}">
	<div id="warningMessage" class="message warning">
		<h5>Warning</h5>
		<p><c:out value="${warningMessage}." /></p>
	</div>
</c:if>
<c:if test="${not empty errorMessage}">
	<div id="errorMessage" class="message error">
		<h5>Error</h5>
		<p><c:out value="${errorMessage}." /></p>
	</div>
</c:if>