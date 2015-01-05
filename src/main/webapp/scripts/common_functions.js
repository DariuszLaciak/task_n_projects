function printLogo(){
	$("#logo").append("<span>PnT Manager</span>");
}
function makeLoading(on){

	if(!on){
		$("#loading_login").hide();
	}
	else {
		$("#loading_login").show();
	}
}

function progressBarUploadPhoto(){
	var reader;
	var progress = document.querySelector('.percent');

	function abortRead() {
		reader.abort();
	}

	function errorHandler(evt) {
		switch(evt.target.error.code) {
		case evt.target.error.NOT_FOUND_ERR:
			alert('File Not Found!');
			break;
		case evt.target.error.NOT_READABLE_ERR:
			alert('File is not readable');
			break;
		case evt.target.error.ABORT_ERR:
			break; // noop
		default:
			alert('An error occurred reading this file.');
		};
	}

	function updateProgress(evt) {
		// evt is an ProgressEvent.
		if (evt.lengthComputable) {
			var percentLoaded = Math.round((evt.loaded / evt.total) * 100);
			// Increase the progress bar length.
			if (percentLoaded < 100) {
				progress.style.width = percentLoaded + '%';
				progress.textContent = percentLoaded + '%';
			}
		}
	}

	function handleFileSelect(evt) {
		// Reset progress indicator on new file selection.
		progress.style.width = '0%';
		progress.textContent = '0%';

		reader = new FileReader();
		reader.onerror = errorHandler;
		reader.onprogress = updateProgress;
		reader.onabort = function(e) {

		};
		reader.onloadstart = function(e) {
			document.getElementById('progress_bar').className = 'loading';
			if(document.querySelector('.error')!=null)
				document.querySelector('.error').className = 'percent';
		};
		reader.onload = function(e) {
			// Ensure that the progress bar displays 100% at the end.
			progress.style.width = '100%';
			progress.textContent = 'Zdjęcie pomyślnie wgrane';

			if (!evt.target.files[0].type.match('image.*')) {
				progress.textContent = 'Złe rozszrzenie pliku';
				document.querySelector('.percent').className = 'error';
			}
		}
		// Read in the image file as a binary string.
		reader.readAsDataURL(evt.target.files[0]);
	}
	document.getElementById('photo_upload').addEventListener('change', handleFileSelect, false);
	$("#photo_upload").ajaxfileupload({

		'action' : 'FileUpload',
		'onComplete' : function(response) {
			popup("success","Zdjęcie wgrane pomyślnie");
		},
		'onStart' : function() {

		}
	});
}

function saveProfile(){



	var data_form = $("form[name='edit_profile_form']").serializeArray();
	var values = new Array();

	$.each(data_form, function(index,element){
		values.push(element.value);
	});

	$.ajax({
		url: 'User',
		type: 'post',
		async: false,
		data: {
			action: "saveProfile",
			data_form: values
		},
		success: function(data){
			var output = jQuery.parseJSON(data);
			isUserLoggedIn(output);
			if(output.edited == 1){
				popup("success","Profil zapisany");
				$("#edit_profile").dialog("close");
			}
			else {
				popup("error","Problem z bazą danych. Skontaktuj się z administratorem systemu");
			}

		}
	});
}

function popup(type,text){
	if($("body").find("#popup").length ==0){
		$("body").append("<div id='popup' class='"+type+"'>"+text+"</div>");
		$("#popup").hide();
	}
	else {
		$("#popup").attr("class",type);
		$("#popup").text(text);
	}
	$("#popup").fadeIn(1000);
	setTimeout(function(){
		$("#popup").fadeOut(1000);
	}, 3000);
}

function isUserLoggedIn(data){
	if(data.error == 'logged_out'){
		popup("error","Sesja wygasła. Zaloguj się ponownie");
		setTimeout(function(){
			location.reload();
		}, 4000);
	}
}

function insertDatePicker(id){
	$("#"+id).datepicker({
		dateFormat: "yy-mm-dd 00:00:00"
	});
}

function readFileFromInput(callback,id)
{               
	var return_s ="";
	if (!window.File || !window.FileReader || !window.FileList || !window.Blob) {
		alert('The File APIs are not fully supported in this browser.');
		return;
	}   

	input = document.getElementById(id);
	if (!input) {
		alert("Um, couldn't find the fileinput element.");
	}
	else if (!input.files) {
		alert("This browser doesn't seem to support the `files` property of file inputs.");
	}
	else if (!input.files[0]) {
		alert("Please select a file before clicking 'Load'");               
	}
	else {
		file = input.files[0];
		fr = new FileReader();
		//fr.readAsText(file);
		fr.onload = function (e) {
			return_s = e.target.result;
			callback(return_s);
		};
	
		fr.readAsText(file);
		
		
	}
	return return_s;
}

function nextSelect(form_id){
	var lastDiv = $("#"+form_id).find(".inputs:last");
	var lastSelect = lastDiv.find("select");
	var lastId = lastSelect.attr("id");
	var lastCount = parseInt(lastId.substring(lastId.lastIndexOf("_")+1,lastId.length));
	var lastLabelCount = parseInt(lastDiv.children("label").text());
	var pattern = lastId.substring(0,lastId.lastIndexOf("_")+1);
	var nextSelect = lastDiv.clone();
	
	nextSelect.find("select").attr("id",pattern+(lastCount+1));
	nextSelect.find("select").attr("name",pattern+(lastCount+1));
	if(nextSelect.find("button").length == 0)
		nextSelect.find("select").before("<button class='smallButton b_red' type='button' onclick='delete_select(\""+pattern+(lastCount+1)+"\")'>Usuń</button>");
	else
		nextSelect.find("button").attr("onclick","delete_select(\""+pattern+(lastCount+1)+"\")");
	nextSelect.find("label").text(lastLabelCount+1);
	
	lastDiv.after(nextSelect);
	/*var next = jQuery.parseJSON(nextSelect);
	lastSelect.after(next.select);*/
}

function delete_select(id){
	var thisDiv = $("#"+id).parent();
	var lastId = parseInt(thisDiv.children("label").text());
	var nextSelects = thisDiv.nextAll("div");
	nextSelects.each(function(){
		lastId = parseInt($(this).children("label").text());
		$(this).children("label").text(lastId-1);
	});
	thisDiv.remove();
}

