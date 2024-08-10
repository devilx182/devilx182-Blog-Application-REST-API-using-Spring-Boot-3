package com.blogappapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogappapi.payloads.ApiResponse;
import com.blogappapi.payloads.UserDTO;
import com.blogappapi.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@SecurityRequirement(name = "auth")
@Tag(name = "User Controller", description = " ")
public class UserController {

	@Autowired
	private UserService userService;

	@Operation(

			description = "This method can access anyone!!",

			responses = {

					@io.swagger.v3.oas.annotations.responses.ApiResponse(description = "User created successfully", responseCode = "201") })

	@PostMapping("/registration")

	public ResponseEntity<UserDTO> registerNewUSER(@Valid @RequestBody UserDTO userDTO) {

		UserDTO createdUserDTO = this.userService.registerNewNormalUser(userDTO);
		return new ResponseEntity<>(createdUserDTO, HttpStatus.CREATED);

	}

	@Operation(

			description = "This method can access only ADMIN !!",

			responses = {

					@io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Access Denied !! Unauthorized", responseCode = "401"),

					@io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Ok !!", responseCode = "200") })

	@GetMapping("/users")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<UserDTO>> getAllUsers() {

		List<UserDTO> users = this.userService.getAllUser();

		if (users.size() <= 0) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(users);
	}

	@Operation(

			description = "This method can access only ADMIN !!",

			responses = {

					@io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Access Denied !! Unauthorized", responseCode = "401"),

					@io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Ok !!", responseCode = "201") })

	@GetMapping("/user/{userId}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<UserDTO> getUserByID(@PathVariable(required = true) int userId) throws Exception {

		UserDTO user = this.userService.getUserByID(userId);

		if (user == null) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(user);

	}

	@Operation(

			description = "This method can access only USER !!",

			responses = {

					@io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Access Denied !! Unauthorized", responseCode = "401"),

					@io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Ok !!", responseCode = "200") })

	@PutMapping("/user/{userid}")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UserDTO userDTO, @PathVariable Integer userid) {

		UserDTO updatedUser = this.userService.updateUser(userDTO, userid);
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);

	}

	@Operation(

			description = "This method has access for ADMIN only !!",

			responses = {

					@io.swagger.v3.oas.annotations.responses.ApiResponse(description = "Access Denied !! Unauthorized", responseCode = "401"),

					@io.swagger.v3.oas.annotations.responses.ApiResponse(description = "User Delete Successfully !!", responseCode = "200"),

					@io.swagger.v3.oas.annotations.responses.ApiResponse(description = "User Not Found !!", responseCode = "404") }

	)

	@DeleteMapping("/user/{userID}")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse> deleteUserHandler(@PathVariable Integer userID) {

		System.out.println("Enter User Id for Delete method" + userID);

		this.userService.deletUser(userID);

		return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted Successfully", true), HttpStatus.OK);

	}

}
