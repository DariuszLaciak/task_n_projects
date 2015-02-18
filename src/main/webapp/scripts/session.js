var idleTime = 0;
var expired = false;
$(document).ready(function () {
	if($("#login").length == 0){
		//Increment the idle time counter every minute.

		var idleInterval = setInterval(timerIncrement, 60000); // 1 minute
		//Zero the idle timer on mouse movement.
		$(this).mousemove(function (e) {
			idleTime = 0;
		});
		$(this).keypress(function (e) {
			idleTime = 0;
		});
	}
});

function timerIncrement() {
	var session_time = $("#session_max_time").text();
	session_time = session_time / 60;
	if(!expired){
		idleTime = idleTime + 1;
		if (idleTime >= session_time) { 
			$.ajax({
				url: 'User',
				type: "POST",
				data: {
					action: "checkSession"
				},
				success: function(data){
					var ans = jQuery.parseJSON(data);
					if(ans.success == 1 || ans.error == 'logged_out'){
						$("body").append("<div class='screen'>");
						$('.screen').css({'display': 'block', opacity: 0.6, 'width':$(document).width(),'height':$(document).height()});
						$('.screen').mousemove(function(){
							window.location.reload();
						});
						$('.screen').click(function(){
							window.location.reload();
						});
						$('.screen').keypress(function(){
							window.location.reload();
						});
						$('.screen').html("<div id='const_msg' class='error'>Sesja wygasła. Zaloguj się ponownie</div>");
						expired = true;
					}
				}
			});
		}
	}
}