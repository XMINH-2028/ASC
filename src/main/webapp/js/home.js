//Tạo hiệu ứng khi click vào tên nhãn hàng ở side bar
$(document).ready(function() {
	$(".side ul > li > span").click(function() {
		$(this).parents("ul").children(".home_wrap").fadeToggle("fast");
		$(this).children(".control").toggleClass("rotate90deg");
	});
});
