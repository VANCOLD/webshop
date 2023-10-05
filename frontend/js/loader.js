
$(document).ready(function() {

    // Use jQuery to fetch the external HTML file
    $.ajax({
        url: '../navbar.html', // Replace with the path to your external HTML file
        dataType: 'html',
        success: function(data) {
            // Insert the content into the specified element
            $('#navbar-bind').replaceWith(data);
        }
    });

    // Use jQuery to fetch the external HTML file
    $.ajax({
        url: '../footer.html', // Replace with the path to your external HTML file
        dataType: 'html',
        success: function(data) {
            // Insert the content into the specified element
            $('#footer-bind').replaceWith(data);
        }
    });
});