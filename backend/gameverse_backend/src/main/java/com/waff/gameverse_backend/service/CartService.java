package com.waff.gameverse_backend.service;

import com.waff.gameverse_backend.dto.AddProductToCartDto;
import com.waff.gameverse_backend.model.Cart;
import com.waff.gameverse_backend.model.Product;
import com.waff.gameverse_backend.model.User;
import com.waff.gameverse_backend.repository.CartRepository;
import com.waff.gameverse_backend.repository.ProductRepository;
import com.waff.gameverse_backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public Cart addToCart(AddProductToCartDto requestDto) {
        try {
            User user = userRepository.findById(requestDto.getUserId())
                    .orElseThrow(() -> new NoSuchElementException("User not found"));
            Product product = productRepository.findById(requestDto.getProductId())
                    .orElseThrow(() -> new NoSuchElementException("Product not found"));

            Cart userCart = user.getCart();

            if (userCart == null) {
                userCart = new Cart();
                userCart.setUser(user);
                cartRepository.save(userCart);
                user.setCart(userCart);
                userRepository.save(user);
            }

            userCart.getProducts().add(product);
            this.userRepository.save(user);
            return userCart;
        } catch (NoSuchElementException ex) {
            logger.error("Error while adding a product to the cart: " + ex.getMessage());
            throw ex;
        }
    }

    public Cart removeFromCart(AddProductToCartDto requestDto) {
        try {
            User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new NoSuchElementException("User not found"));
            Product product = productRepository.findById(requestDto.getProductId())
                .orElseThrow(() -> new NoSuchElementException("Product not found"));

            Cart userCart = user.getCart();

            if (userCart == null) {
                userCart = new Cart();
                userCart.setUser(user);
                cartRepository.save(userCart);
                user.setCart(userCart);
                userRepository.save(user);
                return userCart;
            }

            userCart.getProducts().remove(product);
            this.userRepository.save(user);
            return userCart;
        } catch (NoSuchElementException ex) {
            logger.error("Error while adding a product to the cart: " + ex.getMessage());
            throw ex;
        }
    }

    public Double calculateCartTotal(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User not found"));
            Cart userCart = user.getCart();

            return userCart.getProducts().stream()
                .mapToDouble(Product::getPrice)
                .sum();
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

    public int getProductCount(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NoSuchElementException("User not found"));
            Cart userCart = user.getCart();

            return userCart.getProducts().stream()
                    .mapToInt(Product::getStock)
                    .sum();
        } catch (NoSuchElementException ex) {
            logger.error("Error while getting the cart item count: " + ex.getMessage());
            throw ex;
        }
    }

    public List<Product> getProducts(Long userId) {
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
