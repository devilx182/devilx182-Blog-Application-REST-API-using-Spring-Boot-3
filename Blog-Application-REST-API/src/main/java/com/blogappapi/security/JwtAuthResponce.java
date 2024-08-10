package com.blogappapi.security;



import java.util.HashSet;
import java.util.Set;

import com.blogappapi.entities.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter 
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class JwtAuthResponce {
	
	private String jwtToken;
	
	private String username;
	
	private String userRole;

	
	
}
