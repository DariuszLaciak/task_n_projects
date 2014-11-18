/**
 * 
 */
function logout(){
	$.post(
			'User',
			{
				action: "logout"
			},
			function(data){
				if(data == "1")
					location.reload();
				
			});
}