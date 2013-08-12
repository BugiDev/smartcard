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
        var cardID = $( event.currentTarget ).attr( 'cardid' )
        
        $.get('/smartcard/selectcardedit:selectCard/'+cardID, function(data) {
            });
    
    });
    
    $( "#cancleDelete" ).click(function() {
        $( "#deleteDialog" ).dialog( "close" );
    });
});

