<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h1>Zarządzanie projektem</h1>
<form id='manage_project_form' class='form_styles'>
<%=Common.makeSelect("Wybierz projekt", "project_man", Common.makeSelectOptions("projects_teacher",session.getAttribute("userId").toString())) %>
<div id='manage_fields'></div>
</form>

<script>
$(document).ready(function(){
	$("#project_man").change(function(){
		if($(this).val() == 0){
			$("#manage_fields").html("");
		}
		else {
			generateManageProject($(this).val());
			$("#deadline").datetimepicker({
				timepicker: false,
				lang: "pl",
				format:'Y-m-d'
			});
		}
	});
});
</script>