<%@page import="java.util.ArrayList"%>
<%@page import="pl.edu.pk.laciak.hibernate.DBCommon"%>
<%@page import="pl.edu.pk.laciak.DTO.Teachers"%>
<%@page import="java.util.List"%>
<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%!  %>
<h1>Dodawanie nowego przedmiotu</h1>
<form id='new_subject_form'>
<%=Common.makeInputText("sub_name", "Nazwa", "") %>
<% List<Teachers> lista = DBCommon.getTeachers();
List<String[]> t = new ArrayList<String[]>();
String[] teachers = new String[2];
for(Teachers t1 : lista){
	teachers = new String[2];
	teachers[0] = Long.toString(t1.getId());
	teachers[1] = t1.getName() + " " + t1.getSurname();
	t.add(teachers);
}
%>
<%=Common.makeSelect("Prowadzący","teacher_list", t) %>
<%=Common.br(2) %>
<%=Common.makeButton("Dodaj", "confirm_n_s()", "b_blue") %>
</form>