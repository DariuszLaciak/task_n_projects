<%@page import="pl.edu.pk.laciak.DTO.Task"%>
<%@page import="pl.edu.pk.laciak.DTO.Project"%>
<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@page import="pl.edu.pk.laciak.DTO.Teachers"%>
<%@page import="pl.edu.pk.laciak.DTO.Students"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h1>Ewidencja relizacji</h1>
<% 
try{
	Project p = (Project) session.getAttribute("selectedItem");
if(session.getAttribute("type").equals("student")){
	Students student = (Students)session.getAttribute("userData");  

}
else if(session.getAttribute("type").equals("teacher")){
	Teachers teacher = (Teachers)session.getAttribute("userData");  
	
	
	%>
	<button class='button b_grey' onclick='add_new_step()'>Dodaj nowy etap</button>
	<br /><br />
	<form id='new_step_form' class= 'form_styles'></form>
	
	<%
}


if(!p.getSteps().isEmpty())
	out.println(Common.createTable(Common.createTableDataProjectSteps(p,session.getAttribute("type").toString()), Common.createTableDataProjectStepsHeaders()));
}
catch(ClassCastException e){
	Task t = (Task) session.getAttribute("selectedItem");
	if(session.getAttribute("type").equals("teacher")){
		if(!t.isFinished()){
			out.println(Common.makeHeader(2, "Zakończyć zadanie?"));
			out.println(Common.makeButton("Zakończ", "finishTask()", "smallButton b_green"));
		}
		else {
			out.println(Common.makeHeader(2, "Zadanie jest już zakończone"));
		}
	}
	else {
		if(t.isFinished())
			out.println(Common.makeHeader(2, "Zadanie jest już zakończone"));
		else
			out.println(Common.makeHeader(2, "Zadanie nie jest jeszcze zakończone"));
	}
}

%>
