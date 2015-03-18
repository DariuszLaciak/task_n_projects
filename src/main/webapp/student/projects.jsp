<%@page import="pl.edu.pk.laciak.DTO.Students"%>
<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%=Common.makeHeader(1, "Statystyki projektów") %>
<% Students student = (Students)session.getAttribute("userData"); %>
<%=Common.createTable(Common.createTableProjects(student), Common.createTableProjectsHeaders(true)) %>