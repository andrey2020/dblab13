jQuery(document).ready(function() {
    
    jQuery("#tableSchaechteZulassung td a").live('click', function(event){
        var callServer = true;
        var x=event.currentTarget;
        //alert("The id of the triggered element: " + x.name);
        
        if (x.name=="SchaechteZulassung") {
            
            editOrDeleteCustomer(event);
        }

        if (callServer) {
            // Make ajax request
            
        }

        // Prevent the default browser behavior of navigating to the link
        event.preventDefault();
    })
   
    jQuery("#tableVerboteneSchaechte td a").live('click', function(event){
        var callServer = true;
        var x=event.currentTarget;
        //alert("The id of the triggered element: " + x.name);
        
        if (x.name=="SchaechteZulassung") {
            
            editOrDeleteCustomer(event);
        }

        if (callServer) {
            // Make ajax request
            
        }

        // Prevent the default browser behavior of navigating to the link
        event.preventDefault();
    })
    
    jQuery("#table td a").live('click', function(event){
        var callServer = true;
         var x=event.currentTarget;
       // alert("The id of the triggered element: " + x.name);
        
        if (x.name=="View") {
            
            editOrDeleteCustomer(event);
        }

        if (callServer) {
            // Make ajax request
            
        }

        // Prevent the default browser behavior of navigating to the link
        event.preventDefault();
    })

    jQuery("#table th a, .pagelinks a").live('click', function(event){
        // Make ajax request
        sortTable(event);

        // Prevent the default browser behavior of navigating to the link
        return false;
    })
})

function editOrDeleteCustomer(event) {
    var link = jQuery(event.currentTarget);
    var url = link.attr('href');
    jQuery.get(url, function(data) {
        // Update the result div with the server response
        jQuery("#result").html("<p >" + data + "</p>");
    });
}

function sortTable(event) {
    var link = jQuery(event.currentTarget);
    var url = link.attr('href');
    jQuery.get(url, function(data) {
        // Update the table container with the new table
        jQuery("#tableContainer").html(data);
    });
}