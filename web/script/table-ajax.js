jQuery(document).ready(function() {

    jQuery("#table th a, .pagelinks a").live('click', function(event){
       
        sortTable(event);
        return false;
    });
});


function sortTable(event) {
    var link = jQuery(event.currentTarget);
    var url = link.attr('href');
    
    jQuery.get(url, function(data) {
   //  window.confirm(data.toString());   
    jQuery("#tableContainer").html(data);
    });
}