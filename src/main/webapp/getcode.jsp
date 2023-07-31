<%@ page language="java"
	contentType="text/html; charset=utf-8; text/css" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/code.css">
<title>Getcode</title>
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
	<%--Khi người dùng quên mật khẩu và yêu cầu lấy code xác thực --%>
	<c:otherwise>
		<c:choose>
			<%--Khi người dùng đã lấy code chuyển qua trang xác thực code --%>
			<c:when test="${forget.info.action == 'verify'}">
				<c:redirect url="/verify"></c:redirect>
			</c:when>
			<%--Khi người dùng đã xác thực code chuyển qua trang đặt lại mật khẩu --%>
			<c:when test="${forget.info.action == 'reset'}">
				<c:redirect url="/reset"></c:redirect>
			</c:when>
			<%--Khi người dùng cần lấy code xác thực hiển thị form lấy code --%>
			<c:otherwise>
				<%--Lấy các tham số phản hồi từ servlet --%>
				<c:set var="email" value="${param.email}" ></c:set>
				<c:set var="error" value="${param.error}" ></c:set>
				<c:set var="alert" value="${param.alert}" ></c:set>
				<div class="getcode">
					<form action="<c:url value='/Controller'></c:url>" method="POST">
					    <input type="hidden" name="action" value="getcode">
						<p class="wrap">
							<input id="email" type="text" name="email" placeholder="Enter your email" value="${email == null ? '' : email}">
							<span class="errorAlert">${error == null ? '' : error}</span>
								<span class="alert">${alert == null ? '' : alert}</span>
						</p>
						<button type="submit" id="sub">Get verify code</button>
						<span class="close"><a href="<c:url value='/Controller?action=closeform'></c:url>">+</a></span>
					</form>
				</div>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>
</body>
</html>