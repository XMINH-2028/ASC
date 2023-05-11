<%@ page language="java"
	contentType="text/html; charset=utf-8; text/css" pageEncoding="utf-8" import="database.*"%>
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
<%	//Kiểm tra nếu người dùng đã đăng nhập chưa 
	if (session.getAttribute("user") != null) {
		Account user = (Account)session.getAttribute("user");
		if (user.getRole() == 1) {
			//Nếu người dùng là quản trị viên thì chuyển qua trang admin
			response.sendRedirect(response.encodeRedirectURL("manager/admin"));
		} else {
			//Nếu người dùng không là quản trị viên thì chuyển qua trang home
			response.sendRedirect(response.encodeRedirectURL("home"));
		}
		
	}
	//Lấy dữ liệu phản hồi từ registerServlet qua session "vregister"
	Account v = (Account)session.getAttribute("register");
	String firstname="";
	String lastname="";
	String email = "";
	String password = ""; 
	String repass = "";
	String fnamealert="";
	String lnamealert="";
	String mailalert = "";
	String passalert = "";
	String repalert = "";
	if (v != null) {
		firstname= v.getFirstname();
		lastname = v.getLastname();
		email = v.getEmail();
		password = v.getPassword();
		repass = v.getRepass();
		fnamealert = v.getFnamealert();
		lnamealert = v.getLnamealert();
		mailalert = v.getMailalert();
		passalert = v.getPassalert();
		repalert = v.getRepalert();
	}
%>
<div class="register">
	<div class="container">
		<form action="<%= response.encodeURL("Controller")%>" method="POST">
			<h1>Register</h1>
		    <input type="hidden" name="action" value="doregister">
		    <p class="wrap">
				<label for="firstname">Firstname<span class="erorrAlert"><%= fnamealert == null ? "" : fnamealert%></span></label>
				<input id="firstname" type="text" name="firstname" placeholder="Enter your firstname" value="<%= firstname == null ? "" : firstname%>">
				
			</p>
			<p class="wrap">
				<label for="lastname">Lastname<span class="erorrAlert"><%= lnamealert == null ? "" : lnamealert%></span></label>
				<input id="lastname" type="text" name="lastname" placeholder="Enter your lastname" value="<%= lastname == null ? "" : lastname%>">
				
			</p>
			<p class="wrap">
				<label for="Email">Email<span class="erorrAlert"><%= mailalert == null ? "" : mailalert%></span></label>
				<input id="Email" type="text" name="email" placeholder="Enter Email" value="<%= email == null ? "" : email%>">
				
			</p>
			<p class="wrap">
				<label for="Password">Password<span class="erorrAlert"><%= passalert == null ? "" : passalert%></span></label>
				<input id="Password" type="password" name="password" placeholder="Enter Password" value="<%= password == null ? "" : password %>">
				
			</p>
			<p class="wrap">
				<label for="Password">Repassword<span class="erorrAlert"><%= repalert == null ? "" : repalert%></span></label>
				<input id="Password" type="password" name="repass" placeholder="Enter Repassword" value="<%= repass == null ? "" : repass %>">
				
			</p>
			<p class="footer">
				<a id="reset" href="<%= response.encodeURL("Controller?action=registerreset")%>">Reset</a>
				<button type="submit" id="sub">Create</button>
			</p>
		</form>
		<div class="welcome">
			<h1>Welcome to <br>Smart World</h1>
			<p>Let's create new account here</p>
			<span class="close"><a href="<%= response.encodeURL("Controller?action=closeform")%>">+</a></span>
		</div>
	</div>
</div>
</body>
</html>