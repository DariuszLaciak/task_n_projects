$(document).ready(function() {
	var elems = $(".mmenu").find("a");
	$.each(elems,function(){
		$(this).click(function(){
			$(this).next().slideToggle(500);
				
		});
	});
	
	
	$('#user_name').textfill({ maxFontPixels: 25 });
});
