<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="pl.edu.pk.laciak.DTO.Subject"%>
<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%! Subject s; 
List<Subject> lista;%>
<h1>Lista prowadzonych przedmiotów: </h1>
<% lista = new ArrayList<Subject>();
lista.add(new Subject("nowy")); %>
<%=Common.createSubjectTable(lista) %>
