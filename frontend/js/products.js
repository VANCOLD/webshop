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


function saveProduct() {

    const form = document.querySelector('form');
    const formData = {
        name: form.querySelector('input[name="name"]').value,
        description: form.querySelector('input[name="description"]').value,
        price: Number(form.querySelector('input[name="price"]').value),
        image: form.querySelector('input[name="image"]').value,
        tax: Number(form.querySelector('input[name="tax"]').value),
        stock: Number(form.querySelector('input[name="stock"]').value),
        gtin: form.querySelector('input[name="gtin"]').value,
    };
    
    var productData = {
        name: formData.name,
        description: formData.description,
        price: formData.price,
        image: formData.image,
        tax: formData.tax,
        stock: formData.stock,
        gtin: formData.gtin,
        category: {name: "Games"},
        producer: {name: "Nintendo", address: { city: "Kyoto", country: "Japan", postalCode: "601-8501", street: "11-1 Hokotate-cho"}}
    };
    console.log(productData);

    const accessToken = localStorage.getItem('token');

    // Add a headers object to the AJAX call.
    const headers = {
        // 'Authorization': `Bearer ${localStorage.getItem('token')}`
        'Authorization': `Bearer ${accessToken}`
    };
    // Make an AJAX call to the backend method that saves a new product.
    $.ajax({
      type: 'POST',
      url: 'http://localhost:8080/api/products',
      headers: headers,
      contentType: 'application/json',
      data: JSON.stringify(productData),
      success: function() {
        // Display a message to the user that the product has been saved successfully.
        alert('Product saved successfully!');
      },
      error: function(err) {
        // Log the error to the console.
        console.error('Error saving product: ', err);
      }
    });
}

function createProduct() {
    window.open('/createProduct.html', '_blank');
}