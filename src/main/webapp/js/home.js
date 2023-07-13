const $$ = document.querySelectorAll.bind(document);

//Tạo hiệu ứng khi click vào tên nhãn hàng ở side bar
$(document).ready(function() {
	$(".side ul > li > span").click(function() {
		$(this).parent().next().toggleClass("griddisplay");
		$(this).children(".control").toggleClass("rotate90deg");
	});
});

//Tạo hiệu ứng khi click vào nút chọn trang
$(document).ready(function() {
	$(".pagination .page").click(function() {
		$(this).siblings().removeClass("active");
		$(this).addClass("active");
	});
});

//Vô hiệu hóa nút chuyển về trang trước khi đang ở trang đầu tiên
$(document).ready(function() {
	$(".pagination .prev").click(function(e) {
		let page = $$(".page");
		if (page[0].classList.contains("active")) {
			e.preventDefault();
		}
	});
});

//Vô hiệu hóa nút chuyển về trang tiếp theo khi đang ở trang cuối
$(document).ready(function() {
	$(".pagination .next").click(function(e) {
		let page = $$(".page");
		if (page[page.length-1].classList.contains("active")) {
			e.preventDefault();
		}
	});
});

//Ản hiện các lựa chọn của các mục filter 
$(document).ready(function() {
	$(".filter_child .title").click(function() {
		let l = getOffset(this).left;
		let m = window.innerWidth -$(document).width();
		let x;
		if ($(window).width() + m > 300) {
			x = 280;	
		} else {
			x = 150;
		}
		if ($(window).width() - l < x) {
			if (l > x) {
				$(this).siblings("div").css("left",70-x+"px");
			} else {
				$(this).siblings("div").css("left", $(window).width() - x - l +"px");
			}
			
		} 
		$(window).resize(function(){
			l = getOffset($$(".filter_child .filter_css")[0]).left;
			if ($(window).width() + m > 300) {
				x = 280;	
			} else {
				x = 150;
			}
			if ($(window).width() - l < x) {
				if (l > x) {
					$$(".filter_child .flexdisplay")[0].style.left = 70-x + "px";
				} else {
					if ($(window).width() >= 150) {
						$$(".filter_child .flexdisplay")[0].style.left =  $(window).width() - x - l +"px";
					}
				}
				
			} else {
				$$(".filter_child .flexdisplay")[0].style.left =  0;
			}
		});
		if ($(this).hasClass("filter_css")) {
			$(this).removeClass("filter_css");
			$(this).siblings("div").removeClass("flexdisplay");
		} else {
			$(".filter_child .title").removeClass("filter_css");
			$(".filter_child .title").siblings("div").removeClass("flexdisplay");
			$(this).addClass("filter_css");
			$(this).siblings("div").addClass("flexdisplay");
		}
		$(document).click(function(e) {
			if(e.target.closest(".filter_child") == null) {
				$(".filter_child .title").removeClass("filter_css");
				$(".filter_child .title").siblings("div").removeClass("flexdisplay");
			}
		})
	});
});

//Hàm hiển thị số lượng nội dung filter và đánh dấu những mục filter đã chọn
$(document).ready(function() {
	let list = $$(".filter_child input:checked");
	let number = $$(".filter_child input:checked").length == 0 ? "" : $$(".filter_child input:checked").length;
	$(".filter .fa-filter span").html(number);
	if (list.length != 0) {
		list.forEach((x) => {
			$(x).parents(".filter_child").css("border","2px solid #3F51B5");
		});
	}
});

//Hàm thêm param thể hiện mục filter đã lựa chọn
$(document).ready(function() {
	$(".filter_child button").click(function() {
		let list = $$(".filter_child");
		for(let i = 0; i < list.length; i++) {
			let y = list[i].querySelectorAll("input:checked");
			if (y.length != 0) {
				let html = `<input type='hidden' name='type${i+1}' value='${i+1}'>`;
				list[i].insertAdjacentHTML("afterbegin", html);
			}
		}
	});
});

//Hàm tăng giảm số lượng nội dung filter khi người dùng chọn và bỏ chọn
$(document).ready(function() {
	let list = $$(".filter_child input");
	let number = $(".filter .fa-filter span").html() == "" ? 0 : parseInt($(".filter .fa-filter span").html());
	list.forEach((x) => {
		$(x).click(function() {
			if (x.checked == true) {
				number += 1;
			} else {
				number -= 1;
			}
			if (number == 0) {
				$(".filter .fa-filter span").html("");
			} else {
				$(".filter .fa-filter span").html(number);
			}
		});
	})
});




