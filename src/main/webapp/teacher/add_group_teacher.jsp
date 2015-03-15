<%@page import="org.json.simple.JSONObject"%>
<%@page import="pl.edu.pk.laciak.DTO.Students"%>
<%@page import="java.util.ArrayList"%>
<%@page import="pl.edu.pk.laciak.hibernate.DBCommon"%>
<%@page import="java.util.List"%>
<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%=Common.makeHeader(1, "Dodawanie nowej grupy projektowej") %>
<form id="add_group_teacher_form" class='form_styles'>
<%=Common.makeInputText("group_name", "Nazwa", "") %>
<%=Common.makeCheckBoxSendUnchecked("Wyznaczyć leadera?", "groupLeader", "yes", "no") %>
<div id='leaderInfo'><i>Leaderem jest pierwszy ze studentów w grupie</i></div>
<%=Common.makeHeader(4, "Lista studentów") %>
<%=Common.makeInputPatternSelect("student", 1, Common.makeSelectOptions("students")) %>
<%=Common.makeButton("Kolejny student", "nextSelect(\"add_group_teacher_form\")", "b_green") %>
<%=Common.br(2) %>
<%=Common.makeButton("Dodaj grupę", "confirm_add_group()", "b_grey") %>
</form>

<script>
$(document).ready(function(){
	$("#groupLeader").click(function(){
		 $("#leaderInfo").toggle(this.checked)
	})
});
</script>