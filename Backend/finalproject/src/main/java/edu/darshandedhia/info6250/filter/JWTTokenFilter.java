package edu.darshandedhia.info6250.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import edu.darshandedhia.info6250.config.JWTUtils;
import edu.darshandedhia.info6250.constants.Status;
import edu.darshandedhia.info6250.constants.StatusCode;
import edu.darshandedhia.info6250.response.Response;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JWTTokenFilter extends OncePerRequestFilter{

	@Autowired
	@Qualifier(value = "jwtUtils")
	public JWTUtils jwtUtils;
	
	private final String Authorization = "Authorization";
	private final String PREFIX = "Bearer ";
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = request.getHeader(this.Authorization);
		try {
		if (token == null || token.equals("")) throw new MalformedJwtException("Token not found or null or blank");
		//*****  Fix String issue here using String builder (Strings are immutable and will be stored in memory causing memory leaks)
		//*****  Also token.replace is done below because if token is null it will cause exception. Hence first token null check
		token = token.replace(this.PREFIX, "");  
		JWTUtils utl = new JWTUtils();
		Claims claims = utl.verifyJWTToken(token);
		request.setAttribute("userName", claims.getId().toString());
		request.setAttribute("friend", "darshan.dedhia93_1");  //Remove this later
		filterChain.doFilter(request, response);
		} catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
			writeErrorResponse(response);
		} catch (Exception e) {
			e.printStackTrace();
			writeErrorResponse(response);
		}
	}
	
	public void writeErrorResponse(HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(new Response(StatusCode.unauthorized, Status.error, "UNAUTHORIZED: Invalid token"));
		out.print(json);
		out.flush();
	}
	
}
