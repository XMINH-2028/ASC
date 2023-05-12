//Ẩn hiện menu chuyển trang với các thiết bị di động

$(document).ready(function(){
  	$("header .menu i").click(function(){
    	$(".topnav .item ul").toggle();	
    	$(document).on("click",(e)=> {
	    if (e.target.closest(".left") == null)
		    	$(".topnav .item ul").css("display","none");
		})
  	});
});

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