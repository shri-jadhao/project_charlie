package elearning.project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import elearning.project.email.EmailServiceImpl;
import elearning.project.exceptions.UserNotFoundException;
import elearning.project.models.User;
import elearning.project.repositories.UserRepo;
import elearning.project.securityservice.JWTService;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	private UserRepo userRepository;
	
	@Autowired
	private EmailServiceImpl emailserviceimpl;

	@Autowired

	JWTService service;
	@Autowired
	AuthenticationManager authenticationManager;
//    
        @Override
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
		User save =  userRepository.save(user);
		emailserviceimpl.sendMailWithAttachment(save);
		return save;
	}

	public User updateUser(Long id, User userDetails) {
		Optional<User> optionalUser = getUserById(id);
		// Exception must be handled
		if (optionalUser.isEmpty()) {
			throw new UserNotFoundException("User with ID " + id + " not found.");
		}
		User user = optionalUser.get();
		System.out.println("User is waste guy" + user);
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