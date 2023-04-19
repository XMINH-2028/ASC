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
<%
	String userAlert = "";
	String passAlert = "";
	String username = "";
	String password = "";
	if (request.getParameter("action") != null && request.getParameter("action").equals("submit")) {
		String user = getServletContext().getInitParameter("user");
		String pass = getServletContext().getInitParameter("pass");
		username = request.getParameter("username");
		password = request.getParameter("password");
		if (username.trim().equals("")) {
		 	passAlert = "";
			userAlert = "Please input Username!";
		} else if (password.trim().equals("")) {
			userAlert = "";
			passAlert = "Please input Password!";
		} else if (!(username.equalsIgnoreCase(user) && password.equals(pass))) {
			passAlert = "Username or password is invalid!";
			userAlert = "Username or password is invalid!";
		} else {
			userAlert = "";
			password = "";
			request.getRequestDispatcher("LoginServlet").forward(request, response);
		}
	}
	
%>
<img class="icon" alt="login" src="images/key.png">		
<form action="login" method="POST" id="loginForm">
	<input type="hidden" name="action" value="submit">
	<p class="wrap">
		<label for="Username">Username<span class="erorrAlert userAlert"><%= userAlert%></span></label>
		<input id="Username" type="text" name="username" placeholder="Enter Username" value=<%= username%>>
	</p>
	<p class="wrap">
		<label for="Password">Password<span class="erorrAlert passAlert"><%= passAlert%></span></label>
		<input id="Password" type="password" name="password" placeholder="Enter Password" value=<%= password%>>
	</p>
	<button type="submit" id="sub">Login</button>
	<p>
		<input type="checkbox" id="Remember" name="remember">
		<label for="Remember">Remember me</label>
	</p>
	<br/>
	<div class="footer">
		<button type="button" id="res">Cancel</button>
		<p>Forgot<a href="#"> password?</a></p>
	</div>
</form>
<span class="close"><a href = "home">+</a></span>
</body>
</html>