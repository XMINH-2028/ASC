<%@ page import="controller.*"%>
<% String user = request.getParameter("user");%>
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
				<li class="menu"><i class='fas fa-bars'></i></li>
				<ul class="item">
					<li ><a href="#">Home</a></li>
					<li><a href="#">Products</a></li>
					<li><a href="#">About us</a></li>
				</ul>
			</ul>
		<ul class="right">
		<!-- When logged in -->
			<% if (user != null) { %>
			<li class='admin'><span><%=user.substring(0, 1)%></span>
				<ul class='content vision'>
					<li><span><%=user.substring(0, 1)%></span><%= user%></li>
					<li><a class='logout' href="LogoutServlet">Logout</a></li>
				</ul></li>
			<script>
			$(document).ready(function() {
				$(".admin > span").on("click", function() {
					$(".admin .content").toggleClass("vision");
					$(document).on("click",(e)=> {
					    if (e.target.closest(".admin") == null)
					    	$(".admin .content").addClass("vision");
					})
				});
			});
			</script>
			<!-- When not logged in -->
			<% } else { %>
			<li><a href='login'>Login</a></li>
			<% } %>
		</ul>
	</div>
</header>
