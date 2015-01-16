<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

	<div id='top_menu_left'>
	<div id='top_icons'>
	<% if(session.getAttribute("userData")!=null){ %>
	<img src='images/user_white.png'/>
	<% } %>
	</div>
	<div id='selected_feature'>
	<% if(session.getAttribute("userData")!=null){ %>
	Informacje
	<% } %>
	</div>
	</div>
    <img src='images/pnt1.png' id='logo_img'/>
    <div id='clock'></div>
    
    
    
<script type="text/javascript">
</script>
