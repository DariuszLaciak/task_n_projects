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
		async: false,
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