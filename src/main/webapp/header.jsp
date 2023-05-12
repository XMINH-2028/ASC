<%@ page pageEncoding="utf-8" import="database.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/header.css">
<link rel="stylesheet" type="text/css" href="css/${param.style}">
<link rel="stylesheet" type="text/css" href="css/footer.css">
<title>${param.title}</title>
<script src="https://kit.fontawesome.com/72f1026e9f.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
</head>
<body>
<% Account user = (Account)session.getAttribute("user"); %>
<header>
	<div class="top">
		<img alt="logo" src="images/logo.png" class="logo">
		<form action="#">
			<div class="searchBox">
				<input class="searchInput">
				<button>
					<i class='fas fa-search'></i>
				</button>
			</div>
		</form>
	</div>
	<div class="topnav">
		<ul class="left">
			<li class="menu"><i class='fas fa-bars'></i></li>
			<li class="item">
				<ul>
					<li ><a href="#">Home</a></li>
					<li><a href="#">Products</a></li>
					<li><a href="#">About us</a></li>
				</ul>
			</li>
		</ul>
		<ul class="right">
		<!-- Khi người dùng đã đăng nhập -->
			<% if (user != null) { %>
			<li class='admin'><span><%= user.getEmail().substring(0, 1).toUpperCase()%></span>
				<ul class='content unvisible'>
					<li><span><%= user.getEmail().substring(0, 1).toUpperCase()%></span><%= user.getEmail()%></li>
					<li><a class='logout' href="<%= response.encodeURL(request.getContextPath()+"/Controller?action=logout")%>">Logout</a></li>
				</ul>
			</li>
	
			<!-- Khi người dùng chưa đăng nhập -->
			<% } else { %>
			<li><a href='<%= response.encodeURL(request.getContextPath()+"/Controller?action=login") %>'>Login</a></li>
			<li><a href='<%= response.encodeURL(request.getContextPath()+"/Controller?action=register") %>'>Register</a></li>
			<% } %>
		</ul>
	</div>
	<script type="text/javascript" src="js/header.js"></script>
</header>


