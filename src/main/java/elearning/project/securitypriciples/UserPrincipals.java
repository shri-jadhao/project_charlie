package elearning.project.securitypriciples;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import elearning.project.models.User;


//Generally this class is used to check if the details given in the login form mathces the actual values in the DB
public class UserPrincipals implements UserDetails {
    private User user;
    public UserPrincipals(User user) {
    	this.user=user;
    }
    //Verifies the roles of the login person
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));
    }
    //gets the password from the spring security form
	@Override
	public String getPassword() {
		return user.getPassword();
	}
    //gets the username from the spring security form
	@Override
	public String getUsername() {
		System.out.println(user.getUsername());
		return user.getUsername();
	}
	


}
