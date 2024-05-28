package serviceImpl;

import com.cts.agrofarmingstore.model.User;

import java.util.List;
// Creating interface to define all the methods of User Service Class so that our business logic is kept hidden
public interface userServiceImpl {
    // Method for fetching all users
    public List<User> getAllUsers();
    // Method for fetching User by id
    public User getUserById(Long id);
    // Method for adding User to the database
    public User addUser(User userObj);
    // Method for deleting User from our database
    public void deleteUser(Long id);
    // Method for updating User in our database
    public User updateUser(User user);
    // Method for logging in the web app
    public User logIn(String email, String password);


}
