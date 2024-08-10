package com.blogappapi.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class CategoryDTO {

	private int id;

	@NotEmpty
	@Size(min = 4, message = "Title must be in minimum of 4 Characters !!")
	private String title;

}
