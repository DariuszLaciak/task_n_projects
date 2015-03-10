<%@page import="pl.edu.pk.laciak.DTO.Students"%>
<%@page import="pl.edu.pk.laciak.helpers.BorderStyle"%>
<%@page import="pl.edu.pk.laciak.DTO.Project"%>
<%@page import="pl.edu.pk.laciak.functions.Common"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<h1>Wersja projektu</h1>
<%!Project project;
	Students student;%>
<%
	project = (Project) session.getAttribute("selectedItem");
	student = (Students) session.getAttribute("userData");
%>
<%
	if (Common.getLastVersionOfProject(project) != null) {
%>
<%=Common.makeHeader(3, "Aktualna wersja: <i>"
						+ Common.getLastVersionOfProject(project).getVersion())
						+ "</i>"%>
<%=Common.makeHeader(4, "Zmiany:") %>
<%=Common.makeHeader(5, Common.getLastVersionOfProject(project).getChanges()) %>
<%=Common.makeHeader(4, "Autor: "+Common.getLastVersionOfProject(project).getStudent().getSurname()+
						" " +Common.getLastVersionOfProject(project).getStudent().getName()
						) %>
<%

	} else {
%>
<%=Common.makeHeader(3,
						"Nie ma jeszcze żadnej wersji projektu")%>
<%
	}
	if (project.getRepository() != null) {
%>
<%=Common.makeHeader(3, "Repozytorium ("
						+ project.getRepository().getType() + "): <span id='repoUrl'>"
						+ project.getRepository().getLink() + "</span>")%>
<%
	} else {
%>
<%=Common.makeHeader(3,
						"Nie ma jeszcze ustawionego repozytorium")%>
<%
	if (Common.isTeamLeader(project, student)) {
%>
<form id='addRepositoryForm' class='form_styles'>
	<%=Common.makeRadio("repoType", "git", "Git")%>
	<%=Common.makeRadio("repoType", "svn", "SVN")%>
	<%=Common.makeRadio("repoType", "other", "Inne")%>
	<%=Common.br(2)%>
	<%=Common.makeInputText("repoAddress", "URL",
							"http://")%>
	<%=Common.makeButton("Potwierdź",
							"confirmNewRepo()", "b_green")%>
</form>
<%=Common.insertSeparator("black", BorderStyle.SOLID,
						"left")%>
<%
	}
	}
	if (session.getAttribute("type").equals("student")) {
%>

<form id='addNewVersionForm' class='form_styles'>
	<%=Common.makeHeader(4, "Nowa wersja")%>
	<%=Common.makeInputText("versionNum", "Wersja", "")%>
	<%=Common.makeInputTextArea("versionChanges", "Zmiany",
						"")%>
	<%=Common.makeButton("Dodaj", "confirmNewVersion()",
						"b_green")%>
</form>
<%
	}
%>
