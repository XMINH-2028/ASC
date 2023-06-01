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


//Vô hiệu hóa nút tìm kiếm khi người dùng chưa nhập thông tin cần tìm
$(document).ready(function() {
	$(".top  .submit").on("click", function(e) {
		if ($(".top .searchInput").val() == null || $(".top .searchInput").val().trim() == "") {
			e.preventDefault();
		}
	});
});