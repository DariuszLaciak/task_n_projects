function log(){
	makeLoading(0);
	var user =$("#username").val();
	var pass = $("#password").val();
	
	$.post(
		'Login',
		{
			username: user,
			password: pass
		},
		function(data){
			if(data != "Logged"){
				$("#response_login").html("<h3>"+data+"</h3>");
				$("#username").val("");
				$("#password").val("");
			}
			else {
				location.reload();
			}
			makeLoading(1);
		});
	
}