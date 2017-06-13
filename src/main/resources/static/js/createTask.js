$( "#targetTaskType" ).change(function() {
  
	var openedSection = document.getElementById("openedQuestion");
	var closedSection = document.getElementById("closedQuestion");

	if( this.value == "Відкрите"){
		openedSection.style.display = "block";
		closedSection.style.display = "none";
	} else{
		openedSection.style.display = "none";
		closedSection.style.display = "block";
	}	
});

function transferTextFromTextareaToInput(){
	var textAreaDescription = document.getElementById("textareaDescription")
	var inputDescription = document.getElementById("inputDescription");
	
	var textAreaAnswer = document.getElementById("textareaAnswer");
	var inputAnswer = document.getElementById("inputAnswer");

	inputDescription.value =	textAreaDescription.value.replace(/\n\r?/g, '<br />');
	inputAnswer.value  = textAreaAnswer.value.replace(/\n\r?/g, '<br />');		
}

$(document).ready(function() {
	$("#fileDcrpt").change( function(e) {
		var fileName = '';
		if (e.files && e.files.length > 1)
			fileName = (e.getAttribute('data-multiple-caption') || '')
						.replace('{count}',this.files.length);
		else
			fileName = e.target.value.split('\\').pop();
		if (fileName)
			$("#labelForFileDcrpt").text(fileName);
		else
			label.innerHTML = "Додати файл";
								});

	$("#fileAnswer").change( function(e) {
		var fileName = '';
		if (e.files && e.files.length > 1)
			fileName = (e.getAttribute('data-multiple-caption') || '')
						.replace('{count}',this.files.length);
		else
			fileName = e.target.value.split('\\').pop();
		if (fileName)
			$("#labelForFileAnswer").text(fileName);
		else
			label.innerHTML = "Додати файл";
								});

				

				
	$("form").submit(function(e){
		alert("Завдання додано");
	})			
				
			});




