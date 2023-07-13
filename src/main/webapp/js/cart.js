function setUrl(action, id, quantity) {
	let a = window.location.href;
	let b = a.replace("cart","Controller");
	b += "?action="+ action + "&id=" + id + "&quantity=" + quantity;
	return b;
}

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
		$.get(setUrl("changecart",id,value), function() {});
	});
	
	$(".cart .reduce").click(function() {
		let value = $(this).siblings(".number").val();
		let id = $(this).siblings(".number").attr("data-id");
		if (value > 1) {
			value = parseInt(value) - 1;
			$(this).siblings(".number").val(value);
			$.get(setUrl("changecart",id,value), function() {});
		}
	});
	
	$(".cart .increase").click(function() {
		let value = $(this).siblings(".number").val();
		value = parseInt(value) + 1;
		let id = $(this).siblings(".number").attr("data-id");
		$(this).siblings(".number").val(value);
		$.get(setUrl("changecart",id,value), function() {});
	});
	
	$(".cart_product").click(function(e){
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
					if (!$(this).children("label").hasClass('checkmark')) {
						$(this).children("label").addClass('checkmark');
					} else {
						$(this).children("label").removeClass('checkmark');
					}
					let id = $(this).find(".number").attr("data-id");
					$.get(setUrl("checked",id,0), function() {});
				}
			}
			$(".pay .total").html(vnd(parseInt(total() * 1000000) + "") + "đ");
		}
	})
	
	$(".cart_product .close").click(function(){
		let id = $(this).attr("data-id");
		$.get(setUrl("deletecart",id,0) , function() {});
		$(this).parents(".cart_product").remove();
		$(".pay .total").html(vnd(parseInt(total() * 1000000) + "") + "đ");
	})
	
});