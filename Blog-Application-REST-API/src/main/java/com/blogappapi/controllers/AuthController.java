package com.blogappapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogappapi.entities.Role;
import com.blogappapi.payloads.UserDTO;
import com.blogappapi.security.CustomUserDetailsService;
import com.blogappapi.security.JwtAuthRequest;
import com.blogappapi.security.JwtAuthResponce;
import com.blogappapi.security.JwtTokenHelper;
import com.blogappapi.services.UserService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication Controller", description = " ")
public class AuthController {

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenHelper tokenHelper;

	@Autowired
	private Role role;

	@Autowired
	private UserService userService;

	// Test API

	@GetMapping("/welcome")

	public String welcome() {
		return "Welcome to Spring JWT Tutorials !!";
	}

	@PostMapping("/registration-admin")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<UserDTO> registerNewADMIN(@Valid @RequestBody UserDTO userDTO) {

		UserDTO registerNewUser = this.userService.registerNewADMIN(userDTO);

		return new ResponseEntity<UserDTO>(registerNewUser, HttpStatus.CREATED);

	}

	// Login API
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponce> login(@RequestBody JwtAuthRequest request) {

		this.authenticate(request.getUsername(), request.getPassword());

		UserDetails loadUserByUsername = this.customUserDetailsService.loadUserByUsername(request.getUsername());

		String generatedToken = this.tokenHelper.generateToken(loadUserByUsername);

		JwtAuthResponce authResponce = new JwtAuthResponce();
		authResponce.setJwtToken(generatedToken);
		authResponce.setUsername(tokenHelper.getUsernameFromToken(generatedToken));
		authResponce.setUserRole(role.getName());

		return new ResponseEntity<JwtAuthResponce>(authResponce, HttpStatus.OK);

	}

	private void authenticate(String username, String password) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				password);

		this.authenticationManager.authenticate(authenticationToken);

	}

}
