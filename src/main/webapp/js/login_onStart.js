/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function() {
    $(".username").focus(function() {
        $(".user-icon").css("left", "-48px");
    });
    $(".username").blur(function() {
        $(".user-icon").css("left", "0px");
    });

    $(".password").focus(function() {
        if ($(".tjq-error-popup:first").css('display') === "none") {
            
            $(".pass-icon").css("top", "221px");
        } else {
            $(".pass-icon").css("top", "273px");
        }
        $(".pass-icon").css("left", "-48px");
    });
    $(".password").blur(function() {
         if ($(".tjq-error-popup:first").css('display') === "none") {
            $(".pass-icon").css("top", "221px");
        } else {
            $(".pass-icon").css("top", "273px");
        }
        $(".pass-icon").css("left", "0px");
    });
});