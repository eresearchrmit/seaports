<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ page import="war.model.DataElementCsiro" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:choose>
	<c:when test="${dataelement.filetype == 'jpg' || dataelement.filetype == 'jpeg'}">
		<ul class="prettygallery clearfix">
			<li>
				<a href="data:image/jpeg;charset=utf-8;base64,${dataelement.stringContent}" target="_blank" rel="prettyPhoto" title="${dataelement.name}">
					<img name="${dataelement.name}" src="data:image/jpeg;charset=utf-8;base64,${dataelement.stringContent}" style="max-width:100%; max-height: 500px;" />
				</a>
	    	</li>
		</ul>		
    </c:when>

	<c:otherwise>
		<!-- <textarea name="dataelements[${status.index}].name" rows="12" disabled>${dataelement.stringContent}</textarea> -->
		${dataelement.stringContent}
	</c:otherwise>
</c:choose>