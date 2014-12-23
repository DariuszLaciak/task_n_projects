<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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