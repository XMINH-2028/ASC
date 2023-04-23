$(document).ready(function(){
  	$("header .menu").click(function(){
    	$(this).siblings(".item").toggle("fast");
    	$(document).on("click",(e)=> {
	    if (e.target.closest(".left") == null)
		    	$(this).siblings(".item").css("display","none");
		})
  	});
});
