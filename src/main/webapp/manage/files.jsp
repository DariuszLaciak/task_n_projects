<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h1>Załączone pliki</h1>
<form id='newFileForm' class='form_styles'>
<%=Common.makeUploadFile("newFileObj") %>
<%=Common.makeInputTextArea("newFileComment", "Opis", "") %>
<%=Common.makeButton("Dodaj plik", "uploadNewFile()", "b_green") %>
</form>
<script>
$(document).ready(function(){
	$("#newFileObj").ajaxfileupload({
		'action' : 'NewFileServlet',
		'validate_extensions' : false,
		'onComplete' : function(response) {

			var success = response.success;
			if(success == 1){
				popup("success","Plik OK. Dodaj komentarz i potwierdź wrzucenie");
			}
			else if(success == 2){
				popup("error","Zły lub uszkodzony plik. Wybierz inny");
			}
		}
	});
});
</script>
