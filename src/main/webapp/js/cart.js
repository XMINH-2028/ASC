function total() {
	let a = [];
	let n = 0;
	for (i = 0; i < $(".cart label").length; i++) {
		if (document.querySelectorAll(".cart label")[i].getAttribute("class") == "checkmark") {
			n += 1;
			let x = parseFloat($(".cart .price")[i].attributes["data-price"].value);
			let y = parseInt($(".cart .number")[i].value);
			a[n] = x * y;
		}
	}
	if (a.length == 0) return 0;
	return a.reduce((total,value) => total += value);
}

//Ẩn hiện menu chuyển trang với các thiết bị di động

$(document).ready(function(){
  	$(".top .menu i").click(function(){
    	$(".top .nav").toggle();	
    	$(document).on("click",(e)=> {
	    if (e.target.closest(".left") == null)
		    	$(".top .nav").css("display","none");
		})
  	});
});

$(document).ready(function() {
	let check = 0;
	$(".cart .number").keydown(function(e) {
		let key = e.key;
		let code = key.charCodeAt(0);
		if(code < 48 || (code > 57 && code != 66 && code != 68)) {
			e.preventDefault();
		}
	});
	
	$(".cart .number").focusout(function() {
		if ($(this).val() == "" || $(this).val() == 0) {
			$(this).val(1);
		}
		let id = $(this).attr("data-id");
		let value = $(this).val();
		$(this).val(parseInt(value));
		$.get(setUrl("/cart","changecart",id,value), function() {});
	});
	
	$(".cart .reduce").click(function() {
		let value = $(this).siblings(".number").val();
		value = parseInt(value);
		let id = $(this).siblings(".number").attr("data-id");
		if (value > 1) {
			value = parseInt(value) - 1;
		}
		$(this).siblings(".number").val(value);
		$.get(setUrl("cart","changecart",id,value), function() {});
	});
	
	$(".cart .increase").click(function() {
		let value = $(this).siblings(".number").val();
		value = parseInt(value) + 1;
		let id = $(this).siblings(".number").attr("data-id");
		$(this).siblings(".number").val(value);
		$.get(setUrl("/cart","changecart",id,value), function() {});
	});
	
	$(".cart .cart_product").click(function(e){
		if (!e.target.closest(".link") && !e.target.closest(".close")) {
			if (e.target.closest(".number")) {
				check = $(this).find(".number").attr("data-id");
				$(this).children("label").addClass('checkmark');
			} else if (e.target.closest(".reduce") || e.target.closest(".increase")) {
				check = 0;
				$(this).children("label").addClass('checkmark');
			} else {
				if (check != 0 && check == $(this).find(".number").attr("data-id")) {
					check = 0;
				} else {
					check = 0;
					let id = $(this).find(".number").attr("data-id");
					if (!$(this).children("label").hasClass('checkmark')) {
						$(this).children("label").addClass('checkmark');
						$.get(setUrl("/cart","checked",id,1), function() {});
					} else {
						$(this).children("label").removeClass('checkmark');
						$.get(setUrl("/cart","checked",id,0), function() {});
					}
				}
			}
			$(".top .pay .total").html(vnd(parseInt(total() * 1000000) + "") + "đ");
		}
	})
	
	$(".cart .cart_product .close").click(function(){
		let id = $(this).attr("data-id");
		$.get(setUrl("/cart","deletecart",id,0) , function() {});
		$(this).parents(".cart_product").remove();
		$(".pay .total").html(vnd(parseInt(total() * 1000000) + "") + "đ");
	})
	
	$(".top .pay a").click(function(e) {
		if (total() == 0) e.preventDefault();
	});
	
	$(".top .ordered ").click(function() {
		let a = window.location.href;
		let b = a.replace("/cart","/Controller");
		if (b.includes("?")) {
			b += "&action=cart&page=ordered";
		} else {
			b += "?action=cart&page=ordered";
		}
		$.get(b, function(data) {
			$(".cart").html(data);
			$(".cart").css({"padding":"10px"});
			$(".cart .cart_product").css({"min-width":"300px"});
			$(".right").remove();
			$(".left").css({"width":"100%"});
			$(".nav").css({"display":"flex","position":"unset","padding":"0","width":"auto"});
			$(".nav li").css({"margin-bottom":"0"});
			$(".menu").remove();
			$(".ordered").addClass("active");
			$(".selected").removeClass("active");
		});
	});
	
});