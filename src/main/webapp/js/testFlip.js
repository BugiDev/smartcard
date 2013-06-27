/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$(function() {
    
    var turnRotator = false;
    
    
    
    $("#btn-turn").on("click", function(e) {
        
        if (!turnRotator){
        $(".flipbox").flippy({
            color_target: "",
            direction: "top",
            duration: "750",
            verso: "<span>Gore</span>"
        });
        turnRotator = true;
    }else{
        $(".flipbox").flippy({
            color_target: "",
            direction: "bottom",
            duration: "750",
            verso: "<span>Dole</span>"
        });
        turnRotator = false;
    }          
        e.preventDefault();
    });
});