var addresses = [];

$(document).ready(function () {
    loadCategories();
    loadConsoleGenerations();
    loadProducers();
    loadGenres();
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

                //console.log(data);
                var categories = document.getElementById('create-categories');

                data.forEach( function (element) {
                    var option  = document.createElement('option');
                    option.text  = element.name;
                    option.value = element.id;
                    categories.add(option);
                });

            },
            error: function(err) {
                console.error(err);
            }
        });
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

                //console.log(data);
                var console_generations = document.getElementById('create-console_generations');

                var blank_option   = document.createElement('option');
                blank_option.text  = 'None';
                blank_option.value = 0;
                console_generations.add(blank_option);

                data.forEach( function (element) {
                    var option   = document.createElement('option');
                    option.text  = element.name;
                    option.value = element.id;
                    console_generations.add(option);
                });
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

                //console.log(data);
                var producers = document.getElementById('create-producers');

                data.forEach( function (element) {
                    var option  = document.createElement('option');
                    option.text  = element.name;
                    option.value = element.id;
                    producers.add(option);
                    addresses.push(element.address);
                });

            },
            error: function(err) {
                console.error(err);
            }
        });
    }
}

function loadGenres() {
    const accessToken = localStorage.getItem('token');

    if(accessToken) {
        $.ajax({
            type: 'GET',
            url: `http://localhost:8080/api/genres/all`,
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function(data) {
                //console.log(data);
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

    const catHook = document.getElementById('create-categories');
    const category = catHook.options[catHook.selectedIndex].text;
    const conHook   = document.getElementById('create-console_generations');
    const consoleGeneration = conHook.options[conHook.selectedIndex].text;
    const producerHook = document.getElementById('create-producers');
    const producer = producerHook.options[producerHook.selectedIndex].text;
    
    var productData = {
        name: name,
        description: description,
        price: price,
        image: image,
        tax: tax,
        stock: stock,
        gtin: gtin,
        category: {id: catHook.value, name: category},
        consoleGeneration: {id: conHook.value, name: consoleGeneration},
        producer: {id: producerHook.value, name: producer, address: addresses[producerHook.value]}

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