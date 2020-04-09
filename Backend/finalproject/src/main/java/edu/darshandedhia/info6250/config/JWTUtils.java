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
		SignatureAlgorithm signatureAlgo = SignatureAlgorithm.HS256;
		byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(secretKey);
		Key signingKey = new SecretKeySpec(secretKeyBytes, signatureAlgo.getJcaName());
		
		JwtBuilder builder = Jwts.builder().setId(id)
										   .setIssuedAt(new Date())
										   .setSubject(subject)
										   .setClaims(new HashMap<String, Object>())
										   .setIssuer(issuer)
										   .setExpiration(new Date(System.currentTimeMillis() + (minutes * oneMinuteInMilliSeconds)))
										   .signWith(signatureAlgo, secretKey.getBytes());
		
		return builder.compact();
	}
	
	public Claims verifyJWTToken(String token) {
		System.out.println("Token Verification started");
		 Claims claims = Jwts.parser().setSigningKey(secretKey.getBytes())
									 .parseClaimsJwt(token)
									 .getBody();
		 System.out.println(claims.getId());
		return claims;
	}
}
