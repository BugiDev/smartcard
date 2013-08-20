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
        var subjectID = $( event.currentTarget ).attr( 'subjectid' )
        
        $.get(selectSubjectForDeleteEditURL+subjectID, function(data) {
            });
    
    });
    
    $( "#cancleDelete" ).click(function() {
        $( "#deleteDialog" ).dialog( "close" );
    });
});

