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