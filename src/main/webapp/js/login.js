$(document).ready(function(){
  	$("#reset").on("click", function(e){
		$(".erorrAlert").text(''); 
		$("input:text").val("");
		$("input:password").val("");
	});
});


