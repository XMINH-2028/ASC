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
<% if (session.getAttribute("forget") == null ) { 
	response.sendRedirect(response.encodeRedirectURL(request.getContextPath()+"/home"));
} else {
	//Lấy dữ liệu phản hồi từ Servlet qua session "forget" khi đặt lại mật khẩu
	Account v = (Account)session.getAttribute("forget");
	String repass = "";
	String password = ""; 
	String repalert = "";
	String passalert = "";
	if (v != null) {
		repass = v.getRepass();
		password = v.getPassword();
		repalert = v.getRepalert();
		passalert = v.getPassalert();
	}
	
	if (v.getAction().equals("getcode")) {
		response.sendRedirect(response.encodeRedirectURL(request.getContextPath()+"/getcode"));
	} else if (v.getAction().equals("verify")) {
		response.sendRedirect(response.encodeRedirectURL(request.getContextPath()+"/verify"));
	} else { %>
		<!-- Hiển thị form khi người dùng quên mật khẩu -->
		<div class="reset">
			<form action="<%= response.encodeURL(request.getContextPath()+"/Controller")%>" method="POST">
				<input type="hidden" name="action" value="reset">
				<p class="wrap">
					<input class="password" type="password" name="password" placeholder="Enter Password" 
					value="<%= password == null ? "" : password %>">
					<span class="errorAlert"><%= passalert == null ? "" : passalert%></span>
				</p>
				<p class="wrap">
					<input class="repass" type="password" name="repass" placeholder="Repeat Password" 
					value="<%= repass == null ? "" : repass %>">
					<span class="errorAlert"><%= repalert == null ? "" : repalert%></span>
				</p>
				<button type="submit" id="sub">Reset</button>
				<span class="close"><a href="<%= response.encodeURL(request.getContextPath()+"/Controller?action=closeform")%>">+</a></span>	
			</form>
		</div>
	<% } %>
<% } %>
</body>
</html>