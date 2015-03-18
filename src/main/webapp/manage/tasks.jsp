<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@page import="pl.edu.pk.laciak.DTO.Project"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h1>Przydzielone zadania</h1>
<%
Project p = (Project) session.getAttribute("selectedItem");
if(session.getAttribute("type").equals("teacher")){
	 if(!Common.isProjectOrTaskFinished(session.getAttribute("selectedItem"))){ 
	%>
	<%=Common.makeButton("Dodaj zadanie", "add_new_projectTask()", "b_grey") %>
	<br /><br />
	<%
} else {
	out.println(Common.makeHeader(3, "Projekt zakończony"));
}
}
else {
	
}
%>
<form id='projectTaskForm' class='form_styles'></form>
<%
if(p.getTasks().isEmpty()){
%>
<%=Common.makeHeader(2, "Nie ma jeszcze przydzielonych zadań") %>
<% 
}
else {
	%>
	<%=Common.createTable(Common.createTableDataProjectTasks(p,session.getAttribute("type").toString()), Common.createTableDataProjectTaskHeaders()) %>
	<%
}
%>