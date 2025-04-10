package com.lucas.demo.services;


import com.lucas.demo.models.User;
import com.lucas.demo.models.UserRole;
import com.lucas.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        // Injeção já feita pelo @InjectMocks e @ExtendWith(MockitoExtension.class)
    }

    @Test
    void testFindAllUsers() {
        User user1 = new User(1L, "Alice", "pass1", "12345678901", UserRole.USER);
        User user2 = new User(2L, "Bob", "pass2", "10987654321", UserRole.ADMIN);
        List<User> users = Arrays.asList(user1, user2);

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testFindUserByIdFound() {
        Long userId = 1L;
        User user = new User(userId, "Alice", "pass1", "12345678901", UserRole.USER);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findUserById(userId);

        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getId());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testFindUserByIdNotFound() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<User> result = userService.findUserById(userId);

        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testCreateUser() {
        User user = new User(null, "Alice", "plainPassword", "12345678901", UserRole.USER);
        String encodedPassword = "encodedPassword";
        User savedUser = new User(1L, "Alice", encodedPassword, "12345678901", UserRole.USER);

        // Define comportamento do passwordEncoder e do repositório
        when(passwordEncoder.encode("plainPassword")).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        User result = userService.createUser(user);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(encodedPassword, result.getPassword());
        verify(passwordEncoder, times(1)).encode("plainPassword");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUserSuccess() {
        Long userId = 1L;
        User existingUser = new User(userId, "Alice", "oldPassword", "12345678901", UserRole.USER);
        User userDetails = new User(null, "Alice Updated", "newPassword", "12345678901", UserRole.ADMIN);
        String encodedNewPassword = "encodedNewPassword";
        User updatedUser = new User(userId, "Alice Updated", encodedNewPassword, "12345678901", UserRole.ADMIN);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("newPassword")).thenReturn(encodedNewPassword);
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.updateUser(userId, userDetails);

        assertNotNull(result);
        assertEquals("Alice Updated", result.getName());
        assertEquals(UserRole.ADMIN, result.getRole());
        assertEquals(encodedNewPassword, result.getPassword());
        verify(userRepository, times(1)).findById(userId);
        verify(passwordEncoder, times(1)).encode("newPassword");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testUpdateUserNotFound() {
        Long userId = 1L;
        User userDetails = new User(null, "Alice Updated", "newPassword", "12345678901", UserRole.ADMIN);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.updateUser(userId, userDetails)
        );

        assertEquals("User not found with id " + userId, exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;

        // Chama o método de deleção
        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId);
    }
}

