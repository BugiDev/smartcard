/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
    
    $( "#deleteDialog" ).dialog({
        autoOpen: false
    });

    $( ".selectedDeleteLink" ).click(function(event) {
        $( "#deleteDialog" ).dialog( "option", "modal", true );
        var cardID = $( event.currentTarget ).attr( 'cardid' )
        
        $.get(selectCardForDeleteURL+cardID, function(data) {
            });
    
    });
    
    $( "#cancleDelete" ).click(function() {
        $( "#deleteDialog" ).dialog( "close" );
    });
    
    
    
    $( "#deleteDialogUser" ).dialog({
        autoOpen: false
    });

    $( ".selectedDeleteLink" ).click(function(event) {
        $( "#deleteDialogUser" ).dialog( "option", "modal", true );
        var userID = $( event.currentTarget ).attr( 'userid' )
        
        $.get(selectUserForDeleteURL+userID, function(data) {
            });
    
    });
    
    $( "#cancleDeleteUser" ).click(function() {
        $( "#deleteDialogUser" ).dialog( "close" );
    });
});

