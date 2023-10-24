var productId;


$(document).ready(function () {
    const urlParams = new URLSearchParams(window.location.search);
    productId = urlParams.get('productId');
    loadData();
});


function loadData() {

    const productContainer = $('.product-container');

    $.ajax({
        type: 'GET',
        url: `http://localhost:8080/products/${productId}`,
        success: function(data) {
            const product = data;
            const priceFormatted = new Intl.NumberFormat('de-DE', {
                style: 'currency',
                currency: 'EUR',
            }).format(product.price);
            

            const productTemplate = $(`
                <div class='container h-auto pb-5'>
                    <h2 class='text-center pt-3 mb-1 text-color' style='margin: 20px;'>${product.name}</h2>
                    <div class='product-container d-flex align-items-center' style='margin-top: 20px;'>
                        <img id="product-image" class='product-image col-lg-5 w-25 h-25 mx-4' src='' alt='${product.name}'>
                        <div class="m-4">
                            <p class='product-description'>
                                ${product.description}
                            </p>
                            <div class='product-price'>
                                ${priceFormatted}
                            </div>
                            <div class='product-category'>
                                Category: ${product.category.name}
                            </div>
                            <div class='product-stock'>
                                Stock: ${product.stock}
                            </div>
                            <div>
                                <button class='btn btn-buy-now'
                                    type='button' onclick='addToCart()'>Add to Cart
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            `);
        
            productContainer.append(productTemplate);
            const imageElement = document.getElementById('product-image');

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

}

function addToCart() {                               

  // Retrieve the access token from local storage
  const accessToken = localStorage.getItem('token');

  // Check if the access token exists in local storage
  if (accessToken) {
      $.ajax({
          type: 'PUT',
          url: 'http://localhost:8080/api/users/addToCart/' + productId,
          headers: {
              'Authorization': `Bearer ${accessToken}`
          },
          success: function(data) {
              alert('produkt hinzugefügt');
          },
          error: function(err) {
              // Handle errors
              console.error('Error getting user cart: ', err);
          }
      });
  }
}
