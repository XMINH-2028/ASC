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
<title>Getcode</title>
<script src="https://kit.fontawesome.com/72f1026e9f.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
</head>
<body>	
<% if (session.getAttribute("forget") == null ) { 
	response.sendRedirect(response.encodeRedirectURL("home"));
} else { 
	Account forget = (Account)session.getAttribute("forget"); 
	String email = request.getParameter("email");
	if (forget.getAction().equals("verify")) {
		response.sendRedirect(response.encodeRedirectURL("verify"));
	} else if (forget.getAction().equals("reset")) {
		response.sendRedirect(response.encodeRedirectURL("reset"));
	} else { %>
		<!-- Hiển thị form khi người dùng quên mật khẩu -->
		<div class="getcode">
			<form action="<%= response.encodeURL("Controller")%>" method="POST">
			    <input type="hidden" name="action" value="getcode">
				<p class="wrap">
					<input id="email" type="text" name="email" placeholder="Enter your email" value="<%= email == null ? "" : email%>">
					<span class="errorAlert"><%= request.getParameter("error") == null ? "" :
						request.getParameter("error")%> </span>
						<span class="alert"><%= request.getParameter("alert") == null ? "" :
						request.getParameter("alert")%></span>
				</p>
				<button type="submit" id="sub">Get verify code</button>
				<span class="close"><a href="<%= response.encodeURL("Controller?action=closeform")%>">+</a></span>
			</form>
		</div>
	<% } %>
<% } %>
</body>
</html>