<%@ page language="java"
	contentType="text/html; charset=utf-8; text/css" pageEncoding="utf-8" import="database.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/register.css">
<title>Register</title>
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

<%--Nếu người dùng chưa đăng nhập hiển thị form đăng kí--%>
<%-- Lấy session lưu thông tin người dùng nhập và phản hồi từ servlet --%>
<c:set var="v" value="${sessionScope.register}"></c:set>
<c:set var="firstname" value="${v.firstname}"></c:set>
<c:set var="lastname" value="${v.lastname}"></c:set>
<c:set var="email" value="${v.email}"></c:set>
<c:set var="password" value="${v.password}"></c:set>
<c:set var="repass" value="${v.repass}"></c:set>
<c:set var="fnamealert" value="${v.fnamealert}"></c:set>
<c:set var="lnamealert" value="${v.lnamealert}"></c:set>
<c:set var="mailalert" value="${v.mailalert}"></c:set>
<c:set var="passalert" value="${v.passalert}"></c:set>
<c:set var="repalert" value="${v.repalert}"></c:set>

<div class="register">
	<div class="container">
		<form action="<c:url value='/Controller'></c:url>" method="POST">
			<h1>Register</h1>
		    <input type="hidden" name="action" value="doregister">
		    <p class="wrap">
				<label for="firstname">Firstname<span class="erorrAlert">${fnamealert == null ? '' : fnamealert}</span></label>
				<input id="firstname" type="text" name="firstname" placeholder="Enter your firstname" value="${firstname == null ? '' : firstname}">
				
			</p>
			<p class="wrap">
				<label for="lastname">Lastname<span class="erorrAlert">${lnamealert == null ? '' : lnamealert}</span></label>
				<input id="lastname" type="text" name="lastname" placeholder="Enter your lastname" value="${lastname == null ? '' : lastname}">
				
			</p>
			<p class="wrap">
				<label for="Email">Email<span class="erorrAlert">${mailalert == null ? '' : mailalert}</span></label>
				<input id="Email" type="text" name="email" placeholder="Enter Email" value="${email == null ? '' : email}">
				
			</p>
			<p class="wrap">
				<label for="Password">Password<span class="erorrAlert">${passalert == null ? '' : passalert}</span></label>
				<input id="Password" type="password" name="password" placeholder="Enter Password" value="${password == null ? '' : password}">
				
			</p>
			<p class="wrap">
				<label for="Password">Repassword<span class="erorrAlert">${repalert == null ? '' : repalert}</span></label>
				<input id="Password" type="password" name="repass" placeholder="Enter Repassword" value="${repass == null ? '' : repass}">
				
			</p>
			<p class="footer">
				<a id="reset" href="<c:url value='/Controller?action=registerreset'></c:url>">Reset</a>
				<button type="submit" id="sub">Create</button>
			</p>
		</form>
		<div class="welcome">
			<h1>Welcome to <br>Smart World</h1>
			<p>Let's create new account here</p>
			<span class="close"><a href="<c:url value='/Controller?action=closeform'></c:url>">+</a></span>
		</div>
	</div>
</div>
</body>
</html>