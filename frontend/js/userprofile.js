// Function to retrieve the access token from local storage
function getAccessToken() {
    return localStorage.getItem('token');
}

function getUserIdFromToken(token) {
    try {
        const decodedToken = jwt_decode(token);
        return decodedToken.sub; // Assuming 'sub' is the claim containing the user ID
    } catch (error) {
        console.error('Error decoding token:', error);
        return null;
    }
}

$(document).ready(function () {
    loadUserProfile(); // Call loadUserProfile on document ready to fetch the user's data

    // Save user profile data when the "Save" button is clicked
    $('#user-profile-save-button').click(function () {
        saveUserProfile();
    });
});

function loadUserProfile() {
    const accessToken = getAccessToken();

    if (accessToken) {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/api/users/me',
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function (userData) {
                // Handle the successful response, populate the user profile form
                populateUserProfile(userData);
            },
            error: function (err) {
                console.error('Error loading user profile: ', err);
            }
        });
    }
}

function populateUserProfile(userData) {
    // Populate the user profile form fields with the user's data
    $('#profile-username').val(userData.username);
    $('#profile-firstname').val(userData.firstname);
    $('#profile-lastname').val(userData.lastname);
    $('#profile-password').val(userData.password);
    $('#profile-email').val(userData.email);
    $('#profile-gender').val(userData.gender);
    $('#profile-address-city').val(userData.address.city);
    $('#profile-address-street').val(userData.address.street);
    $('#profile-address-postalcode').val(userData.address.postalCode);
    $('#profile-address-country').val(userData.address.country);
    $('#profile-role').val(userData.role.name);
}

function saveUserProfile() {
    // Implement the logic to save user profile changes
    const accessToken = getAccessToken();
    
      // Get the user ID from the access token
      const userId = getUserIdFromToken(accessToken);
      
    // Get updated user profile data from the input fields
    const updatedProfileData = {
        id: userId,
        username: $('#profile-username').val(),
        firstname: $('#profile-firstname').val(),
        lastname: $('#profile-lastname').val(),
        password: $('#profile-password').val(),
        email: $('#profile-email').val(),
        gender: $('#profile-gender').val(),
        address: {
            city: $('#profile-address-city').val(),
            street: $('#profile-address-street').val(),
            postalCode: $('#profile-address-postalcode').val(),
            country: $('#profile-address-country').val(),
        },
        role: {
            name: $('#profile-role').val()
        },
    };

    if (accessToken) {
        $.ajax({
            type: 'PUT',
            url: 'http://localhost:8080/api/users',
            data: JSON.stringify(updatedProfileData),
            contentType: 'application/json',
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function (response) {
                console.log('User profile updated successfully:', response);
                // You can provide feedback to the user, e.g., display a success message
            },
            error: function (err) {
                console.error('Error updating user profile: ', err);
                // Handle errors and provide feedback to the user
            }
        });
    }
}
