<%@page import="java.util.Date"%>
<%@page import="pl.edu.pk.laciak.functions.FTPCommon"%>
<%@page import="java.net.URI"%>
<%@page import="java.io.File"%>
<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@page import="java.util.Properties" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%!File file;%>

<div id='userpanel'>
	<div id='user_photo'>
		<%	
			file = FTPCommon.getPhoto(Integer.parseInt(session.getAttribute("userId").toString()));
			if (file!=null) {
		%>
		<img
			src="displayPhoto?<%=new Date().getTime() %>" />
		<%
			} else {
		%>
		<img src="images/user.png" />
		<%
			}
		%>
	</div>
	<div id='user_right'>
		<div id='user_name'>
			<span><%=session.getAttribute("user_name")%></span>
		</div>
		<div id='user_buttons'>
			<%=Common.makeButton("Profil", "openEditProfileWindow()", "b_grey")%><br />
			<%=Common.makeButton("Wyloguj", "logout()", "b_grey")%>
		</div>
	</div>
</div>