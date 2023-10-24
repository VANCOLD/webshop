const apiCartIncreaseUrl    = 'http://localhost:8080/api/users/addToCart/';

$(document).ready(function () {
    loadCategories(); // Call loadCategories on document ready

    // Event handler for category selection
    $(document).on('click', '.category-item', function () {
        const categoryId = $(this).data('category-id');
        loadProductsByCategory(categoryId);
    });

    // Initially load all products (no categoryId specified)
    loadProductsByCategory(null);
});

function loadCategories() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/categories',
        success: function (categories) {
            const categorySelect = $('#category-select');
            categorySelect.empty();

            // Add an option for each category
            categories.forEach(function (category) {
                categorySelect.append($('<option>', {
                    value: category.id,
                    text: category.name
                }));
            });
        },
        error: function (err) {
            console.error('Error loading categories: ', err);
        }
    });
}

// Event handler for category selection
$('#category-select').on('change', function () {
    const categoryId = $(this).val(); // Get the selected category value
    loadProductsByCategory(categoryId);
});


function loadProductsByCategory(categoryId) {
    const url = categoryId !== null
        ? `http://localhost:8080/products?categoryId=${categoryId}` // Replace with your backend URL
        : `http://localhost:8080/products`; // Replace with your backend URL

    $.ajax({
        type: 'GET',
        url: url,
        success: function (products) {
            const productsContainer = $('.products');
            productsContainer.empty(); // Clear previous content

            products.forEach(function (product) {

                var imageElement = document.createElement('img');
                imageElement.id = 'product-image' + product.id;

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


                const productCard = $('<div class="product-card">');
                productCard.append('<h3>' + product.name + '</h3>');
                productCard.append('<p>' + product.description + '</p>');
                productCard.append('<p class="price">Price: $' + product.price + '</p>');
                productCard.append(imageElement);

                const addToCartButton = $('<button class="add-to-cart">Add to Cart</button>');
                addToCartButton.data('product-id', product.id);

                addToCartButton.on('click', function () {
                    const productId = $(this).data('product-id');
                    addToCart(productId);
                });

                productCard.append(addToCartButton);

                productsContainer.append(productCard);

            });
        },
        error: function (err) {
            console.error('Error loading products: ', err);
        }
    });
}

function addToCart(productId) {
    // Retrieve the access token from local storage
    const accessToken = localStorage.getItem('token');

    // Check if the access token exists in local storage
    if (accessToken) {
        $.ajax({
            type: 'PUT', // Change the HTTP method to PUT to add the product to the cart
            url: apiCartIncreaseUrl + productId,
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function (data) {
                // Handle the successful addition to the cart
                // You can update the cart count or provide feedback to the user
            },
            error: function (err) {
                // Handle errors
                console.error('Error adding product to the cart: ', err);
            }
        });
    }
}
