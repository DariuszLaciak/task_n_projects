<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<div id='top_menu_left'>
	<% if(session.getAttribute("userData")!=null){ %>
	<div id='top_icons'>
	Powiadomienia
	</div>
	<div id='selected_feature'>
	<% if(session.getAttribute("selectedItem")==null && !session.getAttribute("type").equals("admin")){ %>
	<div id='toggleSelect'>Wybierz projekt lub zadanie</div>
	<div id='selectItem'></div>
	<% } else {%>
	<div id='toggleSelect'>Wybrana aktywność: <span id='activ_name'><%=session.getAttribute("selectedItemName") %></span></div>
	<div id='selectItem'></div>
	<% } %>
	</div>
	<% } %>
	</div>
    <img src='images/pnt1.png' id='logo_img'/>
    <div id='clock'></div>
<script type="text/javascript">
</script>
