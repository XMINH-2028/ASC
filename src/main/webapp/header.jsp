<%@ page import="controller.*"%>
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
			<% if (!LoginServlet.login.equals("")) { %>
			<li class='admin'><span>M</span>
				<ul class='content vision'>
					<li><span><%=(LoginServlet.login).substring(0, 1)%></span><%=LoginServlet.login%></li>
					<li class='logout'>Logout</li>
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
				
				$(".logout").click(function() {
					var xhttp = new XMLHttpRequest();
					xhttp.onreadystatechange = function() {
						window.location.href = "home";
					};
					xhttp.open("GET", "LogoutServlet", true);
					xhttp.send();
				})
			});
			</script>
			<% } else { %>
			<li><a href='login'>Login</a></li>
			<% } %>
		</ul>
	</div>
</header>
