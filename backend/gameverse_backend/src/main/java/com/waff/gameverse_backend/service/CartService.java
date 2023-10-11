package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.model.Cart;
import com.waff.gameverse_backend.model.CartItem;
import com.waff.gameverse_backend.model.Product;
import com.waff.gameverse_backend.model.User;
import com.waff.gameverse_backend.repository.CartRepository;
import com.waff.gameverse_backend.repository.ProductRepository;
import com.waff.gameverse_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    @Autowired
    public CartService(CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public Cart getCartByUserId(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User not found"));

            return user.getCart();
        } catch (NoSuchElementException ex) {
            logger.error("Error while getting the user's cart: " + ex.getMessage());
            throw ex;
        }
    }

    public Cart addToCart(Long userId, Long productId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User not found"));
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new NoSuchElementException("Product not found"));

            Cart userCart = user.getCart();

            if (userCart == null) {
                userCart = new Cart();
                userCart.setUser(user);
            }

            boolean itemExists = false;

            for (CartItem existingItem : userCart.getProducts()) {
                if (existingItem.getProduct().getId().equals(product.getId())) {
                    existingItem.setAmount(existingItem.getAmount() + 1);
                    itemExists = true;
                    break;
                }
            }

            if (!itemExists) {
                CartItem newItem = new CartItem();
                newItem.setProduct(product);
                newItem.setCart(userCart);
                newItem.setAmount(1);
                userCart.getProducts().add(newItem);
            }

            return cartRepository.save(userCart);
        } catch (NoSuchElementException ex) {
            logger.error("Error while adding a product to the cart: " + ex.getMessage());
            throw ex;
        }
    }

    public Cart removeFromCart(Long userId, Long productId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User not found"));
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new NoSuchElementException("Product not found"));

            Cart userCart = user.getCart();

            if (userCart == null) {
                return null;
            }

            userCart.getProducts().removeIf(item -> item.getProduct().getId().equals(productId));

            return cartRepository.save(userCart);
        } catch (NoSuchElementException ex) {
            logger.error("Error while removing a product from the cart: " + ex.getMessage());
            throw ex;
        }
    }

    public Double calculateCartTotal(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User not found"));
            Cart userCart = user.getCart();

            double total = userCart.getProducts().stream()
                    .mapToDouble(item -> item.getProduct().getPrice() * item.getAmount())
                    .sum();

            return total;
        } catch (NoSuchElementException ex) {
            logger.error("Error while calculating the cart total: " + ex.getMessage());
            throw ex;
        }
    }

    public void clearCart(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User not found"));
            Cart userCart = user.getCart();

            userCart.getProducts().clear();
            cartRepository.save(userCart);
        } catch (NoSuchElementException ex) {
            logger.error("Error while clearing the cart: " + ex.getMessage());
            throw ex;
        }
    }

    public Cart updateCartItemQuantity(Long userId, Long productId, int newQuantity) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User not found"));
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new NoSuchElementException("Product not found"));

            Cart userCart = user.getCart();
            CartItem cartItem = userCart.getProducts().stream()
                    .filter(item -> item.getProduct().getId().equals(productId))
                    .findFirst()
                    .orElse(null);

            if (cartItem != null) {
                cartItem.setAmount(newQuantity);
            }

            return cartRepository.save(userCart);
        } catch (NoSuchElementException ex) {
            logger.error("Error while updating the cart item quantity: " + ex.getMessage());
            throw ex;
        }
    }

    public int getCartItemCount(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User not found"));
            Cart userCart = user.getCart();

            return userCart.getProducts().stream()
                    .mapToInt(CartItem::getAmount)
                    .sum();
        } catch (NoSuchElementException ex) {
            logger.error("Error while getting the cart item count: " + ex.getMessage());
            throw ex;
        }
    }

    public List<CartItem> getCartItems(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User not found"));
            Cart userCart = user.getCart();

            if (userCart == null) {
                return Collections.emptyList();
            }

            return userCart.getProducts();
        } catch (NoSuchElementException ex) {
            logger.error("Error while getting cart items: " + ex.getMessage());
            throw ex;
        }
    }

    public List<Cart> getAll() {
        return cartRepository.findAll();
    }
}
