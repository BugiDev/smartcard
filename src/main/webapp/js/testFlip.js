/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$(function() {
    $("#btn-left").on("click", function(e) {
        alert("nesto");
        $(".flipbox").flippy({
            color_target: "red",
            direction: "left",
            duration: "750",
            verso: "<span>Woohoo ! \\o/</span>"
        });
        e.preventDefault();
    });
});