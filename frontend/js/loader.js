$.get("../navbar.html", function(data){
	$("#navbar-bind").replaceWith(data);
});
$.get("../footer.html", function(data){
	$("#footer-bind").replaceWith(data);
});