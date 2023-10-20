// Function to retrieve the access token from local storage
function getAccessToken() {
    return localStorage.getItem('token');
}

$(document).ready(function () {
    loadUsers(); // Load users when the page loads
    $('#user-create-form').submit(function (e) {
        e.preventDefault();
        createUser(); // Handle the form submission to create a new user
    });
});

function loadUsers() {
    const accessToken = getAccessToken();

    if (accessToken) {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/api/users/all/full',
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function (data) {
                // Handle the successful response, e.g., populate the user list
                populateUserList(data);
            },
            error: function (err) {
                console.error('Error loading users: ', err);
            }
        });
    }
}

function createUser() {
    const accessToken = getAccessToken();
    const username = $('#create-username').val();
    const password = $('#create-password').val();
    const firstname = $('#create-firstname').val();
    const lastname = $('#create-lastname').val();
    const street   = $('#create-street').val();
    const city = $('#create-city').val();
    const postalCode = $('#create-postalCode').val();
    const country = $('#create-country').val();
    const email = $('#create-email').val();
    
    const genderHook = document.getElementById("select-gender");
    const gender = genderHook.options[genderHook.selectedIndex].text;
    const address = { city: city, postalCode: postalCode, country: country, street: street};

    if (accessToken && username && password) {
        const userData = {
            username: username,
            password: password,
            firstname: firstname,
            lastname: lastname,
            email: email,
            gender: gender,
            address: address
        };

        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/api/users',
            data: JSON.stringify(userData),
            contentType: 'application/json',
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function (data) {
                // Handle the successful response, e.g., update the user list
                loadUsers();
                // Clears the form fields
                $('#create-username').val('');
                $('#create-password').val('');
                $('#create-firstname').val('');
                $('#create-lastname').val('');
                $('#create-email').val('');
            },
            error: function (xhr, status, error) {
                if (xhr.status === 409) {
                    console.error('User conflict: A user with this username already exists.');
                } else if (xhr.status === 400) {
                    console.error('Bad request: Invalid user data. Please check your input.');
                    // You can display more specific error messages based on the response content.
                } else {
                    console.error('Error creating user: ', error);
                }
            }
        });
    } else {
        console.error('Missing required data (access token, username, or password).');
    }
}

function deleteUser(userId) {
    const accessToken = getAccessToken();

    if (accessToken) {
        $.ajax({
            type: 'DELETE',
            url: `http://localhost:8080/api/users/${userId}`,
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function (data) {
                // Handle the successful response, e.g., update the user list
                loadUsers();
            },
            error: function (err) {
                console.error('Error deleting user: ', err);
            }
        });
    }
}

function updateUser(userId, userData) {
    const accessToken = getAccessToken();

    if (accessToken) {
        // Make an AJAX request to get the user's data
        $.ajax({
            type: 'GET',
            url: `http://localhost:8080/api/users/${userId}`,
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function (data) {
                // Display the modal and populate the form with the user data
                showUpdateForm(data);
            },
            error: function (err) {
                console.error('Error loading user data: ', err);
            }
        });
    }
}

function showUpdateForm(userData) {
    // Display a modal dialog with user data for updating
    const updateForm = `
        <div id="update-user-modal" class="modal">
            <div class="modal-content">
                <h3>Update User</h3>
                <form id="update-user-form">
                    <label for="update-username">Username:</label>
                    <input type="text" id="update-username" name="username" value="${userData.username}" required>
                    <label for="update-password">Password:</label>
                    <input type="password" id="update-password" name="password" value="${userData.password}" required>
                    <!-- Add input fields for other user-related data here with values from userData -->
                    <button type="submit">Update</button>
                </form>
            </div>
        </div>
    `;

    // Append the update form to the document
    $('body').append(updateForm);

    // Handle form submission for updating user data
    $('#update-user-form').submit(function (e) {
        e.preventDefault();
        const newUserData = {
            username: $('#update-username').val(),
            password: $('#update-password').val(),
            // Add other user-related data fields
        };

        // Call a separate function to update the user data
        handleUpdateUser(userData.id, newUserData);
        $('#update-user-modal').remove(); // Remove the modal after submission
    });
}

function handleUpdateUser(userId, newUserData) {
    const accessToken = getAccessToken();

    if (accessToken) {
        $.ajax({
            type: 'PUT',
            url: `http://localhost:8080/api/users/full/${userId}`,
            data: JSON.stringify(newUserData),
            contentType: 'application/json',
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function (data) {
                // Handle the successful response, e.g., update the user list
                console.log(data)
                loadUsers();
            },
            error: function (err) {
                console.error('Error updating user: ', err);
            }
        });
    }
}

function populateUserList(users) {
    const userTableBody = $('#user-list-tbody');
    const userModal = $('#userModal');
    userTableBody.empty();

    users.forEach(function (user) {
        const userRow = `
            <tr data-user-id="${user.id}">
            <td>${user.id}</td>
            <td class="editable" data-field="username">${user.username}</td>
            <td class="editable" data-field="password">${user.password}</td>
            <td class="editable" data-field="gender">${user.gender}</td>
            <td class="editable" data-field="firstname">${user.firstname}</td>
            <td class="editable" data-field="lastname">${user.lastname}</td>
            <td class="editable" data-field="email">${user.email}</td>
            <td>${user.role.name}</td>
            <td>
                <button class="delete-button" data-user-id="${user.id}">Delete</button>
                <button class="update-button" data-user-id="${user.id}">Edit</button>
            </td>
        </tr>
        `;

        userTableBody.append(userRow);
    });
}

    // Handle user updates
    $('.editable').on('blur', function () {
        const userId = $(this).closest('tr').data('user-id');
        const field = $(this).attr('name');
        const newValue = $(this).val();

        updateUserData(userId, field, newValue);
    });

    // Handle user updates
$('.user-list-table').on('click', '.update-button', function () {
    const userId = $(this).data('user-id');
    const row = $(this).closest('tr');
    const editButton = $(this);

    if (editButton.text() === 'Edit') {
        // Switch to edit mode
        editButton.text('Save');
        row.find('.editable').prop('contenteditable', true).addClass('editing');
    } else {
        // Switch to save mode
        editButton.text('Edit');
        row.find('.editable').prop('contenteditable', false).removeClass('editing');

        // Collect the updated data and perform the save
        row.find('.editable.editing').each(function () {
            const field = $(this).data('field');
            const newValue = $(this).text().trim();
            updateUserData(userId, field, newValue);
        });
    }
});


    $('.delete-button').on('click', function () {
        const userId = $(this).data('user-id');
        deleteUser(userId);
    });



function updateUserData(userId, field, newValue) {
    const accessToken = getAccessToken();

    if (accessToken) {
        const userData = {};
        userData[field] = newValue;

        $.ajax({
            type: 'PUT',
            url: `http://localhost:8080/api/users/${userId}`,
            data: JSON.stringify(userData),
            contentType: 'application/json',
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function (data) {
                // Handle the successful response, e.g., update the user list
                loadUsers();
            },
            error: function (err) {
                console.error('Error updating user data: ', err);
            }
        });
    }
}
