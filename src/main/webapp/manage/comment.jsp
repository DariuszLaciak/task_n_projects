<%@page import="pl.edu.pk.laciak.DTO.Task"%>
<%@page import="pl.edu.pk.laciak.DTO.Project"%>
<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%! Project proj=null;
	Task task=null;
	boolean noComment = true;%>
<h1>Komentarze</h1>
<% if(!Common.isProjectOrTaskFinished(session.getAttribute("selectedItem"))){ %>
<form id='newCommentForm' class='form_styles'>
<%=Common.makeInputTextArea("newCommentText", "Treść", "") %>
<%=Common.br(1) %>
<%=Common.makeButton("Dodaj nowy komentarz", "newComment()", "b_green") %>
</form>
<% 
}
else {
	%>
	<%=Common.makeHeader(3, "Aktywonść zakończona") %>
	<%
}
noComment = true;
if(session.getAttribute("selectedItemType").equals("t")){
	task = (Task)session.getAttribute("selectedItem");
	if(!task.getComment().isEmpty()){
		noComment = false;
	}
}
else {
	proj = (Project) session.getAttribute("selectedItem");
	if(!proj.getComment().isEmpty()){
		noComment = false;
	}
}

if(!noComment){
	%>
<%=Common.createTable(Common.createTableComments(proj, task), Common.createTableCommentsHeaders()) %>

<% }
else {
	out.println(Common.makeHeader(3, "Nie ma jeszcze komentarzy"));
}
%>