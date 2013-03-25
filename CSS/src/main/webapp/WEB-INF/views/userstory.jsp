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
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="grid_12">
	<!-- Titles -->
	<div style="margin-left: 20px; float:left">
		<h2>${userstory.name} </h2>
		<h4>${userstory.region.name}</h4>
	</div>
	
	<a href="/CSS/auth/userstory/view?id=${userstory.id}" target="_blank" style="margin-right: 10px; float:right">
		<button class="btnAddDataElement btn btn-icon btn-blue btn-doc" >
			<span></span>Preview the report
		</button>
	</a>
	<a href="#" target="_blank" style="margin-right: 10px; float:right">
		<button class="btnAddDataElement btn btn-icon btn-grey btn-globe" >
			<span></span>Publish the report
		</button>
	</a>
	<a href="javascript: $('#userStoryForm').submit();" style="margin-right: 10px; float:right">
		<button type="button" class="btn btn-icon btn-blue btn-check floatright">
			<span></span>Save changes
		</button>
	</a>
	<div id="dataElementAdder">
		<a href="/CSS/auth/userstory/addText?story=${userstory.id}" style="margin-right: 10px; float:right">
			<button class="btnAddDataElement btn btn-icon btn-blue btn-plus">
				<span></span>Add Text to Story
			</button>
		</a>
	</div>
	
	<div class="clear"></div><br />
	
	<c:set var="successMessage" scope="request" value="${successMessage}"/>
	<c:set var="warningMessage" scope="request" value="${warningMessage}"/>
	<c:set var="errorMessage" scope="request" value="${errorMessage}"/> 			
	<jsp:include page="notifications.jsp" />
	
	<form:form id="userStoryForm" method="post" action="/CSS/auth/userstory/save" modelAttribute="userstory">
	  	<form:input value="${userstory.id}" type="hidden" path="id" />
	  	<c:if test="${not empty userstory.dataElements}">		
		 	<ul id="sortable">
		 	
		 	<!-- Iteration on every element in the User Story -->
		 	<c:forEach items="${userstory.dataElements}" var="dataelement" varStatus="status">
		 			
	 			<c:set var="dataelement" scope="request" value="${dataelement}"/>
	 			
	 			<li class="sortableItem" id="dataElement${dataelement.id}">
		 			<div class="box round${dataelement.included == false ? ' box-disabled' : ''}">
						
						<div class="box-header<c:if test="${dataelement.class.simpleName == 'DataElementFile' && dataelement.filetype == 'comment'}"> box-header-white</c:if>" >
							<h5 class="floatleft">${dataelement.name}<c:if test="${dataelement.class.simpleName == 'DataElementFile' && dataelement.filetype != 'comment'}">.${dataelement.filetype}</c:if></h5>
							
							<!-- 'Include/Exclude' button -->
							<button type="button" class="btn-mini ${dataelement.included == false ? 'btn-grey btn-plus' : 'btn-blue btn-minus'} floatright" onclick="location.href='/CSS/auth/userstory/includeDataElement?story=${userstory.id}&dataelement=${dataelement.id}'" title="Include/Exclude from the story">
								<span></span>Include/Exclude
							</button>
							
							<!-- 'Remove Text' button -->
							<c:if test="${dataelement.class.simpleName == 'DataElementText'}">
								<a class="lnkRemoveTextFromStory" href="/CSS/auth/userstory/deleteText?text=${dataelement.id}" title="Delete the text from the story">
									<button type="button" class="btn btn-icon ${dataelement.included == false ? 'btn-grey' : 'btn-blue'} btn-small btn-cross floatright" style="margin-right:5px">
										<span></span>Delete
									</button>
								</a>
							</c:if>
							<div class="clear"></div>
						</div>
						
						<input type="hidden" name="dataElements[${status.index}].id" value="${dataelement.id}" id="dataElements[${status.index}].id" >
						<input type="hidden" name="dataElements[${status.index}].name" value="${dataelement.name}" id="dataElements[${status.index}].name">
						<input type="hidden" name="dataElements[${status.index}].position" value="${dataelement.position}" id="dataElements[${status.index}].position" class="dataElementPosition">
						
						<!-- Text comment data element -->
					 	<c:if test="${dataelement.class.simpleName == 'DataElementText'}">
							<textarea name="comments" class="tinymce" rows="12">${dataelement.text}</textarea>	
                 		</c:if>
                 		
						<c:if test="${dataelement.class.simpleName != 'DataElementText'}">
							<div class="box-content">
								<!-- CSIRO Data Element -->
								<c:if test="${dataelement.class.simpleName == 'DataElementCsiro'}">
			 						<jsp:include page="dataElementCsiro.jsp" />
			 					</c:if>
			 					
			 					<!-- CMAR Data Element -->
								<c:if test="${dataelement.class.simpleName == 'DataElementCmar'}">
			 						<jsp:include page="dataElementCmar.jsp" />
			 					</c:if>
								
								<!-- Engineering Model Data Element -->
								<c:if test="${dataelement.class.simpleName == 'DataElementEngineeringModel'}">
			 						<jsp:include page="dataElementEngineeringModel.jsp" />
			 					</c:if>
		
								<!-- File Data Element, display a picture if JPEG, textarea with content otherwise -->
								<c:if test="${dataelement.class.simpleName == 'DataElementFile'}">
				 					<jsp:include page="dataElementFile.jsp" />
								</c:if>
	                 		</div>
                 		</c:if>
					</div>
				</li>
				<c:if test="${status.index} % 2 != 0">
					<div class="clearfix"></div>
				</c:if>
			</c:forEach>
			</ul>

			<script language="javascript" type="text/javascript" src="<c:url value="/resources/js/tiny_mce/tiny_mce.js" />"></script>
			<script type="text/javascript">
				tinyMCE.init({
			        // General options
			        mode : "textareas",
			        theme : "advanced",
			        plugins : "pagebreak,style,layer,table,save,advhr,advimage,advlink,emotions,iespell,inlinepopups,insertdatetime,preview,media,searchreplace,print,contextmenu,paste,directionality,fullscreen,noneditable,visualchars,nonbreaking,xhtmlxtras,template",
			
			        // Theme options
			        theme_advanced_buttons1 : "save,newdocument,|,bold,italic,underline,strikethrough,|,sub,sup,charmap,|,bullist,numlist,blockquote,outdent,indent,|,justifyleft,justifycenter,justifyright,justifyfull" 
			        	 + "fontselect,fontsizeselect,forecolor,backcolor,formatselect,|,link,unlink,|,insertdate,inserttime,|,cleanup,code,|,help",
			        theme_advanced_buttons2 : "",
			        theme_advanced_buttons3 : "",
			        theme_advanced_buttons4 : "",
			        theme_advanced_toolbar_location : "top",
			        theme_advanced_toolbar_align : "left",
			        theme_advanced_statusbar_location : "bottom",
			        theme_advanced_resizing : false,
			        width: "100%"
				});
			</script>
			
			<script type="text/javascript">
				$(document).ready(function () {
					
					// Resize the sortable list items 2 by 2 to have a proper grid
					//resizeListItems();
					
					// Enables the sortable list
					$( "#sortable" ).sortable({
						placeholder: "sortable-placeholder",
						cursor: 'crosshair',
			            update: function(event, ui) {
			            	// Reorder the positions into the hidden field of each data element
			            	var order = $("#sortable").sortable("toArray");
							for (var i = 0; i < order.length; i++)
							{
								var element = $("#" + order[i]);
								element.find(".dataElementPosition").attr("value", (i + 1));
							}
							// Resize the sortable list items 2 by 2 to have a proper grid
							//resizeListItems();
			            }
					});
					$( "#sortable" ).disableSelection();
					
					// This is not a Jquery '$', but a JSTL tag to get the current number of data elements
					var index = ${fn:length(userstory.dataElements)};
				});
				
				// Resize the sortable list items 2 by 2 to have a proper grid
				function resizeListItems() {
					var listItems = $("#sortable li.sortableItem .box");
					var boxOne = null;
					listItems.each(function(idx, box) {
						if (idx % 2 != 0) {
							boxTwo = $(box);
							boxOne.css('height','auto');
							boxTwo.css('height','auto');
							if (boxOne.height() > boxTwo.height())
								boxTwo.height(boxOne.height());
							else if (boxTwo.height() > boxOne.height())
								boxOne.height(boxTwo.height());
					    }
					    else
					    	boxOne = $(box);
					});
				}
			</script>
		</c:if>
		<div class="clearfix"></div><br />
		<button type="button" class="btn btn-icon btn-blue btn-check floatright" onclick="$('#userStoryForm').submit();">
			<span></span>Save changes
		</button>
		<div class="clearfix"></div><br />
	</form:form>
		
	<div id="confirmTextDeletionModalWindow" title="Permanently delete this text ?">
		<p>Are you sure you want to permanently delete this text ?</p> 
	</div>
	<script type="text/javascript">
		setupConfirmBox("confirmTextDeletionModalWindow", "lnkRemoveTextFromStory");
	</script>
</div>