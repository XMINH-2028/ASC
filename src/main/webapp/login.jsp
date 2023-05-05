<%@ page language="java"
	contentType="text/html; charset=utf-8; text/css" pageEncoding="utf-8"%>
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
	<div class="login">
		<div class="container">
		<%	
			if (session.getAttribute("mail") != null) {
				response.sendRedirect("admin/index");
			}
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String mailalert = request.getParameter("mailalert");
			String passalert = request.getParameter("passalert");
			if (email == null) {
				Cookie ck[] = request.getCookies();
				String mail = getServletContext().getInitParameter("mail");
				if (ck != null) {
					for (Cookie i : ck) {
						if (i.getValue().equals(mail)) {
							email = mail;					
						}
					}
				}
			}
	
		%>
			<form action="LoginServlet" method="POST">
				<h1>Sign in</h1>
			    <input type="hidden" name="action" value="submit">
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
					<button type="button" id="reset">Cancel</button>
					<script>
						$(document).ready(function(){
						  	$("#reset").on("click", function(){
								$(".erorrAlert").text(''); 
								$("input:text").val("");
								$("input:password").val("");
							});
						});
					</script>
					<p>Forgot<a href="#"> password?</a></p>
				</div>
			</form>
			<div class="welcome">
				<h1>Welcome to <br>Smart World</h1>
				<p>To keep connected  with us<br>please login with your personal info</p>
				<span class="close"><a href="home">+</a></span>
			</div>
		</div>
	</div>
</body>
</html>