<%@page import="pl.edu.pk.laciak.DTO.Task"%>
<%@page import="pl.edu.pk.laciak.DTO.Project"%>
<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h1>Oceny</h1>
<% 
boolean noted = false;
try{
	Project p = (Project)session.getAttribute("selectedItem");
	if(!Common.createTableDataNotes(p).isEmpty()){
		out.println(Common.createTable(Common.createTableDataNotes(p), Common.createTableDataNotesHeaders()));
	}
	else {
		out.println(Common.makeHeader(2, "Nie wystawiono jeszcze ocen"));
	}
}
catch(ClassCastException e){
	Task t = (Task)session.getAttribute("selectedItem");
	if(t.getNote() != null){
		out.println(Common.makeHeader(2, "Ocena wystawiona: "+t.getNote().getValue()));
		noted = true;
	}
	else {
		out.println(Common.makeHeader(2, "Jeszcze nie oceniono zadania"));
	}
}
if(session.getAttribute("type").equals("teacher") && !noted){
%><br /><br />
	<%=Common.makeButton("Wystaw nową", "addNote()", "b_grey") %>
	<form id='newNoteForm' class='form_styles'></form>
<%
}
%>