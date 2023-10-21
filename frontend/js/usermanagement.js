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
    const role = "user"; //automatically assigns user role


    if (accessToken && username && password) {
        const userData = {
            username: username,
            password: password,
            firstname: firstname,
            lastname: lastname,
            email: email,
            gender: gender,
            address: address,
            role: role
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
                // Now call loadUsers() as a callback function
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
    userTableBody.empty();

    users.forEach(function (user) {
        const userRow = `
            <tr data-user-id="${user.id}">
            <td>${user.id}</td>
            <td class="editable" data-field="username">${user.username}</td>
            <td class="editable" data-field="password">
            <span class="placeholder">****</span>
            <input type="password" value="${user.password}" style="display: none;"></td>
            <td class="editable" data-field="gender">${user.gender}</td>
            <td class="editable" data-field="firstname">${user.firstname}</td>
            <td class="editable" data-field="lastname">${user.lastname}</td>
            <td class="editable" data-field="email">${user.email}</td>
            <td class="editable" data-field="role" data-user-id="${user.id}">${user.role.name}</td>
            <td>
                <button class="delete-button" data-user-id="${user.id}">Delete</button>
                <button class="update-button" data-user-id="${user.id}">Edit</button>
                <button class="admin-button" data-user-id="${user.id}">Change Role</button>
                </td>
        </tr>
        `;
        userTableBody.append(userRow);
    });
}


function changeRoleToAdmin() {
    // Find the element containing the role string and update it
    const roleElement = document.querySelector('.editable[user.role.name]');
    if (roleElement) {
        roleElement.textContent = 'Admin';
    }
}

// Add a click event listener to the "Change Role" button
$('.user-list-table').on('click', '.admin-button', function () {
    const userId = $(this).data('user-id'); // Get the user ID from the button's data attribute

    // Find the element containing the role string for the specific user and update it
    const roleElement = $(`td[data-field="role"][data-user-id="${userId}"]`);
    
    if (roleElement.length) {
        roleElement.text('Admin');
    }
});


// Add an event listener to the Edit button
const editButtons = document.querySelectorAll('.edit-button');

editButtons.forEach((button) => {
    button.addEventListener('click', function () {
        const parent = this.closest('td');
        const input = parent.querySelector('input[type="password"]');
        const placeholder = parent.querySelector('.placeholder');

        // Toggle the display of input and placeholder
        if (input.style.display === 'none') {
            input.style.display = 'block';
            placeholder.style.display = 'none';
        } else {
            input.style.display = 'none';
            placeholder.style.display = 'inline';
        }
    });
});


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
        } else if (editButton.text() === 'Save') {
            // Handle the save logic here
            const field = row.find('.editing').data('field');
            const newValue = row.find('.editing').text().trim();
            updateUserData(userId, field, newValue);

            // Switch back to edit mode after saving
            editButton.text('Edit');
            row.find('.editable').prop('contenteditable', false).removeClass('editing');
        }
    });


    // Handle user deletions (using event delegation)
$('.user-list-table').on('click', '.delete-button', function () {
    const userId = $(this).data('user-id'); // Get the user ID from the data attribute
    if (confirm(`Are you sure you want to delete user with ID ${userId}?`)) {
        // If the user confirms the deletion, call the deleteUser function
        deleteUser(userId);
    }
});

    function updateUserData(userId, field, newValue, row) {
        const accessToken = getAccessToken();
    
        if (accessToken) {
            const updateData = {
                [field]: newValue // Create a JSON object with a single field
            };
    
            $.ajax({
                type: 'PUT',
                url: `http://localhost:8080/api/users/${userId}`,
                data: JSON.stringify(updateData), // Send only the field to update
                contentType: 'application/json',
                headers: {
                    'Authorization': `Bearer ${accessToken}`
                },
                success: function (data) {
                    console.log('Update successful:', data);
                },
                error: function (err) {
                    console.error('Error updating user data: ', err);
                }
            }).done(function() {
                loadUsers();
                row.find('.editable').prop('contenteditable', false).removeClass('editing');
            });
        }
    }
    