var productId;

$(document).ready(function () {
    const urlParams = new URLSearchParams(window.location.search);
    productId = urlParams.get('productId');
    loadData();
});

function loadData() {
    const productContainer = $('.product-container');
    // const product = productData.products.find(p => p.id === productId);
    const accessToken = localStorage.getItem('token');

    $.ajax({
        type: 'GET',
        url: `http://localhost:8080/api/products/${productId}`,
        headers: {
            'Authorization': `Bearer ${accessToken}`
        },
        success: function(data) {
            const product = data;
        
            const priceFormatted = new Intl.NumberFormat('de-DE', {
                style: 'currency',
                currency: 'EUR',
            }).format(product.price);
            
            const imageSrc = isValidURL(product.image) ? product.image : './images/articles/games/placeholder_game.png';
        
            const productTemplate = `
                <div class="container h-auto pb-5">
                                    <h2 class="text-center pt-3 mb-1" style="margin: 20px;">${product.name}</h2>
                                    <div class="product-container d-flex align-items-center" style="margin-top: 20px;">
                                        <img class="product-image col-lg-5" src="${imageSrc}" alt="${product.name}">
                                        <div>
                                            <p class="product-description">
                                            ${product.description}
                                            </p>
                                            <div class="product-price">
                                                ${priceFormatted}
                                            </div>
                                            <div class="product-category">
                                                Category: ${product.category.name}
                                            </div>
                                            <div class="product-stock">
                                                Stock: ${product.stock}
                                            </div>
                                            <div>
                                                <button class="btn btn-buy-now"
                                                    type="button" onclick="addToCart()">Add to Cart
                                                </button>
                                                
                                            </div>
                                        </div>
                                    </div>
                                </div>
            `;
        
            productContainer.append(productTemplate);
        }
        
    });

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
              alert("produkt hinzugefügt");
          },
          error: function(err) {
              // Handle errors
              console.error('Error getting user cart: ', err);
          }
      });
  }
}