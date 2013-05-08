<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<%@ page import= "org.springframework.web.servlet.tags.*" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="grid_12">

	<a href="javascript: history.back();" class="floatleft" title="Go back to previous page">
		<button class="btnDeleteStory btn-icon btn-blue btn-arrow-left" >
			<span></span>Back
		</button>
	</a>

	<center>
		<h2>Terms of Service</h2>
	</center>
	
	<p style="text-align:center; margin: 25px 0; font-size: 12pt">
		<strong>Copyright (c) 2013, RMIT University, Australia. All rights reserved.</strong>
	</p>
	
	<p style="text-align:justify; font-size: 12pt">Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
		<ul style="text-align:justify; font-size: 12pt">
		<li>Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.</li>
		<li>Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the  distribution.</li>
		<li>Neither the name of RMIT University nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.</li>
		</ul>
	</p>
	
	<p style="text-align:justify; font-size: 12pt">This software is provided by the regents and contributors "as is" and any express or implied warranties, including, but not limited to, the implied warranties of merchantability and fitness for a particular purpose are disclaimed. In no event shall the regents and contributors be liable for any direct, indirect, incidental, special, exemplary, or consequential damages (including, but not limited to, procurement of substitute goods or services; loss of use, data, or profits; or business interruption) however caused and on any theory of liability, whether in contract, strict liability, or tort  (including negligence or otherwise) arising in any way out of the use of this software, even if advised of the possibility of such damage.</p>
			
	<a name="license" ></a>
	<center>
		<h2>Reports license</h2>
	</center>		
		<p style="text-align:justify; font-size: 12pt">
			The content of all reports on this website are licensed under a <a href="" title="View the full licence statement">Creative Commons Attribution license.</a>
		</p>
		<p style="text-align:justify; font-size: 12pt">
		<img src="<c:url value="/resources/img/help/cc-by-nc-nd.png" />" title="View the full licence statement" />
		</p>
		<p style="text-align:justify; font-size: 12pt">
			We request attribution as: &copy; [author name] [year of publication]
		</p>
		<p style="text-align:justify; font-size: 12pt">
			example:<i> &copy; John Smith 2013</i>
		</p>
		<p style="text-align:justify; font-size: 12pt">
			The author name and year of publication can be found in each report.
		</p>
</div>

