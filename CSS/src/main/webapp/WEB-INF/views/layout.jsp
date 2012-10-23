<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" /></title>
</head>
<body>
<table border="1" cellpadding="2" cellspacing="2" align="center">
	<tr>
		<td height="90" colspan="4" bgcolor="#8FBCDB"><tiles:insertAttribute name="header"/>
		</td>
	</tr>
	<tr>
		<td height="430" colspan="3" bgcolor="#F8E4CC"><tiles:insertAttribute name="menu" /></td>
		<td width="900"><tiles:insertAttribute name="body" /></td>
	</tr>
	<tr>
		<td height="20" colspan="4" bgcolor="silver"><tiles:insertAttribute name="footer" />
		</td>
	</tr>
</table>
</body>
</html>
