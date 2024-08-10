package com.blogappapi.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blogappapi.payloads.UserDTO;

// Use UserDTO Class to pass object beacause we don't want to expose entity class property
@Service
public interface UserService {
	
	// Writing Crud Method and use this method in Controller Class
	
	
	
	// The implemetation of this all methods write in ServiceImpl Class ( impl Package )
	
	public UserDTO registerNewADMIN(UserDTO userDTO); // This methos is used for create new user with assign User Role (ADMIN or USER )
	
	public UserDTO registerNewNormalUser(UserDTO userDTO); // This method id used for create normal user
	
	public List<UserDTO> getAllUser();
	
	public UserDTO getUserByID(int userID);
	
	public UserDTO updateUser(UserDTO userDTO, int userID);
	
	public void deletUser(int userID);
		
		
}


