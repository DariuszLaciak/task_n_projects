<%@page import="java.io.File"%>
<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@page import="java.util.Properties" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%!File file;%>

<div id='userpanel'>
	<div id='user_photo'>
		<%	
			file = new File(Common.getPhotoUrl(request.getSession().getAttribute("userId").toString()));
			if (file.exists()) {
		%>
		<img
			src="images/users/<%=session.getAttribute("userId")%>/photo.jpg" />
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
			<%=Common.makeButton("Profil", "openEditProfileWindow()", "b_blue")%><br />
			<%=Common.makeButton("Wyloguj", "logout()", "b_blue")%>
		</div>
	</div>
</div>