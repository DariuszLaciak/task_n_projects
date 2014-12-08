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
				var reply = jQuery.parseJSON(data);
				if(reply == 1){
					location.reload();
				}
			});
}