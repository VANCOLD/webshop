package com.waff.gameverse_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waff.gameverse_backend.dto.AddProductToCartDto;
import com.waff.gameverse_backend.model.Cart;
import com.waff.gameverse_backend.service.CartService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void addToCart() throws Exception {
        // Arrange
        AddProductToCartDto addProductToCartDto = new AddProductToCartDto();
        addProductToCartDto.setUserId(1L);
        addProductToCartDto.setProductId(1L);

        // Create a mock of the CartService
        CartService cartService = mock(CartService.class);
        Cart testCart = new Cart(); // Create a test Cart
        when(cartService.addToCart(any(AddProductToCartDto.class))).thenReturn(testCart);

        // Create the CartController and inject the mock CartService
        CartController cartController = new CartController(cartService, null);

        // Use the mockMvc to test the controller
        mockMvc.perform(MockMvcRequestBuilders.put("/api/cart/add")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(addProductToCartDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.products", Matchers.hasSize(0))); // You should customize this to match the response structure
    }

    @Test
    void getCart() throws Exception {
        // Arrange
        Long userId = 1L;
        CartService cartService = mock(CartService.class);
        Cart testCart = new Cart(); // Create a test Cart
        when(cartService.getCartByUserId(userId)).thenReturn(testCart);

        CartController cartController = new CartController(cartService, null);

        // Use the mockMvc to test the controller
        mockMvc.perform(MockMvcRequestBuilders.get("/api/cart/" + userId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.items", Matchers.hasSize(0))); // You should customize this to match the response structure
    }

    @Test
    void removeFromCart() throws Exception {
        // Arrange
        AddProductToCartDto addProductToCartDto = new AddProductToCartDto();
        CartService cartService = mock(CartService.class);
        Cart testCart = new Cart(); // Create a test Cart
        when(cartService.removeFromCart(addProductToCartDto)).thenReturn(testCart);

        CartController cartController = new CartController(cartService, null);

        // Use the mockMvc to test the controller
        mockMvc.perform(MockMvcRequestBuilders.put("/api/cart/remove")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(addProductToCartDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.products", Matchers.hasSize(0))); // You should customize this to match the response structure
    }

    @Test
    void clearCart() throws Exception {
        // Arrange
        long userId = 1L;
        CartService cartService = mock(CartService.class);

        CartController cartController = new CartController(cartService, null);

        // Use the mockMvc to test the controller
        mockMvc.perform(MockMvcRequestBuilders.put("/api/cart/clear/" + userId))
                .andExpect(status().isOk());
    }

    @Test
    void calculateCartTotal() throws Exception {
        // Arrange
        Long userId = 1L;
        CartService cartService = mock(CartService.class);
        Double total = 100.0; // Your expected total
        when(cartService.calculateCartTotal(userId)).thenReturn(total);

        CartController cartController = new CartController(cartService, null);

        // Use the mockMvc to test the controller
        mockMvc.perform(MockMvcRequestBuilders.get("/api/cart/total/" + userId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(total)));
    }

    @Test
    void getCartItemCount() throws Exception {
        // Arrange
        Long userId = 1L;
        CartService cartService = mock(CartService.class);
        Integer itemCount = 5; // Your expected item count
        when(cartService.getProductCount(userId)).thenReturn(itemCount);

        CartController cartController = new CartController(cartService, null);

        // Use the mockMvc to test the controller
        mockMvc.perform(MockMvcRequestBuilders.get("/api/cart/count/" + userId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(itemCount)));
    }

    @Test
    void getCartItems() throws Exception {
        // Arrange
        Long userId = 1L;
        CartService cartService = mock(CartService.class);

        // Create a list of SimpleProductDto
        List<SimpleProductDto> cartItems = new ArrayList<>();
        cartItems.add(new SimpleProductDto(1L, "Product 1", "Description 1", 10.0, "image1.jpg", 5, 100, "1234567890"));
        cartItems.add(new SimpleProductDto(2L, "Product 2", "Description 2", 15.0, "image2.jpg", 8, 75, "9876543210"));

        // Use doReturn to specify behavior
        doReturn(cartItems).when(cartService).getProducts(userId);

        CartController cartController = new CartController(cartService, null);

        // Use the mockMvc to test the controller
        mockMvc.perform(MockMvcRequestBuilders.get("/api/cart/items/" + userId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(cartItems.size())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Product 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is("Product 2")));
    }

}
