
package elearning.project.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import elearning.project.securityFilter.JwtFilter;
import elearning.project.securityservice.MyUserDetailsService;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	@Autowired
	MyUserDetailsService userDetailsService;
	@Autowired
	JwtFilter jwtfilter;

	@Bean
	//Configures the behavior of the spring security i.e login form ,postman API etccc...
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    System.out.println("hello bro !!! in config file");
	    return http
	            .csrf(csrf -> csrf.disable()) // Disables CSRF protection
	            .authorizeHttpRequests(auth -> auth
//	                sd.requestMatchers("/api/assessments").hasRole("STUDENT")  //role based api access
	                .requestMatchers("/api/user/**","/api/user/gettoken/**","/api/quiz","/api/user","/api/courses","api/courses/**","/api/enrollments","/api/enrollments/**","/api/assessments","/api/assessments/**","/api/submission","api/quiz","api/quiz/**").permitAll() // Allows access to register and login endpoints

//	                .requestMatchers("/api/user/gettoken").permitAll()
	                .anyRequest().authenticated()
	            )
	            .httpBasic(Customizer.withDefaults()) // Enables HTTP Basic authentication
	            .sessionManagement(session -> session
	                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Makes the session stateless
	            )
	            .authenticationProvider(authenticationProvidor())
	            .addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class)  //here we are inserting the jwtfilter before the usernameauthenticationfilter
	            .build();
	}
//    @Bean  -> this bean is responsible for the form details authentication but still handcode
//    public UserDetailsService userDetailsService() {
//    	
//    }
	@Bean
	public AuthenticationProvider authenticationProvidor() {
		System.out.println("Hi bro in authenticationprovider");
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		// provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance()); //-> it
		// creates problem after encoding the password.It expects user to put encoded
		// password inorder to login which is difficult
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		provider.setUserDetailsService(userDetailsService);
		return provider;
    }
//
//	// JWT starts step-1   
	@Bean
//	//its used for generation of the token
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		System.out.println("Hi bro in authentication manager");
		return config.getAuthenticationManager();
	}
//
}


//UsernamePasswordAuthenticationFilter.class -> its responsible for taking the form submissions
// DaoAuthenticationProvider extends auth.. which implments
// AuthenticationProvider which is indirectly extends the AuthenticationProvider
