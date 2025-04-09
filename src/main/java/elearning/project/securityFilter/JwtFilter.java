package elearning.project.securityFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import elearning.project.securitypriciples.UserPrincipals;
import elearning.project.securityservice.JWTService;
import elearning.project.securityservice.MyUserDetailsService;

import java.io.IOException;

//its our created filter which verifies the JWT token before the userAuthenticationTokenFilter
@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private JWTService jwtService;

	@Autowired
	ApplicationContext context;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
    //Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJraWxsIiwiaWF0IjoxNzIzMTgzNzExLCJleHAiOjE3MjMxODM4MTl9.5nf7dRzKRiuGurN2B9dHh_M5xiu73ZzWPr6rbhOTTHs
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String email = null;
        //seperating the Bearer from the JWT token
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
		
			token = authHeader.substring(7);
			System.out.println(token);
			email = jwtService.extractUserName(token);
		}

		if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = context.getBean(MyUserDetailsService.class).loadUserByUsername(email);
			System.out.println("inside jwt filter "+userDetails);
			if (jwtService.validateToken(token, (UserPrincipals) userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				//setting the authentication
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}

		filterChain.doFilter(request, response);
	}
}