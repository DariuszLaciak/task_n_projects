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

function uploadNewFile(){
//	var file = $("#newFileObj")[0];
//	var comment = $("#newFileComment").val();
//	var formData = new FormData();
//	formData.append("upload",file.files[0],file.files[0].filename);
//	formData.append("newFileComment",comment);
	
}