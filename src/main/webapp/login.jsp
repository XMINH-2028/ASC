<%@ page language="java"
	contentType="text/html; charset=utf-8; text/css" pageEncoding="utf-8" import="database.*"%>
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
<%	//Kiểm tra nếu người dùng đã đăng nhập chưa 
	if (session.getAttribute("user") != null) {
		Account user = (Account)session.getAttribute("user");
		if (user.getRole() == 1) {
			//Nếu người dùng là quản trị viên thì chuyển qua trang admin
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath()+"/manager/admin"));
		} else {
			//Nếu người dùng không là quản trị viên thì chuyển qua trang home
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath()+"/home"));
		}
		
	}
	//Lấy dữ liệu phản hồi từ LoginServlet qua session "vlogin"
	Account v = (Account)session.getAttribute("vlogin");
	String email = "";
	String password = ""; 
	String mailalert = "";
	String passalert = "";
	if (v != null) {
		email = v.getEmail();
		password = v.getPassword();
		mailalert = v.getMailalert();
		passalert = v.getPassalert();
	}
	//Khi chuyển đến trang login kiểm tra cookie nếu có lưu thông tin đăng nhập thì lấy email từ cookie và điền vào form 
	if (email.equals("")) {
		Cookie ck[] = request.getCookies();
		if (ck != null) {
			for (Cookie i : ck) {
				if (i.getName().equals("email")) {
					email = i.getValue();					
				}
			}
		}
	}

%>
<div class="login">
	<div class="container">
		<form action="<%= response.encodeURL(request.getContextPath()+"/Controller")%>" method="POST">
			<h1>Sign in</h1>
		    <input type="hidden" name="action" value="dologin">
			<p class="wrap">
				<label for="Email">Email<span class="erorrAlert"><%= mailalert == null ? "" : mailalert%></span></label>
				<input id="Email" type="text" name="email" placeholder="Enter Email" value="<%= email == null ? "" : email%>">
			</p>
			<p class="wrap">
				<label for="Password">Password<span class="erorrAlert"><%= passalert == null ? "" : passalert%></span></label>
				<input id="Password" type="password" name="password" placeholder="Enter Password" value="<%= password == null ? "" : password %>">
			</p>
			<button type="submit" id="sub">Login</button>
			<p>
				<input type="checkbox" id="Remember" name="remember">
				<label for="Remember">Remember me</label>
			</p>
			<div class="footer">
				<a id="reset" href="<%= response.encodeURL(request.getContextPath()+"/Controller?action=loginreset")%>">Reset</a>
				<p>Forgot<a href="<%= response.encodeURL(request.getContextPath()+"/Controller?action=forget")%>"> password?</a></p>
			</div>
		</form>
		<div class="welcome">
			<h1>Welcome to <br>Smart World</h1>
			<p>To keep connected  with us<br>please login with your personal info</p>
			<span class="close"><a href="<%= response.encodeURL(request.getContextPath()+"/Controller?action=closeform")%>">+</a></span>
		</div>
	</div>
</div>
</body>
</html>