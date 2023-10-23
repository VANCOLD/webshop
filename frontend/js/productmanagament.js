var addresses = [];

$(document).ready(function () {
    loadCategories();
    loadEsrb();
    loadConsoleGenerations();
    loadProducers();
    loadProducts();

    const fileInput = document.getElementById("create-image");

    fileInput.addEventListener("change", function () {
      const selectedFile = fileInput.files[0];

      if (selectedFile) {
        const blobUrl = URL.createObjectURL(selectedFile);
        $("#create-image-preview").attr("src", blobUrl);
      } else {
        $("#create-image-preview").attr("src", "./images/articles/games/placeholder_game.png");
      }
    });


    const fileInput2 = document.getElementById("update-image");

    fileInput2.addEventListener("change", function () {
      const selectedFile = fileInput2.files[0];

      if (selectedFile) {
        const blobUrl = URL.createObjectURL(selectedFile);
        $("#update-image-preview").attr("src", blobUrl);
      } else {
        $("#update-image-preview").attr("src", "./images/articles/games/placeholder_game.png");
      }
    });
});

function loadCategories() {
    const accessToken = localStorage.getItem('token');

    if(accessToken) {
        $.ajax({
            type: 'GET',
            url: `http://localhost:8080/api/categories/all`,
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function(data) {

                var categories = document.getElementById('create-categories');
                var categories_update = document.getElementById('update-categories');
                categories_update.disabled = false;

                categories_update.addEventListener("change", function() {
                    // Your code to handle the selection change goes here
                    if(categories_update.value == 1) {

                        $('#update-esrb').show();
                        $('#label-update-esrb').show();
                        $('#update-console_generations').show();
                        $('#label-update-console_generations').show();


                    } else if(categories_update.value == 2) {

                        $('#update-esrb').hide();
                        $('#label-update-esrb').hide();
                        $('#update-console_generations').show();
                        $('#label-update-console_generations').show();

                    } else {

                        $('#update-esrb').hide();
                        $('#label-update-esrb').hide();
                        $('#update-console_generations').hide();
                        $('#label-update-console_generations').hide();

                    }
                });

                // Add an event listener for the "change" event
                categories.addEventListener("change", function () {
                    // Your code to handle the selection change goes here
                    if(categories.value == 1) {

                        $('#create-esrb').show();
                        $('#label-esrb').show();
                        $('#create-console_generations').show();
                        $('#label-console_generations').show();


                    } else if(categories.value == 2) {

                        $('#create-esrb').hide();
                        $('#label-esrb').hide();
                        $('#create-console_generations').show();
                        $('#label-console_generations').show();

                    } else {

                        $('#create-esrb').hide();
                        $('#label-esrb').hide();
                        $('#create-console_generations').hide();
                        $('#label-console_generations').hide();


                    }
                });

                data.forEach( function (element) {
                    var option  = document.createElement('option');
                    option.text  = element.name;
                    option.value = element.id;
                    categories.add(option);

                    var option2  = document.createElement('option');
                    option2.text  = element.name;
                    option2.value = element.id;
                    categories_update.add(option2);
                });
                categories_update.disabled = true;

            },
            error: function(err) {
                console.error(err);
            }
        });
    }
}

function loadEsrb() {
    const accessToken = localStorage.getItem('token');

    if(accessToken) {
        $.ajax({
            type: 'GET',
            url: `http://localhost:8080/api/products/esrb`,
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function(data) {
                //console.log(data);
                var esrb = document.getElementById('create-esrb');
                var esrb_update = document.getElementById('update-esrb');
                esrb_update.disabled = false;
                data.forEach( function (element) {

                    var option  = document.createElement('option');
                    option.text  = element;
                    esrb.add(option);

                    var option2  = document.createElement('option');
                    option2.text  = element;
                    esrb_update.add(option2);
                });
                esrb_update.disabled = true;
            }
        })
    }
}

