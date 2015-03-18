<%@page import="pl.edu.pk.laciak.DTO.Students"%>
<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%! Students s; %>
<%=Common.makeHeader(1, "Statystyki przedmiotów") %>
<% s = (Students) session.getAttribute("userData"); %>
<%=Common.createTable(Common.createTableSubjectsStudent(s), Common.createTableSubjectsStudentHeaders()) %>