<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); %>
<h1>Dodaj nowy projekt</h1>
<form id='new_project_form' class='form_styles'>
<%=Common.makeInputText("project_name", "Nazwa", "") %>
<%=Common.makeInputTextReadOnly("project_start", "Data rozpoczęcia", sdf.format(new Date())) %>
<%=Common.makeSelect("Przedmiot", "project_subject", Common.makeSelectOptions("subjects",session.getAttribute("userId").toString())) %>
<%=Common.makeCheckBoxSendUnchecked("Deadline", "isDeadline", "yes","no") %>
<%=Common.makeInputTextReadOnly("project_deadline", "", sdf.format(new Date())) %>
<%=Common.makeInputTextArea("project_text", "Opis(opcjonalne)", "") %>
<h4>Typ</h4>
<%=Common.makeRadio("project_type", "indiv", "Indywidualny") %>
<%=Common.makeRadio("project_type", "group", "Zespołowy") %>

<div id='project_type_div'></div>

</form>

<script>
$(document).ready(function(){
	$("#new_project_form input[name='project_type']").change(function(){
		makeProjectSelect($(this).val());
	});
	
	$("#isDeadline").change(function(){
		if(!this.checked){
	    	$("#project_deadline").hide();
	    	$("#project_deadline").val('<%=sdf.format(new Date())%>');
		}
		else {
			$("#project_deadline").show();
		}
	});
	$("#project_start").datetimepicker({
		 format:'Y-m-d',
		 lang: "pl",
		 timepicker: false
	});
	$("#project_deadline").datetimepicker({
		 format:'Y-m-d',
		 lang: "pl",
		 timepicker: false
	});
});
</script>