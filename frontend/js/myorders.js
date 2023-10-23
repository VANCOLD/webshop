const apiMyOrders        = 'http://localhost:8080/api/users/orders';

$(document).ready(function () {
    loadMyOrders();
})

function loadMyOrders() {

    const myOrdersContainer = $('.myorders-container');
    const accessToken = localStorage.getItem('token');
    if (accessToken) {
        $.ajax({
            type: 'GET',
            url: apiMyOrders,
            headers: {
                'Authorization': `Bearer ${accessToken}`
            },
            success: function(data) {
                const orders = data;
                

                for (const order of orders) {
                    
                    console.log(order)

                    var myOrdersHTML = `
                    <div class="myorders-container">
                    <div class="cart-item">
                    <div class="cart-item-details">
                    <h2>${order.orderStatus}</h2>
                    <div>`;

                                        
                    var printedItems = []
                    for(orderedItem of order.orderedProducts) {

                        if(!printedItems.includes(orderedItem)) {

                            const priceFormatted = new Intl.NumberFormat('de-DE', {
                                style: 'currency',
                                currency: 'EUR',
                            }).format(orderedItem.price);

                            myOrdersHTML += `
                            <h3  class='pt-3 mb-1' style='margin: 20px;'>
                            ${orderedItem.name}
                            </h2>
                            </div>
                            <div class="m-4">
                                <p class='product-description'>
                                    ${orderedItem.description}
                                </p>
                            </div>

                            <div class='product-price'>
                                Price: ${priceFormatted}
                            </div>

                            <div class='product-tax'>
                                Tax: ${orderedItem.tax}
                            </div>

                            <div class='product-amount'>
                                Amount: ${orderedItem.amount}
                            </div>
                                </div>
                            </div>
                            </div>`;
                            printedItems.push(orderedItem);
                        }
                    }
                    myOrdersContainer.append(myOrdersHTML);
                }
            }
        });
    }
}