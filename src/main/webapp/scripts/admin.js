/**
 * 
 */

$(document).ready(function(){
	$("#add_new").click(function(){
		$("#m_content").load("admin/add_new.jsp");
	});
	$("#add_new_subject").click(function(){
		$("#m_content").load("admin/new_subject.jsp");
	});
});

function confirm_n_s(){
	var name = $("#sub_name").val();
	var teacher = $("#teacher_list").val();
	$.ajax({
		url: "Admin",
		type: "post",
		async: false,
		data: {
			action: "add_new_subject",
			name: name,
			teacher: teacher
		},
		success: function(data){
			var output = jQuery.parseJSON(data);
			isUserLoggedIn(output);
			if(output.success == 1){
				popup("success","Pomyślnie dodano przedmiot");
			}
			else {
				popup("error","Problem z bazą danych");
			}
		}
	});
}


function add_new_t(){
	
	var radio_type = $("#add_new_form input:checked").val();
	if($("#form_add").length == 0)
		$("#m_content").append("<div id='form_add' class='form_styles'>");
	else
		$("#form_add").html("");
	
	if(radio_type != undefined){
		$.ajax({
			url: "Admin",
			type: "post",
			async: false,
			data: {
				action: "add_new_form_type",
				type: radio_type
			},
			success: function(data){
				var output = jQuery.parseJSON(data);
				isUserLoggedIn(output);
				if(output.success == 1){
					$("#form_add").html(output.form);
					insertDatePicker("new_birthday");
				}
			}
		});
	}
	else {
		popup("error","Wybierz jedną z opcji");
	}
}

function confirm_add(){
	var radio_type = $("#add_new_form input:checked").val();
	
	
	
	if(radio_type == "indiv"){
		var form_values = $("#new_user").serializeArray();
		var values = new Array();
		

		$.each(form_values, function(index,element){
			if(element.name != "add_new_type"){
				values.push(element.value);
			}
			
		});
		$.ajax({
			url: "Admin",
			type: "post",
			async: false,
			data: {
				action: "add_indiv",
				form_data: values
			},
			success: function(data){
				var output = jQuery.parseJSON(data);
				isUserLoggedIn(output);
				if(output.success == 1){
					popup("success","Użytkownik pomyślnie dodany");
				}
				else if(output.success == 3){
					popup("error","Zły format daty");
				}
				else if(output.success == 4){
					popup("error","Zły pesel lub numer semestru");
				}
				else if(output.success == 6){
					popup("error","Użytkownik już istnieje");
				}
			}
		});
	}
	else {
		function receivedText(file_content) {
			$.ajax({
				url: "Admin",
				type: "post",
				async: false,
				data: {
					action: "add_group",
					form_data: file_content
				},
				success: function(data){
					var output = jQuery.parseJSON(data);
					isUserLoggedIn(output);
					if(output.success == 1){
						popup("success","Grupa użytkowników pomyślnie dodana");
						$("#form_add").append("<h3 style='color: green'>Dodano: "+output.added+" użytkowników</h3>")
					}
					else if(output.success == 2){
						popup("error","Nieprawidłowy format pliku - zły typ użytkownika [Linijka "+output.user_no+"]");
					}
					else if(output.success == 3){
						popup("error","Nieprawidłowy format pliku - zły format daty [Linijka "+output.user_no+"]");
					}
					else if(output.success == 4){
						popup("error","Nieprawidłowy format pliku - zły pesel lub numer semestru [Linijka "+output.user_no+"]");
					}
					else if(output.success == 5){
						popup("error","Nieprawidłowy format pliku - zła liczba parametrów użytkownika [Linijka "+output.user_no+"]");
					}
					else if(output.success == 6){
						popup("error","Błąd danych - Użytkownik już istnieje [Linijka "+output.user_no+"]");
					}
				}
			});
		 }
		readFileFromInput(receivedText,"new_group_users");
	}
	
}

function student_form(){
	
	if($("input[name=new_type]:checked").val() == 'student'){
		$("#form_add button").before("<div class='inputs student_input'><label class='l_input'>Numer indeksu:</label><input type='text' id='student_index' name='student_index' size='30'></input></div>")
		$("#form_add button").before("<div class='inputs student_input'><label class='l_input'>Numer semestru:</label><input type='text' id='student_period' name='student_period' size='30'></input></div>")
		$("#form_add button").before("<div class='inputs student_input'><label class='l_input'>Grupa akademicka:</label><input type='text' id='student_group' name='student_group' size='30'></input></div>")
	}
	else {
		$(".student_input").remove();
	}
}


