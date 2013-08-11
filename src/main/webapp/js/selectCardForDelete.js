/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
    
    $( "#deleteDialog" ).dialog({
        autoOpen: false
    });

    $( "#deleteLink" ).click(function() {
        $( "#deleteDialog" ).dialog( "option", "modal", true );
        var cardID = $( "#deleteLink" ).attr( 'cardid' )
        
        $.get('/smartcard/newcardsusers:selectCard/'+cardID, function(data) {
            });
    
    });
    
    $( "#cancleDelete" ).click(function() {
        $( "#deleteDialog" ).dialog( "close" );
    });
    
    
    
    $( "#deleteDialogUser" ).dialog({
        autoOpen: false
    });

    $( "#deleteLinkUser" ).click(function() {
        $( "#deleteDialogUser" ).dialog( "option", "modal", true );
        var userID = $( "#deleteLinkUser" ).attr( 'userid' )
        
        $.get('/smartcard/newcardsusers:selectUser/'+userID, function(data) {
            });
    
    });
    
    $( "#cancleDeleteUser" ).click(function() {
        $( "#deleteDialogUser" ).dialog( "close" );
    });
});

