package com.blogappapi.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

public class PostDTO {

	private int id;

	@NotEmpty
	@Size(min = 4, message = "Title must be in minimum of 4 Characters !!")
	private String title;

	@NotEmpty
	@Size(min = 4, message = "Content must be in minimum of 4 Characters !!")
	private String content;

	@NotEmpty
	private String image;

	private java.util.Date addedDate;

	private UserDTO user;

	private CategoryDTO category;

}
