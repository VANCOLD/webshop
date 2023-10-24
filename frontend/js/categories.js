$(document).ready(function () {
    loadCategories();
});


function loadCategories() {
    const accessToken = localStorage.getItem('token');

    if (accessToken) {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/api/categories/all',
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function (categories) {
                populateCategories(categories);
            
            },
            error: function (error) {
                console.log('Error:', error);
            }
        });
    }
}


function populateCategories(categories) {
    const categoryTableBody = $('#category-list-tbody');
    categoryTableBody.empty();  

    var categoryRow = ``; 
    categories.forEach(function (category) {
        if(category.id % 2 == 0) {
            categoryRow +=  `
            <tr style="background-color:#ffffff; text-align:center;" data-user-id="${category.id}">
                <td data-field="id">${category.id}</td>
                <td data-field="name">${category.name}</td>
                <td>
                    <button class="delete-button" data-user-id="${category.id}">Delete</button>
                </td>
            </tr>`;
        } else {
            categoryRow +=  `
            <tr style="background-color:#f2f2f2; text-align:center;" data-user-id="${category.id}">
                <td data-field="id">${category.id}</td>
                <td data-field="name">${category.name}</td>
                <td>
                    <button class="delete-button" data-user-id="${category.id}">Delete</button>
                </td>
            </tr>`;
        }
        
        // Append the categoryRow to a table or another container element.
    });
    $('#user-display-table').append(categoryRow);
}

function saveCategory() {
    // Retrieve the access token from local storage
    const accessToken = localStorage.getItem('token');

    // Check if the access token exists in local storage
    if (accessToken) {

        const name = document.getElementById('category-name').value;
        // The access token doesn't exist in local storage, so you can use the URL without the token.
        $.ajax({
            type: 'POST',
            url: 'http://localhost:8080/api/categories',
            data: JSON.stringify({id: null, name: name}),
            contentType: 'application/json',
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function(data) {
                loadCategories();
            },
            error: function(err) {
                // Handle errors
                console.error('Error getting user cart: ', err);
            }
        }); 
    }
}

function deleteCategory(productId) {
    // Retrieve the access token from local storage
    const accessToken = localStorage.getItem('token');

    // Check if the access token exists in local storage
    if (accessToken) {

        // The access token doesn't exist in local storage, so you can use the URL without the token.
        $.ajax({
            type: 'DELETE',
            url: 'http://localhost:8080/api/categories/'+productId,
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function(data) {
                loadCategories();
            },
            error: function(err) {
                // Handle errors
                console.error('Error getting user cart: ', err);
            }
        }); 
    }
}

// Handle user deletions (using event delegation)
$('.user-list-table').on('click', '.delete-button', function () {
    const productId = $(this).data('user-id'); 
    if (confirm(`Are you sure you want to delete category with ID ${productId}?`)) {
        // If the user confirms the deletion, call the deleteUser function
        deleteCategory(productId);
    }
});