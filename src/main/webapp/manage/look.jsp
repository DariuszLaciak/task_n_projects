<%@page import="pl.edu.pk.laciak.DTO.Project"%>
<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@page import="pl.edu.pk.laciak.DTO.Teachers"%>
<%@page import="pl.edu.pk.laciak.DTO.Students"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<h1>Ewidencja relizacji</h1>
<% 
if(session.getAttribute("type").equals("student")){
	Students student = (Students)session.getAttribute("userData");  

}
else if(session.getAttribute("type").equals("teacher")){
	Teachers teacher = (Teachers)session.getAttribute("userData");  
	%>
	<button class='button b_grey' onclick='add_new_step()'>Dodaj nowy etap</button>
	<form id='new_step_form' class= 'form_styles'></form>
	
	<%
}
Project p = (Project) session.getAttribute("selectedItem");
if(!p.getSteps().isEmpty())
	out.println(Common.createTable(Common.createTableDataProjectSteps(p), Common.createTableDataProjectStepsHeaders()));
%>
