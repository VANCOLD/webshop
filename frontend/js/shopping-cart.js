// Construct the URL based on the userId
const apiCartGetUrl         = 'http://localhost:8080/api/users/cart';
const apiCartReduceUrl      = 'http://localhost:8080/api/users/removeFromCart/';
const apiCartIncreaseUrl    = 'http://localhost:8080/api/users/addToCart/';
const apiOpenOrderUrl       = 'http://localhost:8080/api/users/openOrder';
const apiSaveOrderUrl       = 'http://localhost:8080/api/users/saveOrder';

var itemCount = 0;

$(document).ready(function () {
    loadData();
});

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
            itemCount += 1;
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


                const cartItemHTML = `
                    <div class="cart-items-container">
                        <div class="cart-item">
                            <img id="product-image${productId}" src="" alt="${product.name}" style="width: 125px; height: 125px;">
                            <div class="cart-item-details">
                                <h2>${product.name}</h2>
                                <p>${product.description}</p>
                                <p>Price: ${priceFormatted}</p>
                            </div>
                            <div class="quantity-controls">
                                <button class="quantity-btn minus" onclick="reduceProduct(${productId})">-</button>
                                <span class="quantity">${quantity}</span>
                                <button class="quantity-btn plus" onclick="increaseProduct(${productId})">+</button>
                            </div>
                        </div>
                    </div>
                `;

                cartContainer.append(cartItemHTML);
                const imageElement = document.getElementById('product-image'+productId);

                // Assuming you received the Blob from a fetch request
                fetch('http://localhost:8080/files/' + product.image)
                .then(response => {
                    if (response.status >= 200 && response.status < 300) {
                        return response.blob();
                    } else {
                        console.error('HTTP error! Status:', response.status);
                        // Handle the error appropriately
                    }
                })
                .then(blobData => {
                    const imageUrl = URL.createObjectURL(blobData);
                    imageElement.src = imageUrl;
                    
                })
                .catch(error => {
                    console.error('Error loading the image:', error);
                    imageElement.src = './images/articles/games/placeholder_game.png';
                });
            }
        });

        $('.checkout-button').on('click', function() {
            if(itemCount > 0)
                checkout();
        });
    }
}

function checkout() {
    window.location.href = "order.html";
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


// Function to calculate the total amount
function calculateTotalAmount(cartData) {
    let total = 0;
    for (const product of cartData.products) {
        total += product.price;
    }
    return total;
} 