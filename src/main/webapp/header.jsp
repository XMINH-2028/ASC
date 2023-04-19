<%@ page import="controller.*"%>
<jsp:useBean id="admin" class="bean.User" scope="session"></jsp:useBean>
<% if (admin.getUsername() == null) 
	{ 
		response.sendRedirect("Controller");
	} else {%>
	<header>
		<div class="top">
			<img alt="logo" src="images/logo.png" class="logo">
			<form action="#">
				<div class="searchBox">
					<input class="searchInput">
					<button>
						<i class='fas fa-search'></i>
					</button>
				</div>
			</form>
		</div>
		<div class="topnav">
			<ul class="left">
				<li><a href="#">Home</a></li>
				<li><a href="#">Products</a></li>
				<li><a href="#">About us</a></li>
			</ul>
			<ul class="right">
				<% if (admin.getUsername() != null && !admin.getUsername().equals("")) { %>
				<li class='admin'><span>M</span>
					<ul class='content vision'>
						<li><span><%=admin.getUsername().substring(0, 1)%></span><%=admin.getUsername()%></li>
						<li><a class='logout' href="Controller?action=logout">Logout</a></li>
					</ul></li>
				<script>
				$(document).ready(function() {
					$(".admin > span").on("click", function() {
						$(".content").toggleClass("vision");
						$(document).on("click",(e)=> {
						    if (e.target.closest(".admin") == null)
						    	$(".content").addClass("vision");
						})
					});
					
					
				});
				</script>
				<% } else { %>
				<li><a href='Controller?action=login'>Login</a></li>
				<% } %>
			</ul>
		</div>
	</header>
<% }  %>
