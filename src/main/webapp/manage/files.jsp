<%@page import="pl.edu.pk.laciak.DTO.Task"%>
<%@page import="pl.edu.pk.laciak.DTO.Project"%>
<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%! Project proj = null;
	Task task = null;
	boolean areFiles = false;%>
<h1>Załączone pliki</h1>
<% if(session.getAttribute("type").equals("student")){ %>
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
<%} 
if(session.getAttribute("selectedItem") instanceof Project){
	proj = (Project) session.getAttribute("selectedItem");
	if(!proj.getFiles().isEmpty())
		areFiles = true;
}
else {
	task = (Task) session.getAttribute("selectedItem");
	if(!task.getFiles().isEmpty())
		areFiles = true;
}
out.println(Common.br(1));
if(areFiles){
%>
<%=Common.createTable(Common.createTableFiles(proj, task), Common.createTableFilesHeaders()) %>

<% }
else {%>
<%=Common.makeHeader(3, "Nie dodano jeszcze żadnych plików") %>
<% } %>