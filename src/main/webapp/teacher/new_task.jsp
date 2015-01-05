<%@page import="pl.edu.pk.laciak.DTO.Subject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="pl.edu.pk.laciak.hibernate.DBCommon"%>
<%@page import="java.util.List"%>
<%@page import="pl.edu.pk.laciak.DTO.Students"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h1>Dodaj nowe zadanie</h1>

<form id='new_task_form' class='form_styles'>
<%=Common.makeInputText("task_name", "Nazwa", "") %>
<% SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm"); %>
<%=Common.makeInputTextReadOnly("task_start", "Data rozpoczęcia", sdf.format(new Date())) %>
<%=Common.makeSelect("Student", "task_student", Common.makeSelectOptions("students")) %>
<%=Common.makeSelect("Przedmiot", "task_subject", Common.makeSelectOptions("subjects",session.getAttribute("userId").toString())) %>
<%=Common.makeCheckBoxSendUnchecked("Deadline", "isDeadline", "yes","no") %>
<%=Common.makeInputTextReadOnly("task_deadline", "", sdf.format(new Date())) %>
<%=Common.makeButton("Dodaj zadanie", "confirm_add_task()", "b_grey") %>
</form>

<script>
$(document).ready(function(){
	$("#task_start").datetimepicker({
		 format:'Y-m-d H:i',
		 lang: "pl",
		 step: 15
	});
	$("#isDeadline").change(function(){
		if(!this.checked){
	    	$("#task_deadline").hide();
	    	$("#task_deadline").val('<%=sdf.format(new Date())%>');
		}
		else {
			$("#task_deadline").show();
		}
	});
	$("#task_deadline").datetimepicker({
		 format:'Y-m-d H:i',
		 lang: "pl",
		 step: 15
	});
	
	
});
</script>