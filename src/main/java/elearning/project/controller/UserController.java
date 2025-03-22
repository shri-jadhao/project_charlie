package elearning.project.controller;

import elearning.project.models.User;
import elearning.project.securityservice.JWTService;
import elearning.project.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

	@Autowired
	private UserService service;
    @Autowired
    JWTService jwtservice;
	@PostMapping("")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User createdUser = service.createUser(user);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
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
	@GetMapping("/gettoken/{username}")
	public String myhome(@PathVariable String username) {
		System.out.println("In the token generation");
		return jwtservice.generateToken(username);
	}
//	
	@GetMapping("name/{username}")
	public User getusername(@PathVariable String username) {
		return service.getusername(username);
	}

}