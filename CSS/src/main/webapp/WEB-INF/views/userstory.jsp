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
	<h2>${userstory.name} </h2>
	<h6>${userstory.region.name}</h6>
	<br />
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
	
	<form:form id="userStoryForm" method="post" action="/CSS/spring/userstory/save" modelAttribute="userstory">
	  	<form:input value="${userstory.id}" type="hidden" path="id" />
	  	<c:if test="${not empty userstory.dataElements}">		
		 	<ul id="sortable">
		 	
		 	<!-- Iteration on every element in the User Story -->
		 	<c:forEach items="${userstory.dataElements}" var="dataelement" varStatus="status">
		 			<li class="sortableItem" id="dataElement${dataelement.id}">
			 			<div class="box round${dataelement.included == false ? ' box-disabled' : ''}">
							
							<div class="box-header<c:if test="${dataelement.class.simpleName == 'DataElementFile' && dataelement.filetype == 'comment'}"> box-header-white</c:if>" >
								<h5 class="floatleft">${dataelement.name}<c:if test="${dataelement.class.simpleName == 'DataElementFile' && dataelement.filetype != 'comment'}">.${dataelement.filetype}</c:if></h5>
								
								<!-- 'Include/Exclude' button -->
								<button type="button" class="btn-mini ${dataelement.included == false ? 'btn-grey btn-plus' : 'btn-blue btn-minus'} floatright" onclick="location.href='/CSS/spring/userstory/includeDataElement?story=${userstory.id}&dataelement=${dataelement.id}'" title="Include/Exclude from the story">
									<span></span>Include/Exclude
								</button>
								
								<!-- 'Remove Text' button -->
								<c:if test="${dataelement.class.simpleName == 'DataElementFile' && dataelement.filetype == 'comment'}">
									<a class="lnkRemoveTextFromStory" href="/CSS/spring/userstory/deleteText?text=${dataelement.id}" title="Delete the text from the story">
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
							
							<c:if test="${dataelement.class.simpleName == 'DataElementCsiro'}">
		 					<!-- CSIRO Data Element -->
		 					<c:choose>
			 					<c:when test="${not empty dataelement.csiroDataList}">
			 						<p style="margin-top:10px; text-align:center; font-weight"><b>${dataelement.csiroDataList[0].parameters.modelName} model (${dataelement.csiroDataList[0].parameters.model.name}) in the region ${dataelement.csiroDataList[0].parameters.region.name} for ${dataelement.csiroDataList[0].parameters.emissionScenario.description} (${dataelement.csiroDataList[0].parameters.emissionScenario.name})</b></p>
				 					
				 					<table class="data display datatable" id="example">
										<thead>
											<tr>
												<th>Variable</th>
												<th>Base value</th>
												<th>Variation in ${dataelement.csiroDataList[0].year}</th>
											</tr>
										</thead>
					 					<tbody>
					 						<c:forEach items="${dataelement.csiroDataList}" var="csiroData" varStatus="dataLoopStatus">
						 						<tr class="${dataLoopStatus.index % 2 == 0 ? 'even' : 'odd'}">
							 						<td class="center">${csiroData.variable.name}</td>
							 						<td class="center">${csiroData.baseline.value} ${csiroData.baseline.variable.uom}</td>
							 						<td class="center"><c:if test="${csiroData.value > 0}">+</c:if><c:if test="${csiroData.value < 0}">-</c:if>${csiroData.value} ${csiroData.variable.uomVariation}</td>
						 						</tr>
						 					</c:forEach>
					 					</tbody>
				 					</table>
				 					<i>Data provided by CSIRO on <fmt:formatDate value="${dataelement.csiroDataList[0].creationDate}" pattern="dd MMM yyyy" /> was the best available to date</i>
				 				</c:when>
				 				<c:otherwise>
				 					<div id="warningMessage" class="message warning">
										<h5>No Data</h5>
										<p>No data corresponding to the selected settings.</p>
									</div>
				 				</c:otherwise>
		 					</c:choose>
		 				</c:if>
							
						<c:if test="${dataelement.class.simpleName == 'DataElementEngineeringModel'}">
		 					<!-- Engineering Model Data Element -->
							<div id="points-chart-${status.index}">
							</div>
							<br />
							
							<script type="text/javascript" language="javascript">
								var series = new Array();
								<c:forEach items="${dataelement.engineeringModelDataList}" var="engModelData" varStatus="loop">
								    series[${loop.index}] = new Array();
								    <c:forEach items="${engModelData.values}" var="values">
								    	series[${loop.index}].push([${values.key}, ${values.value}]);
								    </c:forEach>
								</c:forEach>
								
							
								var graphTitle = "${dataelement.engineeringModelDataList[0].variable.name} over time";
								var yAxisTitle = "${dataelement.engineeringModelDataList[0].variable.shortName}";
								
							    var plot = $.jqplot('points-chart-${status.index}', 
							    		[<c:forEach items="${dataelement.engineeringModelDataList}" var="engModelData" varStatus="loop">
											series[${loop.index}]<c:if test="${!loop.last}">,</c:if>
										</c:forEach>],
								{
							    	title: graphTitle,
							    	series: [<c:forEach items="${dataelement.engineeringModelDataList}" var="engModelData" varStatus="loop">{
							                	label: "${engModelData.parameters.emissionScenario.name} ${engModelData.parameters.model.name}", 
							                	showMarker: false,
							                	lineWidth: 1
							                }<c:if test="${!loop.last}">,</c:if>
							    			</c:forEach>],
							    	axes: {
							            xaxis: {
							              label: "Time",
							              pad: 0
							            },
							            yaxis: {
							              label: yAxisTitle
							            }
							    	},
							        legend: {
							            show: true,
							            location: "se"
							        }
								});
							</script>
							
							<table class="data display datatable" id="example">
								<tbody>
									<tr>
										<th>Asset Code</th>
										<td class="center">${dataelement.engineeringModelDataList[0].asset.assetCode}</td>
									</tr>
									<tr>
										<th>Description</th>
										<td class="center">${dataelement.engineeringModelDataList[0].asset.description}</td>
									</tr>
									<tr>
										<th>Year Built</th>
										<td class="center">${dataelement.engineeringModelDataList[0].asset.yearBuilt}</td>
									</tr>
									<tr>
										<th>Zone</th>
										<td class="center">${dataelement.engineeringModelDataList[0].asset.zone}</td>
									</tr>
									<tr>
										<th>Distance from coast</th>
										<td class="center">${dataelement.engineeringModelDataList[0].asset.distanceFromCoast} km</td>
									</tr>
									<tr>
										<th>Exposure class</th>
										<td class="center">${dataelement.engineeringModelDataList[0].asset.exposureClass}</td>
									</tr>
									<tr>
										<th>Carbonation class</th>
										<td class="center">${dataelement.engineeringModelDataList[0].asset.carbonationClass}</td>
									</tr>
									<tr>
										<th>Chloride class</th>
										<td class="center">${dataelement.engineeringModelDataList[0].asset.chlorideClass}</td>
									</tr>
									<tr>
										<th>Concrete cover</th>
										<td class="center">${dataelement.engineeringModelDataList[0].asset.cover} mm</td>
									</tr>
									<tr>
										<th>Size of concrete element</th>
										<td class="center">${dataelement.engineeringModelDataList[0].asset.dmember} mm</td>
									</tr>
									<tr>
										<th>Design strength</th>
										<td class="center">${dataelement.engineeringModelDataList[0].asset.fprimec} MPa</td>
									</tr>
									<tr>
										<th>Water to cement ratio</th>
										<td class="center">${dataelement.engineeringModelDataList[0].asset.wc}</td>
									</tr>
									<tr>
										<th>Cement content</th>
										<td class="center">${dataelement.engineeringModelDataList[0].asset.ce} kg/m3</td>
									</tr>
									<tr>
										<th>Diameter of rebar</th>
										<td class="center">${dataelement.engineeringModelDataList[0].asset.dbar} mm</td>
									</tr>
								</tbody>
							</table>
		 				</c:if>
		 				
 						<c:if test="${dataelement.class.simpleName == 'DataElementText'}">
							<textarea name="comments" rows="12" onfocus="if ($(this).val() == 'Add text here...') { $(this).val(''); }" onblur="if ($(this).val() == '') { $(this).val('Add text here...'); }">${dataelement.text}</textarea>	
	                 	</c:if>

						<c:if test="${dataelement.class.simpleName == 'DataElementFile'}">
		 					<!-- File Data Element, display a picture if JPEG, textarea with content otherwise -->
		 					<input type="hidden" name="dataElements[${status.index}].filetype" value="${dataelement.filetype}" id="dataElements[${status.index}].filetype">
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
			                 		<textarea class="no-border as-plain-text" rows="12" disabled>${dataelement.stringContent}</textarea>
								</c:otherwise>
							</c:choose>
						</c:if>
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
		<button type="button" class="btn btn-icon btn-blue btn-check floatright" onclick="$('#userStoryForm').submit();">
			<span></span>Save
		</button>
		<div class="clearfix"></div><br />
	</form:form>
	
	<div id="dataElementAdder">
		<a href="/CSS/spring/userstory/addText?story=${userstory.id}">
			<button class="btnAddDataElement btn btn-icon btn-blue btn-plus" >
				<span></span>Add Text to Story
			</button>
		</a>
	</div>
		
	<div id="confirmTextDeletionModalWindow" title="Permanently delete this text ?">
		<p>Are you sure you want to permanently delete this text ?</p> 
	</div>
	<script type="text/javascript">
		setupConfirmBox("confirmTextDeletionModalWindow", "lnkRemoveTextFromStory");
	</script>
</div>