function loadConsoleGenerations() {
    const accessToken = localStorage.getItem('token');

    if(accessToken) {
        $.ajax({
            type: 'GET',
            url: `http://localhost:8080/api/console_generations/all`,
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function(data) {

                var console_generations         = document.getElementById('create-console_generations');
                var console_generations_update  = document.getElementById('update-console_generations');
                console_generations_update.disabled = false;
                
                data.forEach( function (element) {
                    var option   = document.createElement('option');
                    option.text  = element.name;
                    option.value = element.id;
                    console_generations.add(option);
                    
                    var option2   = document.createElement('option');
                    option2.text  = element.name;
                    option2.value = element.id;
                    console_generations_update.add(option2);
                });

                console_generations_update.disabled = true;
            },
            error: function(err) {
                console.error(err);
            }
        });
    }
}

function loadProducers() {
    const accessToken = localStorage.getItem('token');

    if(accessToken) {
        $.ajax({
            type: 'GET',
            url: `http://localhost:8080/api/producers/all`,
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function(data) {

                var producers = document.getElementById('create-producers');
                var producers_update = document.getElementById('update-producers');
                producers_update.disabled = false;

                data.forEach( function (element) {
                    var option  = document.createElement('option');
                    option.text  = element.name;
                    option.value = element.id;
                    producers.add(option);

                    var option2  = document.createElement('option');
                    option2.text  = element.name;
                    option2.value = element.id;
                    producers_update.add(option2);

                    addresses.push(element.address);
                });
                producers_update.disabled = true;
            },
            error: function(err) {
                console.error(err);
            }
        });
    }
}

function saveProduct() {
 
    const name        = $('#create-name').val();
    const description = $('#create-description').val();
    const price       = $('#create-price').val();
    const image       = $('#create-image').val();
    const tax         = $('#create-tax').val();
    const stock       = $('#create-stock').val();
    const gtin        = $('#create-gtin').val();
    const available   = $('#create-available').val();

    const catHook = document.getElementById('create-categories');
    const category = catHook.options[catHook.selectedIndex].text;
    const conHook   = document.getElementById('create-console_generations');
    const consoleGeneration = conHook.options[conHook.selectedIndex].text;
    const producerHook = document.getElementById('create-producers');
    const producer = producerHook.options[producerHook.selectedIndex].text;
    const esrbHook = document.getElementById('create-esrb');
    const esrbRating = esrbHook.options[esrbHook.selectedIndex].text;

    var productData = {
        name: name,
        description: description,
        price: price,
        image: image,
        tax: tax,
        stock: stock,
        gtin: gtin,
        available: available + 'T00:00:00',
        category: {id: catHook.value, name: category},
        consoleGeneration: category == 'Games' || category == 'Consoles' ? {id: conHook.value, name: consoleGeneration} : {id: null, name: "None"},
        producer: {id: producerHook.value, name: producer, address: addresses[producerHook.value]},
        esrbRating: category == 'Games' ? esrbRating : 'No Rating'

    };

    const accessToken = localStorage.getItem('token');


    // Make an AJAX call to the backend method that saves a new product.
    $.ajax({
      type: 'POST',
      url: 'http://localhost:8080/api/products',
      data: JSON.stringify(productData),
      contentType: 'application/json',
      headers: {
          'Authorization': `Bearer ${accessToken}`
      },
      success: function(response) {
        saveImage(accessToken, response.id);
      },
      error: function(err) {
        // Log the error to the console.
        console.error('Error saving product: ', err);
      }
    });

}

