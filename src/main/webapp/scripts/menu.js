$(document).ready(function() {
	var elems = $(".mmenu").find("a");
	$.each(elems,function(){
		$(this).click(function(){
			$(this).next().slideToggle(500);
		});
	});
	
	if($("#selected_menu").html() != ""){
		$("#selected_menu").show();
	}
	
	var menus = $("#selected_menu");
	
	
	$('#user_name').textfill({ maxFontPixels: 25 });
	
	$("#toggleSelect").click(function(){
		if ($('#selectItem').is(':visible')){
			$("#selectItem").slideUp();
		}
		else{
			$.ajax({
				url: "User",
				type: "POST",
				data: {
					action: "selectItem"
				},
				success: function(data){
					var obj = $.parseJSON(data);
					isUserLoggedIn(obj);
					$("#selectItem").text("");
					$("#selectItem").empty();
					$("#selectItem").html(obj.html);
					$("#selectItem").slideDown();
				}
			});
		}
		
	});
});

function selectItem(type,id){
	
	$.ajax({
		url: "User",
		type: "POST",
		data: {
			action: "confirmSelectItem",
			type: type,
			id: id
		},
		success: function(data){
			var obj = $.parseJSON(data);
			isUserLoggedIn(obj);
			if(obj.success == 1){
				$("#m_content").html("Wybrano aktywność");
				$("#toggleSelect").html("Wybrana aktywność: <span id='activ_name'>"+obj.name+"</span>");
				$("#selected_menu").html(obj.menuToCreate);
				var menus = $("#selected_menu").find("a");
				$.each(menus,function(){
					$(this).click(function(){
						$(this).next().slideToggle(500);
					});
				});
				$("#selected_menu").show();
				$(document).ready(function(){
					$("#teacher_activ_look").click(function(){
						$("#m_content").load("manage/look.jsp");
					});
					$("#teacher_activ_files").click(function(){
						$("#m_content").load("manage/files.jsp");
					});
					$("#teacher_activ_comment").click(function(){
						$("#m_content").load("manage/comment.jsp");
					});
					$("#teacher_activ_tasks").click(function(){
						$("#m_content").load("manage/tasks.jsp");
					});
					$("#teacher_activ_notes").click(function(){
						$("#m_content").load("manage/notes.jsp");
					});
					$("#teacher_activ_version").click(function(){
						$("#m_content").load("manage/version.jsp");
					});
				});
				$("#selectItem").slideUp(500);
			}
			else if(obj.success == 2){
				popup("error", "Nie jestes przypisany/a do tego projektu/zadania");
			}
		}
	});
}
