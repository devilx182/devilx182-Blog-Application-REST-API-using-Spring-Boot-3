package com.blogappapi.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

public class UserDTO {

	private int id;

	@NotEmpty
	@Size(min = 4, message = "Username must be in minimum of 4 Characters !!")
	private String name;

	@Email(message = "Your Email is not valid !! Use xyz@domian.com !!")
	private String email;

	@NotEmpty
	@Size(min = 8, message = "Password must be min 8 chars !!")

	private String password;

	@NotEmpty
	@Size(min = 4, message = "About must be min 4 chars !!")
	private String about;

}
