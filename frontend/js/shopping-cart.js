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

                // Calculate the total amount
                const totalAmount = calculateTotalAmount(data);

                // Update the total amount displayed in the cart
                $('#total-amount').text(`Total: ${totalAmount} EUR`);
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

                // Calculate the total amount
                const totalAmount = calculateTotalAmount(data);

                // Update the total amount displayed in the cart
                $('#total-amount').text(`Total: ${totalAmount} EUR`);
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

                // Check if the product image is a valid URL, otherwise use the placeholder
                const imageSrc = isValidURL(product.image) ? product.image : './images/articles/games/placeholder_game.png';

                return `
                <div class="cart-items-container">
                <!-- Repeat this block for each product -->
                <div class="cart-item">
                  <img src="${imageSrc}" alt="${product.name}" style="width: 125px; height: 125px;">
                  <div class="cart-item-details">
                    <h2>${product.name}</h2>
                    <p>${product.description}</p>
                    <p>Price: ${priceFormatted}</p>
                  </div>
                  <div class="quantity-controls">
                  <button class="quantity-btn minus">-</button>
                  <span class="quantity">1</span>
                  <button class="quantity-btn plus">+</button>
                </div>
                </div>
              </div>              
                    `;
            });
            cartContainer.html(cartItemsHTML.join(''));
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
    
    // Quantity Button Handling
    const quantityButtons = document.querySelectorAll('.quantity-btn');
    const quantityElements = document.querySelectorAll('.quantity');
  
    quantityButtons.forEach((button, index) => {
        button.addEventListener('click', function() {
            let quantity = parseInt(quantityElements[index].textContent, 10);
  
            if (button.classList.contains('plus')) {
                quantity++;
            } else if (button.classList.contains('minus') && quantity > 1) {
                quantity--;
            }
  
            quantityElements[index].textContent = quantity;
        });
    });

    // Function to calculate the total amount
    function calculateTotalAmount(cartData) {
        let total = 0;
        for (const product of cartData.products) {
            total += product.price;
        }
        return total;
    } 
});
