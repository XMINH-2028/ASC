//Toggle arrow direction in popular product 
$(document).ready(function(){
  $(".side span").click(function(){
    $(this).parent().children(".wrap").fadeToggle("fast");
     $(this).children(".control").toggleClass("rotate90deg");
  });
});