<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<div id="menu">
<h3>Menu: </h3>
<%=Common.makeMenu(session.getAttribute("type").toString()) %>
</div>