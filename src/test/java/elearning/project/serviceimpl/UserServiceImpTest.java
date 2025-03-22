package elearning.project.serviceimpl;
import elearning.project.exceptions.UserExistsError;
import elearning.project.models.User;
import elearning.project.repositories.UserRepo;
import elearning.project.securityservice.JWTService;
import elearning.project.services.UserServiceImp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImpTest {

    @InjectMocks
    private UserServiceImp userService;

    @Mock
    private UserRepo userRepo;

    @Mock
    private JWTService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private BCryptPasswordEncoder encoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        when(userRepo.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(users, result);
    }

    @Test
    public void testGetUserById() {
        User user = new User();
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserById(1L);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setPassword("plainPassword");
        when(encoder.encode("plainPassword")).thenReturn("encodedPassword");
        when(userRepo.save(any(User.class))).thenReturn(user);

        User result = userService.createUser(user);

        assertEquals(user, result);
        assertEquals("encodedPassword", user.getPassword());
    }

    @Test
    public void testCreateUser_UserExists() {
        User user = new User();
        user.setUsername("existingUser");

        // Mock the repository to return a user when findUserByUsername is called
        when(userRepo.findUserByUsername("existingUser")).thenReturn(user);

        // Assert that the UserExistsError is thrown
        assertThrows(UserExistsError.class, () -> userService.createUser(user));
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepo.save(any(User.class))).thenReturn(user);

        User result = userService.updateUser(1L, user);

        assertEquals(user, result);
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        when(userRepo.findById(anyLong())).thenReturn(Optional.of(user));
        doNothing().when(userRepo).delete(any(User.class));

        userService.deleteUser(1L);

        verify(userRepo, times(1)).delete(user);
    }

    @Test
    public void testGetUsername() {
        User user = new User();
        when(userRepo.findUserByUsername(anyString())).thenReturn(user);

        User result = userService.getusername("username");

        assertEquals(user, result);
    }

    @Test
    public void testAuthentication() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(jwtService.generateToken(anyString())).thenReturn("token");

        String result = userService.authentication(user);

        assertEquals("token", result);
    }
}
