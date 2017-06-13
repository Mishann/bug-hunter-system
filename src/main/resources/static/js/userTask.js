$(document).ready(function() {
	
		var variantRadio = document.getElementsByName("variant")[0];
		var type = document.getElementById("type").innerText;

		var closedQuestion = document.getElementById("closedQuestion");
		var openedQuestion = document.getElementById("openedQuestion");

					$("#solutionTab").css("visibility", "visible");

					if (type == "Відкрите") {

						closedQuestion.style.display = "none"
						openedQuestion.style.display = "block";
					} else {
						closedQuestion.style.display = "block"
						openedQuestion.style.display = "none";

					}
					$("form").submit(
							function(e) {

								// Кнопка відправити зникає
								$(".btn btn-primary pull-right").css("display",
										"none");

								// Правильна відповідь стає доступною
								$("#solutionTab").css("visibility", "visible");

							});

					$("#file").change( function(e) {
										var fileName = '';
										if (e.files && e.files.length > 1)
											fileName = (e
													.getAttribute('data-multiple-caption') || '')
													.replace('{count}',
															this.files.length);
										else
											fileName = e.target.value.split(
													'\\').pop();

										if (fileName)
											$("#labelForFileInput").text(fileName);
										
										else
											label.innerHTML = "Додати файл";
									});

				});


	function transferTextFromTextareaToInput(){
	
	var textAreaAnswer = document.getElementById("textareaAnswer")
	var inputAnswer = document.getElementById("inputAnswer");
	
	inputAnswer.value = textAreaAnswer.value.replace(/\n\r?/g, '<br />');	
	
}
