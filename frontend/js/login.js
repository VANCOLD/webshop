$(document).ready(function() {
    // Event handler for the login form submission
    $("#login").click(function(event) {
      // Prevent the default form submission
      event.preventDefault();
  
      // Get the form data
      var username = $("#usernameLogin").val();
      var password = $("#passwordLogin").val();
  
      // Create an object with the login data
      var loginData = {
        username: username,
        password: password
      };
      
      console.log(loginData);
  
      // Send an AJAX POST request to your backend
      $.ajax({
        url: "http://localhost:8080/authenticate", 
        method: 'POST',
        data: JSON.stringify(loginData),
        contentType: 'application/json', 
        dataType: 'text', 
        success: (response) => {
            const accessToken = response;
            localStorage.setItem('token', accessToken); 
            window.location.href = "index.html?token=" + accessToken;
        },
        error: (err) => {
            console.log(err);
            alert("Loging in user was unsuccessful!");
            console.error(err, "Login fehlgeschlagen!");
        }
      });
    });


    $("#register").click(function(event) {
      // Prevent the default form submission
      event.preventDefault();

        // Get the form data
      var username = $("#username").val();
      var password = $("#password").val();
  
      // Create an object with the login data
      var registerData = {
        username: username,
        password: password
      };
      
  
      $.ajax({
        url: "http://localhost:8080/register", 
        method: 'POST',
        data: JSON.stringify(registerData),
        contentType: 'application/json', 
        dataType: 'text', 
        success: (response) => {

            // Assume 'response' is the JSON response received from the backend
            var jsonObject = JSON.parse(response);
            updateUser(jsonObject.id);
            alert("Registered user successfully!\nPlease log in");

        },
        error: (err) => {
            console.error(err, "Login fehlgeschlagen!");
            alert("Registered user unsuccessful!");
        }
      });
      
    });

    function updateUser(userid) {
      var updateData = {
        username: username,
        password: password
      };
    }
  });