const apiCancelOrderUrl  = 'http://localhost:8080/api/users/deleteOrder';
const apiConfirmOrderUrl = 'http://localhost:8080/api/users/confirmOrder';
const apiLoggedInUserUrl = 'http://localhost:8080/api/users/me';
const apiOpenOrderUrl    = 'http://localhost:8080/api/users/openOrder';
const apiMyOrders        = 'http://localhost:8080/api/users/orders';


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
            type: 'DELETE',
            url: apiCancelOrderUrl,
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function(data) {
                window.location.href = "shoppingcart.html";
            },
            error: function(err) {
                console.log(err);
            }
        });
    }
}

function loadMyOrders() {
    const myOrdersContainer = $('myorders-container')
    const accessToken = localStorage.getItem('token');
    if (accessToken) {
        $.ajax({
            type: 'GET',
            url: apiMyOrders,
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function(data) {
                const product = data;

                const priceFormatted = new Intl.NumberFormat('de-DE', {
                    style: 'currency',
                    currency: 'EUR',
                }).format(product.price);
                
                const imageSrc = isValidURL(product.image) ? product.image: './images/articles/games/placeholder_game.png';

                const myOrdersHTML = `
                    <div class="cart-items-container">
                        <div class="cart-item">
                            <img src="${imageSrc}" alt="${product.name}" style="width: 125px; height: 125px;">
                            <div class="cart-item-details">
                                <h2>${product.name}</h2>
                                <p>Price: ${priceFormatted}</p>
                                <span class="quantity">${quantity}</span>
                            </div>
                        </div>
                    </div>
                `;
                myOrdersContainer.append(myOrdersHTML);
            }
        })
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
                $('.cart-container').replaceWith('<p style="color:white; font-size:20px;" class="mt-4">Deine Bestellung wurde erfolgreich abgeschlossen!</p>');
            },
            error: function(err) {
                console.log(err);
            }
        });
    }
}

function backToUserProfile() {
    window.location.href = "userprofile.html";
}
