package elearning.project.services;

<<<<<<< HEAD
import java.util.List;
import java.util.Optional;
=======
import elearning.project.exceptions.UserExistsError;
import elearning.project.models.User;
import elearning.project.repositories.UserRepo;
import elearning.project.securityservice.JWTService;
>>>>>>> aec97648dfbccb43c859679465efa38f77017d07

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import elearning.project.email.EmailServiceImpl;
import elearning.project.exceptions.UserNotFoundException;
import elearning.project.models.User;
import elearning.project.repositories.UserRepo;
import elearning.project.securityservice.JWTService;

@Service
public class UserServiceImp implements UserService {

<<<<<<< HEAD
	@Autowired
	private UserRepo userRepository;
	
	@Autowired
	private EmailServiceImpl emailserviceimpl;
=======
>>>>>>> aec97648dfbccb43c859679465efa38f77017d07

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);

<<<<<<< HEAD
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
=======
    @Autowired
    private UserRepo userRepository;
>>>>>>> aec97648dfbccb43c859679465efa38f77017d07

    @Autowired
    private JWTService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

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

<<<<<<< HEAD
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
=======
    public List<User> getAllUsers() {
        logger.info("Fetching all users");
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        logger.info("Fetching user with ID: {}", id);
        return userRepository.findById(id);
    }
>>>>>>> aec97648dfbccb43c859679465efa38f77017d07

    public User createUser(User user) {
        logger.info("Creating user with username: {}", user.getUsername());
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

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
            return null;
        }
    }

    public void deleteUser(Long id) {
        logger.info("Deleting user with ID: {}", id);
        Optional<User> optionalUser = getUserById(id);
        if (optionalUser.isPresent()) {
            userRepository.delete(optionalUser.get());
        } else {
            logger.error("User with ID: {} not found", id);
        }
    }

    @Override
    public User getusername(String username) {
        logger.info("Fetching user by username: {}", username);
        return userRepository.findUserByUsername(username);
    }

	@Override
	public User findByUserName(String username) {
		  System.out.println("Done!");
		  return userRepository.findUserByUsername(username);
	}

}