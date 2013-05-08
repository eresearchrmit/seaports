<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" /></title>
<tiles:insertAttribute name="imports" />
</head>
<body>
	<div id="main-container">
	    <div class="container_12" style="padding-bottom: 15px">
	        <div>
	            <div id="branding">
	                <div class="floatleft">
	                	<a href="/CSS"><img src="<c:url value="/resources/img/theme/css-logo-darkblue-100x100.png" />" alt="Logo" /></a>
	               	</div>
	               	<div id="main-title" class="floatleft"><div style="margin-top:25px">Climate Smart Seaports [beta]</div></div>
	                <div class="floatright">
						<tiles:insertAttribute name="user" ignore="true" />
	                </div>
	                <div class="clear"></div>
	                <div class="pitch">
						<h3 class="lightblue">Facilitating the identification and collection of relevant data so that Australian ports can become Climate Smart Seaports. </h3>
					</div>
	            </div>
	        </div>
	        <div class="clear"></div>
	        <div>
				<tiles:insertAttribute name="menu" ignore="true" />
	        </div>
	        <div class="clear"></div>
	        <div id="page-body" style="min-height:500px">        	
				<tiles:insertAttribute name="body" ignore="true" />
			</div>
	        <div class="clear"></div>
	    </div>
	    
	    <div class="floatleft no-print" style="margin:5px 0 15px 0">
			<a href="http://www.rmit.edu.au/research/eres" title="RMIT University" target="_blank"><img src="<c:url value="/resources/img/theme/rmit-logo.png" />" alt="RMIT University" /></a>
		</div>
		<div class="floatright no-print" style="margin-top:5px">
			<a href="http://www.ands.org.au/" title="Australian National Data Service" target="_blank"><img src="<c:url value="/resources/img/theme/ands-logo.png" />" alt="ANDS" /></a>
		</div>
    </div>
    <div id="site_info">
		<tiles:insertAttribute name="footer" ignore="true" />
	</div>
</body>
</html>
