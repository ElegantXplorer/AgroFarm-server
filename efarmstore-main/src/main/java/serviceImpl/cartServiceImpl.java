package serviceImpl;

import com.cts.agrofarmingstore.dto.CartDTO;
import com.cts.agrofarmingstore.dto.UpdateCart;
import com.cts.agrofarmingstore.model.Cart;

import java.util.List;

// Creating interface to define all the methods of Cart Service Class so that our business logic is kept hidden
public interface cartServiceImpl {
    // Method for fetching all carts
    public List<Cart> getAllCarts();

    // Methods for creating carts
    public Cart createCart(CartDTO cartdto);
    // Method for removing carts
    public void removeCart(Long id);
    // Methods for updating carts
    public Cart updateCart(Long id, UpdateCart updateCart);
    // Method for fetching carts by user id
    public List<Cart> getCartsByUserId(Long userId);

}
