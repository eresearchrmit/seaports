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
		<h2><c:out value="${userstory.name}" /></h2>
		<h4><c:out value="${userstory.seaport.region.name}" /> region</h4>
	</div>
	
	<c:choose>
		<c:when test="${userstory.mode == 'published'}">
			<a href="#" style="margin-right: 10px; float:right">
				<button class="btnAddDataElement btn btn-icon btn-green btn-globe" >
					<span></span>Report Published
				</button>
			</a>
			<a href="/CSS/auth/userstory/view?id=${userstory.id}" target="_blank" style="margin-right: 10px; float:right">
				<button class="btnAddDataElement btn btn-icon btn-blue btn-doc" >
					<span></span>Preview the report
				</button>
			</a>
			<a href="#" style="margin-right: 10px; float:right">
				<button class="btnDeleteStory btn-icon btn-grey btn-cross" >
					<span></span>Delete the report
				</button>
			</a>
			<a href="#" style="margin-right: 10px; float:right">
				<button type="button" class="btn btn-icon btn-grey btn-refresh floatright">
					<span></span>Save order
				</button>
			</a>
			<div id="dataElementAdder">
				<a href="#" style="margin-right: 10px; float:right">
					<button class="btnAddDataElement btn btn-icon btn-grey btn-plus">
						<span></span>Add Text to report
					</button>
				</a>
			</div>
		</c:when>
		<c:otherwise>
			<a class="lnkPublishUserStory" href="/CSS/auth/userstory/publish?id=${userstory.id}" style="margin-right: 10px; float:right">
				<button class="btnAddDataElement btn btn-icon btn-blue btn-globe" >
					<span></span>Publish the report
				</button>
			</a>
			<a href="/CSS/auth/userstory/view?id=${userstory.id}" target="_blank" style="margin-right: 10px; float:right">
				<button class="btnAddDataElement btn btn-icon btn-blue btn-doc" >
					<span></span>Preview the report
				</button>
			</a>
			<a class="lnkDeleteUserStory" href="/CSS/auth/userstory/delete?id=${userstory.id}" style="margin-right: 10px; float:right">
				<button class="btnDeleteStory btn-icon btn-blue btn-cross" >
					<span></span>Delete the report
				</button>
			</a>
			<a href="javascript: $('#userStoryForm').submit();" style="margin-right: 10px; float:right">
				<button type="button" class="btn btn-icon btn-blue btn-refresh floatright">
					<span></span>Save order
				</button>
			</a>
			<div id="btnTextAddition">
				<button id="btnOpenAddTextDataElementModalWindow" type="button" 
				class="btnAddText btn btn-icon btn-blue btn-plus"
				style="margin-right: 10px; float:right">
					<span></span>Add Text to report
				</button>
			</div>
			<div id="addTextDataElementModalWindow" class="box round" title="Add text" style="display:none; padding:0;">
				<form:form id="textAdditionForm" method="post" action="/CSS/auth/userstory/addText" > 
					<textarea name="textContent" class="tinymce" rows="12">
					</textarea>
					<br />
					<div style="height: 50px">
						<a href="javascript: $('#textAdditionForm').submit();" style="margin-right: 10px; float:right">
							<button type="button" class="btn btn-icon btn-blue btn-plus btn-small floatright">
								<span></span>Add Text to report
							</button>
						</a>
						<input type="hidden" name="userStoryId" value="${userstory.id}" >
						<div style="margin-right: 10px; float:right">
							<span class="hint">Insert text after:</span>
							<select name="textInsertPosition">
								<option value="0">[Insert in 1st position]</option>
								<c:if test="${not empty userstory.dataElements}">
									<c:forEach items="${userstory.dataElements}" var="dataelement" varStatus="status">
										<option value="${dataelement.position}"${status.index == 0 ? ' selected' : ''}>
										${dataelement.name}<c:if test="${dataelement.class.simpleName == 'DataElementFile'}">.${dataelement.filetype}</c:if>
										</option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>
				</form:form>
			</div>
		</c:otherwise>
	</c:choose>
	
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
						
						<div class="box-header" >
							<h5 class="floatleft">${dataelement.name}<c:if test="${dataelement.class.simpleName == 'DataElementFile'}">.${dataelement.filetype}</c:if> ${dataelement.included == false ? '[excluded]' : ''}</h5>
							
							<c:if test="${userstory.mode != 'published'}">
								<!-- 'Include/Exclude' button -->
								<button type="button" class="btn-mini btn-blue ${dataelement.included == false ? ' btn-plus' : ' btn-minus'} floatright" onclick="location.href='/CSS/auth/userstory/includeDataElement?story=${userstory.id}&dataelement=${dataelement.id}'" title="Include/Exclude from the story">
									<span></span>Include/Exclude
								</button>
							
								<!-- 'Remove Text' button -->
								<c:if test="${dataelement.class.simpleName == 'DataElementText'}">
									<a class="lnkRemoveTextFromStory" href="/CSS/auth/userstory/deleteText?text=${dataelement.id}" title="Delete the text from the story">
										<button type="button" class="btn btn-icon btn-blue btn-small btn-cross floatright" style="margin-right:5px">
											<span></span>Delete
										</button>
									</a>
									<button id="btnOpenEditTextDataElementModalWindow" type="button" 
									class="btn btn-small btn-icon btn-blue btn-edit floatright btnEditText"
									style="margin-right: 10px; float:right" onClick="$('#hdnTextDataElementToEditId').val(${dataelement.id}); tinyMCE.activeEditor.setContent('${dataelement.text}');">
										<span></span>Edit text
									</button>
								</c:if>
							</c:if>
							<div class="clear"></div>
						</div>
						
						<input type="hidden" name="dataElements[${status.index}].id" value="${dataelement.id}" id="dataElements[${status.index}].id" >
						<input type="hidden" name="dataElements[${status.index}].name" value="${dataelement.name}" id="dataElements[${status.index}].name">
						<input type="hidden" name="dataElements[${status.index}].position" value="${dataelement.position}" id="dataElements[${status.index}].position" class="dataElementPosition">
						
						<c:if test="${dataelement.included == true}">
						
						<!-- Text comment data element -->
					 	<c:if test="${dataelement.class.simpleName == 'DataElementText'}">
							<div style="padding: 10px">
								${dataelement.text}		
							</div>				
                 		</c:if>
                 		
						<c:if test="${dataelement.class.simpleName != 'DataElementText'}">
							<div class="box-content">
								<%-- ABS Data Element --%>
				 				<c:if test="${dataelement.class.simpleName == 'DataElementAbs'}">
				 					<jsp:include page="dataElementAbs.jsp" />
				 				</c:if>
				 				
				 				<%-- BITRE Data Element --%>
				 				<c:if test="${dataelement.class.simpleName == 'DataElementBitre'}">
				 					<jsp:include page="dataElementBitre.jsp" />
				 				</c:if>
							
								<%-- Past Data Element --%>
				 				<c:if test="${dataelement.class.simpleName == 'DataElementPast'}">
				 					<jsp:include page="dataElementPast.jsp" />
				 				</c:if>
				 				
				 				<%-- Acorn-Sat Data Element --%>
				 				<c:if test="${dataelement.class.simpleName == 'DataElementAcornSat'}">
				 					<jsp:include page="dataElementAcornSat.jsp" />
				 				</c:if>
								
								<%-- CSIRO Data Element --%>
				 				<c:if test="${dataelement.class.simpleName == 'DataElementCsiro'}">
				 					<jsp:include page="dataElementCsiro.jsp" />
				 				</c:if>
				 				
				 				<%-- CMAR Data Element --%>
				 				<c:if test="${dataelement.class.simpleName == 'DataElementCmar'}">
				 					<jsp:include page="dataElementCmar.jsp" />
				 				</c:if>
				 				
				 				<%-- Engineering Model Data Element --%>
				 				<c:if test="${dataelement.class.simpleName == 'DataElementEngineeringModel'}">
				 					<jsp:include page="dataElementEngineeringModel.jsp" />
				 				</c:if>
				 				
				 				<%-- Vulnerability Data Element --%>
				 				<c:if test="${dataelement.class.simpleName == 'DataElementVulnerability'}">
				 					<jsp:include page="dataElementVulnerability.jsp" />
				 				</c:if>
				 				
				 				<%-- File Data Element, display a picture if JPEG, textarea with content otherwise --%>
								<c:if test="${dataelement.class.simpleName == 'DataElementFile'}">
				 					<jsp:include page="dataElementFile.jsp" />
								</c:if>
	                 		</div>
                 		</c:if><%-- End if data element is not text comment --%>
					
						</c:if><%-- End if data element included --%>
					</div>
				</li>
				<c:if test="${status.index} % 2 != 0">
					<div class="clearfix"></div>
				</c:if>
			</c:forEach>
			</ul>
			
			<c:if test="${userstory.mode != 'published'}">
			<script type="text/javascript">
				$(document).ready(function () {
					
					// Resize the sortable list items 2 by 2 to have a proper grid
					//resizeListItems();
					var draggedTextContent = null; 
					
					// Enables the sortable list
					$( "#sortable" ).sortable({
						placeholder: "sortable-placeholder",
						cursor: 'crosshair',
						start: function(event, ui) {
							draggedTextContent = ui.item.find('.tinymce').html();
						},
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
		</c:if>
		<div class="clearfix"></div><br />
	</form:form>
	
	<c:if test="${userstory.mode != 'published'}">
		<div id="editTextDataElementModalWindow" class="box round" title="Edit text" style="display:none; padding:0;">
			<form:form id="editTextForm" method="post" action="/CSS/auth/userstory/editText"> 
				<input id="hdnTextDataElementToEditId" type="hidden" name="dataElementId" />
				<textarea id="txtDataElementToEditContent" name="textContent" class="tinymce" rows="25">
				</textarea>
				<div style="height: 50px">
				<a href="javascript: $('#editTextForm').submit();" style="margin-right: 10px; margin-top:20px; float:right">
					<button type="button" class="btn btn-icon btn-blue btn-check btn-small floatright">
						<span></span>Save changes
					</button>
				</a>
				</div>
			</form:form>
		</div>
	</c:if>
	
	<script language="javascript" type="text/javascript" src="<c:url value="/resources/js/tiny_mce/tiny_mce.js" />"></script>
	<script type="text/javascript">
		$(document).ready(function () {
			setupDialogBox("addTextDataElementModalWindow", "btnOpenAddTextDataElementModalWindow");
			setupDialogBoxByClass("editTextDataElementModalWindow", "btnEditText");
			
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
		        width: "100%",
		        height: "300px"
			});
		});
	</script>
	
	<div id="confirmTextDeletionModalWindow" title="Permanently delete this text ?" style="display:none">
		<p>Are you sure you want to permanently delete this text ?</p> 
	</div>
	<div id="confirmUserStoryPublishModalWindow" title="Really publish this report ?" style="display:none">
		<p class="message"><span class="error"><b>Publishing this report will make it available to anyone visiting this portal.</b></span></p>
		<p class="message"><span class="error"><b>It will also automatically be submitted to <a href="http://ands.org.au/">ANDS</a> and appear on the <a href="http://researchdata.ands.org.au/">Research Data Australia</a> portal.</b></span></p>
		<p class="message"><span class="error"><b>Once published, a report cannot be edited, deleted or made private again.</b></span></p>
		<p>Are you sure you want to publish this report ?</p> 
	</div>
	<div id="confirmUserStoryDeletionModalWindow" title="Permanently delete the report ?" style="display:none">
	  <p class="message"><span class="error"><b>Warning: this will also delete all the data elements and texts contained in this report. This action cannot be undone !</b></span></p>
	  <p>Are you sure you want to permanently delete this report ?</p> 
	</div>
	<script type="text/javascript">
		setupConfirmBox("confirmTextDeletionModalWindow", "lnkRemoveTextFromStory");
		setupConfirmBox("confirmUserStoryPublishModalWindow", "lnkPublishUserStory");
		setupConfirmBox("confirmUserStoryDeletionModalWindow", "lnkDeleteUserStory");
	</script>
</div>