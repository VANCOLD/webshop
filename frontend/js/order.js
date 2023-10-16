const apiCancelOrderUrl  = 'http://localhost:8080/api/users/cancelOrder';
const apiConfirmOrderUrl = 'http://localhost:8080/api/users/confirmOrder';
const apiLoggedInUserUrl = 'http://localhost:8080/api/users/me';
const apiOpenOrderUrl    = 'http://localhost:8080/api/users/openOrder';


$(document).ready(function () {
    loadLoggedInUser();
    loadOrderedProducts();
});

function loadLoggedInUser() {

    // Retrieve the access token from local storage
    const accessToken = localStorage.getItem('token');

    // Check if the access token exists in local storage
    if (accessToken) {
        $.ajax({
            type: 'GET',
            url: apiLoggedInUserUrl,
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function(data) {
                const cartContainer = $('.user-bind');
                const anrede = getAnrede(data);
                const userDataHtml = `
                <div id="user-data mt-4 mb-4">
                <h2>User Information</h2>
                <form>
                    <div class="form-group">
                        <label for="name"><h4 class="text-color mt-4 mb-2">Name:</h4></label>
                        <div name="name"><h5>${anrede} ${data.firstname} ${data.lastname}</h5></div>
                    </div>
                    <div class="form-group">
                        <label for="email"><h4 class="text-color mt-4 mb-2">Email:</h4></label>
                        <div name="email"><h5>${data.email}</h5></div>
                    </div>
                    <div class="form-group mb-4">
                        <label for="address"><h4 class="text-color mt-4 mb-2">Address:</h4></label>
                        <div name="address"><h5>${data.address.street}, ${data.address.postalCode} ${data.address.city} in ${data.address.country} </h5></div>
                    </div>
                </form>
            </div>`;
            cartContainer.replaceWith(userDataHtml);
            },
            error: function(err) {
                console.log(err);                
            }
        });
    }
}

function getAnrede(data) {
    if(data.gender == 'Female') {
        return 'Mrs.';
    }

    if(data.gender == 'Male') {
        return 'Mr.';
    }


    return '';
}

function loadOrderedProducts() {
    // Retrieve the access token from local storage
    const accessToken = localStorage.getItem('token');

    // Check if the access token exists in local storage
    if (accessToken) {
        $.ajax({
            type: 'GET',
            url: apiOpenOrderUrl,
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function(data) {
                console.log(data);
                const orderContainer = $('.order-bind');
                var orderHtml = `
                <div id="order-data mt-4 mb-4">
                    <h2>Order Details</h2>
                `;

                data.orderedProducts.forEach((element) => {
                    const total = element.amount * element.price;
                    const totalFormatted = new Intl.NumberFormat('de-DE', {
                        style: 'currency',
                        currency: 'EUR',
                    }).format(total);
                    orderHtml += `
                    <div class="mb-4 mt-4">
                    <h4 class="text-color">Product: ${element.name} - Quantity: ${element.amount} - Total Price: ${totalFormatted} </h4>
                    </div>`;
                });

                orderHtml += `</div>`;
                orderContainer.replaceWith(orderHtml);

            },
            error: function(err) {
                console.log(err);
            }
        });
    }
}

function cancelOrder() {
    // Retrieve the access token from local storage
    const accessToken = localStorage.getItem('token');

    // Check if the access token exists in local storage
    if (accessToken) {
        $.ajax({
            type: 'PUT',
            url: apiCancelOrderUrl,
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function(data) {
                console.log(data);
            },
            error: function(err) {
                window.location.href = "shoppingcart.html";
            }
        });
    }
}


function confirmOrder() {
    // Retrieve the access token from local storage
    const accessToken = localStorage.getItem('token');

    // Check if the access token exists in local storage
    if (accessToken) {
        $.ajax({
            type: 'PUT',
            url: apiConfirmOrderUrl,
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function(data) {
                $('.cart-container').replaceWith('Done');
            },
            error: function(err) {
                console.log(err);
            }
        });
    }
}