const apiGetItemsUrl = 'http://localhost:8080/api/users/cart';
const apiLoggedInUserUrl = 'http://localhost:8080/api/users/me';
const apiSaveOrder       = 'http://localhost:8080/api/users/saveOrder';


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
            url: apiGetItemsUrl,
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function(data) {
                const orderContainer = $('.order-bind');
                var orderHtml = `
                <div id="order-data mt-4 mb-4">
                    <h2>Order Details</h2>
                `;

            // Sort the products by ID
            data.products.sort((a, b) => a.id - b.id);

            const productQuantities = new Map();

            // Iterate through the products and update quantities
            data.products.forEach(product => {
                const productId = product.id;

                if (productQuantities.has(productId)) {
                    // Product already exists in the cart, increase the quantity
                    productQuantities.set(productId, productQuantities.get(productId) + 1);
                } else {
                    // Product is not in the cart, add it with quantity 1
                    productQuantities.set(productId, 1);
                }
            });

            // Iterate through productQuantities and display each product once with its quantity and buttons
            productQuantities.forEach((quantity, productId) => {
                const product = data.products.find(p => p.id === productId);

                if (product) {
                    const priceFormatted = new Intl.NumberFormat('de-DE', {
                        style: 'currency',
                        currency: 'EUR',
                    }).format(product.price * quantity);



                    orderHtml += `
                    <div class="mb-4 mt-4">
                    <h4 class="text-color">Product: ${product.name} - Quantity: ${quantity} - Total Price: ${priceFormatted}</h4>
                    </div>`;
                }
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

function createOrder    () {
    // Retrieve the access token from local storage
    const accessToken = localStorage.getItem('token');

    // Check if the access token exists in local storage
    if (accessToken) {
        $.ajax({
            type: 'POST',
            url: apiSaveOrder,
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