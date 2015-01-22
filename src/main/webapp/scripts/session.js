var idleTime = 0;
var expired = false;
$(document).ready(function () {
	//Increment the idle time counter every minute.
	if(!expired){
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
	session_time = session_max_time / 60;
	idleTime = idleTime + 1;
	if (idleTime > session_time) { 
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
		$('body').append("<div id='const_msg' class='error'>Sesja wygasła. Zaloguj się ponownie</div>");
		expired = true;
	}
}