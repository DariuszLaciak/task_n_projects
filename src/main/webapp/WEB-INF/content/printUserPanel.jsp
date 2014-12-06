<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<div id='userpanel'>
	<div id='user_photo'>
		<img src="images/user.png" />
	</div>
	<div id='user_right'>
		<div id='user_name'><span><%=session.getAttribute("user_name") %></span></div>
		<div id='user_buttons'>
			<%=Common.makeButton("Profil","edit_profile()","b_blue") %><br />
			<%=Common.makeButton("Wyloguj","logout()","b_blue") %>
		</div>
	</div>
</div>