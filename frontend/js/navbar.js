$(document).ready(function() {

    // Get token
    var token = localStorage.getItem("token");

    if(token == null) {
        alert("kein token gefunden");
    }

});