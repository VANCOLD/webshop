package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.CartDto;
import com.waff.gameverse_backend.model.Cart;
import com.waff.gameverse_backend.model.CartItem;
import com.waff.gameverse_backend.model.Product;
import com.waff.gameverse_backend.model.User;
import com.waff.gameverse_backend.repository.CartRepository;
import com.waff.gameverse_backend.repository.ProductRepository;
import com.waff.gameverse_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    @Autowired
    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public Cart getCartByUserId(Long userId) {
        // Use the findByUserId method from the repository to fetch carts by user ID
        User user = this.userRepository.findById(userId).orElseThrow(
                () -> new NoSuchElementException("User existiert nicht!"));

        // You can handle the result here, e.g., return the list of carts
        return user.getCart();
    }

    public Cart addToCart(Long userId, Long productId) {
        // Retrieve the user's cart by userId
        Cart userCart = this.getCartByUserId(userId);

        // Check if the user has a cart, if not, create a new one
        if (userCart == null) {
            userCart = new Cart();
            userCart.setUser(userRepository.findById(userId).orElse(null)); // Assuming you have a userRepository
        }

        // Retrieve the product by productId
        Product product = productRepository.findById(productId).orElse(null); // Assuming you have a productRepository

        // Create a flag to check if the item already exists in the cart
        boolean itemExists = false;

        // Iterate through the cart items to find if the product already exists
        for (CartItem existingItem : userCart.getProducts()) {
            if (existingItem.getProduct().getId().equals(product.getId())) {
                // If the product already exists, update the amount
                existingItem.setAmount(existingItem.getAmount() + 1);
                itemExists = true;
                break;  // No need to continue iterating
            }
        }

        if (!itemExists) {
            // If the item doesn't exist in the cart, create a new one
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setCart(userCart);
            newItem.setAmount(1); // Set the amount to 1 for a new item
            userCart.getProducts().add(newItem);
        }

        // Save the updated cart
        return cartRepository.save(userCart);
    }

    public Cart removeFromCart(Long userId, Long productId) {
        // Retrieve the user's cart by userId
        Cart userCart = this.getCartByUserId(userId);

        if (userCart == null) {
            // Handle the case where the user's cart doesn't exist
            return null;
        }

        // Check if the product is in the cart
        userCart.getProducts().removeIf(item -> item.getProduct().getId().equals(productId));
        // Save the updated cart
        return cartRepository.save(userCart);
    }

    public Double calculateCartTotal(Long userId) {
        // Retrieve the user's cart by userId
        Cart userCart = this.getCartByUserId(userId);

        // Calculate the total price based on the products in the cart
        double total = userCart.getProducts().stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getAmount())
                .sum();
        return total;
    }

    public void clearCart(Long userId) {
        // Retrieve the user's cart by userId
        Cart userCart = this.getCartByUserId(userId);

        // Clear the products from the cart
        userCart.getProducts().clear();
        // Save the updated cart
        cartRepository.save(userCart);
    }

    public Cart updateCartItemQuantity(Long userId, Long productId, int newQuantity) {
        // Retrieve the user's cart by userId
        Cart userCart = this.getCartByUserId(userId);
        // Find the cart item corresponding to the product
        CartItem cartItem = userCart.getProducts().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst().orElse(null);

        if (cartItem != null) {
            // Update the cart item's quantity
            cartItem.setAmount(newQuantity);
        }

        // Save the updated cart
        return cartRepository.save(userCart);
    }

    public int getCartItemCount(Long userId) {
        // Retrieve the user's cart by userId
        Cart userCart = this.getCartByUserId(userId);

        // Calculate the total number of items in the cart
        return userCart.getProducts().stream()
                .mapToInt(CartItem::getAmount)
                .sum();
    }

    public List<CartItem> getCartItems(Long userId) {
        // Retrieve the user's cart by userId
        Cart userCart = this.getCartByUserId(userId);

        if (userCart == null) {
            // Handle the case where the user's cart doesn't exist
            return Collections.emptyList(); // Return an empty list if the cart doesn't exist
        }

        // Return the list of cart items
        return userCart.getProducts();
    }

    public List<Cart> getAll(){
        return this.cartRepository.findAll();
    }

}

