<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="pl.edu.pk.laciak.functions.Common"%>
<div id="login">
	<div id="login_form">
		<form name='login_form'>

			<%=Common.makeHeader(1, "Log in")%>
			<br />
			<%=Common.makeInputText("username", "Username", "")%>
			<%=Common.makeInputPassword("password", "Password")%>
			<%=Common.makeButton("Login", "log()")%>
			<div id='loading_login'></div>
		</form>
		<br />
		<div id='response_login'></div>
	</div>
	<div id="login_form_buttons"></div>
</div>