function printLogo(){
	$("#logo").append("<span>PnT Manager</span>");
}
function makeLoading(on){
	if(!on){
		$("body").css("display","block");
		$("body").css("background","black");
		$("body").css("opacity","0.5");
		$("body").css("cursor","wait");
	}
	else {
		
		$("body").css("background","#192346");
		$("body").css("opacity","1.0");
		$("body").css("cursor","auto");
	}
}