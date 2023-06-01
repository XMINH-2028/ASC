<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css'></c:url>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/header.css'></c:url>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/${param.style}'></c:url>">
<link rel="stylesheet" type="text/css" href="<c:url value='/css/footer.css'></c:url>">
<title>${param.title}</title>
<script src="https://kit.fontawesome.com/72f1026e9f.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
</head>
<body>
<%-- Lấy session lưu thông tin người dùng --%>
<c:set var="user" value="${sessionScope.user}"></c:set>
<header>
	<div class="top">
		<img alt="logo" src="<c:url value='/images/logo.png'></c:url>" class="logo">
		<form action='<c:url value="/Controller"></c:url>' method="GET">
			<div class="searchBox">
				<input class="searchAction" type="hidden" name="action" value="search">
				<input class="searchInput" type="text" name="text" value="${sessionScope.text == null ? '' : sessionScope.text}">
				<button type="submit" class="submit">
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
					<li ><a href='<c:url value="/home"></c:url>'>Home</a></li>
					<li><a href="#">Products</a></li>
					<li><a href="#">About us</a></li>
				</ul>
			</li>
		</ul>
		<ul class="right">
		<%-- Hiển thị trạng thái khi người dùng đã đăng nhập hoặc chưa đăng nhập --%> 
		<c:set var="userMail" value="${user.email}"></c:set>
			<c:choose>
				<%-- Khi người dùng đã đăng nhập --%> 
				<c:when test="${user != null}">
					<li class='admin'><span>${fn:toUpperCase(fn:substring(userMail,0,1))}</span>
						<ul class='content unvisible'>
							<li><span>${fn:toUpperCase(fn:substring(userMail,0,1))}</span>${userMail}</li>
							<li><a class='logout' href="<c:url value='/Controller?action=logout'></c:url>">Logout</a></li>
						</ul>
					</li>
				</c:when>
				
				<%-- Khi người dùng chưa đăng nhập --%>
				<c:otherwise>
					<li><a href="<c:url value='/Controller?action=login'></c:url>">Login</a></li>
					<li><a href="<c:url value='/Controller?action=register'></c:url>">Register</a></li>
				</c:otherwise>
			</c:choose>
			<li><a href="#"><i class='fas fa-cart-plus cart'></i><span></span></a></li>
		</ul>
	</div>
	<script type="text/javascript" src="<c:url value='/js/header.js'></c:url>"></script>
</header>


