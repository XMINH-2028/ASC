<%@ page language="java"
	contentType="text/html; charset=utf-8; text/css" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/login.css">
<title>Login</title>
<script src="https://kit.fontawesome.com/72f1026e9f.js" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
</head>
<body>	
<%-- Lấy session lưu thông tin người dùng --%>
<c:set var="user" value="${sessionScope.user}"></c:set>

<%--Nếu người dùng đã đăng nhập chuyển qua trang chủ với role = 2 và trang admin với role = 1--%>
<c:if test="${user != null}">
	<c:choose>
		<c:when test="${user.role == 1}">
			<c:redirect url="/manager/admin"></c:redirect>
		</c:when>
		<c:otherwise>
			<c:redirect url="/home"></c:redirect>
		</c:otherwise>
	</c:choose>
</c:if>

<%--Nếu người dùng chưa đăng nhập hiển thị form đăng nhập--%>
<%-- Lấy session lưu thông tin người dùng nhập và phản hồi từ servlet --%>
<c:set var="v" value="${sessionScope.login}"></c:set>
<c:set var="email" value="${v.email}"></c:set>
<c:set var="password" value="${v.password}"></c:set>
<c:set var="mailalert" value="${v.mailalert}"></c:set>
<c:set var="passalert" value="${v.passalert}"></c:set>

<%--Khi chuyển đến trang login kiểm tra cookie nếu có lưu thông tin đăng nhập thì lấy email từ cookie và điền vào form --%>
<c:if test="${cookie.email != null && v == null}">
	<c:set var="email" value="${cookie.email.value}"></c:set>
</c:if>

<div class="login">
	<div class="container">
		<form action='<c:url value="/Controller"></c:url>' method="POST">
			<h1>Sign in</h1>
		    <input type="hidden" name="action" value="dologin">
			<p class="wrap">
				<label for="Email">Email<span class="erorrAlert">${mailalert == null ? '' : mailalert}</span></label>
				<input id="Email" type="text" name="email" placeholder="Enter Email" value="${email == null ? '' : email}">
			</p>
			<p class="wrap">
				<label for="Password">Password<span class="erorrAlert">${passalert == null ? '' : passalert}</span></label>
				<input id="Password" type="password" name="password" placeholder="Enter Password" value="${password == null ? '' : password}">
			</p>
			<button type="submit" id="sub">Login</button>
			<p>
				<input type="checkbox" id="Remember" name="remember">
				<label for="Remember">Remember me</label>
			</p>
			<div class="footer">
				<a id="reset" href='<c:url value="/Controller?action=loginreset"></c:url>'>Reset</a>
				<p>Forgot<a href='<c:url value="/Controller?action=forget"></c:url>'> password?</a></p>
			</div>
		</form>
		<div class="welcome">
			<h1>Welcome to <br>Smart World</h1>
			<p>To keep connected  with us<br>please login with your personal info</p>
			<span class="close"><a href='<c:url value="/Controller?action=closeform"></c:url>'>+</a></span>
		</div>
	</div>
</div>
</body>
</html>