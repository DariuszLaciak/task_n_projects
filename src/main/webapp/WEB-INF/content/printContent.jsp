<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%

if(session.getAttribute("userId") == null){
	%>
	<jsp:include page="printLogin.jsp"></jsp:include>
	<%
}
else {
	//if((String)session.getAttribute("type") == "student"){
		%>
		<jsp:include page="printPageUser.jsp"></jsp:include>
		<%
	//}
}
%>