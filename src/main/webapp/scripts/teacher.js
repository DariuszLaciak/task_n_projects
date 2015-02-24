/**
 * 
 */

$(document).ready(function(){
	$("#view_subjects").click(function(){
		$("#m_content").load("teacher/view_subjects.jsp");
	});
	$("#add_task").click(function(){
		$("#m_content").load("teacher/new_task.jsp");
	});
	$("#add_group_teacher").click(function(){
		$("#m_content").load("teacher/add_group_teacher.jsp");
	});
	$("#add_project").click(function(){
		$("#m_content").load("teacher/new_project.jsp");
	});
	$("#manage_project").click(function(){
		$("#m_content").load("teacher/manage_project.jsp");
	});
	$("#teacher_activ_look").click(function(){
		$("#m_content").load("manage/look.jsp");
	});
	$("#teacher_activ_files").click(function(){
		$("#m_content").load("manage/files.jsp");
	});
	$("#teacher_activ_comment").click(function(){
		$("#m_content").load("manage/comment.jsp");
	});
	$("#teacher_activ_tasks").click(function(){
		$("#m_content").load("manage/tasks.jsp");
	});
	$("#teacher_activ_notes").click(function(){
		$("#m_content").load("manage/notes.jsp");
	});
	$("#teacher_activ_version").click(function(){
		$("#m_content").load("manage/version.jsp");
	});
});

function sub_details(id){
	alert("Tu kiedyś będzie pokazywanie szczegółów");
}

function confirm_add_task(){
	var form_values = $("#new_task_form").serializeArray();
	var values = new Array();
	

	$.each(form_values, function(index,element){
		values.push(element.value);
	});
	
	$.ajax({
		url: "Teacher",
		type: "post",
		data: {
			action: "add_task",
			form_values: values
		},
		success: function(data){
			var output = jQuery.parseJSON(data);
			isUserLoggedIn(output);
			if(output.success == 1){
				popup("success","Pomyślnie dodano zadanie");
			}
			else if(output.success == 2){
				popup("error","Zły format daty rozpoczęcia");
			}
			else if(output.success == 3){
				popup("error","Nieprawidłowy student lub przedmiot");
			}
			else if(output.success == 4){
				popup("error","Zły format deadlinu");
			}
			
		}
	});
}

function confirm_add_group(){
	var form_values = $("#add_group_teacher_form").serializeArray();
	var values = new Array();
	

	$.each(form_values, function(index,element){
		values.push(element.value);
	});
	
	$.ajax({
		url: "Teacher",
		type: "post",
		data: {
			action: "add_group",
			form_values: values
		},
		success: function(data){
			var output = jQuery.parseJSON(data);
			isUserLoggedIn(output);
			if(output.success == 1){
				popup("success","Pomyślnie dodano grupę");
			}
			else if(output.success == 2){
				popup("error","Hibernate error");
			}
			else if(output.success == 3){
				popup("error","Nieprawidłowy student");
			}
			
		}
	});
}

function makeProjectSelect(value){
	$.ajax({
		url: "Teacher",
		type: "post",
		data: {
			action: "project_type",
			type: value
		},
		success: function(data){
			var output = jQuery.parseJSON(data);
			isUserLoggedIn(output);
			$("#project_type_div").html(output.html);
		}
	});
}

function add_project(){
	var form_values = $("#new_project_form").serializeArray();
	var values = new Array();
	

	$.each(form_values, function(index,element){
		values.push(element.value);
	});
	
	$.ajax({
		url: "Teacher",
		type: "post",
		data: {
			action: "add_project",
			form_values: values
		},
		success: function(data){
			var output = jQuery.parseJSON(data);
			isUserLoggedIn(output);
			if(output.success == 1){
				popup("success","Pomyślnie dodano projekt");
			}
			else if(output.success == 2){
				popup("error","Zły format daty rozpoczęcia lub deadline");
			}
			else if(output.success == 3){
				popup("error","Nieprawidłowy student lub grupa lub przedmiot");
			}
			else if(output.success == 4){
				popup("error","Niepodano wszystkich danych");
			}
			
		}
	});
}

function generateManageProject(val){
	$.ajax({
		url: "Teacher",
		type: "post",
		data: {
			action: "manage_project",
			id: val
		},
		success: function(data){
			var output = jQuery.parseJSON(data);
			isUserLoggedIn(output);
			$("#manage_fields").html(output.html);
			$("#isDeadline").change(function(){
				if(!this.checked){
			    	$("#new_deadline").hide();
			    	$("#new_deadline").val(new Date().dateFormat("Y-m-d"));
				}
				else {
					$("#new_deadline").show();
				}
			});
			$("#new_deadline").datetimepicker({
				timepicker: false,
				lang: "pl",
				format:'Y-m-d'
			});
			$("#deadline").datetimepicker({
				timepicker: false,
				lang: "pl",
				format:'Y-m-d'
			});
		}
	});
}

function manage_project(){
	var form_values = $("#manage_project_form").serializeArray();
	var values = new Array();
	var students = new Array();
	

	$.each(form_values, function(index,element){
		if(element.name.indexOf("student") == -1)
			values.push(element.value);
		else
			students.push(element.value);
	});
	
	$.ajax({
		url: "Teacher",
		type: "post",
		data: {
			action: "confirm_manage_project",
			form_values: values,
			form_students: students
		},
		success: function(data){
			var output = jQuery.parseJSON(data);
			isUserLoggedIn(output);
			if(output.success == 1){
				popup("success","Pomyślnie edytowano projekt");
			}
			else if(output.success == 2){
				popup("error","Zły format deadline");
			}
		}
	});
}

function add_new_step(){
	$.ajax({
		url: "Teacher",
		type: "post",
		data: {
			action: "add_new_step"
		},
		success: function(data){
			var output = jQuery.parseJSON(data);
			isUserLoggedIn(output);
			$("#new_step_form").html(output.html);
		}
	});
}

function confirm_add_new_step(){
	var form_values = $("#new_step_form").serializeArray();
	var values = new Array();
	
	var number = $("#new_step_form").find("h4").text();
	var num = number.substring(number.lastIndexOf(" ")+1,number.length);
	values.push(num);
	$.each(form_values, function(index,element){
		values.push(element.value);
	});
	
	$.ajax({
		url: "Teacher",
		type: "post",
		data: {
			action: "confirm_add_new_step",
			form_values: values
		},
		success: function(data){
			var output = jQuery.parseJSON(data);
			isUserLoggedIn(output);
			if(output.success == 2){
				popup("error", "Podaj opis");
			}
			else if(output.success == 1){
				popup("success","Pomyślnie dodano etap");
			}
		}
	});
}

function finishStep(id){
	$.ajax({
		url: "Teacher",
		type: "post",
		data: {
			action: "finishStep",
			id: id
		},
		success: function(data){
			var output = jQuery.parseJSON(data);
			isUserLoggedIn(output);
			if(output.success == 1){
				popup("success", "Zakończono etap");
				var trs = $(".result_table").find("tr");
				$.each(trs,function(index,value){
					if($(this).find("td").first().text() == id){
						$(this).find("td:nth-child(3)").text("true");
						$(this).find("td:nth-child(4)").html("");
					}
					
				});
			}
			else if(output.success == 2){
				popup("error","Projekt nie jest przypisany do Ciebie");
			}
		}
	});
}