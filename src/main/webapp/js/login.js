/*function validateFormLogin() {
	//Tên đăng nhập không được để trống
	if (!$("#Username").val().trim()) {
		$(".passAlert").text('');
		$(".userAlert").text('Please input Username!');
		return 0;
	} else {
		$(".userAlert").text('');
		//Password không được để trống
		if (!$("#Password").val().trim()) {
			$(".passAlert").text('Please input Password!');
		return 0;
		}
	}
	$(".userAlert").text('');
	$(".passAlert").text('');
	return 1;
}*/

//Reset form
$(document).ready(function(){
  	$("#res").on("click", function(){
		$(".erorrAlert").text(''); 
		$("input:text").val('');
		$("input:password").val('');
	});
});

/*$(document).on("click", "#sub", function() { 
	let x = validateFormLogin();
	if ( x == 1) {
	    $.get("LoginServlet",$("#loginForm").serialize(),function(responseText) {
			if (responseText == "Erorr")
				$(".erorrAlert").text('Username or password is invalid!'); 
			else if ((responseText == "Success"))
				$("#loginForm").submit();
	    });
	 }
});*/
