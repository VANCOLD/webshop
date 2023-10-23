var addresses = [];

$(document).ready(function () {
    loadCategories();
    loadEsrb();
    loadConsoleGenerations();
    loadProducers();
    loadGenres();


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
                });

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
                data.forEach( function (element) {
                    var option  = document.createElement('option');
                    option.text  = element;
                    esrb.add(option);
                });

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

                //console.log(data);
                var console_generations = document.getElementById('create-console_generations');
                
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
        consoleGeneration: category == 'Games' || category == 'Consoles' ? {id: conHook.value, name: consoleGeneration} : null,
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
            console.log(imageReference);

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
                image: image,
                tax: tax,
                stock: stock,
                gtin: gtin,
                available: available + 'T00:00:00',
                category: {id: catHook.value, name: category},
                consoleGeneration: category == 'Games' || category == 'Consoles' ? {id: conHook.value, name: consoleGeneration} : null,
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