<%@ page session="true"%>
<%@ page language="java" %>
<%@ page import="java.lang.*" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.codec.binary.*"%>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ page import="org.apache.commons.lang.*" %>
<%@ page language="java" import="war.model.UserStory" %>
<%@ page language="java" import="war.model.User" %>
<%@ page language="java" import="war.model.DataElement" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="grid_12">
	<h2>Workboard: ${workboard.name} </h2>
	<form:form method="post" action="/CSS/spring/workboard/update?workboardid=${workboard.id}" modelAttribute="stringfiles">
	  	<c:if test="${not empty dataelements}">		
		 	<ul id="sortable">
		 	<c:forEach items="${dataelements}" var="dataelement" varStatus="status">
		 			<li style="list-style-type:none;float:left;width:45%">
			 			<div class="box round">
							<div class="box-header">
								<h5 class="floatleft">${dataelement.name}<c:if test="${dataelement.type != 'data'}">.${dataelement.type}</c:if></h5>
								<button type="button" class="btn btn-icon btn-blue btn-small btn-arrow-right floatright" onclick="location.href='/CSS/spring/userstory/includeDataElement?story=${workboard.id}&dataelement=${dataelement.id}'" >
									<span></span>Add
								</button>
								<div class="clear"></div>
							</div>
							<input name="dataelements[${status.index}].id" value= ${dataelement.id} type="hidden">
							<input name="dataelements[${status.index}].name" value= ${dataelement.name} type="hidden">
							
							<c:choose>
								<c:when test="${dataelement.type == 'jpg'}">
									<ul class="prettygallery clearfix">
			                        	<li>
			                        		<a href="data:image/jpeg;charset=utf-8;base64,${dataelement.stringContent}" rel="prettyPhoto[gallery2]" title="">
			                            		<img name="dataelements[${status.index}].stringContent" src="data:image/jpeg;charset=utf-8;base64,${dataelement.stringContent}" class="dataElementThumb" />
			                            	</a>
			                            </li>
									</ul>		
			                 	</c:when>
							
						  		<c:when test="${dataelement.type == 'data'}">
									${dataelement.stringContent}
			                 	</c:when>
			                 		
			                 	<c:otherwise>
									<textarea name="dataelements[${status.index}].stringContent" rows="12" disabled>${dataelement.stringContent}</textarea>
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