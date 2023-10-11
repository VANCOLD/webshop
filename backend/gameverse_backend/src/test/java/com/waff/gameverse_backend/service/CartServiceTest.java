package com.waff.gameverse_backend.service;
import com.waff.gameverse_backend.dto.CartDto;
import com.waff.gameverse_backend.model.*;
import com.waff.gameverse_backend.repository.CartRepository;
import com.waff.gameverse_backend.repository.ProductRepository;
import com.waff.gameverse_backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
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
import static org.mockito.Mockito.*;

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
        Cart userCart = new Cart();
        Product product = new Product();
        product.setId(productId);

//        when(cartService.getCartByUserId(userId)).thenReturn(Collections.singletonList(userCart));
//        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        Cart updatedCart = cartService.addToCart(userId, productId);

        // Assert
        assertThat(updatedCart).isNotNull();
        assertThat(updatedCart.getProducts()).hasSize(1);
        assertThat(updatedCart.getProducts().get(0).getProduct()).isEqualTo(product);
    }

    @Test
    void removeFromCartShouldRemoveProductFromCart() {
        // Arrange
        Long userId = 1L;
        Long productId = 2L;
        Cart userCart = new Cart();
        Product product = new Product();
        product.setId(productId);
        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        userCart.setProducts(Collections.singletonList(cartItem));

        //when(cartService.getCartByUserId(userId)).thenReturn(Collections.singletonList(userCart));

        // Act
        Cart updatedCart = cartService.removeFromCart(userId, productId);

        // Assert
        assertThat(updatedCart).isNotNull();
        assertThat(updatedCart.getProducts()).isEmpty();
    }

    @Test
    void testGetUserCart() {
        // Arrange
        Long userId = 4L;
        Cart userCart = new Cart();
        //when(cartService.getCartByUserId(userId)).thenReturn(Collections.singletonList(userCart));

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
        Product product1 = new Product();
        Product product2 = new Product();
        product1.setId(productId1);
        product2.setId(productId2);

        Cart userCart = new Cart();
        userCart.setProducts(new ArrayList<>());
        //when(cartService.getCartByUserId(userId)).thenReturn(Collections.singletonList(userCart));
        when(productRepository.findById(productId1)).thenReturn(Optional.of(product1));
        when(productRepository.findById(productId2)).thenReturn(Optional.of(product2));

        // Act
        cartService.addToCart(userId, productId1);
        cartService.addToCart(userId, productId2);
        Double total = cartService.calculateCartTotal(userId);

        // Assert
        assertNotNull(total);
        assertEquals(
                2 * productRepository.findById(productId1).get().getPrice()
                        + productRepository.findById(productId2).get().getPrice(),
                total,
                0.001
        );
    }

    @Test
    void testClearCart() {
        // Arrange
        Long userId = 6L;
        Long productId1 = 5L;
        Long productId2 = 6L;
        Product product1 = new Product();
        Product product2 = new Product();
        product1.setId(productId1);
        product2.setId(productId2);

        Cart userCart = new Cart();
        userCart.setProducts(new ArrayList<>());
        //when(cartService.getCartByUserId(userId)).thenReturn(Collections.singletonList(userCart));
        when(productRepository.findById(productId1)).thenReturn(Optional.of(product1));
        when(productRepository.findById(productId2)).thenReturn(Optional.of(product2));

        // Act
        cartService.addToCart(userId, productId1);
        cartService.addToCart(userId, productId2);
        cartService.clearCart(userId);
        Cart cart = cartService.getCartByUserId(userId);

        // Assert
        assertNull(cart);
    }

    @Test
    void testUpdateCartItemQuantity() {
        // Arrange
        Long userId = 7L;
        Long productId = 7L;
        int newQuantity = 3;
        Product product = new Product();
        product.setId(productId);

        Cart userCart = new Cart();
        userCart.setProducts(new ArrayList<>());
        //when(cartService.getCartByUserId(userId)).thenReturn(Collections.singletonList(userCart));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        // Act
        cartService.addToCart(userId, productId);
        Cart updatedCart = cartService.updateCartItemQuantity(userId, productId, newQuantity);

        // Assert
        assertNotNull(updatedCart);
        assertTrue(
                updatedCart.getProducts().stream()
                        .anyMatch(item -> item.getProduct().getId().equals(productId) && item.getAmount() == newQuantity)
        );
    }

    @Test
    void testGetCartItemCount() {
        // Arrange
        Long userId = 8L;
        Long productId1 = 8L;
        Long productId2 = 9L;
        Product product1 = new Product();
        Product product2 = new Product();
        product1.setId(productId1);
        product2.setId(productId2);

        Cart userCart = new Cart();
        userCart.setProducts(new ArrayList<>());
        //when(cartService.getCartByUserId(userId)).thenReturn(Collections.singletonList(userCart));
        when(productRepository.findById(productId1)).thenReturn(Optional.of(product1));
        when(productRepository.findById(productId2)).thenReturn(Optional.of(product2));

        // Act
        cartService.addToCart(userId, productId1);
        cartService.addToCart(userId, productId2);
        int itemCount = cartService.getCartItemCount(userId);

        // Assert
        assertEquals(2, itemCount);
    }

    @Test
    void testGetCartItems() {
        // Arrange
        Long userId = 9L;
        Long productId1 = 10L;
        Long productId2 = 11L;
        Product product1 = new Product();
        Product product2 = new Product();
        product1.setId(productId1);
        product2.setId(productId2);

        Cart userCart = new Cart();
        userCart.setProducts(new ArrayList<>());
        //when(cartService.getCartByUserId(userId)).thenReturn(Collections.singletonList(userCart));
        when(productRepository.findById(productId1)).thenReturn(Optional.of(product1));
        when(productRepository.findById(productId2)).thenReturn(Optional.of(product2));

        // Act
        cartService.addToCart(userId, productId1);
        cartService.addToCart(userId, productId2);
        List<CartItem> cartItems = cartService.getCartItems(userId);

        // Assert
        assertNotNull(cartItems);
        assertEquals(2, cartItems.size());
    }
}