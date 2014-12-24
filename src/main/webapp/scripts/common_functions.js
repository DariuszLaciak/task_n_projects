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
			
		  action : 'FileUpload',
		  onComplete : function(response) {
		      var statusVal = JSON.stringify(response.status);
		},
		'onStart' : function() {
		        
		}
		});
}

function saveProfile(){

	
	
	var data_form = $("form[name='edit_profile_form']").serializeArray();
	
	
	$.ajax({
		url: 'User',
		type: 'post',
		async: false,
		data: {
			action: "saveProfile",
			data_form: data_form
		},
		success: function(){
			alert("co");
			

		}
	});
}


