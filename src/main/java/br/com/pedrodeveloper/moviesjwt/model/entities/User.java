package br.com.pedrodeveloper.moviesjwt.model.entities;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User implements UserDetails {

	private static final long serialVersionUID = 1530179812182264045L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	
	private String user;
	
	private String pass;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//No roles in this project... yet
		return null;
	}

	@Override
	public String getPassword() {
		return getPass();
	}

	@Override
	public String getUsername() {
		return getUser();
	}

	@Override
	public boolean isAccountNonExpired() {
		//Accounts never expire
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		//Accounts are never locked
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		//Credentials never expire
		return true;
	}

	@Override
	public boolean isEnabled() {
		//Users are always enabled
		return true;
	}
}
