<%@ page language="java"
	contentType="text/html; charset=utf-8; text/css" pageEncoding="utf-8" 
	import="database.*, sendemail.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/code.css">
<title>Verify</title>
<script src="https://kit.fontawesome.com/72f1026e9f.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
</head>
<body>
<% if (session.getAttribute("forget") == null && session.getAttribute("register") == null) { 
	response.sendRedirect(response.encodeRedirectURL("home"));
} else {
	String action = "";
	Account getSession;
	if (session.getAttribute("forget") != null) {
		action = "forgetverify";
		getSession = (Account)session.getAttribute("forget");
	} else {
		action = "registerverify";
		getSession = (Account)session.getAttribute("register");
	}
	if (getSession.getAction().equals("getcode")) {
		response.sendRedirect(response.encodeRedirectURL("getcode"));
	} else if (getSession.getAction().equals("reset")) {
		response.sendRedirect(response.encodeRedirectURL("reset"));
	} else { %>
		<!-- Hiển thị form để người dùng nhập mã xác thực  -->
		<div class="verify">
			<form action="<%= response.encodeURL("Controller")%>" method="POST">
				<input type="hidden" name="action" value="<%= action%>">
				<p class="wrap">
					<input id="code" type="text" name="code" placeholder="Enter verify code">
					<span class="alert"><%= request.getParameter("alert") == null ? "" :
						request.getParameter("alert")%></span>
					<span class="errorAlert"><%= request.getParameter("error") == null ? "" :
						request.getParameter("error")%></span>
				</p>
				<button type="submit" id="sub">Verify</button>
				<span class="close"><a href="<%= response.encodeURL("Controller?action=closeform")%>">+</a></span>
			</form>
		</div>
	<% } %>
<% } %>
</body>
</html>