<%@page import="pl.edu.pk.laciak.DTO.Teachers"%>
<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% Teachers teacher = (Teachers)session.getAttribute("userData"); %>
<%=Common.makeHeader(1, "Lista grup projektowych") %>
<%=Common.createTable(Common.createTableGroups(teacher), Common.createTableGroupsHeaders()) %>