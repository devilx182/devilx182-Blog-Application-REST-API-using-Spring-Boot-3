package com.blogappapi.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blogappapi.config.AppConstantValues;
import com.blogappapi.entities.Role;
import com.blogappapi.entities.User;
import com.blogappapi.payloads.UserDTO;
import com.blogappapi.repositories.RoleRepository;
import com.blogappapi.repositories.UserRepository;
import com.blogappapi.services.UserService;
import com.blogappapi.exceptions.*;

@Service
public class UserServiceImpl implements UserService {
	
	//How we can Autowire Interface bz UserRepository is a interface 
	//---> Spring Create runtime/ dynamically class proxy class of UserRepository 
	//		that's why we are able to Autowire UserRepository Interface anyware.
	
	@Autowired
	private PasswordEncoder passwordEncoder; 
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private RoleRepository roleRepository;
	
	
	//Register User with assign Role : this API For Normal User Registation
	@Override
	public UserDTO registerNewADMIN(UserDTO userDTO) {
		
		User user  = this.dtoToUser(userDTO);
		
		// Encoded Password 
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//Assign Role
		Role role = this.roleRepository.findById(AppConstantValues.ADMIN).get();
		
		//Set Role to ADMIN
		
		user.getRoles().add(role);
		
		// now Save user with assign role as USER
		
		User savedUser = this.userRepository.save(user);
		
		//Convert User to UserDTO
		UserDTO userToDto = this.userToDto(savedUser);
		
		
		return userToDto ;
	}
	
	// Save Method : this is implemetation of Service Interface method
		@Override
		public UserDTO registerNewNormalUser(UserDTO userDTO) {

			// Convert User DTO object into User Entity object bz we got userDto from method
			// method write in bellow
			User u = this.dtoToUser(userDTO);

			// Save User Entity object in database
			
			u.setPassword(passwordEncoder.encode(userDTO.getPassword()));
			
			//Assign Role
			Role role = this.roleRepository.findById(AppConstantValues.USER).get();
					
			//Set Role to USER
			u.getRoles().add(role);
			
			// now Save user with assign role as USER
			User savedUser = this.userRepository.save(u);

			// Convert & Return User Entity Object into UserDto Object bz return type is
			// userDTO
			return this.userToDto(savedUser);
		}

	
	
	
	
	// Get All Method : this is implemetation of Service Interface method
	@Override
	public List<UserDTO> getAllUser() {

		// Get All Users Entity
		List<User> users = (List<User>) this.userRepository.findAll();

		// Convert user into dto and send bz return type is UserDTO
		List<UserDTO> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

		return userDtos;
	}

	// Get one Method : this is implemetation of Service Interface method
	@Override
	public UserDTO getUserByID(int userID) {
		// Got one Users Entity
		User user = this.userRepository.findById(userID)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userID));

		// Convert User into UserDTO and send bz return type is UserDTO
		return this.userToDto(user);
	}

	
	
	
	// Update Method : this is implemetation of Service Interface method
	@Override
	public UserDTO updateUser(UserDTO userDTO, int userID) {

		// Got All Users Entity bz we pass user entity object id
		
		User user = this.userRepository.findById(userID).orElseThrow(() -> new ResourceNotFoundException("User", "id", userID));
					
		
		// Setting user entity object value to userDTO object
		//user.setId(userDTO.getId());
		user.setName(userDTO.getName());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());
		user.setAbout(userDTO.getAbout());

		// Saving User Entity object in database
		
		User updatedUser = this.userRepository.save(user);

		// Converting & return the user entity object into userDTO object
		UserDTO userDTO1 = this.userToDto(updatedUser);
		return userDTO1;
	}
	
	

	// Delete Method : this is implemetation of Service Interface method
	@Override
	public void deletUser(int userID) {

		// Got All Users Entity bz we pass user entity object id
		User user = this.userRepository.findById(userID)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userID));

		// deleting the perticular entity object
		this.userRepository.delete(user);

	}

//***************************************************************************************************************************************************************************	

	// Convert DTO Objects in Entity Object

	private User dtoToUser(UserDTO userDto) {
		
		// Conversion using modelmapper class object
		
		User user = this.modelMapper.map(userDto, User.class);
		
//		User user = new User();
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());

		return user;

	}

	// Convert Entity Object in DTO Objects
	private UserDTO userToDto(User user) {

		
		// Conversion using modelmapper class object
		
		UserDTO uDto = this.modelMapper.map(user, UserDTO.class);
		
//		UserDTO uDto = new UserDTO();
//		uDto.setId(user.getId());
//		uDto.setName(user.getName());
//		uDto.setEmail(user.getEmail());
//		uDto.setPassword(user.getPassword());
//		uDto.setAbout(user.getAbout());
		return uDto;

	}



}
