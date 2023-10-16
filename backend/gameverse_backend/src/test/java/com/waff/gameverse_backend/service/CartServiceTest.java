package com.waff.gameverse_backend.service;
import com.waff.gameverse_backend.dto.AddProductToCartDto;
import com.waff.gameverse_backend.model.*;
import com.waff.gameverse_backend.repository.ProductRepository;
import com.waff.gameverse_backend.repository.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@DirtiesContext
@Import(CartService.class)
@ActiveProfiles("test")
@Disabled
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void addToCartShouldAddProductToCart() {
        // Arrange
        Long userId = 1L;
        Long productId = 2L;
        AddProductToCartDto requestDto = new AddProductToCartDto();
        requestDto.setUserId(userId);
        requestDto.setProductId(productId);

        // Act
        Cart updatedCart = cartService.addToCart(requestDto);

        // Assert
        assertThat(updatedCart).isNotNull();
        assertThat(updatedCart.getProducts()).hasSize(1);
        assertThat(updatedCart.getProducts().get(0).getId()).isEqualTo(productId);
    }

    @Test
    void removeFromCartShouldRemoveProductFromCart() {
        // Arrange
        Long userId = 1L;
        Long productId = 2L;
        AddProductToCartDto requestDto = new AddProductToCartDto();
        requestDto.setUserId(userId);
        requestDto.setProductId(productId);

        // Act
        Cart updatedCart = cartService.removeFromCart(requestDto);

        // Assert
        assertThat(updatedCart).isNotNull();
        assertThat(updatedCart.getProducts()).isEmpty();
    }

    @Test
    void testGetUserCart() {
        // Arrange
        Long userId = 4L;

        // Act
        Cart cart = cartService.getCartByUserId(userId);

        // Assert
        assertNotNull(cart);
        assertEquals(userId, cart.getUser().getId());
    }

    @Test
    void testCalculateCartTotal() {
        // Arrange
        Long userId = 5L;
        Long productId1 = 3L;
        Long productId2 = 4L;
        AddProductToCartDto requestDto1 = new AddProductToCartDto();
        requestDto1.setUserId(userId);
        requestDto1.setProductId(productId1);

        AddProductToCartDto requestDto2 = new AddProductToCartDto();
        requestDto2.setUserId(userId);
        requestDto2.setProductId(productId2);

        // Act
        cartService.addToCart(requestDto1);
        cartService.addToCart(requestDto2);
        Double total = cartService.calculateCartTotal(userId);

        // Assert
        assertNotNull(total);
        // Update the expected total based on your product prices and quantities
        assertEquals(0.0, total, 0.001); // Update with the correct value
    }

    @Test
    void testClearCart() {
        // Arrange
        Long userId = 6L;

        // Act
        cartService.clearCart(userId);
        Cart cart = cartService.getCartByUserId(userId);

        // Assert
        assertNull(cart);
    }

    @Test
    void testGetProductCount() {
        // Arrange
        Long userId = 8L;
        Long productId1 = 8L;
        Long productId2 = 9L;
        AddProductToCartDto requestDto1 = new AddProductToCartDto();
        requestDto1.setUserId(userId);
        requestDto1.setProductId(productId1);

        AddProductToCartDto requestDto2 = new AddProductToCartDto();
        requestDto2.setUserId(userId);
        requestDto2.setProductId(productId2);

        // Act
        cartService.addToCart(requestDto1);
        cartService.addToCart(requestDto2);
        int itemCount = cartService.getProductCount(userId);

        // Assert
        assertEquals(2, itemCount);
    }

    @Test
    void testGetProducts() {
        // Arrange
        Long userId = 9L;
        Long productId1 = 10L;
        Long productId2 = 11L;
        AddProductToCartDto requestDto1 = new AddProductToCartDto();
        requestDto1.setUserId(userId);
        requestDto1.setProductId(productId1);

        AddProductToCartDto requestDto2 = new AddProductToCartDto();
        requestDto2.setUserId(userId);
        requestDto2.setProductId(productId2);

        // Act
        cartService.addToCart(requestDto1);
        cartService.addToCart(requestDto2);
        List<Product> cartItems = cartService.getProducts(userId);

        // Assert
        assertNotNull(cartItems);
        assertEquals(2, cartItems.size());
    }
}