function saveImage(accessToken, id) {

    const fileInput = document.getElementById("create-image");
    const selectedFile = fileInput.files[0];
    const formData = new FormData();
    formData.append("file", selectedFile);

    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/api/files',
        data: formData,
        processData: false,  // Prevent automatic processing
        contentType: false,  // Let the browser set content type
        headers: {
            'Authorization': `Bearer ${accessToken}`
        },
        success: function(response) {

            const imageReference = response.reference;

            const name        = $('#create-name').val();
            const description = $('#create-description').val();
            const price       = $('#create-price').val();
            const image       = $('#create-image').val();
            const tax         = $('#create-tax').val();
            const stock       = $('#create-stock').val();
            const gtin        = $('#create-gtin').val();
            const available   = $('#create-available').val();
        
            const catHook = document.getElementById('create-categories');
            const category = catHook.options[catHook.selectedIndex].text;
            const conHook   = document.getElementById('create-console_generations');
            const consoleGeneration = conHook.options[conHook.selectedIndex].text;
            const producerHook = document.getElementById('create-producers');
            const producer = producerHook.options[producerHook.selectedIndex].text;
            const esrbHook = document.getElementById('create-esrb');
            const esrbRating = esrbHook.options[esrbHook.selectedIndex].text;
        
            var productData = {
                id: id,
                name: name,
                description: description,
                price: price,
                tax: tax,
                stock: stock,
                gtin: gtin,
                available: available + 'T00:00:00',
                category: {id: catHook.value, name: category},
                consoleGeneration: category == 'Games' || category == 'Consoles' ? {id: conHook.value, name: consoleGeneration} : {id: null, name: "None"},
                producer: {id: producerHook.value, name: producer, address: addresses[producerHook.value]},
                esrbRating: category == 'Games' ? esrbRating : 'No Rating',
                image: imageReference
        
            };
            

            // Make an AJAX call to the backend method that saves a new product.
            $.ajax({
                type: 'PUT',
                url: 'http://localhost:8080/api/products',
                data: JSON.stringify(productData),
                contentType: 'application/json',
                headers: {
                    'Authorization': `Bearer ${accessToken}`
                },
                success: function() {
                    alert("Produkt erfolgreich erstellt!");
                },
                error: function(err) {
                // Log the error to the console.
                console.error('Error saving product: ', err);
                }
            });
        },
        error: function(err) {
            console.error(err);
        }
    });
}

function loadProducts() {
    const accessToken = localStorage.getItem('token');

    if (accessToken) {
        $.ajax({
            type: 'GET',
            url: 'http://localhost:8080/api/products/all',
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function (data) {
                // Handle the successful response, e.g., populate the product list
                populateProductList(data);
            },
            error: function (err) {
                console.error('Error loading products: ', err);
            }
        });
    }
}

function populateProductList(products) {
    const userTableBody = $('#product-list-tbody');
    userTableBody.empty();

    products.forEach(function (product) {
        let productRow = `
            <tr data-user-id="${product.id}">
            <td>${product.id}</td>
            <td data-field="name" data-user-id>${product.name}</td>
            <td data-field="description">.....</td>
            <td data-field="price">${product.price}</td>
            <td data-field="tax">${product.tax}</td>
            <td data-field="stock">${product.stock}</td>
            <td data-field="gtin">${product.gtin}</td>
            <td data-field="available">${product.available}</td>
            <td data-field="esrb">${product.esrbRating}</td>
            <td data-field="category">${product.category.name}</td>
            <td data-field="congen">${product.consoleGeneration.name}</td>
            <td data-field="producer">${product.producer.name}</td>
            <td>
            <button class="delete-button" data-user-id="${product.id}">Delete</button>
            <button class="update-button" data-user-id="${product.id}">Edit</button>
            </td>
            </tr>`;
        userTableBody.append(productRow);
    });
}

// Handle user updates
$('.user-list-table').on('click', '.update-button', function () {
    const productId = $(this).data('user-id');
    const editButton = $(this);


    if (editButton.text() === 'Edit') {
        editButton.text('Revert');
        
        const buttons = $('.user-list-table button')
        buttons.each(function() {
            if($(this).data('user-id') != productId && $(this).hasClass('update-button')) {
                $(this).text('Edit');
            }
        });

        $('#update-name').prop('disabled',false);
        $('#update-description').prop('disabled',false);
        $('#update-price').prop('disabled',false);
        $('#update-tax').prop('disabled',false);
        $('#update-stock').prop('disabled',false);
        $('#update-gtin').prop('disabled',false);
        $('#update-image').prop('disabled',false);
        $('#update-available').prop('disabled',false);
        $('#update-categories').prop('disabled',false);
        $('#update-esrb').prop('disabled',false);
        $('#update-producers').prop('disabled',false);
        $('#update-console_generations').prop('disabled',false);
        $('#update-button').prop('disabled',false);

        loadProduct(productId);

    } else if (editButton.text() === 'Revert') {
        updateProductData(userId);
        resetUpdateForm();

        // Switch back to edit mode after saving
        editButton.text('Edit');

    }
});

