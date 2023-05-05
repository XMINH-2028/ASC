<%@ page import="jakarta.servlet.http.Cookie" pageEncoding="utf-8"%>

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
			<li><a href='login' class="loginform">Login</a></li>
		</ul>
	</div>
	
	<script>
		//Ẩn hiện menu chuyển trang với các thiết bị di động
		$(document).ready(function(){
		  	$("header .menu").click(function(){
		    	$(this).siblings(".item").children().toggle("fast");
		    	$(document).on("click",(e)=> {
			    if (e.target.closest(".left") == null)
				    	$(this).siblings(".item").children().css("display","none");
				})
		  	});
		});
	</script>
</header>


