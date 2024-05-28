package serviceImpl;

import com.cts.agrofarmingstore.model.Product;

import java.util.List;

// Creating interface to define all the methods of Product Class so that our business logic is kept hidden
public interface productServiceImpl {
    // Method for fetching all Products
    public List<Product> getAllProducts();
    //Method for fetching Products by product Id
    public Product getProductById(Long id);

    // Method for adding product
    public Product addProduct(Product product);
    // Method for deleting product
    public void deleteProduct(Long id);
    // Method for updating Product
    public Product updateProduct(Product product);
    // Method for searching product based on Description
    public List<Product> searchProductsBasedOnDescription(String description);
    //Method for searching product based on Category
    public List<Product> searchProductsBasedOnCategory(String category);
    //Method for searching product based on Product Name
    public List<Product> searchProductsBasedOnName(String name);
    // Method for searching product based on Price Range
    public List<Product> searchProductsBasedOnPriceRange(double price1, double price2);

}
