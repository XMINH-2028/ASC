<%@ page import="jakarta.servlet.http.Cookie" pageEncoding="utf-8" import="database.*"%>
<% Account user = (Account)session.getAttribute("user"); %>
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
			<li class="item">
				<ul>
					<li ><a href="#">Home</a></li>
					<li><a href="#">Products</a></li>
					<li><a href="#">About us</a></li>
				</ul>
			</li>
		</ul>
		<ul class="right">
		<!-- Khi người dùng đã đăng nhập -->
			<% if (user != null) { %>
			<li class='admin'><span><%= (user.getEmail()).substring(0, 1).toUpperCase()%></span>
				<ul class='content unvisible'>
					<li><span><%= (user.getEmail()).substring(0, 1).toUpperCase()%></span><%=user.getEmail()%></li>
					<li><a class='logout' href="<%= response.encodeURL("Controller?action=logout")%>">Logout</a></li>
				</ul>
			</li>
			<script>
			//Ẩn hiện thông tin khi người dùng click vào biểu tượng tên của mình khi đã đăng nhập
			$(document).ready(function() {
				$(".admin > span").on("click", function() {
					$(".admin .content").toggleClass("unvisible");
					$(document).on("click",(e)=> {
					    if (e.target.closest(".admin") == null)
					    	$(".admin .content").addClass("unvisible");
					})
				});
			});
			</script>
			<!-- Khi người dùng chưa đăng nhập -->
			<% } else { %>
			<li><a href='<%= response.encodeURL("Controller?action=login") %>'>Login</a></li>
			<li><a href='<%= response.encodeURL("Controller?action=register") %>'>Register</a></li>
			<% } %>
		</ul>
	</div>
	
	<script>
		//Ẩn hiện menu chuyển trang với các thiết bị di động
		$(document).ready(function(){
		  	$("header .menu i").click(function(){
		    	$(this).parent().siblings(".item").children().toggle("fast");
		    	$(document).on("click",(e)=> {
			    if (e.target.closest(".left") == null)
				    	$(this).parent().siblings(".item").children().css("display","none");
				})
		  	});
		});
	</script>
</header>


