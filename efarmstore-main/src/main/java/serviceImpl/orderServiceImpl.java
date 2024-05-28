package serviceImpl;

import com.cts.agrofarmingstore.model.Order;

import java.util.List;

// Creating interface to define all the methods of Order Service Class so that our business logic is kept hidden
public interface orderServiceImpl {
    // Method for fetching all orders
    public List<Order> getAllOrders();
    // Method for fetching orders by Order id
    public Order getOrderById(Long id);
    //Method for placing order by user id
    public Order placeOrder(Long userId);
    // Method for cancelling order
    public String removeOrder(Long id);
    // Method for calculating discount based on quantity
    public double totalPriceAfterDiscount(int quantity, double price);
    // Method for fetching Orders by user id
    public List<Order> getOrdersByUserId(Long userId);
}
