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