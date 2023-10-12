$(document).ready(function () {

    // Replace with the actual user ID or retrieve it through authentication
    const userId = 1;

    // Retrieve the access token from local storage
    const accessToken = localStorage.getItem('token');

    // Construct the URL based on the userId
    const apiUrl = 'http://localhost:8080/api/cart/' + userId;

    // Check if the access token exists in local storage
    if (accessToken) {
        $.ajax({
            type: 'GET',
            url: apiUrl,
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function(data) {
                // Handle the successful responses
                populateCart(data);
            },
            error: function(err) {
                // Handle errors
                console.error('Error getting user cart: ', err);
            }
        });
    } else {
        // The access token doesn't exist in local storage, so you can use the URL without the token.
        $.ajax({
            type: 'GET',
            url: apiUrl,
            success: function(data) {
                // Handle the successful response
                populateCart(data);
            },
            error: function(err) {
                // Handle errors
                console.error('Error getting user cart: ', err);
            }
        });
    }

    function populateCart(cartData) {
        const cartContainer = $('.cart-container');
    
        if (cartData.products.length === 0) {
            cartContainer.html('<p>Your cart is empty.</p>');
        } else {
            const cartItemsHTML = cartData.products.map(product => {
                // Format the price as a currency string
                const priceFormatted = new Intl.NumberFormat('de-DE', {
                    style: 'currency',
                    currency: 'EUR',
                }).format(product.price);
    
                return `
                    <div class="cart-item">
                        <img src="${product.image}" alt="${product.name}">
                        <div class="item-details">
                            <h3>${product.name}</h3>
                            <p>${product.description}</p> 
                            <p>Price: ${priceFormatted}</p>
                        </div>
                    </div>
                `;
            });
    
            cartContainer.html(cartItemsHTML.join(''));
        }
    }
});
