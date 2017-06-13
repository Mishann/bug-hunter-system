function transferTextFromTextareaToInput(){
	var textAreaText = document.getElementById("textareaAbout").value;
	var input = document.getElementById("inputAbout");
	
	input.value = textAreaText.replace(/\n\r?/g, '<br />');
}


function callUploadFileInput(){
	var input = document.getElementById("upload-file-input");
	input.click();
}


//
//$(document).ready(function() {
//    $("#upload-file-input").on("change", uploadFile);
//  });
//  
//  /**
//   * Upload the file sending it via Ajax at the Spring Boot server.
//   */
//  function uploadFile() {
//    $.ajax({
//      url: "/uploadImage",
//      type: "POST",
//      data: new FormData($("#upload-file-form")[0]),
//      enctype: 'multipart/form-data',
//      processData: false,
//      contentType: false,
//      cache: false,
//      success: function () {
//        $("#upload-file-message").text("File succesfully uploaded");
//      },
//      error: function () {
//        // Handle upload error
//        $("#upload-file-message").text(
//            "File not uploaded (perhaps it's too much big)");
//      }
//    });
//  } // function uploadFile