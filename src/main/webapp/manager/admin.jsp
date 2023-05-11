<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import="database.*"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="../css/style.css">
<link rel="stylesheet" type="text/css" href="../css/admin.css">
<title>Admin</title>
<script src="https://kit.fontawesome.com/72f1026e9f.js" crossorigin="anonymous"></script>
</head>
<body>
<% 
	//Kiểm tra nếu người dùng chưa đăng nhập hoặc đã đăng nhập và hết thời gian session thì chyển sang trang login
	Account user = (Account)session.getAttribute("user");
	if (user == null) {
		//Kiểm tra nếu người dùng chưa đăng nhập hoặc đã đăng nhập và hết phiên làm việc thì chyển sang trang login
		response.sendRedirect(response.encodeRedirectURL("../login"));
	} else if (user.getRole() == 2) {
		//Kiểm tra nếu người dùng đã đăng nhập và không phải admin thì chuyển qua trang chủ
		response.sendRedirect(response.encodeRedirectURL("../home"));
	}
%>
<div class="grid-container">
	<div class="left">
		<h1>SMART WORLD</h1>
		<ul class="list">
			<li class="active">Dashboard</li>
			<li>Staff Manager</li>
		</ul>
		<a href="<%= response.encodeURL("../Controller?action=logout")%>">Logout</a>
	</div>
	<div class="right">
		<div class="top"></div>
		<div class="bottom">
			<!-- Lấy tên người dùng từ email để hiện thông điệp chào mừng khi đăng nhập -->
			<% 
				if (user != null) { %>
					<h1>Welcome <%= (user.getEmail()).substring(0,(user.getEmail()).indexOf("@")) %></h1>
			<% } %>	
		</div>
	</div>
</div>
</body>
</html>