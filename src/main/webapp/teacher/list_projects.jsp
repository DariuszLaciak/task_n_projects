<%@page import="pl.edu.pk.laciak.hibernate.DBCommon"%>
<%@page import="pl.edu.pk.laciak.DTO.Teachers"%>
<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%=Common.makeHeader(1, "Statystyki projektów") %>
<% Teachers teacher = (Teachers)session.getAttribute("userData"); 
DBCommon.refreshPojo(teacher);%>
<%=Common.createTable(Common.createTableProjects(teacher), Common.createTableProjectsHeaders(false)) %>