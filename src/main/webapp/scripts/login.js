function log(){
	var user =$("#username").val();
	var pass = $("#password").val();
	loading_screen("Trwa logowanie...");
	
	$.ajax({
		url: 'Login',
		type: "POST",
		data: {
			username: user,
			password: pass
		},
		success: function(data){
			if(data != "Zalogowany"){
				$("#response_login").html("<h3>"+data+"</h3>");
				$("#username").val("");
				$("#password").val("");
				loading_done();
			}
			else {
				location.reload();
			}
		}
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
	$("form[name='login_form']").submit(function(ev){
		ev.preventDefault();
	});
});