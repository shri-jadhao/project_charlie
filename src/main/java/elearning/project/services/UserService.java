package elearning.project.services;

import elearning.project.models.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User createUser(User user);
    User updateUser(Long id, User userDetails);
    void deleteUser(Long id);
	User getusername(String username);
	public String authentication(User user);
	public User findByUserName(String username);
}