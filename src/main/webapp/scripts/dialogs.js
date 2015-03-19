/**
 * 
 */

$(document).ready(function(){
	var dialogs = [
	               "edit_profile","studentsGroup"
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


function openStudentsGroupWindow(id){
	openStudentsGroupWindowSettings();
	$.post(
			'Teacher',
			{
				action: "studentsGroup",
				id: id
			},
			function(data){
				
				var html = jQuery.parseJSON(data);
				if(html.form != null){
					$('#studentsGroup').html(html.form);
					$("#studentsGroup").dialog("open");
				}
				else {
					popup("error","Sesja wygasła. Zaloguj się ponownie");
					setTimeout(function(){
						location.reload();
					}, 1000);
				}
			});
	
}

function openStudentsGroupWindowSettings(){
	$("#studentsGroup").dialog({
		title: "Lista studentów",
		autoOpen: false,
		width: 390,
		height: 300,
		minWidth: 390,
		minHeight: 300,
		resizable: true,
		buttons: [{
			text: "Wyjdź",
			click: function() {
				$( this ).dialog( "close" );
			}
		}
		]
		
	});
}