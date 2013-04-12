<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/reset.css" />"  media="screen" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/text.css" />"  media="screen" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/grid.css" />"  media="screen" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/fancy-button/fancy-button.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/themes/base/jquery.ui.all.css" />" />

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/layout.css" />"  media="screen" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/nav.css" />"  media="screen" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/tabs.css" />"  media="screen" />
<!--[if IE 6]><link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/ie6.css" />" media="screen" /><![endif]-->
<!--[if IE 7]><link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/ie.css" />" media="screen" /><![endif]-->
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/table/demo_page.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/prettyPhoto.css" />" />
<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/bubblepopup.css" />" />

<link rel="stylesheet" type="text/css" href="<c:url value="/resources/css/print.css" />" />

<!-- BEGIN: load jquery -->
<script type="text/javascript" src="<c:url value="/resources/js/jquery-1.6.4.min.js" />" ></script>
<!-- jQuery UI -->
<script type="text/javascript" src="<c:url value="/resources/js/jquery-ui/jquery.ui.core.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-ui/jquery.ui.widget.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-ui/external/jquery.bgiframe-2.1.2.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-ui/jquery.ui.mouse.min.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-ui/jquery.ui.draggable.min.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-ui/jquery.ui.position.min.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-ui/jquery.ui.dialog.min.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-ui/jquery.ui.mouse.min.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-ui/jquery.ui.sortable.min.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-ui/jquery.ui.tabs.min.js" />" ></script>
<!-- jQuery effects -->
<script type="text/javascript" src="<c:url value="/resources/js/jquery-ui/jquery.effects.core.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery-ui/jquery.effects.fade.min.js" />" ></script>
<!-- jQuery plugins -->
<script type="text/javascript" src="<c:url value="/resources/js/table/jquery.dataTables.min.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.maphilight.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/fancy-button/fancy-button.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/jquery.bubble-popup-v3.min.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/js/highcharts/highcharts.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/highcharts/highcharts-more.js" />" ></script>
<script type="text/javascript" src="<c:url value="/resources/js/pretty-photo/jquery.prettyPhoto.js" />"></script>
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
