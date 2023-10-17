// Function to retrieve the access token from local storage
function getAccessToken() {
    return localStorage.getItem('token');
}

$(document).ready(function () {
    loadUsers(); // Load users when the page loads
    $('#user-form').submit(function (e) {
        e.preventDefault();
        createUser(); // Handle the form submission to create a new user
    });
});

function loadUsers() {
    const accessToken = getAccessToken();

    if (accessToken) {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/api/users/all',
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
    const username = $('#username').val();
    const password = $('#password').val();
    // Add other user-related data as needed

    if (accessToken && username && password) {
        const userData = {
            username: username,
            password: password, // You should handle password hashing on the server
            // Add other user-related data as needed
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
                // Clear the form fields
                $('#username').val('');
                $('#password').val('');
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
            url: `http://localhost:8080/api/users/${userId}`,
            data: JSON.stringify(newUserData),
            contentType: 'application/json',
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function (data) {
                // Handle the successful response, e.g., update the user list
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
    userTableBody.empty();

    users.forEach(function (user) {
        const userRow = `
            <tr data-user-id="${user.id}">
                <td>${user.id}</td>
                <td><input class="editable" name="username" value="${user.username}"></td>
                <td><input class="editable" name="password" value="${user.password}"></td>
                <td><input class="editable" name="gender" value="${user.gender}"></td>
                <td><input class="editable" name="firstname" value="${user.firstname}"></td>
                <td><input class="editable" name="lastname" value="${user.lastname}"></td>
                <td><input class="editable" name="email" value="${user.email}"></td>
                <td><input class="editable" name="address" value="${user.address}"></td>
                <td><input class="editable" name="role" value="${user.role}"></td>
                <td>
                    <button class="delete-button" data-user-id="${user.id}">Delete</button>
                    <button class="update-button" data-user-id="${user.id}">Update</button>
                </td>
            </tr>
        `;

        userTableBody.append(userRow);
    });

    // Handle user updates
    $('.editable').on('blur', function () {
        const userId = $(this).closest('tr').data('user-id');
        const field = $(this).attr('name');
        const newValue = $(this).val();

        updateUserData(userId, field, newValue);
    });

    $('.delete-button').on('click', function () {
        const userId = $(this).data('user-id');
        deleteUser(userId);
    });

    $('.update-button').on('click', function () {
        const userId = $(this).data('user-id');
        // Implement the logic to update the user data, e.g., show a form
    });
}

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
