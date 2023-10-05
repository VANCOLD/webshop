$(document).ready(function() {
  // Event handler for the login form submission
  $("#login").click(function(event) {
    // Prevent the default form submission
    event.preventDefault();

    // Get the form data
    var username = $("#usernameLogin").val();
    var password = $("#passwordLogin").val();

    // Reset validation classes and error message
    $("#usernameLogin").removeClass("is-invalid");
    $("#passwordLogin").removeClass("is-invalid");
    $("#loginErrorMessage").hide();

    // Perform validation checks
    var valid = true;
    if (username === "") {
      $("#usernameLogin").addClass("is-invalid");
      valid = false;
    }
    if (password === "") {
      $("#passwordLogin").addClass("is-invalid");
      valid = false;
    }

    if (!valid) {
      $("#loginErrorMessage").show();
      return;
    }

    // Create an object with the login data
    var loginData = {
      username: username,
      password: password
    };

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
        alert("Login unsuccessful. Please check your credentials.");
        console.error(err, "Login fehlgeschlagen!");
      }
    });
  });

  // Event handler for the registration form submission
  $("#register").click(function(event) {
    // Prevent the default form submission
    event.preventDefault();

    // Get the form data
    var username = $("#username").val();
    var password = $("#password").val();

    // Reset validation classes and error message
    $("#username").removeClass("is-invalid");
    $("#password").removeClass("is-invalid");
    $("#registerErrorMessage").hide();

    // Perform validation checks
    var valid = true;
    if (username === "") {
      $("#username").addClass("is-invalid");
      valid = false;
    }
    if (!/^[A-Za-z0-9]+$/.test(username)) {
      $("#username").addClass("is-invalid");
      valid = false;
    }
    if (password === "") {
      $("#password").addClass("is-invalid");
      valid = false;
    }
    
    if (!valid) {
      $("#registerErrorMessage").show();
      return;
    }

    // Create an object with the registration data
    var registerData = {
      username: username,
      password: password
    };

    // Send an AJAX POST request to your backend
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
        console.error(err, "Registration fehlgeschlagen!");
        alert("Registration unsuccessful. Please try again.");
      }
    });
  });
});
