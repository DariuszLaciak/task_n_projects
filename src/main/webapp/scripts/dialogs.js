/**
 * 
 */

$(document).ready(function(){
	var dialogs = [
	               "edit_profile"
	               ]
	$.each(dialogs,function(value,key){
		if($("body").find("#"+key).length == 0){
			$("body").append("<div id='"+key+"'>");
		}
	});
	$(document).on("dialogopen", function() {
		$("body").append("<div class='screen'>");
		$('.screen').css({'display': 'block', opacity: 0.6, 'width':$(document).width(),'height':$(document).height()});
		
		
		
		});
	$(document).on("dialogclose", function() {
		$(".screen").remove();
		//$('.screen').css({'display': 'none'});
	});

});



function openEditProfileWindow(){
	editProfileWindowSettings();
	$.post(
			'User',
			{
				action: "edit_profile"
			},
			function(data){
				
				var html = jQuery.parseJSON(data);
				if(html.form != null){
					$('#edit_profile').html(html.form);
					$("#edit_profile").dialog("open");
					$("#user_birthday").datepicker({
						dateFormat: "yy-mm-dd"
					});
					progressBarUploadPhoto();
				}
				else {
					popup("error","Sesja wygasła. Zaloguj się ponownie");
					setTimeout(function(){
						location.reload();
					}, 1000);
				}
			});
	
}

function editProfileWindowSettings(){
	$("#edit_profile").dialog({
		title: "Edytuj profil",
		autoOpen: false,
		width: 450,
		height: 500,
		resizable: false,
		buttons: [{
			text: "Zapisz",
			click: function() {
				saveProfile();
			}
		},
		{
			text: "Anuluj",
			click: function() {
				$( this ).dialog( "close" );
			}
		}
		]
		
	});
}