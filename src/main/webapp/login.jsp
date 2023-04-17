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
<jsp:useBean id="user" class="bean.User" scope="session"></jsp:useBean>
<jsp:setProperty property="*" name="user"/>
<%
	String action = request.getParameter("action");
	if (action != null) {
		if (request.getParameter("username").equals("")) { %>
			<jsp:setProperty property="username" name="user" value="" />
		<% }
		if (request.getParameter("password").equals("")) { %>
			<jsp:setProperty property="password" name="user" value="" />
		<% }
		if (user.validate()) {
			String us = getServletContext().getInitParameter("user");
			String pa = getServletContext().getInitParameter("pass");
			if (user.getUsername().equalsIgnoreCase(us) && user.getPassword().equals(pa)) {
				user.setPassmess("");
				user.setUsermess("");
				response.sendRedirect("home.jsp");
				
			} else {
				user.setPassmess("Username or password is invalid!");
				user.setUsermess("Username or password is invalid!");
			}
		}
	}
%>

<img class="icon" alt="login" src="images/key.png">		
<form action="login.jsp" method="POST">
    <input type="hidden" name="action" value="1">
	<p class="wrap">
		<label for="Username">Username<span><jsp:getProperty property="usermess" name="user"/></span></label>
		<input id="Username" type="text" name="username" placeholder="Enter Username" value="<jsp:getProperty property="username" name="user"/>">
	</p>
	<p class="wrap">
		<label for="Password">Password<span><jsp:getProperty property="passmess" name="user"/></span></label>
		<input id="Password" type="password" name="password" placeholder="Enter Password" value="<jsp:getProperty property="password" name="user"/>">
	</p>
	<button type="submit" id="sub">Login</button>
	<p>
		<input type="checkbox" id="Remember" name="remember">
		<label for="Remember">Remember me</label>
	</p>
	<div class="footer">
		<button type="reset">Cancel</button>
		<p>Forgot<a href="#"> password?</a></p>
	</div>
</form>
<span class="close"><a href = "home">+</a></span>
</body>
</html>