<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="pl.edu.pk.laciak.functions.Common"%>
<div id="login">
	<div id="login_form">
		<form name='login_form' action='#' onsubmit="log()">

			<%=Common.makeHeader(1, "Zaloguj sie")%>
			<br />
			<%=Common.makeInputText("username", "Login", "")%>
			<%=Common.makeInputPassword("password", "Haslo")%>
			<input type='submit' class='b_blue' value='Zaloguj' />
			<div id='loading_login'></div>
		</form>
		<br />
		<div id='response_login'></div>
	</div>
	<div id="login_form_buttons"></div>
</div>