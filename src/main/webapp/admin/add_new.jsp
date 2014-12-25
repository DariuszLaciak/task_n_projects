<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="pl.edu.pk.laciak.functions.Common"%>
<h1>Dodawanie nowych użytkowników</h1>
<form id='add_new_form'>
	Grupowo z pliku <input type='radio' name='add_new_type' value='group'><br />
	Pojedynczy użytkownik <input type='radio' name='add_new_type' value='indiv'><br />
	<%=Common.makeButton("Zatwierdź", "add_new_t()", "b_blue")%>
	<br /><br />
</form>
