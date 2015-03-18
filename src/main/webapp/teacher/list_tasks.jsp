<%@page import="pl.edu.pk.laciak.DTO.Teachers"%>
<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%=Common.makeHeader(1, "Statystyki zadań") %>
<% Teachers teacher = (Teachers)session.getAttribute("userData"); %>
<%=Common.createTable(Common.createTableTasks(teacher), Common.createTableTasksHeaders(false)) %>