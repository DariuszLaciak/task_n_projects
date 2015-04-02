<%@page import="pl.edu.pk.laciak.DTO.Students"%>
<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%=Common.makeHeader(1, "Statystyki projektów") %>
<% Students student = (Students)session.getAttribute("userData"); %>
<%if(Common.doesStudentHaveProjects(student)){ %>
<%=Common.createTable(Common.createTableProjects(student), Common.createTableProjectsHeaders(true)) %>
<% } else { %>
<%=Common.makeHeader(3, "Nie masz żadnych projektów") %>
<% } %>