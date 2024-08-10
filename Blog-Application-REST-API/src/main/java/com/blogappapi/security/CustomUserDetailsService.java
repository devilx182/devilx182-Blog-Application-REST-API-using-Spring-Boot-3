package com.blogappapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blogappapi.repositories.UserRepository;

import com.blogappapi.entities.User;
import com.blogappapi.exceptions.ResourceNotFoundException;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userRepository.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User", "Email id : "+ username, 0));
		
		//return user.map(CustomUserDetails::new).orElseThrow(()->new UsernameNotFoundException("User Not Found !!"+username));
		
		return user;
	
	}
	
	

}
