<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="../css/style.css">
<link rel="stylesheet" type="text/css" href="../css/index.css">
<title>Admin</title>
<script src="https://kit.fontawesome.com/72f1026e9f.js" crossorigin="anonymous"></script>
</head>
<body>
<% 
	if (session.getAttribute("mail") == null) {
		response.sendRedirect("../login");
	} 
%>
<div class="grid-container">
	<div class="left">
		<h1>SMART WORLD</h1>
		<ul class="list">
			<li class="active">Dashboard</li>
			<li>Staff Manager</li>
		</ul>
		<button><a href="../LogoutServlet">Logout</a></button>
	</div>
	<div class="right">
		<div class="top"></div>
		<div class="bottom">
			<% String  text =  (String)session.getAttribute("mail");
				if (text != null) { %>
					<h1>Welcome <%= text.substring(0,text.indexOf("@")) %></h1>
			<% } %>	
		</div>
	</div>
</div>
</body>
</html>