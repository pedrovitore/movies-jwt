package br.com.pedrodeveloper.moviesjwt.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Example on how to filter http requests and validate its content
 * with the authentication manager to help undertand how security 
 * filters work. Use this for educational purposes only.<br>
 * This implementation in particular is useless because Spring Boot 
 * already have a implementation for Basic Auth, lmao.
 * @author Pedro Vitore
 */
//Uncomment this if you want to use this filter 
//@Component
public class BasicTokenFilter extends OncePerRequestFilter {
	
	private static final String BASIC_PREFIX = "Basic ";

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		//Checks if authorization token is of expected type
		if (auth == null || !auth.startsWith(BASIC_PREFIX)) {
			logger.info("Authorization denied: " + BASIC_PREFIX + " prefix not found.");
			filterChain.doFilter(request, response);
			return;
		}
		
		//Gets the token without prefix
		String encodedToken = auth.substring(BASIC_PREFIX.length(), auth.length());

		//Checks if token is encoded in correct format
		if (encodedToken == null || encodedToken.isEmpty() || !Base64.isBase64(encodedToken)) {
			logger.info("Authorization denied: Token is not encoded in Base64 format.");
			filterChain.doFilter(request, response);
			return;
		}
		
		//Decodes the token
		byte[] decodeBase64 = Base64.decodeBase64(encodedToken);
		String decodedToken = new String(decodeBase64);
		
		//Checks if token is in format user:pass
		String[] split = decodedToken.split(":");
		if (split == null || split.length != 2) {
			logger.info("Authorization denied: Token info is not in expected format.");
			filterChain.doFilter(request, response);
			return;
		}

		//Authenticates the user
		String username = split[0];
		String password = split[1];
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
		try {
			authenticationManager.authenticate(authentication);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} catch (Exception e) {
			logger.info("Authorization denied: Invalid username or password.");
		}
		
		filterChain.doFilter(request, response);
	}
	
}