function resetUpdateForm() {

    $('#update-id').val('');
    $('#update-name').val('');
    $('#update-description').val('');
    $('#update-price').val('');
    $('#update-tax').val('');
    $('#update-stock').val('');
    $('#update-gtin').val('');
    $('#update-available').val('');
    $('#update-categories').val('');
    $('#update-esrb').val('');
    $('#update-producers').val('');
    $('#update-console_generations').val('');
    $('#update-image').val('');
    $("#update-image-preview").attr("src", "./images/articles/games/placeholder_game.png");

    $('#update-name').prop('disabled',true);
    $('#update-description').prop('disabled',true);
    $('#update-price').prop('disabled',true);
    $('#update-tax').prop('disabled',true);
    $('#update-stock').prop('disabled',true);
    $('#update-gtin').prop('disabled',true);
    $('#update-available').prop('disabled',true);
    $('#update-categories').prop('disabled',true);
    $('#update-esrb').prop('disabled',true);
    $('#update-producers').prop('disabled',true);
    $('#update-console_generations').prop('disabled',true);
    $('#update-image').prop('disabled',true);
    $('#update-button').prop('disabled',true);
}


// Handle user deletions (using event delegation)
$('.user-list-table').on('click', '.delete-button', function () {
    const productId = $(this).data('user-id'); // Get the user ID from the data attribute
    if (confirm(`Are you sure you want to delete product with ID ${productId}?`)) {
        // If the user confirms the deletion, call the deleteUser function
        deleteProduct(productId);
        resetUpdateForm();
    }
});


function deleteProduct(productId) {
    const accessToken = localStorage.getItem('token');

    if (accessToken) {
        $.ajax({
            type: 'DELETE',
            url: `http://localhost:8080/api/products/${productId}`,
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function (data) {
                // Handle the successful response, e.g., update the user list
                // Now call loadUsers() as a callback function
                loadProducts();
            },
            error: function (err) {
                console.error('Error deleting user: ', err);
            }
        });
    }
}

function loadProduct(productId) {
    const accessToken = localStorage.getItem('token');

    if (accessToken) {
        $.ajax({
            type: 'get',
            url: `http://localhost:8080/api/products/${productId}`,
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function (data) {
                
                $('#update-id').prop('disabled',false);
                $('#update-id').val(data.id);
                $('#update-id').prop('disabled', true);
                $('#update-name').val(data.name);
                $('#update-description').val(data.description);
                $('#update-price').val(data.price);
                $('#update-tax').val(data.tax);
                $('#update-stock').val(data.stock);
                $('#update-gtin').val(data.gtin);
                $('#update-image').val(data.image);
                $('#update-available').val(data.available.split('T')[0]);
                $('#update-categories').val(data.category.id);
                $('#update-esrb').val(data.esrbRating);
                $('#update-producers').val(data.producer.id);
                $('#update-console_generations').val(data.consoleGeneration.id);


                const imageElement = document.getElementById('update-image-preview');

                fetch('http://localhost:8080/files/' + data.image)
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

            },
            error: function (err) {
                console.error('Error deleting user: ', err);
            }
        });
    }
}

