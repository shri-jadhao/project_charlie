package elearning.project.controller;

import elearning.project.models.User;
import elearning.project.securityservice.JWTService;
import elearning.project.services.UserService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

	@Autowired
	private UserService service;
	@Autowired
	JWTService jwtservice;
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User createdUser = service.createUser(user);

		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);

		// return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
		// System.out.println("Creating"+service.createUser(user));
		// return null;
		// return new ResponseEntity<>(createdUser,HttpStatus.CREATED);

	}

	@GetMapping("")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = service.getAllUsers();
		return ResponseEntity.ok(users);
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		User user = service.getUserById(id).get();
		return ResponseEntity.ok(user);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
		User updatedUser = service.updateUser(id, userDetails);
		return ResponseEntity.ok(updatedUser);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		service.deleteUser(id);
		return ResponseEntity.noContent().build();
	}

//generates the JWT token through username
	@PostMapping("/gettoken/{username}")
	public String myhome(@PathVariable String username) {
		System.out.println("donr!!!");
		if (service.findByUserName(username) == null) {
			throw new UsernameNotFoundException("User is not available");
		}
		return jwtservice.generateToken(username);
	}

//	
<<<<<<< HEAD
	@GetMapping("name/{username}")
=======
	@GetMapping("{username}")
>>>>>>> aec97648dfbccb43c859679465efa38f77017d07
	public User getusername(@PathVariable String username) {
		return service.getusername(username);
	}

}