$(document).ready(function(){
	$("#student_activ_look").click(function(){
		$("#m_content").load("manage/look.jsp");
	});
	$("#student_activ_files").click(function(){
		$("#m_content").load("manage/files.jsp");
	});
	$("#student_activ_comment").click(function(){
		$("#m_content").load("manage/comment.jsp");
	});
	$("#student_activ_tasks").click(function(){
		$("#m_content").load("manage/tasks.jsp");
	});
	$("#student_activ_notes").click(function(){
		$("#m_content").load("manage/notes.jsp");
	});
	$("#student_activ_version").click(function(){
		$("#m_content").load("manage/version.jsp");
	});
	$("#subject_list_st").click(function(){
		$("#m_content").load("student/subjects.jsp");
	});
	$("#task_list_st").click(function(){
		$("#m_content").load("student/tasks.jsp");
	});
	$("#project_list_st").click(function(){
		$("#m_content").load("student/projects.jsp");
	})
});

function confirmNewRepo(){
	var form_values = $("#addRepositoryForm").serializeArray();
	var values = new Array();
	

	$.each(form_values, function(index,element){
		values.push(element.value);
	});
	
	$.ajax({
		url: "Student",
		type: "post",
		data: {
			action: "addNewRepo",
			form_values: values
		},
		success: function(data){
			var output = jQuery.parseJSON(data);
			isUserLoggedIn(output);
			if(output.success == 1){
				popup("success","Pomyślnie dodano repozytorium");
				$("#m_content").load("manage/version.jsp");
			}
		}
	});
}

function confirmNewVersion(){
	var form_values = $("#addNewVersionForm").serializeArray();
	var values = new Array();
	

	$.each(form_values, function(index,element){
		values.push(element.value);
	});
	
	$.ajax({
		url: "Student",
		type: "post",
		data: {
			action: "addNewVersion",
			form_values: values
		},
		success: function(data){
			var output = jQuery.parseJSON(data);
			isUserLoggedIn(output);
			if(output.success == 1){
				popup("success","Pomyślnie dodano wersje projektu");
				$("#m_content").load("manage/version.jsp");
			}
		}
	});
}

function uploadNewFile(){
	var comment = $("#newFileComment").val();
	
	$.ajax({
		url: "Student",
		type: "post",
		data: {
			action: "newFile",
			comment: comment
		},
		success: function(data){
			var output = jQuery.parseJSON(data);
			isUserLoggedIn(output);
			if(output.success == 1){
				popup("success", "Pomyslnie wstawiono plik");
				$("#m_content").load("manage/files.jsp");
			}
			else if(output.success == 2){
				popup("error", "Problem z serwerem FTP. Skontaktuj się z administratorem");
			}
		}
	});
	
}