function updateProduct() {

    const id          = $('#update-id').val();
    const name        = $('#update-name').val();
    const description = $('#update-description').val();
    const price       = $('#update-price').val();
    const tax         = $('#update-tax').val();
    const image       = document.getElementById('update-image-preview').src
    const stock       = $('#update-stock').val();
    const gtin        = $('#update-gtin').val();
    const available   = $('#update-available').val();

    const catHook = document.getElementById('update-categories');
    const category = catHook.options[catHook.selectedIndex].text;
    const conHook   = document.getElementById('update-console_generations');
    const consoleGeneration = conHook.options[conHook.selectedIndex].text;
    const producerHook = document.getElementById('update-producers');
    const producer = producerHook.options[producerHook.selectedIndex].text;
    const esrbHook = document.getElementById('update-esrb');
    const esrbRating = esrbHook.options[esrbHook.selectedIndex].text;

    var productData = {
        id: id,
        name: name,
        description: description,
        price: price,
        image: image,
        tax: tax,
        stock: stock,
        gtin: gtin,
        available: available + 'T00:00:00',
        category: {id: catHook.value, name: category},
        consoleGeneration: category == 'Games' || category == 'Consoles' ? {id: conHook.value, name: consoleGeneration} : {id: null, name: "None"},
        producer: {id: producerHook.value, name: producer, address: addresses[producerHook.value]},
        esrbRating: category == 'Games' ? esrbRating : 'No Rating'

    };

    const accessToken = localStorage.getItem('token');


    // Make an AJAX call to the backend method that saves a new product.
    $.ajax({
      type: 'PUT',
      url: 'http://localhost:8080/api/products',
      data: JSON.stringify(productData),
      contentType: 'application/json',
      headers: {
          'Authorization': `Bearer ${accessToken}`
      },
      success: function(response) {
        updateImage(accessToken, response.id);
      },
      error: function(err) {
        // Log the error to the console.
        console.error('Error updating product: ', err);
      }
    });
   
}

function updateImage(accessToken) {

    const fileInput = document.getElementById("update-image");
    const selectedFile = fileInput.files[0];
    const formData = new FormData();    
    formData.append("file", selectedFile);

    $.ajax({
        type: 'POST',
        url: 'http://localhost:8080/api/files',
        data: formData,
        processData: false,  // Prevent automatic processing
        contentType: false,  // Let the browser set content type
        headers: {
            'Authorization': `Bearer ${accessToken}`
        },
        success: function(response) {

            const imageReference = response.reference;

            const id          = $('#update-id').val();
            const name        = $('#update-name').val();
            const description = $('#update-description').val();
            const price       = $('#update-price').val();
            const image       = $('#update-image').val();
            const tax         = $('#update-tax').val();
            const stock       = $('#update-stock').val();
            const gtin        = $('#update-gtin').val();
            const available   = $('#update-available').val();
        
            const catHook = document.getElementById('update-categories');
            const category = catHook.options[catHook.selectedIndex].text;
            const conHook   = document.getElementById('update-console_generations');
            const consoleGeneration = conHook.options[conHook.selectedIndex].text;
            const producerHook = document.getElementById('update-producers');
            const producer = producerHook.options[producerHook.selectedIndex].text;
            const esrbHook = document.getElementById('update-esrb');
            const esrbRating = esrbHook.options[esrbHook.selectedIndex].text;
        
            var productData = {
                id: id,
                name: name,
                description: description,
                price: price,
                image: image,
                tax: tax,
                stock: stock,
                gtin: gtin,
                available: available + 'T00:00:00',
                category: {id: catHook.value, name: category},
                consoleGeneration: category == 'Games' || category == 'Consoles' ? {id: conHook.value, name: consoleGeneration} : {id: null, name: "None"},
                producer: {id: producerHook.value, name: producer, address: addresses[producerHook.value]},
                esrbRating: category == 'Games' ? esrbRating : 'No Rating',
                image: imageReference
        
            };
            

            // Make an AJAX call to the backend method that saves a new product.
            $.ajax({
                type: 'PUT',
                url: 'http://localhost:8080/api/products',
                data: JSON.stringify(productData),
                contentType: 'application/json',
                headers: {
                    'Authorization': `Bearer ${accessToken}`
                },
                success: function() {
                    alert("Produkt erfolgreich geupdated!");
                },
                error: function(err) {
                // Log the error to the console.
                console.error('Error saving product: ', err);
                }
            });
        },
        error: function(err) {
            console.error(err);
        }
    });
}