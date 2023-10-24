var orderStatusArray = [];
var orderArray = [];

$(document).ready(function () {
    loadOrderStatus();
    loadOrders();
});

function loadOrders() {
    const accessToken = localStorage.getItem('token');

    if(accessToken) {
        $.ajax({
            type: 'GET',
            url: `http://localhost:8080/api/orders/all`,
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function(orders) {
                populateOrders(orders);
            },
            error: function(err) {
                console.error(err)
            }
        });
    }
}

function populateOrders(orders) {

    const orderTableBody = $('#order-list-tbody');
    orderTableBody.empty();
    orderArray = [];


    orders.forEach(function (order) {

        orderArray.push(order);
        var products = new Map();
        order.orderedProducts.forEach(function (product) {
            products.set(product.name, product.amount );
        });

        var output = '';
        for (var [key, value] of products) {  
            output += value + 'x ' + key + '<br>';  
         }

        console.log(output);

        var orderRow = $('<tr data-user-id="' + order.id + '">');
        orderRow.append('<td data-field="id">' + order.id + '</td>');
        orderRow.append('<td data-field="status" data-user-id><select id="order-status' + order.id + '"></select></td>');
        orderRow.append('<td data-field="user">' + order.user.username + '</td>');   
        orderRow.append('<td data-field="products">' + output + '</td>'); 
        orderRow.append('<td><button class="delete-button" data-user-id="' + order.id +'">Delete</button><button class="update-button" data-user-id="' + order.id +'">Edit</button></td>');
        orderRow.append('</tr>');
        orderTableBody.append(orderRow);

        orderStatusSelect = document.getElementById('order-status'+order.id);

        let index = 1;
        for( const status of orderStatusArray) {

            var option   = document.createElement('option');
            option.text  = status;
            option.value = index;
            orderStatusSelect.add(option);  

            if(option.text == order.orderStatus) {
                orderStatusSelect.value = index;
            }
            index ++;
        }
    
        orderStatusSelect.disabled = true;
    });
}


function loadOrderStatus(){
    const accessToken = localStorage.getItem('token');

    if(accessToken) {
        $.ajax({
            type: 'GET',
            url: `http://localhost:8080/api/orders/orderstatus`,
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function(stati) {
                orderStatusArray = stati;
            },
            error: function(err) {
                console.error(err);
            }
        });
    }
}


// Handle user deletions (using event delegation)
$('.user-list-table').on('click', '.delete-button', function () {
    const orderId = $(this).data('user-id'); // Get the user ID from the data attribute
    if (confirm(`Are you sure you want to delete order with ID ${orderId}?`)) {
        // If the user confirms the deletion, call the deleteUser function
        deleteProduct(orderId);
        loadOrders();
    }
});

function deleteProduct(orderId) {
    const accessToken = localStorage.getItem('token');

    if(accessToken) {
        $.ajax({
            type: 'DELETE',
            url: `http://localhost:8080/api/orders/` + orderId,
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function(data) {
                loadOrders();
            },
            error: function(err) {
                console.error(err);
            }
        });
    }
}

// Handle user updates
$('.user-list-table').on('click', '.update-button', function () {
    const orderId = $(this).data('user-id');
    const editButton = $(this);

    const select = document.getElementById('order-status' + orderId);

    if (editButton.text() === 'Edit') {
        editButton.text('Revert');
        
        const buttons = $('.user-list-table button')
        buttons.each(function() {
            if($(this).data('user-id') != orderId && $(this).hasClass('update-button')) {
                $(this).text('Edit');
                var oldId = $(this).data('user-id');
                const oldSelect = document.getElementById('order-status' + oldId);
                oldSelect.disabled = true;
            }
        });

        select.disabled = false;

    } else if (editButton.text() === 'Revert') {
        // Switch back to edit mode after saving
        editButton.text('Edit');
        select.disabled = true;
        updateOrder(orderId);
    }
});

function updateOrder(orderId) {

    const accessToken = localStorage.getItem('token');

    if (accessToken) {


        const selectHook = document.getElementById('order-status'+orderId);
        const orderStatus = selectHook.options[selectHook.selectedIndex].text;

        var order = orderArray.find((element) => element.id = orderId);

        const orderData = {
            id: order.id,
            orderStatus: orderStatus,
            user: order.user,
            orderedProducts: order.orderedProducts
        }
        $.ajax({
            type: 'PUT',
            url: `http://localhost:8080/api/orders`,
            data: JSON.stringify(orderData), // Send only the field to update
            contentType: 'application/json',
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function (data) {
                console.log('Update successful:', data);
                loadOrders();
            },
            error: function (err) {
                console.error('Error updating user data: ', err);
            }
        });
    }
}