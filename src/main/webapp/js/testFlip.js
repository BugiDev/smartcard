/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var turnRotator = false;

var clickPreview = false;


$("#btn-turn").on("click", function(e) {

    var cardQuestion = $('.cardQuestionPlace').text();
    var cardAnswer = $('.cardAnswerPlace').text();
    if (!turnRotator) {
        $(".flipbox").flippy({
            color_target: "",
            direction: "top",
            duration: "750",
            verso: "<center><div class='cardHead'>Card Question</div><br/><div class='cardTextSelectorQuestion'>" + cardQuestion + "</div></center>"
        });
        turnRotator = true;
    } else {
        $(".flipbox").flippy({
            color_target: "",
            direction: "bottom",
            duration: "750",
            verso: "<center><div class='cardHead'>Card Answer</div><br/><div class='cardTextSelectorAnswer'>" + cardAnswer + "</div></center>"
        });
        turnRotator = false;
    }
    e.preventDefault();
});

$(".alt_btn_preview").on("click", function(e) {
    
        if (turnRotator) {
        	var asd = $('#cardQuestion').val();
        $(".cardTextSelectorQuestion").text(asd);
    } else {
    	var bsd = $('#cardAnswer').val();
       $(".cardTextSelectorAnswer").text(bsd);
    }
    
    if(!clickPreview){
         $("#cardPreviewZone").show();
    }
   
});

$(".close_btn").on("click", function(e) {
    
     $("#cardPreviewZone").hide();
   
});
