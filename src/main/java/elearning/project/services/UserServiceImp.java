package elearning.project.services;

import elearning.project.exceptions.UserExistsError;
import elearning.project.models.User;
import elearning.project.repositories.UserRepo;
//import elearning.project.securityservice.JWTService;
import elearning.project.securityservice.JWTService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	private UserRepo userRepository;

	@Autowired

	JWTService service;
	@Autowired
	AuthenticationManager authenticationManager;
//    
	public String authentication(User user) {
		System.out.println("Hello bro i am in authentication");
		Authentication a = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		if (a.isAuthenticated()) {

			return service.generateToken(user.getUsername());
		}
		return "failure";

	}
//    //its the encoder which converts the entered password to an encoded text
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	public List<User> getAllUsers() {
		System.out.println("hello in all user");
		return userRepository.findAll();
	}

	public Optional<User> getUserById(Long id) {
		return userRepository.findById(id);
	}

	public User createUser(User user) {
		//encoding process here
		user.setPassword(encoder.encode(user.getPassword()));
		if(userRepository.findUserByUsername(user.getUsername())!=null){
			throw new UserExistsError("User already Exists!");
		}
		return userRepository.save(user);
	}

	public User updateUser(Long id, User userDetails) {
		Optional<User> optionaluser = getUserById(id);
		// Exception must be handled
		User user = optionaluser.get();
		user.setUsername(userDetails.getUsername());
		user.setEmail(userDetails.getEmail());
		user.setRole(userDetails.getRole());
		return userRepository.save(user);
	}

	public void deleteUser(Long id) {
		User user = getUserById(id).get();
		userRepository.delete(user);
	}

	@Override
	public User getusername(String username) {
		System.out.println("I am in the worst situation:)");
		return userRepository.findUserByUsername(username);
	}

}