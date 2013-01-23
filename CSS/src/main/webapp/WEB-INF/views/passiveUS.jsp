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
<%@ taglib prefix='fn' uri='http://java.sun.com/jsp/jstl/functions' %>

<div class="grid_12">
	<h2>${userstory.name} </h2>
	
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
	<c:if test="${not empty errorMessage}">
		<div class="message error">
			<h5>Error</h5>
			<p>${errorMessage}.</p>
		</div>
	</c:if>
	
	<form:form method="post" action="/CSS/spring/userstory/save?id=${userstory.id}" modelAttribute="userstory">
	  	<form:input value="${userstory.id}" type="hidden" path="id" />
	  	<c:if test="${not empty userstory.dataElements}">		
		 	<ul id="sortable">
		 	<c:forEach items="${userstory.dataElements}" var="dataelement" varStatus="status">
		 			<li class="sortableItem" id="dataElement${dataelement.id}">
			 			<div class="box round">
							<div class="box-header<c:if test="${dataelement.type == 'comment'}"> box-header-white</c:if>" >
								<h5 class="floatleft">${dataelement.name}<c:if test="${dataelement.type != 'data' && dataelement.type != 'comment'}">.${dataelement.type}</c:if></h5>
								<button type="button" class="btn btn-icon btn-blue btn-small btn-cross floatright" onclick="location.href='/CSS/spring/userstory/includeDataElement?story=${userstory.id}&dataelement=${dataelement.id}'" >
									<span></span>Remove
								</button>
								<div class="clear"></div>
							</div>
							<input type="hidden" name="dataElements[${status.index}].id" value="${dataelement.id}" id="dataElements[${status.index}].id" >
							<input type="hidden" name="dataElements[${status.index}].name" value="${dataelement.name}" id="dataElements[${status.index}].name">
							<input type="hidden" name="dataElements[${status.index}].type" value="${dataelement.type}" id="dataElements[${status.index}].type">
							<input type="hidden" name="dataElements[${status.index}].position" value="${dataelement.position}" id="dataElements[${status.index}].position" class="dataElementPosition">
							
							<c:choose>
								<c:when test="${dataelement.type == 'jpg'}">
									<ul class="prettygallery clearfix">
			                        	<li>
			                        		<a href="data:image/jpeg;charset=utf-8;base64,${dataelement.stringContent}" rel="prettyPhoto[gallery2]" title="">
			                            		<img name="dataElements[${status.index}].stringContent" src="data:image/jpeg;charset=utf-8;base64,${dataelement.stringContent}" class="dataElementThumb" />
			                            	</a>
			                            </li>
									</ul>		
			                 	</c:when>
							
								<c:when test="${dataelement.type == 'comment'}">
									<textarea name="dataElements[${status.index}].stringContent" rows="12" onfocus="if ($(this).val() == 'Add text here...') { $(this).val(''); }" onblur="if ($(this).val() == '') { $(this).val('Add text here...'); }">${dataelement.stringContent}</textarea>
			                 	</c:when>
							
						  		<c:when test="${dataelement.type == 'data'}">
									${dataelement.stringContent}
			                 	</c:when>
			                 	
			                 	<c:otherwise>
									<textarea class="no-border as-plain-text" rows="12" disabled>${dataelement.stringContent}</textarea>
								</c:otherwise>
							</c:choose>
						</div>
					</li>
					<c:if test="${status.index} % 2 != 0">
						<div class="clearfix"></div>
					</c:if>
			</c:forEach>
			</ul>
			
			<script type="text/javascript">
				$(document).ready(function () {
					// Resize the sortable list items 2 by 2 to have a proper grid
					resizeListItems();
					
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
							resizeListItems();
			            }
					});
					$( "#sortable" ).disableSelection();
					
					// This is not a Jquery '$', but a JSTL tag to get the current number of data elements
					var index = ${fn:length(userstory.dataElements)};
					
					// Enable the addition of Data Element into sortable list
					$(".btnAddDataElement").click(function (e) {
					    e.preventDefault();
					    
					    // Adds the new 'comment' Data Element as last element
					    var $li = $('<li class="sortableItem" id="dataElement0"><div class="box round">' +
						    	'<div class="box-header box-header-white">' +
									'<h5 class="floatleft">Story text</h5>' +
									'<button type="button" class="btn btn-icon btn-blue btn-small btn-cross floatright" onclick="confirm(\'Are you sure you want to remove this text from the user story ?\')" >' +
										'<span></span>Remove' +
									'</button>' +
									'<div class="clear"></div>' +
								'</div>' +
								'<input type="hidden" name="dataElements[' + index + '].id" value="0" id="dataElements[' + index + '].id" >' +
								'<input type="hidden" name="dataElements[' + index + '].name" value="Story text" id="dataElements[' + index + '].name">' +
								'<input type="hidden" name="dataElements[' + index + '].type" value="comment" id="dataElements[' + index + '].type">' +
								'<input type="hidden" name="dataElements[' + index + '].position" value="' + (index + 1) + '" id="dataElements' + index + '].position" class="dataElementPosition">' +
						    '<textarea name="dataElements[' + index + '].stringContent" rows="12" onfocus="if ($(this).val() == \'Add text here...\') { $(this).val(\'\'); }" onblur="if ($(this).val() == \'\') { $(this).val(\'Add text here...\'); }">Add text here...</textarea>' +
						    '</div>' +
					    '</li>');
					    $("#sortable").append($li);
					    $("#sortable").sortable('refresh');
					    index++;
					    
					    // Resize the sortable list items 2 by 2 to have a proper grid
					    resizeListItems();
					});
				});
				
				// Resize the sortable list items 2 by 2 to have a proper grid
				function resizeListItems() {
					var listItems = $("#sortable li.sortableItem .box");
					var boxOne = null;
					listItems.each(function(idx, box) {
						if (idx % 2 != 0) {
							boxTwo = $(box);
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
		<div id="dataElementAdder">
			<button type="submit" class="btnAddDataElement btn btn-icon btn-blue btn-plus" >
				<span></span>Add Text to Story
			</button>
		</div>
		<button type="button" class="btn btn-icon btn-blue btn-check floatright" onclick="submit()">
			<span></span>Save
		</button>
	</form:form>
</div>