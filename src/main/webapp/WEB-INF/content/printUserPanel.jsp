<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

    <div id='userpanel'>
    <p>Witaj <%=session.getAttribute("user_name") %> </p>
    <p>Jestes zalogowany jako <%=session.getAttribute("type") %></p>
    <%=Common.makeButton("Logout","logout()") %>
    </div>