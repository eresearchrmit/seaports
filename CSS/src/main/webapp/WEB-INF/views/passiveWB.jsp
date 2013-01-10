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

<div class="grid_12">
	<h2>Workboard: ${workboard.workBoardName} </h2>
	<form:form method="post" action="/CSS/spring/workboard/update?workboardid=${workboard.workBoardID}" modelAttribute="stringfiles">
	  	<c:if test="${not empty dataelements}">		
		 	<ul id="sortable">
		 	<c:forEach items="${dataelements}" var="dataelement" varStatus="status">
		 			<li style="list-style-type:none;float:left;width:45%">
			 			<div class="box round">
							<div class="box-header">
								<h5 class="floatleft">${dataelement.filename}<c:if test="${dataelement.type != 'data'}">.${dataelement.type}</c:if></h5>
								<button type="button" class="btn btn-icon btn-blue btn-small btn-arrow-right floatright" onclick="location.href='/CSS/spring/userstory/addfile?workboardid=${workboard.workBoardID}&fileid=${dataelement.fileid}'" >
									<span></span>Add
								</button>
								<div class="clear"></div>
							</div>
							<input name="files[${status.index}].fileid" value= ${dataelement.fileid} type="hidden">
							<input name="files[${status.index}].filename" value= ${dataelement.filename} type="hidden">
							
							<c:choose>
								<c:when test="${dataelement.type == 'jpg'}">
									<ul class="prettygallery clearfix">
			                        	<li>
			                        		<a href="data:image/jpeg;charset=utf-8;base64,${dataelement.filecontent}" rel="prettyPhoto[gallery2]" title="">
			                            		<img name="dataelements[${status.index}].filecontent" src="data:image/jpeg;charset=utf-8;base64,${dataelement.filecontent}" class="dataElementThumb" />
			                            	</a>
			                            </li>
									</ul>		
			                 	</c:when>
							
						  		<c:when test="${dataelement.type == 'data'}">
									${dataelement.filecontent}
			                 	</c:when>
			                 		
			                 	<c:otherwise>
									<textarea name="files[${status.index}].filecontent" rows="12" disabled>${dataelement.filecontent}</textarea>
								</c:otherwise>
							</c:choose>
						</div>
					</li>
			</c:forEach>
			</ul>
			<script type="text/javascript">
				$(document).ready(function () {
					$( "#sortable" ).sortable();
					$( "#sortable" ).disableSelection();
				});
			</script>
		</c:if>
	</form:form>
</div>