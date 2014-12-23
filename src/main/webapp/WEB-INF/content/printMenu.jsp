<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="menu">
<h3>Menu: </h3>
<%=Common.makeMenu(session.getAttribute("type").toString()) %>
</div>