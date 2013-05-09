<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/reset.css" />"  media="screen" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/text.css" />"  media="screen" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/grid.css" />"  media="screen" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/jquery/jquery-ui-1.10.3.min.css" />" />

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/layout.css" />"  media="screen" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/nav.css" />"  media="screen" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/tabs.css" />"  media="screen" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/buttons.css" />"  media="screen" />
<!--[if IE 6]><link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/ie6.css" />" media="screen" /><![endif]-->
<!--[if IE 7]><link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/ie.css" />" media="screen" /><![endif]-->
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/prettyPhoto.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/zebra_tooltips.css" />" />

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/print.css" />" />

<!-- jQuery & jQuery UI -->
<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.9.1.min.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-ui-1.10.3.min.js" />" ></script>

<!-- jQuery plugins -->
<script type="text/javascript" src="<c:url value="/resources/js/table/jquery.dataTables.min.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.maphilight.js" />"></script>
<!--<script type="text/javascript" src="<c:url value="/resources/js/jquery.bubble-popup-v3.min.js" />"></script>-->
<script type="text/javascript" src="<c:url value="/resources/js/highcharts/highcharts.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/highcharts/highcharts-more.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/pretty-photo/jquery.prettyPhoto.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.zebra_tooltips.min.js" />"></script>
<!-- END: load jquery plugins -->

<script language="javascript" type="text/javascript" src="<c:url value="/resources/js/setup.js" />"></script>

<script type="text/javascript">
   $(document).ready(function () {
		setSidebarHeight();
		
		// Resize each image so it is a good sized thumbnail
		$.each($("img.dataElementThumb"), function() {
			var maxWidth = 450;
			var maxHeight = 150;
			var width = $(this).width();
			var height = $(this).height();
			if((width / maxWidth) < (height / maxHeight)) {
				var newHeight = height * (maxWidth / width);
				$(this).css("width", maxWidth);
				$(this).css("height", newHeight);
			}
			else {
				var newWidth = width * (maxHeight / height);
				$(this).css("width", newWidth);
				$(this).css("height", maxHeight);
          }
		});
		setupPrettyPhoto();
	});
</script>
