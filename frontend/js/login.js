  // Select the form and input fields from login
  const loginForm = document.querySelector('.login-validation');
  const usernameLogin = document.getElementById('usernameLogin');
  const passwordLogin = document.getElementById('passwordLogin');


  // Select the form and input fields from login
  const registerForm = document.querySelector('.register-validation');
  const username = document.getElementById('username');
  const password = document.getElementById('password');
  const passwordRepeat = document.getElementById('passwordRepeat');
  const email = document.getElementById('email');

  // Get a reference to the select element
  let dropdown = document.getElementById('gender');
  // Get the selected option element
  const gender = dropdown.options[dropdown.selectedIndex];
  // Get the value of the selected option


  const firstname = document.getElementById('firstname');
  const surname = document.getElementById('surname');
  const postalcode = document.getElementById('postalcode');
  const city = document.getElementById('city');
  const address = document.getElementById('address');


  // Add an event listener to the login button
  document.getElementById('login').addEventListener('click', function (e) {
    e.preventDefault(); // Prevent the form from submitting

    // Reset any previous error messages
    resetErrorMessages();

    // Perform validation checks
    if (usernameLogin.value.trim() === '') {
      displayErrorMessage(usernameLogin, 'Username is required.');
    }

    if (passwordLogin.value.trim() === '') {
      displayErrorMessage(passwordLogin, 'Password is required.');
    }

    // If there are no errors, you can submit the form
    if (!hasErrors()) {
      // Submit the form or perform further actions
      login(e); // This submits the form
    }
  });

  // Add an event listener to the register button
  document.getElementById('register').addEventListener('click', function (e) {
    e.preventDefault(); // Prevent the form from submitting

    // Reset any previous error messages
    resetErrorMessages();

      // Perform validation checks
      if (username.value.trim() === '') {
        displayErrorMessage(username, 'Username is required.');
      }
    
      if (password.value.trim() === '') {
        displayErrorMessage(password, 'Password is required.');
      } else {
        if(password.value !== passwordRepeat.value) {
          displayErrorMessage(password, 'Passwords do not match.');
        }
      }

      if (passwordRepeat.value.trim() === '') {
        displayErrorMessage(passwordLogin, 'Password is required.');
      }

      if (email.value.trim() === '') {
        displayErrorMessage(email, 'Email is required.');
      } else {
        // Regular expression for a basic email validation
        const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
        // Test the email against the regex pattern
        if ( !emailRegex.test(email.value) ) {
          displayErrorMessage(email, 'Invalid Email address was provided.');
        }
      }

      if (firstname.value.trim() === '') {
        displayErrorMessage(firstname, 'Firstname is required.');
      }

      if (surname.value.trim() === '') {
        displayErrorMessage(surname, 'Surname is required.');
      }

      if (city.value.trim() === '') {
        displayErrorMessage(city, 'City is required.');
      }

      if (address.value.trim() === '') {
        displayErrorMessage(address, 'Address is required.');
      }
      
      if (postalcode.value.trim() === '') {
        displayErrorMessage(postalcode, 'Postalcode is required.');
      }

    // If there are no errors, you can submit the form
    if (!hasErrors()) {
      // Submit the form or perform further actions
      register(e); // This submits the form
    }
  });


  // Function to display error messages
  function displayErrorMessage(inputElement, message) {
    const errorDiv = document.createElement('div');
    errorDiv.className = 'invalid-feedback';
    errorDiv.textContent = message;
    inputElement.classList.add('is-invalid');
    inputElement.parentNode.appendChild(errorDiv);
  }

  // Function to reset error messages
  function resetErrorMessages() {
    const errorMessages = document.querySelectorAll('.invalid-feedback');
    errorMessages.forEach(function (errorMessage) {
      errorMessage.remove();
    });

    const invalidInputs = document.querySelectorAll('.is-invalid');
    invalidInputs.forEach(function (input) {
      input.classList.remove('is-invalid');
    });
  }

  // Function to check if there are any errors
  function hasErrors() {
    return document.querySelectorAll('.is-invalid').length > 0;
  }


  function login(event) {

    // Prevent the default form submission
    event.preventDefault();

    // Create an object with the login data
    var loginData = {
      username: usernameLogin.value,
      password: passwordLogin.value
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
        window.location.href = "index.html";
      },
      error: (err) => {
        console.log(err);
        alert("Login unsuccessful. Please check your credentials.");
        console.error(err, "Login fehlgeschlagen!");
      }
    });
  }

  function register(event) {

    // Prevent the default form submission
    event.preventDefault();

    // Create an object with the registration data
    var registerData = {
      username: username.value,
      password: password.value,
      firstname: firstname.value,
      lastname: surname.value,
      email: email.value,
      address: {street: address.value, city: city.value, postalCode: postalcode.value},
      gender: gender.textContent
    };

    // Send an AJAX POST request to your backend
    $.ajax({
      url: "http://localhost:8080/register",
      method: 'POST',
      data: JSON.stringify(registerData),
      contentType: 'application/json',
      dataType: 'text',
      success: (response) => {
        // Get a reference to the button element by its ID or any other suitable method
        const button = document.getElementById('nav-signin-tab');

        // Check if the button element exists
        if (button) {
          // Programmatically click the button
          button.click();
          alert("Registered user successfully!\nPlease log in");
        }

      },
      error: (err) => {
        console.error(err, "Registration fehlgeschlagen!");
        alert("Registration unsuccessful. Please try again.");
      }
    });
  }
