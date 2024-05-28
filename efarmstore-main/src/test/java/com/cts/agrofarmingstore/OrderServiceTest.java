package com.cts.agrofarmingstore;


import com.cts.agrofarmingstore.exception.ResourceNotFoundException;
import com.cts.agrofarmingstore.model.*;
import com.cts.agrofarmingstore.repository.*;
import com.cts.agrofarmingstore.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private OrderItemsRepository orderItemsRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private OrderItems orderItem;
    private User user;
    private Product product;
    private List<Cart> cartList;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        product = new Product();
        product.setId(1L);
        product.setStockAvailable(10);

        order = new Order();
        order.setId(1L);
        order.setTotalQuantity(5);
        order.setTotalPrice(50.0);
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setStatus("Success");

        orderItem = new OrderItems();
        orderItem.setQuantity(2);
        orderItem.setPrice(10.0);
        orderItem.setOrder(order);
        orderItem.setProduct(product);

        cartList = new ArrayList<>();
        cartList.add(new Cart(2, user, product));
    }

    @Test
    void testGetAllOrders() {
        when(orderRepository.findAll()).thenReturn(List.of(order));

        List<Order> result = orderService.getAllOrders();

        assertEquals(List.of(order), result);
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testGetAllOrdersEmpty() {
        when(orderRepository.findAll()).thenReturn(new ArrayList<>());

        assertThrows(ResourceNotFoundException.class, () -> orderService.getAllOrders());
        verify(orderRepository, times(1)).findAll();
    }

    @Test
    void testGetOrderById() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        Order result = orderService.getOrderById(1L);

        assertEquals(order, result);
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testGetOrderByIdNotFound() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderById(1L));
        verify(orderRepository, times(1)).findById(1L);
    }


    @Test
    void testRemoveOrder() {
        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));
        when(orderItemsRepository.findAll()).thenReturn(List.of(orderItem));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));

        String result = orderService.removeOrder(1L);

        assertEquals("Your order has been cancelled successfully", result);
        verify(orderRepository, times(1)).findById(1L);
        verify(orderItemsRepository, times(1)).findAll();
        verify(productRepository, times(1)).findById(1L);
    }


}