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
        url: 'http://localhost:8080/categories', // Replace with your backend URL
        success: function (categories) {
            const categoriesContainer = $('.categories');
            categoriesContainer.empty();

            categories.forEach(function (category) {
                const categoryItem = $('<div class="category-item">');
                categoryItem.text(category.name);
                categoryItem.data('category-id', category.id);

                categoriesContainer.append(categoryItem);
            });
        },
        error: function (err) {
            console.error('Error loading categories: ', err);
        }
    });
}

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
                productCard.append('<p>Price: $' + product.price + '</p>');
                productCard.append(imageElement);

                productsContainer.append(productCard);


            });
        },
        error: function (err) {
            console.error('Error loading products: ', err);
        }
    });
}
