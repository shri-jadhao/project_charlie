package elearning.project.services;

import elearning.project.exceptions.UserAlreadyExists;
import elearning.project.models.User;
import elearning.project.repositories.UserRepo;
import elearning.project.securityservice.JWTService;
import elearning.project.email.EmailServiceImpl;
import elearning.project.exceptions.UserNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);

	@Autowired
	private UserRepo userRepository;

	@Autowired
	private JWTService service;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private EmailServiceImpl emailserviceimpl;

	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	@Override
	public String authentication(User user) {
		logger.info("Authenticating user: {}", user.getUsername());
		Authentication a = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		if (a.isAuthenticated()) {
			logger.info("Authentication successful for user: {}", user.getUsername());
			return service.generateToken(user.getUsername());
		}
		logger.warn("Authentication failed for user: {}", user.getUsername());
		return "failure";
	}

	@Override
	public List<User> getAllUsers() {
		logger.info("Fetching all users");
		return userRepository.findAll();
	}

	@Override
	public Optional<User> getUserById(Long id) {
		logger.info("Fetching user with ID: {}", id);
		return userRepository.findById(id);
	}

	@Override
	public User createUser(User user) {
		if (userRepository.findUserByEmail(user.getEmail()) != null) {
			throw new UserAlreadyExists("User with this email already exists!");
		} else if (userRepository.findUserByUsername(user.getUsername()) != null) {
			throw new UserAlreadyExists("Username already exists!,Try Diiferent Username");
		}
		logger.info("Creating user with username: {}", user.getUsername());
		user.setPassword(encoder.encode(user.getPassword()));
		User savedUser = userRepository.save(user);
		emailserviceimpl.sendMailWithAttachment(savedUser);
		return savedUser;
	}

	@Override
	public User updateUser(Long id, User userDetails) {
		logger.info("Updating user with ID: {}", id);
		Optional<User> optionalUser = getUserById(id);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setUsername(userDetails.getUsername());
			user.setEmail(userDetails.getEmail());
			user.setRole(userDetails.getRole());
			return userRepository.save(user);
		} else {
			logger.error("User with ID: {} not found", id);
			throw new UserNotFoundException("User with ID " + id + " not found.");
		}
	}

	@Override
	public void deleteUser(Long id) {
		logger.info("Deleting user with ID: {}", id);
		Optional<User> optionalUser = userRepository.findById(id);
		if (optionalUser.isEmpty()) {
			System.out.println("hello i deletion");
			logger.error("User with ID: {} not found", id);
			throw new UserNotFoundException("User with ID " + id + " not found.");

		} else {
			userRepository.delete(optionalUser.get());
		}
	}

	@Override
	public User getusername(String username) {
		logger.info("Fetching user by username: {}", username);
		return userRepository.findUserByUsername(username);
	}

	@Override
	public User findByUserName(String username) {
		logger.info("Fetching user by username: {}", username);
		return userRepository.findUserByUsername(username);
	}
}