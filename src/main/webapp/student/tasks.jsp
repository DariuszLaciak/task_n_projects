<%@page import="pl.edu.pk.laciak.DTO.Students"%>
<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%=Common.makeHeader(1, "Statystyki zadań") %>
<% Students student = (Students)session.getAttribute("userData"); 
if(!student.getTasks().isEmpty()){%>
<%=Common.createTable(Common.createTableTasks(student), Common.createTableTasksHeaders(true)) %>
<% } 
else {%>
<%=Common.makeHeader(3, "Nie masz żadnych zadań") %>
<% }%>