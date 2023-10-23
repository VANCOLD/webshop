// Function to retrieve the access token from local storage
function getAccessToken() {
    return localStorage.getItem('token');
}

var roles;

$(document).ready(function () {
    loadRoles();
    loadUsers();

    // Add an event listener to the user creation form
    $('#user-create-form').submit(function (e) {
        e.preventDefault();
        if (passwordsMatch()) {
            createUser();
        } else {
            $('#password-match-error').show();
        }
    });

});

// Function to check if the passwords match
function passwordsMatch() {
    const password = $('#create-password').val();
    const repeatPassword = $('#repeat-password').val();
    return password === repeatPassword;
}

// Add an event listener to the "Repeat Password" input to hide the error message
$('#repeat-password').on('input', function () {
    $('#password-match-error').hide();
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
                populateUserList(data, roles);
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
    const roleHook   = document.getElementById("select-role");
    const role = roleHook.options[roleHook.selectedIndex].text;

    const address = { city: city.trim(), postalCode: postalCode.trim(), country: country.trim(), street: street.trim()};



    if (accessToken && username && password) {
        const userData = {
            username: username.trim(),
            password: password.trim(),
            firstname: firstname.trim(),
            lastname: lastname.trim(),
            email: email.trim(),
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
                $('#repeat-password').val('');
                $('#create-firstname').val('');
                $('#create-lastname').val('');
                $('#create-email').val('');
                $('#create-street').val('');
                $('#create-city').val('');
                $('#create-country').val('');
                $('#create-postalCode').val('');
                $('#create-gender').value = 'Male';
                $('#create-role').value = 'user';
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
                loadUsers();
            },
            error: function (err) {
                console.error('Error updating user: ', err);
            }
        });
    }
}

function populateUserList(users,roles) {
    const userTableBody = $('#user-list-tbody');
    userTableBody.empty();

    users.forEach(function (user) {
        let userRow = `
            <tr data-user-id="${user.id}">
            <td>${user.id}</td>
            <td class="editable" data-field="username" data-user-id>${user.username}</td>
            <td> **** </td>
            <td class="editable" style="display:none;" data-field="password">${user.password}</td>
            <td class="editable" data-field="gender">${user.gender}</td>
            <td class="editable" data-field="firstname">${user.firstname}</td>
            <td class="editable" data-field="lastname">${user.lastname}</td>
            <td class="editable" data-field="email">${user.email}</td>
            <td class="editable" data-field="address" data-user-id="${user.address.id}">${user.address.street};${user.address.postalCode};${user.address.city};${user.address.country}</td>`;

            userRow += `
            <td class="editable" style="width:100px" data-field="role" data-user-id="${user.id}">  
                    <select disabled id="update-role${user.id}">
                        ${roleOptions.join('')} <!-- Join the array to form the options -->
                    </select>
            </td>
            <td>
                <button class="delete-button" data-user-id="${user.id}">Delete</button>
                <button class="update-button" data-user-id="${user.id}">Edit</button>
                </td>
            </tr>`;
        userTableBody.append(userRow);
    });
}

function loadRoles() {
    const accessToken = getAccessToken();
    if (accessToken) {
        $.ajax({
            type: 'GET',
            url: `http://localhost:8080/api/roles/all`,
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function (data) {
                roles = data;
                const roleOptions = roles.map(function (role) {
                    return `<option value="${role.id}"}>${role.name}</option>`;
                });
    
    
                $('#role-bind').replaceWith(` 
                <select id="select-role" class="form-select" aria-label="Select your role">
                    ${roleOptions.join('')} <!-- Join the array to form the options -->
                </select>`);
                
            },
            error: function (err) {
                console.error('Error retrieving role data: ', err);
            }
        });
    }
}



    // Handle user updates
    $('.user-list-table').on('click', '.update-button', function () {
        const userId = $(this).data('user-id');
        const row = $(this).closest('tr');
        const editButton = $(this);

        if (editButton.text() === 'Edit') {
            // Switch to edit mode
            editButton.text('Save');
            row.find('.editable').prop('contenteditable', true).addClass('editing');
            document.getElementById('update-role'+userId).disabled = false;
        } else if (editButton.text() === 'Save') {
            updateUserData(userId);

            // Switch back to edit mode after saving
            editButton.text('Edit');
            document.getElementById('update-role'+userId).disabled = true;
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

        // Get a reference to the table by its ID
        const table = document.getElementById('user-display-table');
        
        var cellContent = []

        // Check if the table exists
        if (table) {
            // Search for the row with the specified data-user-id attribute
            const rows = table.querySelectorAll(`tr[data-user-id="${userId}"]`);
            // Access the cells in the row
            const cells = rows[0].cells;

            // Loop through the cells and extract their content
            for (let i = 0; i < cells.length; i++) {
                console.log(i + " " + cells[i].textContent);
                if(i != 2) { 
                    if(i != 9) {
                        if(i != 8) {
                            cellContent.push(cells[i].textContent);
                        } else {
                            const out = cells[i].textContent.split(';');
                            const dataUserId = cells[i].getAttribute('data-user-id');
                            cellContent.push({id: dataUserId, street:out[0].trim(), postalCode: out[1].trim(), city: out[2].trim(), country: out[3].trim()})
                        }
                    } else {
                        const select = cells[i].querySelector('select');
                        cellContent.push({id: select.value, name: select.options[select.selectedIndex].text.trim()});
                    }
                }
            }

            console.log(cellContent);
        }


        const updateData = {
            id: cellContent[0].trim(),
            username: cellContent[1].trim(),
            password: cellContent[2].trim(),
            gender: cellContent[3].trim(),
            firstname: cellContent[4].trim(),
            lastname: cellContent[5].trim(),
            email: cellContent[6].trim(),
            address: cellContent[7],
            role: cellContent[8]
        };

        $.ajax({
            type: 'PUT',
            url: `http://localhost:8080/api/users`,
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
    