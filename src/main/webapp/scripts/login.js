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

function updateClock ( )
{
	var currentTime = new Date ( );
	var currentHours = currentTime.getHours ( );
	var currentMinutes = currentTime.getMinutes ( );
	var currentSeconds = currentTime.getSeconds ( );

//	Pad the minutes and seconds with leading zeros, if required
	currentMinutes = ( currentMinutes < 10 ? "0" : "" ) + currentMinutes;
	currentSeconds = ( currentSeconds < 10 ? "0" : "" ) + currentSeconds;


//	Compose the string for display
	var currentTimeString = currentHours + ":" + currentMinutes + ":" + currentSeconds;


	$("#clock").html(currentTimeString);

}

$(document).ready(function(){
	setInterval('updateClock()', 1000);
});