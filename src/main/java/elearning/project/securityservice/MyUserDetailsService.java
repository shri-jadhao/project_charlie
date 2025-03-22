package elearning.project.securityservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import elearning.project.models.User;
import elearning.project.repositories.UserRepo;
import elearning.project.securitypriciples.UserPrincipals;

//MyUserDetailsService implements the UserDetailsService(interface) and talks with the DB and finds if the user exists or not and helps in authentication
//This is the file which talks with the database and has a role in authentication of the user by returning the principle.

@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	UserRepo repo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    User user = repo.findUserByUsername(username);
		if (user == null) {
//			System.out.println("Username not found");
			throw new UsernameNotFoundException("username not found");
		}
		return new UserPrincipals(user);  
	}

}
/*
 * Authentication: When a user attempts to log in, Spring Security will use the
 * loadUserByUsername method to retrieve the user details. The StudentPrincipals
 * object contains the user's credentials and authorities, which Spring Security
 * uses to authenticate the user.
 * 
 * Authorization: After authentication, Spring Security uses the
 * StudentPrincipals object to determine what the authenticated user is allowed
 * to do. This includes checking the user's roles and permissions.
 * 
 * User Details: The StudentPrincipals class encapsulates the Student object,
 * providing a way to access user-specific information throughout the security
 * context.
 */