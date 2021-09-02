package br.com.pedrodeveloper.moviesjwt.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.pedrodeveloper.moviesjwt.model.dto.AuthDto;
import br.com.pedrodeveloper.moviesjwt.model.dto.TokenDto;
import br.com.pedrodeveloper.moviesjwt.util.JwtTokenUtil;

@RestController
public class AuthController {

	private static final String LOGIN_ERROR_MESSAGE = "Invalid username or password.";
	
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@PostMapping("/auth")
	public ResponseEntity<TokenDto> authenticate(@Valid @RequestBody AuthDto dto) {
		try {
			//Gets user from database
			UserDetails user = userDetailsService.loadUserByUsername(dto.getUsername());
			
			//Authenticates the user usin the manager
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword(), user.getAuthorities());
			authenticationManager.authenticate(authentication);
			
			//Generates the token
			String token = jwtTokenUtil.generateToken(user);
			
			//Successful. Return the token.
			TokenDto tokenDto = new TokenDto();
			tokenDto.setToken(token);
			ResponseEntity<TokenDto> responseEntity = new ResponseEntity<>(tokenDto, HttpStatus.OK);
			return responseEntity;
		} catch (AuthenticationException e) {
			//return error if authentication fails
			TokenDto tokenDto = new TokenDto();
			tokenDto.setError(LOGIN_ERROR_MESSAGE);
			ResponseEntity<TokenDto> responseEntity = new ResponseEntity<>(tokenDto, HttpStatus.BAD_REQUEST);
			return responseEntity;
		}
	}
}
