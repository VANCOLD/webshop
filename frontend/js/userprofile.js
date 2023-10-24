// Function to retrieve the access token from local storage
function getAccessToken() {
    return localStorage.getItem('token');
}

function getUserIdFromToken(token) {
    const tokenParts = token.split('.');
    
    if (tokenParts.length !== 3) {
        console.error('Invalid token format');
        return null;
    }

    const encodedPayload = tokenParts[1];
    const base64 = encodedPayload.replace('-', '+').replace('_', '/');
    const decodedPayload = atob(base64);

    try {
        const payloadObj = JSON.parse(decodedPayload);
        if (payloadObj && payloadObj.id) {
            return payloadObj.id;
        } else {
            console.error('Token does not contain the "id" claim.');
            return null;
        }
    } catch (error) {
        console.error('Error decoding token:', error);
        return null;
    }
}

// Usage
const token = 'getAccessToken()';
const userId = getUserIdFromToken(token);
if (userId !== null) {
    console.log('User ID:', userId);
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
    
        // Clear all form fields before populating them with the new data
        $('#profile-username').val('');
        $('#profile-firstname').val('');
        $('#profile-lastname').val('');
        $('#profile-password').val('');
        $('#profile-email').val('');
        $('#profile-gender').val('');
        $('#profile-address-city').val('');
        $('#profile-address-street').val('');
        $('#profile-address-postalcode').val('');
        $('#profile-address-country').val('');
        $('#profile-role').val('');
    
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

function goToMyOrders() {
    window.location.href = "myOrders.html";
}

