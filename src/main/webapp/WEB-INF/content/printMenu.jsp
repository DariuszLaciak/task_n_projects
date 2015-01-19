<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="menu">
<h3>Menu: </h3>
<div id='selected_menu'>
<% if(session.getAttribute("selectedItem")!= null && !session.getAttribute("type").equals("admin")){ %>
<%=Common.makeMenu(session.getAttribute("type").toString(),session.getAttribute("selectedItemType").toString()) %>
<% } %>
</div>
<%=Common.makeMenu(session.getAttribute("type").toString(),"normal") %>
</div>