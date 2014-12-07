/**
 * 
 */

$(document).ready(function(){
	var dialogs = [
	               "edit_profile", "screen"
	               ]
	$.each(dialogs,function(value,key){
		if($("body").find("#"+key).length == 0){
			$("body").append("<div id='"+key+"'>");
		}
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
						dateFormat: "yy-mm-dd 00:00:00"
					});
				}
				else {
					alert("Problem z serwerem");
				}
			});
	
}

function editProfileWindowSettings(){
	$("#edit_profile").dialog({
		title: "Edytuj profil",
		autoOpen: false,
		width: 400,
		height: 500,
		buttons: [{
			text: "Zapisz",
			click: function() {
				alert("zapisano");
			}
		},
		{
			text: "Anuluj",
			click: function() {
				$( this ).dialog( "close" );
			}
		}
		],
		open: function(){
			$('#screen').css({'display': 'block', opacity: 0.7, 'width':$(document).width(),'height':$(document).height()});

		},
		close: function(){
			$('#screen').css({'display': 'none'});
		}
	});
}