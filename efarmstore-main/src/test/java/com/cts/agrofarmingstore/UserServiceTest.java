package com.cts.agrofarmingstore;


import com.cts.agrofarmingstore.exception.ResourceNotFoundException;
import com.cts.agrofarmingstore.model.User;
import com.cts.agrofarmingstore.repository.UserRepository;
import com.cts.agrofarmingstore.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        List<User> userList = new ArrayList<>();
        Mockito.when(userRepository.findAll()).thenReturn(userList);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            userService.getAllUsers();
        });
    }

    @Test
    public void testGetAllUsers_NoUsersPresent() {
        List<User> emptyList = new ArrayList<>();
        Mockito.when(userRepository.findAll()).thenReturn(emptyList);

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            userService.getAllUsers();
        });

        Assertions.assertEquals("No user exists", exception.getMessage());
    }


    @Test
    public void testGetUserById() {
        Long userId = 1L;
        Optional<User> userOptional = Optional.of(new User());
        Mockito.when(userRepository.findById(userId)).thenReturn(userOptional);

        User user = userService.getUserById(userId);

        Assertions.assertNotNull(user);
    }


    @Test
    public void testUpdateUser_ExistingUser() {
        User existingUser = new User();
        existingUser.setId(1L);
        existingUser.setEmailId("test@example.com");
        existingUser.setPassword("password");

        Mockito.when(userRepository.findById(existingUser.getId())).thenReturn(Optional.of(existingUser));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(existingUser);

        User updatedUser = userService.updateUser(existingUser);

        Assertions.assertEquals(existingUser, updatedUser);
    }

    @Test
    public void testDeleteUser_ExistingUser() {
        Long userId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);

        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        userService.deleteUser(userId);

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(userId);
    }

    @Test
    public void testLogIn_CorrectCredentials() {
        String email = "test@example.com";
        String password = "password";
        User user = new User();
        user.setEmailId(email);
        user.setPassword(password);

        List<User> userList = new ArrayList<>();
        userList.add(user);
        Mockito.when(userRepository.findAll()).thenReturn(userList);

        User loggedInUser = userService.logIn(email, password);

        Assertions.assertEquals(user, loggedInUser);
    }

    @Test
    public void testLogIn_IncorrectCredentials() {
        String email = "test@example.com";
        String password = "wrong_password";
        User user = new User();
        user.setEmailId(email);
        user.setPassword("password");

        List<User> userList = new ArrayList<>();
        userList.add(user);
        Mockito.when(userRepository.findAll()).thenReturn(userList);

        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            userService.logIn(email, password);
        });

        Assertions.assertEquals("Your password or email is incorrect", exception.getMessage());
    }
}



