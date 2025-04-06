//package elearning.project.controller;
//
//import elearning.project.models.User;
//import elearning.project.securityservice.JWTService;
//import elearning.project.services.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//public class UserControllerTest {
//
//    @InjectMocks
//    private UserController userController;
//
//    @Mock
//    private UserService userService;
//
//    @Mock
//    private JWTService jwtService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testCreateUser() {
//        User user = new User();
//        when(userService.createUser(any(User.class))).thenReturn(user);
//
//        ResponseEntity<User> response = userController.createUser(user);
//
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
//        assertEquals(user, response.getBody());
//    }
//
//    @Test
//    public void testGetAllUsers() {
//        List<User> users = Arrays.asList(new User(), new User());
//        when(userService.getAllUsers()).thenReturn(users);
//
//        ResponseEntity<List<User>> response = userController.getAllUsers();
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(users, response.getBody());
//    }
//
//    @Test
//    public void testGetUserById() {
//        User user = new User();
//        when(userService.getUserById(anyLong())).thenReturn(Optional.of(user));
//
//        ResponseEntity<User> response = userController.getUserById(1L);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(user, response.getBody());
//    }
//
//    @Test
//    public void testUpdateUser() {
//        User user = new User();
//        when(userService.updateUser(anyLong(), any(User.class))).thenReturn(user);
//
//        ResponseEntity<User> response = userController.updateUser(1L, user);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(user, response.getBody());
//    }
//
//    @Test
//    public void testDeleteUser() {
//        doNothing().when(userService).deleteUser(anyLong());
//
//        ResponseEntity<Void> response = userController.deleteUser(1L);
//
//        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
//    }
//
//    @Test
//    public void testGenerateToken() {
//        String token = "token";
//        when(jwtService.generateToken(anyString())).thenReturn(token);
//
//        String response = userController.getToken("username");
//
//        assertEquals(token, response);
//    }
//
//    @Test
//    public void testGetUsername() {
//        User user = new User();
//        when(userService.getusername(anyString())).thenReturn(user);
//
//        User response = userController.getusername("username");
//
//        assertEquals(user, response);
//    }
//}
