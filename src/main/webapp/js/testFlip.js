/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

var turnRotator = false;


$("#btn-turn").on("click", function(e) {

    var cardQuestion = $('.cardQuestionPlace').text();
    var cardAnswer = $('.cardAnswerPlace').text();
    if (!turnRotator) {
        $(".flipbox").flippy({
            color_target: "",
            direction: "top",
            duration: "750",
            verso: "<span class='cardTextSelectorQuestion'>" + cardQuestion + "</span>"
        });
        turnRotator = true;
    } else {
        $(".flipbox").flippy({
            color_target: "",
            direction: "bottom",
            duration: "750",
            verso: "<span class='cardTextSelectorAnswer'>" + cardAnswer + "</span>"
        });
        turnRotator = false;
    }
    e.preventDefault();
});

$(".alt_btn_preview").on("click", function(e) {
    alert("radi prew");
        if (!turnRotator) {
        $(".cardTextSelectorQuestion").text( $('.cardQuestionPlace').text());
    } else {
       $(".cardTextSelectorAnswer").text($('.cardAnswerPlace').text());
    }

   
});