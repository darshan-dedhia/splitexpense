package edu.darshandedhia.info6250.config;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Component;

import edu.darshandedhia.info6250.pojo.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtils implements Serializable{
	
	private final static long oneMinuteInMilliSeconds = 60000;
	private final static String secretKey = "cqRKvUJh72O20m2H01Tw";
	
	public String generateUserJWTToken(String userName) {
		return generateJWTToken(userName, "tokenGenerator", "AuthToken", 120);
	}
	
	public String generateJWTToken(String id, String issuer, String subject, int minutes) {
		
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

	    long nowMillis = System.currentTimeMillis();
	    Date now = new Date(nowMillis);
	    long ttlMillis = minutes * 60000;

	    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(this.secretKey);
	    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

	    JwtBuilder builder = Jwts.builder().setId(id)
	                                .setIssuedAt(now)
	                                .setSubject(subject)
	                                .setIssuer(issuer)
	                                .signWith(signatureAlgorithm, signingKey);

	   
	    if (ttlMillis >= 0) {
	    long expMillis = nowMillis + ttlMillis;
	        Date exp = new Date(expMillis);
	        builder.setExpiration(exp);
	    }
	    
	    return builder.compact();
		
	}
	
	public Claims verifyJWTToken(String token) {
		 //This line will throw an exception if it is not a signed JWS (as expected)
	    Claims claims = Jwts.parser()         
	       .setSigningKey(DatatypeConverter.parseBase64Binary(this.secretKey))
	       .parseClaimsJws(token).getBody();
		/*
		 * System.out.println("ID: " + claims.getId()); System.out.println("Subject: " +
		 * claims.getSubject()); System.out.println("Issuer: " + claims.getIssuer());
		 * System.out.println("Expiration: " + claims.getExpiration());
		 */
	    return claims;
	}
}
