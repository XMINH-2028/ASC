<%@ page language="java" contentType="text/html; text/css; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/login.css">
<title>Login</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<script type="text/javascript" src="js/login.js"></script>
</head>
<body>
<jsp:useBean id="admin" class="bean.User" scope="session"></jsp:useBean>
<% if (admin.getUsername() != null && admin.getUsername().equals("") && admin.getPassword().equals("")) {
	String usermess = request.getParameter("usermess");
	String username = request.getParameter("username");
	String passmess = request.getParameter("passmess");
	String password = request.getParameter("password");
%>
	<img class="icon" alt="login" src="images/key.png">		
	<form action="Controller" method="POST">
	    <input type="hidden" name="action" value="submit">
		<p class="wrap">
			<label for="Username">Username<span class="erorrAlert"><%= usermess == null ? "" : usermess%></span></label>
			<input id="Username" type="text" name="username" placeholder="Enter Username" value="<%= username == null ? "" : username%>">
		</p>
		<p class="wrap">
			<label for="Password">Password<span class="erorrAlert"><%= passmess == null ? "" : passmess%></span></label>
			<input id="Password" type="password" name="password" placeholder="Enter Password" value="<%= password == null ? "" : password%>">
		</p>
		<button type="submit" id="sub">Login</button>
		<p>
			<input type="checkbox" id="Remember" name="remember">
			<label for="Remember">Remember me</label>
		</p>
		<div class="footer">
			<button type="button" id="reset">Cancel</button>
			<p>Forgot<a href="#"> password?</a></p>
		</div>
	</form>
	<span class="close"><a href = "home">+</a></span>
<% } else { 
	request.getRequestDispatcher("Controller?login=on").forward(request, response);
} %>
</body>
</html>