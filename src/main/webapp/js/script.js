//Hàm lấy vị trí của phần tử so với document
function getOffset(el) {
  const rect = el.getBoundingClientRect();
  return {
    left: rect.left + window.scrollX,
    top: rect.top + window.scrollY
  };
}

function vnd(value) {
	let b = value.split("");
	let c = "";
	for (i = 0; i < b.length; i++) {
		c += b[i];
		if (i != (b.length - 1) && (b.length - 1 - i) % 3 == 0) {
			c += ",";
		}
	}
	return c;
}

$(document).ready(function() {
	$('.addcart').click(function(e) {
		let id = $(this).attr("data-id");
		let url = $(this).attr("data-url");
		let img = $(this).attr("data-img");
		let name = $(this).attr("data-name");
		let price = $(this).attr("data-price");
		$.get('Controller?action=addbuy', function(data) {
	        if (data == "false") {
				window.location.replace(url);
			} else {
				let text = `<div class="addcart_form">
					<div class="addcart_main">
						<img src="` + img + `">
						<div class="addcart_info">
							<p class="name">` + name + `</p>
							<p class="price">` + price + `đ</p>
						</div>
						<div class="quantity">
							<p>Quantity: <span class="reduce">-</span><input class="number" type="text" value="1">
							<span class="increase">+</span>
							</p>
							<button>Add to Cart</button>
						</div>
						<span class="close">+</span>
					</div>
				</div>`;
				$('body').append(text);
				$(".addcart_main .number").keydown(function(e) {
					let key = e.key;
					let code = key.charCodeAt(0);
					if(code < 48 || (code > 57 && code != 66 && code != 68)) {
						e.preventDefault();
					}
				});
				$(".addcart_main .number").focusout(function() {
					if ($(this).val() == "" || $(this).val() == 0) {
						$(this).val(1);
					}
					let value = $(this).val();
					$(this).val(parseInt(value));
				});
				$(".addcart_main .reduce").click(function() {
					let value = $(".number").val();
					if (value > 1) {
						$(".number").val(value - 1);
					}
				});
				$(".addcart_main .increase").click(function() {
					let value = $(".number").val();
					$(".number").val(parseInt(value) + 1);
				});
				$(".addcart_main .close").click(function() {
					$(".addcart_form").remove();
				});
				$(".addcart_main button").click(function() {
					$.get('Controller?action=addcart&id=' + id + "&quantity=" + $(".number").val(), function(data) {
						$(".shopping_cart .total_quantity").html("");
						setTimeout(()=>{
							$(".shopping_cart .total_quantity").html(data);
						},100);
						
						$(".addcart_form").remove();
					});
				});
			}
	    });
			
	});
});
