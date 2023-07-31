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
<c:choose>
	<%--Khi người dùng không yêu cầu đặt lại mật khẩu chuyển qua trang chủ --%>
	<c:when test="${forget == null}">
		<c:redirect url="/home"></c:redirect>
	</c:when>
	<%--Khi người dùng quên mật khẩu và yêu cầu đặt lại mật khẩu --%>
	<c:otherwise>
		<c:choose>
			<%--Khi người dùng chưa lấy code chuyển qua trang lấy code --%>
			<c:when test="${forget.info.action == 'getcode'}">
				<c:redirect url="/getcode"></c:redirect>
			</c:when>
			<%--Khi người dùng chưa xác thực code chuyển qua xác thực code --%>
			<c:when test="${forget.info.action == 'verify'}">
				<c:redirect url="/verify"></c:redirect>
			</c:when>
			<%--Khi người dùng đã xác thực code hiển thị form đặt lại mật khẩu --%>
			<c:otherwise>
				<%--Lấy các tham số phản hồi từ servlet qua session forget --%>
				<c:set var="password" value="${forget.info.password}" ></c:set>
				<c:set var="checkPass" value="${forget.checkInfo.password}" ></c:set>
				<c:set var="repass" value="${forget.info.repass}" ></c:set>
				<c:set var="checkRepass" value="${forget.checkInfo.repass}" ></c:set>
				
				<div class="reset">
					<form action="<c:url value='/Controller'></c:url>" method="POST">
						<input type="hidden" name="action" value="reset">
						<p class="wrap">
							<input class="password" type="password" name="password" placeholder="Enter Password" 
							value="${password == null ? '' : password}">
							<span class="errorAlert">${checkPass == null ? '' : checkPass}</span>
						</p>
						<p class="wrap">
							<input class="repass" type="password" name="repass" placeholder="Repeat Password" 
							value="${repass == null ? '' : repass}">
							<span class="errorAlert">${checkRepass == null ? '' : checkRepass}</span>
						</p>
						<button type="submit" id="sub">Reset</button>
						<span class="close"><a href="<c:url value='/Controller?action=closeform'></c:url>">+</a></span>	
					</form>
				</div>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>
</body>
</html>