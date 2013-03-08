<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:if test="${not empty successMessage}">
	<div id="successMessage" class="message success">
		<h5>Success !</h5>
		<p>${successMessage}.</p>
	</div>
	<!-- Makes the success messages fade out after 3 seconds -->
	<script type="text/javascript">
		$(document).ready(function(){
			setTimeout(function(){
				$("#successMessage").fadeOut("slow", function () {
					$("#successMessage").remove();
				});
			}, 3000);
		});
	</script>
</c:if>
<c:if test="${not empty warningMessage}">
	<div class="message warning">
		<h5>Warning</h5>
		<p>${warningMessage}.</p>
	</div>
</c:if>
<c:if test="${not empty errorMessage}">
	<div class="message error">
		<h5>Error</h5>
		<p>${errorMessage}.</p>
	</div>
</c:if>