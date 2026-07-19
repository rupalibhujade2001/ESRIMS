package com.enterprise.auth_service.security;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration}")
	private long expiration;

	public String generateToken(String email,String role) {
		return Jwts.builder().claim("role", role).subject(email).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + expiration)).signWith(getSignInKey()).compact();

	}
	
	public String extractUserName(String token) {
		return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload().getSubject();
	}
	
	public Date extractExpiration(String token) {
		return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload().getExpiration();
		//return secret;
		
	}
	
	public boolean isTokenExpired(String token) {
		
		return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
		
		
	}
	
	public boolean isTokenValid(String token,UserDetails userDetails) {
		
		return extractUserName(token).equals(userDetails.getUsername()) && !isTokenExpired(token);	
	}

	private SecretKey getSignInKey() {

		return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

	}

}
