package elearning.project.securityservice;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Value;

//Genearation,validation of the JWT token is done here
//Additionally Operation in claims also done here such as extracting username,validating it etc.
@Service
public class JWTService {
	private String secretKey;

//	private long jwtExpiryTime = 86400000;

	public JWTService() {
		try {
			// Generate a secret key for HMAC SHA-256
			KeyGenerator keygen = KeyGenerator.getInstance("HmacSHA256");
			SecretKey sk = keygen.generateKey();
			// Encoding the key
			secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private Map<String, Object> claims = new HashMap<>();

	// Generate a JWT token for the given username
	public String generateToken(String username) {
		return Jwts.builder().claims().add(claims).subject(username).issuedAt(new Date(System.currentTimeMillis()))
		.expiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000 )).and().signWith(getKey()).compact();
	}

//  Get the secret key for signing the JWT
	private SecretKey getKey() {
//  decoding the token to password
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

//  Extract the username from the JWT token
	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}

//  Extract a specific claim from the JWT token
	private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claimResolver.apply(claims);
	}

//  Extract all claims from the JWT token
	private Claims extractAllClaims(String token) {
		return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
	}

//  Validate the JWT token
	public boolean validateToken(String token, UserDetails userDetails) {
		final String userName = extractUserName(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

//  Check if the JWT token is expired
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

//  Extract the expiration date from the JWT token
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
}