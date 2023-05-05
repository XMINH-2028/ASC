<%@ page import="controller.*, jakarta.servlet.http.Cookie"%>

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
			<li><a href='Controller?action=loginform' class="loginform">Login</a></li>
		</ul>
	</div>
	
	<script>
		$(document).ready(function(){
		  	$("header .menu").click(function(){
		    	$(this).siblings(".item").children().toggle("fast");
		    	$(document).on("click",(e)=> {
			    if (e.target.closest(".left") == null)
				    	$(this).siblings(".item").children().css("display","none");
				})
		  	});
		});
	
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
</header>


