<%@ page language="java"
	contentType="text/html; charset=utf-8; text/css" pageEncoding="utf-8" 
	import="database.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<%--Lấy session lưu yêu cầu và thông tin người dùng nhập khi quên mật khẩu--%>
<c:set var="forget" value="${sessionScope.forget}"></c:set>
<%--Lấy session lưu yêu cầu và thông tin người dùng nhập khi đăng kí tài khoản mới--%>
<c:set var="register" value="${sessionScope.register}"></c:set>


<%--Khi người dùng không yêu cầu đặt lại mật khẩu và không cần xác thực code khi đăng kí mới chuyển qua trang chủ --%>
<c:if test="${forget == null && register == null}">
	<c:redirect url="/home"></c:redirect>
</c:if>
<%--Khi người dùng quên mật khẩu hoặc đăng kí mới cần xác thực--%>
<c:choose>
	<%--Khi người dùng quên mật khẩu --%>
	<c:when test="${forget != null}">
		<c:set var="actionValue" value="forgetverify"></c:set>
		<c:set var="v" value="${forget}"></c:set>
	</c:when>
	<%--Khi người dùng đăng kí mới --%>
	<c:otherwise>
		<c:set var="actionValue" value="registerverify"></c:set>
		<c:set var="v" value="${register}"></c:set>
	</c:otherwise>
</c:choose>
	
<c:choose>
	<%--Khi người dùng chưa lấy code chuyển qua trang lấy code --%>
	<c:when test="${v.action == 'getcode'}">
		<c:redirect url="/getcode"></c:redirect>
	</c:when>
	<%--Khi người dùng đã xác thực code chuyển qua trang đặt lại mật khẩu --%>
	<c:when test="${v.action == 'reset'}">
		<c:redirect url="/reset"></c:redirect>
	</c:when>
	<%--Khi người dùng chưa submit form đăng kí tài khoản mới --%>
	<c:when test="${v.action == 'register'}">
		<c:redirect url="/register"></c:redirect>
	</c:when>
	<%--Khi người dùng đã lấy code hiển thị form xác thực --%>
	<c:otherwise>
		<%--Lấy các tham số phản hồi từ servlet qua session forget --%>
		<c:set var="error" value="${param.error}" ></c:set>
		<c:set var="alert" value="${param.alert}" ></c:set>
		
		<div class="verify">
			<form action="<c:url value='/Controller'></c:url>" method="POST">
				<input type="hidden" name="action" value="${actionValue}">
				<p class="wrap">
					<input id="code" type="text" name="code" placeholder="Enter verify code">
					<span class="alert">${alert == null ? '' : alert}</span>
					<span class="errorAlert">${error == null ? '' : error}</span>
				</p>
				<button type="submit" id="sub">Verify</button>
				<span class="close"><a href="<c:url value='/Controller?action=closeform'></c:url>">+</a></span>
			</form>
		</div>
	</c:otherwise>
</c:choose>
</body>
</html>