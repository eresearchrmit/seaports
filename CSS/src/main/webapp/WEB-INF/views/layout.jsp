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
    <div class="container_12">
        <div>
            <div id="branding">
                <div class="floatleft">
                	<img src="<c:url value="/resources/img/logo-CSS-100x100.png" />" alt="Logo" />
               	</div>
               	<div id="main-title" class="floatleft"><div style="margin-top:25px">Climate Smart Seaports</div></div>
                <div class="floatright">
					<tiles:insertAttribute name="user" ignore="true" />
                </div>
                <div class="clear"></div>
            </div>
        </div>
        <div class="clear"></div>
        <div>
			<tiles:insertAttribute name="menu" ignore="true" />
        </div>
        <div class="clear"></div>
		<tiles:insertAttribute name="sidemenu" ignore="true" />
		<tiles:insertAttribute name="body" ignore="true" />
        <div class="clear"></div>
	    <div id="site_info">
			<tiles:insertAttribute name="footer" ignore="true" />
	    </div>
    </div>
    </div>
</body>
</html>
