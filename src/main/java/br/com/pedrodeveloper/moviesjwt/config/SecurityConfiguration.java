package br.com.pedrodeveloper.moviesjwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter  {

	@Autowired
	private UserDetailsService userService;
	
//	@Autowired
//	private BasicTokenFilter basicTokenFilter;
	
	@Autowired
	private JwtTokenFilter jwtTokenFilter;
//	@Autowired
//	private PasswordEncoder encoder;
	
	//Expose authentication manager as a bean
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService)
				.passwordEncoder(passwordEncoder());
	}
	
	//Ignore swagger endpoints
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
        	.csrf().disable()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/stats", "/swagger-ui.html")
			.permitAll()
			.antMatchers(HttpMethod.POST, "/auth")
			.permitAll()
			.antMatchers("/**")
			.authenticated()
			//dont use JSESSIONID cookies
			.and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			//JWT filter
			.and()
//			.addFilterBefore(basicTokenFilter, UsernamePasswordAuthenticationFilter.class);
        	.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
