$(document).ready(function() {
    // Event handler for the login form submission
    $("#loginForm").submit(function(event) {
      // Prevent the default form submission
      event.preventDefault();
  
      // Get the form data
      var username = $("#username").val();
      var password = $("#password").val();
  
      // Create an object with the login data
      var loginData = {
        username: username,
        password: password
      };
      
      console.log(loginData);
  
      // Send an AJAX POST request to your backend
      $.ajax({
        type: "POST",
        url: "/authenticate", // Replace with the actual URL of your backend endpoint
        data: JSON.stringify(loginData),
        contentType: "application/json",
        success: function(response) {
          // Handle the successful login response here
          console.log("Login successful: ", response);
          localStorage.setItem("token", response);
        },
        error: function(error) {
          // Handle any errors that occurred during the login
          console.error("Login failed: ", error);
        }
      });
    });
  });