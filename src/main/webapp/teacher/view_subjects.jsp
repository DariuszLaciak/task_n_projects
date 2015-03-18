<%@page import="pl.edu.pk.laciak.DTO.Teachers"%>
<%@page import="pl.edu.pk.laciak.hibernate.DBCommon"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="pl.edu.pk.laciak.DTO.Subject"%>
<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%=Common.makeHeader(1, "Statystyki przedmiotów") %>
<% Teachers t = (Teachers) session.getAttribute("userData"); %>
<%=Common.createTable(Common.createTableSubjectsTeacher(t), Common.createTableSubjectsTeacherHeaders()) %>