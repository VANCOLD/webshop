// Construct the URL based on the userId
const apiCartGetUrl         = 'http://localhost:8080/api/users/cart';
const apiCartReduceUrl      = 'http://localhost:8080/api/users/removeFromCart/';
const apiCartIncreaseUrl    = 'http://localhost:8080/api/users/addToCart/';
const apiOpenOrderUrl       = 'http://localhost:8080/api/users/openOrder';
const apiSaveOrderUrl       = 'http://localhost:8080/api/users/saveOrder';

$(document).ready(function () {
    checkOrders();
    loadData();
});

function checkOrders() {
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
                window.location.href = "order.html";
            },
            error: function(err) {
                console.log(data);
            }
        });
    }
}


function loadData() {
    // Retrieve the access token from local storage
    const accessToken = localStorage.getItem('token');

    // Check if the access token exists in local storage
    if (accessToken) {
        $.ajax({
            type: 'GET',
            url: apiCartGetUrl,
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function(data) {
                // Handle the successful responses
                populateCart(data);

                // Calculate the total amount
                const totalAmount = calculateTotalAmount(data);

                const totalFormatted = new Intl.NumberFormat('de-DE', {
                    style: 'currency',
                    currency: 'EUR',
                }).format(totalAmount);

                // Update the total amount displayed in the cart
                $('#total-amount').text(`Total: ${totalFormatted}`);
            },
            error: function(err) {
                // Handle errors
                console.error('Error getting user cart: ', err);
            }
        });
    } 
}


function populateCart(cartData) {
    const cartContainer = $('.cart-container');

    if (cartData.products.length === 0) {
        cartContainer.html('<p>Your cart is empty.</p>');
    } else {
        // Sort the products by ID
        cartData.products.sort((a, b) => a.id - b.id);

        const productQuantities = new Map();

        // Iterate through the products and update quantities
        cartData.products.forEach(product => {
            const productId = product.id;

            if (productQuantities.has(productId)) {
                // Product already exists in the cart, increase the quantity
                productQuantities.set(productId, productQuantities.get(productId) + 1);
            } else {
                // Product is not in the cart, add it with quantity 1
                productQuantities.set(productId, 1);
            }
        });

        // Clear the cart container before adding items
        cartContainer.empty();

        // Iterate through productQuantities and display each product once with its quantity and buttons
        productQuantities.forEach((quantity, productId) => {
            const product = cartData.products.find(p => p.id === productId);

            if (product) {
                const priceFormatted = new Intl.NumberFormat('de-DE', {
                    style: 'currency',
                    currency: 'EUR',
                }).format(product.price);

                const imageSrc = isValidURL(product.image) ? product.image : './images/articles/games/placeholder_game.png';

                const cartItemHTML = `
                    <div class="cart-items-container">
                        <div class="cart-item">
                            <img src="${imageSrc}" alt="${product.name}" style="width: 125px; height: 125px;">
                            <div class="cart-item-details">
                                <h2>${product.name}</h2>
                                <p>${product.description}</p>
                                <p>Price: ${priceFormatted}</p>
                            </div>
                            <div class="quantity-controls">
                                <button class="quantity-btn minus" data-product-id="${productId}">-</button>
                                <span class="quantity">${quantity}</span>
                                <button class="quantity-btn plus" data-product-id="${productId}">+</button>
                            </div>
                        </div>
                    </div>
                `;

                cartContainer.append(cartItemHTML);
            }
        });

        // Attach event listeners to the Add and Remove buttons using class selectors
        $('.quantity-btn.minus').on('click', function() {
            const productId = $(this).data('product-id');
            reduceProduct(productId);
        });

        $('.quantity-btn.plus').on('click', function() {
            const productId = $(this).data('product-id');
            increaseProduct(productId);
        });

        $('.checkout-button').on('click', function() {
            checkout();
        });
    }
}

function checkout() {
    const accessToken = localStorage.getItem('token');

    // Check if the access token exists in local storage
    if (accessToken) {
        // The access token doesn't exist in local storage, so you can use the URL without the token.
        $.ajax({
            type: 'POST',
            url: apiSaveOrderUrl,
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function(data) {
                window.location.href = "order.html";
            },
            error: function(err) {
                // Handle errors
                console.error('Error getting user cart: ', err);
            }
        }); 
    }
}




function reduceProduct(productId) {
    // Retrieve the access token from local storage
    const accessToken = localStorage.getItem('token');

    // Check if the access token exists in local storage
    if (accessToken) {
    // The access token doesn't exist in local storage, so you can use the URL without the token.
        $.ajax({
            type: 'PUT',
            url: apiCartReduceUrl + productId,
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function(data) {
                loadData();
            },
            error: function(err) {
                // Handle errors
                console.error('Error getting user cart: ', err);
            }
        }); 
    }
}


function increaseProduct(productId) {

    // Retrieve the access token from local storage
    const accessToken = localStorage.getItem('token');

    // Check if the access token exists in local storage
    if (accessToken) {
        // The access token doesn't exist in local storage, so you can use the URL without the token.
        $.ajax({
            type: 'PUT',
            url: apiCartIncreaseUrl + productId,
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function(data) {
                loadData();
            },
            error: function(err) {
                // Handle errors
                console.error('Error getting user cart: ', err);
            }
        }); 
    }
}


// Function to check if a URL is valid
function isValidURL(str) {
    // We use a regular expression to check if the string is a valid URL
    const pattern = new RegExp('^(https?:\\/\\/)?'+ // protocol
        '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|' + // domain name
        '((\\d{1,3}\\.){3}\\d{1,3}))' + // OR ip (v4) address
        '(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*' + // port and path
        '(\\?[;&a-z\\d%_.~+=-]*)?' + // query string
        '(\\#[-a-z\\d_]*)?$', 'i'); // fragment locator
    return !!pattern.test(str);
}


// Function to calculate the total amount
function calculateTotalAmount(cartData) {
    let total = 0;
    for (const product of cartData.products) {
        total += product.price;
    }
    return total;
} 
