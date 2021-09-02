package br.com.pedrodeveloper.moviesjwt.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.pedrodeveloper.moviesjwt.util.JwtTokenUtil;

/**
 * Example on how to filter http requests and validate its JWT token.
 * @author Pedro Vitore
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {
	
	private static final String BEARER_PREFIX = "Bearer ";

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		//Checks if authorization token is of expected type
		if (auth == null || !auth.startsWith(BEARER_PREFIX)) {
			logger.info("Authorization denied: " + BEARER_PREFIX + " prefix not found.");
			filterChain.doFilter(request, response);
			return;
		}
		
		//Gets the token without prefix
		String encodedToken = auth.substring(BEARER_PREFIX.length(), auth.length());

		//Gets username from the token
		String usernameFromToken;
		try {			
			usernameFromToken = jwtTokenUtil.getUsernameFromToken(encodedToken);
		} catch (Exception e) {
			logger.info("Authorization denied: Could not read JWT token.");
			logger.info("Details: " + e.getClass().getName() + " - " + e.getMessage());
			filterChain.doFilter(request, response);
			return;
		}
		
		//Retrieves user from the database
		UserDetails userDetails;
		try {
			userDetails = userDetailsService.loadUserByUsername(usernameFromToken);
		} catch (UsernameNotFoundException e) {
			//Unauthorize if user does not exist
			logger.info("Authorization denied: User " + usernameFromToken + " not found.");
			filterChain.doFilter(request, response);
			return;
		}
		
		//validates if token belongs to user
		if (!jwtTokenUtil.validateToken(encodedToken, userDetails)) {
			logger.info("Authorization denied: Invalid token for user " + usernameFromToken + ".");
			filterChain.doFilter(request, response);
			return;
		}
		
		//Finally authenticates the user
		UsernamePasswordAuthenticationToken authorizationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		authorizationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authorizationToken);
		
		filterChain.doFilter(request, response);
	}
	
}
