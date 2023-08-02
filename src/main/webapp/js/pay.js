const $$ = document.querySelectorAll.bind(document);

$(document).ready(function() {
	$(".back").click(function() {
		
	});
	
	for (i = 0; i < $(".contact input").length; i++) {
		let x = $$("input")[i];
		x.addEventListener("keydown",function() {
			x.nextSibling.innerText  = "";
		});
		
		x.addEventListener("focusout",function() {
			checkContact();
		});
	}
	
	$(".contact button").click(function(){
		let a = window.location.href;
		let b = a.replace("/pay","/Controller");
		if (b.includes("?")) {
			b += "&action=address&type=get";
		} else {
			b += "?action=address&type=get";
		}
		
		$.get(b, function(data) {
			getData(data);
		});
	});
	if ($(".contact #address").html() != ""){
		$(".contact #address").css("border", "none");
	};
	
	$(".top button").click(function() {
		if (checkContact() != 0) {
			window.scrollTo(0, 0);
		} else {
			let a = window.location.href;
			let b = a.replace("/pay","/Controller");
			if (b.includes("?")) {
				b += "&action=order";
			} else {
				b += "?action=order";
			}
			b += "&name=" + $(".contact #name").val() + "&phone=" + $(".contact #phone").val() ;
			$.get(b, function(data) {
				if (data == 'true') {
					a = window.location.href;
					b = a.replace("/pay","/home");
					location.replace(b);
				}
			});
		}
	});
});

function getData(data) {
	$('body').append(data);
	$(".selectform .close").click(function(){
		$(".selectform").remove();
	});
	let x = $$(".selectform input")[0];
	x.addEventListener("keydown",function() {
		x.nextSibling.innerHTML = "";
	});
	$(".selectform button").click(function(){
		if ($(".selectform input").val().trim() == "") {
			$(".selectform .checkDetail").html("please input detail address");
		} else {
			let x = $("#detail").val() + ", " + $("#wards option:selected").html() + ", " 
					+ $("#district option:selected").html() + ", " + $("#province option:selected").html();
			let a = window.location.href;
			let b = a.replace("/pay","/Controller");
			if (b.includes("?")) {
				b += "&action=address&type=save";
			} else {
				b += "?action=address&type=save";
			}
			b += "&house=" + $("#detail").val() + "&wards=" + $("#wards").val() + "&district=" 
			+ $("#district").val() + "&province=" + $("#province").val();
			$.get(b, function() {});
			$(".contact #address").html(x);
			$(".contact #address").css("border", "none");
			checkContact();
			$(".selectform").remove();
		}
	});
	$(".selectform select").change(function(e){
		let a = window.location.href;
		let b = a.replace("/pay","/Controller");
		if (b.includes("?")) {
			b += "&action=address&type=change";
		} else {
			b += "?action=address&type=change";
		}
		if (e.target.id == "province") {
			b += "&house=" + $("#detail").val() + "&wards=&district=" 
			+ "&province=" + $("#province").val();
		} else if (e.target.id == "distric") {
			b += "&house=" + $("#detail").val() + "&wards=&district=" 
			+ $("#district").val() + "&province=" + $("#province").val();
		} else {
			b += "&house=" + $("#detail").val() + "&wards=" + $("#wards").val() + "&district=" 
			+ $("#district").val() + "&province=" + $("#province").val();
		}
		$.get(b, function(data) {
			$(".selectform").remove();
			getData(data);
		});
	});	
			
}

function checkContact() {
	const arr = ["032","033","034","035","036","037","038","039","086","096","097","098",
				"088","091","094","083","084","085","081","082",
				"089","090","093","070","079","077","076","078",
				"092","056","058","099","059"];
	const arr1 = [];
	for (i = 0; i < arr.length; i++) {
		arr1[i] = arr[i].replace("0", ""); 
	}
	
	let regex = "";
	let regex1 = "";
	let rs = 0;
	for (i = 0; i < arr.length; i++) {
		if (i == 0) {
			regex += "(^" + arr[i] + "|";
			regex1 += "(^" + arr1[i] + "|";
		} else if (i == arr.length - 1) {
			regex += "^" + arr[i] + ")";
			regex1 += "^" + arr1[i] + ")";
		} else {
			regex += "^" + arr[i] + "|";
			regex1 += "^" + arr1[i] + "|";
		}
	}
	if ($(".contact #name").val().trim() == "") {
		$(".contact .checkName").text("please input receiver's name");
		rs++;
	} else {
		$(".contact .checkName").text("");
	}
	if ($(".contact #phone").val().trim() == "") {
			$(".contact .checkPhone").text("please input receiver's phone number");
			rs++;
	} else if ($(".contact #phone").val().substring(0,5) == "(+84)") {
		if ($(".contact #phone").val().substring(5).trim().match(/\d{10}/) &&
		$(".contact #phone").val().substring(5).trim().length == 10 &&
		$(".contact #phone").val().substring(5).trim().match(regex)) {
			$(".contact .checkPhone").text("");
			$(".contact #phone").val($(".contact #phone").val().substring(5).trim());
		} else if ($(".contact #phone").val().substring(5).trim().match(/\d{9}/) &&
		$(".contact #phone").val().substring(5).trim().length == 9 &&
		$(".contact #phone").val().substring(5).trim().match(regex1)) {
			$(".contact .checkPhone").text("");
			$(".contact #phone").val("0" + $(".contact #phone").val().substring(5).trim());
		} else {
			$(".contact .checkPhone").text("incorrect phone number");
			rs++;
		}	
	} else if ($(".contact #phone").val().trim().match(/\d{10}/)) {
		if ($(".contact #phone").val().trim().length == 10 && 
		$(".contact #phone").val().trim().match(regex)) {
			$(".contact .checkPhone").text("");
			$(".contact #phone").val($(".contact #phone").val().trim());
		} else {
			$(".contact .checkPhone").text("incorrect phone number");
			rs++;
		}	
	} else {
		$(".contact .checkPhone").text("incorrect phone number");
		rs++;
	}
	if ($(".contact #address").html().trim() == "") {
		$(".contact .checkAddress").text("please input receiver's address");
		rs++;
	} else {
		$(".contact .checkAddress").text("");
	}
	return rs;
}

