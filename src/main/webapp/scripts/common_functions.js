function printLogo(){
	$("#logo").append("<span>PnT Manager</span>");
}
function makeLoading(on){
	
	
	
	if(!on){
		$("#loading_login").hide();
	}
	else {
		$("#loading_login").show();
	}
}