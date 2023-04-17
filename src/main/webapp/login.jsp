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
<img class="icon" alt="login" src="images/key.png">		
<form action="LoginServlet" method="GET" id="loginForm">
	<p class="wrap">
		<label for="Username">Username<span class="erorrAlert userAlert"></span></label>
		<input id="Username" type="text" name="username" placeholder="Enter Username">
	</p>
	<p class="wrap">
		<label for="Password">Password<span class="erorrAlert passAlert"></span></label>
		<input id="Password" type="password" name="password" placeholder="Enter Password">
	</p>
	<button type="button" id="sub">Login</button>
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