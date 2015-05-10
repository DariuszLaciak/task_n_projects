function newComment(){
	var form_values = $("#newCommentForm").serializeArray();
	var values = new Array();

	$.each(form_values, function(index,element){
		values.push(element.value);
	});

	$.ajax({
		url: "User",
		type: "post",
		data: {
			action: "newComment",
			form_values: values
		},
		success: function(data){
			var output = jQuery.parseJSON(data);
			isUserLoggedIn(output);
			if(output.success == 1){
				popup("success", "Pomyslnie wystawiono komentarz");
				$("#m_content").load("manage/comment.jsp");
			}
		}
	});
}

function downloadFile(id){
	$.ajax({
		url: "User",
		type: "post",
		data: {
			action: "downloadFile",
			id: id
		},
		success: function(data){
			var output = jQuery.parseJSON(data);
			isUserLoggedIn(output);
			if(output.success == 1){
				var form = $('<form method="POST" action="DownloadFile">');
				form.append($('<input type="hidden" name="file" value="' + output.fileName + '">'));
				$('body').append(form);
				form.submit();
				form.remove();
			}
			else if(output.success == 2){
				popup("error","Niepoprawny plik");
			}
			else if(output.success == 3){
				popup("error","Nie masz praw by pobrać ten plik");
			}
			else if(output.success == 4){
				popup("error","Nie ma podanego pliku");
			}
		}
	});
}

function assignUsers(formId,afterId){
	openAssignStudentsWindow(function(){
		appendHiddenDiv(formId,afterId);
	});
}

function assignUsersScripts(){
	$(document).ready(function(){
		$("#assignedStudents").droppable({
			drop: function(event, ui){
				var id = ui.draggable.attr("id");
				var student = id.substring(id.lastIndexOf("_")+1,id.length);
				var val = ui.draggable.text();
				var object = ui;
				ui.draggable.remove();
				$( "<li class='droppedStudent'></li>" ).text( val ).appendTo( this );
				if(!$("#studentListFinal").length)
					$("<div id='studentListFinal'></div>").appendTo(this).append(student+",");
				else
					$("#studentListFinal").append(student+",");
				
			}
		});
		$(".studentAcademicLi").draggable({ 
			containment: "#assigningWindow",
			cursor: "move",
			appendTo: "body"});
		$(".studentAcademicLi" ).disableSelection();
	});
}

function selectStudentsAcademic(){
	$(document).ready(function(){
		$("#academicGroupSelect").change(function(){
			var academic = $(this).val();
			if(academic){
				$.ajax({
					url: "User",
					type: "post",
					data: {
						action: "fillStudentsAcademic",
						id: academic
					},
					success: function(data){
						var html = jQuery.parseJSON(data);
						if(html.form != null){
							$('#studentListUl').html(html.form);
							assignUsersScripts();
						}
						else {
							popup("error","Sesja wygasła. Zaloguj się ponownie");
							setTimeout(function(){
								location.reload();
							}, 1000);
						}
					}
				});
			}
			else {
				$("#assignedStudents").html("");
			}
		});
	});
}

function appendHiddenDiv(formId,afterId){
	var students = $("#studentListFinal").text();
	if(!$("#studentsTask").length)
		$("#"+formId).find("#"+afterId).after("<input id='studentsTask' name='studentsTask' type='hidden' value='"+students+"'></input>");
	else
		$("#studentsTask").val(students);
}