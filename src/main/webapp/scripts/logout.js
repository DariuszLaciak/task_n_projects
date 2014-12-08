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
				if(reply.logout_reply == 1){
					location.reload();
				}
			});
}