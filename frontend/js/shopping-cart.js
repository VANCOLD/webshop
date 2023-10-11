// shopping-cart.js

// Function to fetch cart data from the backend
function fetchCartData(userId) {
    return fetch(`/api/cart/get?userId=${userId}`)
        .then(response => response.json());
}

// Function to update cart item quantity
function updateCartItemQuantity(userId, productId, newQuantity) {
    return fetch(`/api/cart/update?userId=${userId}&productId=${productId}&quantity=${newQuantity}`, {
        method: 'PUT'
    })
    .then(response => response.json());
}

// Function to remove item from the cart
function removeCartItem(userId, productId) {
    return fetch(`/api/cart/remove?userId=${userId}&productId=${productId}`, {
        method: 'DELETE'
    })
    .then(response => response.json());
}

// Function to update the cart total on the page
function updateCartTotal(total) {
    const totalElement = document.getElementById('cart-total');
    totalElement.innerText = `$${total.toFixed(2)}`;
}

// Event listener for the "Add to Cart" button
document.getElementById('add-to-cart-button').addEventListener('click', () => {
    const userId = getUserId();
    const productId = getProductId();

    // Call the backend to add the product to the cart
    fetch(`/api/cart/add?userId=${userId}&productId=${productId}`, {
        method: 'POST'
    })
    .then(response => response.json())
    .then(data => {
        // Update the cart view with the new data
        updateCartView(userId);
    });
});

// Event listener for quantity input buttons
document.querySelectorAll('.btn-quantity').forEach(button => {
    button.addEventListener('click', () => {
        const userId = getUserId(); // Implement your logic to get the user ID here
        const productId = getProductId(); // Implement your logic to get the product ID here
        const quantityElement = document.querySelector('.quantity');

        let newQuantity = parseInt(quantityElement.textContent, 10);

        if (button.classList.contains('minus')) {
            newQuantity = Math.max(1, newQuantity - 1);
        } else if (button.classList.contains('plus')) {
            newQuantity += 1;
        }

        // Call the backend to update the cart item quantity
        updateCartItemQuantity(userId, productId, newQuantity)
            .then(() => {
                quantityElement.textContent = newQuantity;
                updateCartView(userId);
            });
    });
});

// Event listener for "Remove Item" button
document.querySelectorAll('.btn-remove').forEach(button => {
    button.addEventListener('click', () => {
        const userId = getUserId(); // Implement your logic to get the user ID here
        const productId = getProductId(); // Implement your logic to get the product ID here

        // Call the backend to remove the item from the cart
        removeCartItem(userId, productId)
            .then(() => {
                updateCartView(userId);
            });
    });
});

// Function to update the cart view (items, total, etc.)
function updateCartView(userId) {
    fetchCartData(userId)
        .then(cartData => {
            // Implement your logic to update the HTML with cart data here
            // For example, you can loop through cartData and generate the HTML for each item.
            
            // Calculate the cart total
            const total = cartData.total;
            updateCartTotal(total);
        });
}

// Implement a function to get the user ID (e.g., from a session or authentication)
function getUserId() {
    // Implement your logic to get the user ID here
}

// Implement a function to get the product ID
function getProductId() {
    // Implement your logic to get the product ID here
}

// Call the initial update of the cart view when the page loads
document.addEventListener('DOMContentLoaded', () => {
    const userId = getUserId(); // Implement your logic to get the user ID here
    updateCartView(userId);
});

// Example: Fetch product data from your back-end (replace with actual API call)
async function fetchProducts() {
    try {
        const response = await fetch('/api/products');
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error fetching products:', error);
    }
}

// Call this function to initialize your products
async function initializeProducts() {
    const products = await fetchProducts();
    // Process and display products
}

// Call the initializeProducts function to load products when the page loads
initializeProducts();

