$(document).ready(function() {
	var elems = $(".mmenu").find("a");
	$.each(elems,function(){
		$(this).click(function(){
			if(!$(this).next().is(":visible"))
				$(this).next().slideDown(500);
			else
				$(this).next().slideUp(500);
		});
	});
	
	
	$('#user_name').textfill({ maxFontPixels: 25 });
});
