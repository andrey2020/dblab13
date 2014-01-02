jQuery(document).ready(function() {
    
   jQuery("#table td a").live('click', function(event){
        var x=event.currentTarget;
        var xx=false;
        
        if (x.name === "View") {
                var link = jQuery(event.currentTarget);
    var url = link.attr('href');
    jQuery.get(url, function(data) {
        // Update the result div with the server response
        jQuery("#result").html("<p >" + data + "</p>");
    });
             $('#content').animate({scrollTop:0}, 'slow');
             event.preventDefault();
        }
        if (x.name === "Delete")  {
            event.preventDefault();
            xx=window.confirm("Bitte bestätigen Sie die Entfernung");
        }
            if (xx){
            var link = jQuery(event.currentTarget);
            var url = link.attr('href');
            
            jQuery.get(url, function(data) {
        // Update the result div with the server response
            jQuery("#tableContainer").html(data);
    });

                
            }
             
        
    });

   jQuery("#table th a, .pagelinks a").live('click', function(event){
        
        sortTable(event);
        return false;
    });
    
   jQuery("#nebenTable1 td a").live('click', function(event){
        var x=event.currentTarget;       
        if (x.name === "linkAction") {
            
            editOrDeleteCustomer(event);
            event.preventDefault();
        }
        
    });
    
   jQuery("#nebenTable1 th a, .paging-inline a").live('click', function(event){
        editOrDeleteCustomer(event);
        return false;
    });
   
   jQuery("#nebenTable2 td a").live('click', function(event){
        var x=event.currentTarget;       
        if (x.name === "linkAction") {
            
            editOrDeleteCustomer(event);
            event.preventDefault();
        }
        
    });
    
   jQuery("#nebenTable2 th a, .paging-inline a").live('click', function(event){
        editOrDeleteCustomer(event);
        return false;
    });
    
   jQuery("#nebenTable3 td a").live('click', function(event){
        var x=event.currentTarget;       
        if (x.name === "linkAction") {
            
            editOrDeleteCustomer(event);
            event.preventDefault();
        }
        
    });
    
   jQuery("#nebenTable3 th a, .paging-inline a").live('click', function(event){
        editOrDeleteCustomer(event);
        return false;
    }); 
    
});

function deleteObject(event) {
    var link = jQuery(event.currentTarget);
    var url = link.attr('href');
    window.confirm("Bitte bestätigen Sie die Entfernung");
    jQuery.get(url, function(data) {
        // Update the result div with the server response
        jQuery("#tableContainer").html(data);
    });
}

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