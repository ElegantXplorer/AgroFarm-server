package com.cts.agrofarmingstore;


import com.cts.agrofarmingstore.dto.CartDTO;
import com.cts.agrofarmingstore.exception.ResourceNotFoundException;
import com.cts.agrofarmingstore.model.Cart;
import com.cts.agrofarmingstore.model.Product;
import com.cts.agrofarmingstore.model.User;
import com.cts.agrofarmingstore.repository.CartRepository;
import com.cts.agrofarmingstore.repository.ProductRepository;
import com.cts.agrofarmingstore.repository.UserRepository;
import com.cts.agrofarmingstore.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CartServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartService cartService;

    private Cart cart;
    private CartDTO cartDTO;
    private User user;
    private Product product;
    private List<Cart> cartList;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        product = new Product();
        product.setId(1L);
        product.setPrice(10.0);

        cartDTO = new CartDTO(2, 1L, 1L);

        cart = new Cart();
        cart.setId(1L);
        cart.setQuantity(2);
        cart.setUser(user);
        cart.setProduct(product);
        cart.setPrice(20.0);

        cartList = new ArrayList<>();
        cartList.add(cart);
    }

    @Test
    void testGetAllCarts() {
        when(cartRepository.findAll()).thenReturn(cartList);

        List<Cart> result = cartService.getAllCarts();

        assertEquals(cartList, result);
        verify(cartRepository, times(1)).findAll();
    }

    @Test
    void testGetAllCartsEmpty() {
        when(cartRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(ResourceNotFoundException.class, () -> cartService.getAllCarts());
        verify(cartRepository, times(1)).findAll();
    }


    @Test
    void testCreateCartProductNotFound() {
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cartService.createCart(cartDTO));
        verify(productRepository, times(1)).findById(cartDTO.getProductId());
        verify(userRepository, times(0)).findById(cartDTO.getUserId());
    }
}