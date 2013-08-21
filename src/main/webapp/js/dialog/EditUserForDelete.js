/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {
    
    $( "#deleteDialog" ).dialog({
        autoOpen: false
    });

    $( ".callDeleteLink" ).click(function(event) {
        $( "#deleteDialog" ).dialog( "option", "modal", true );
        var userID = $( event.currentTarget ).attr( 'userid' )
        
        $.get(selectUserForDeleteEditURL+userID, function(data) {
            });
    
    });
    
    $( "#cancleDelete" ).click(function() {
        $( "#deleteDialog" ).dialog( "close" );
    });
});

