<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ page import="war.model.DataElementCsiro" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:choose>
	<c:when test="${dataelement.filetype == 'jpg' || dataelement.filetype == 'jpeg'}">
		<ul class="prettygallery clearfix">
			<li>
				<a href="data:image/jpeg;charset=utf-8;base64,${dataelement.stringContent}" rel="prettyPhoto[gallery2]" title="${dataelement.name}">
					<img name="${dataelement.name}" src="data:image/jpeg;charset=utf-8;base64,${dataelement.stringContent}" class="dataElementThumb" />
				</a>
	    	</li>
		</ul>		
    </c:when>
    
	<c:otherwise>
		<!-- <textarea name="dataelements[${status.index}].name" rows="12" disabled>${dataelement.stringContent}</textarea> -->
		${dataelement.stringContent}
	</c:otherwise>
</c:choose>