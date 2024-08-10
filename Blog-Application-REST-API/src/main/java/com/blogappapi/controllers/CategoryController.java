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
import com.blogappapi.payloads.CategoryDTO;
import com.blogappapi.services.CategoryService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
@Tag(name = "Category Controller", description = " ")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<CategoryDTO> createHandler(@Valid @RequestBody CategoryDTO categoryDTO) {
		CategoryDTO dto = this.categoryService.createCategory(categoryDTO);
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}

	@GetMapping("/")
	public ResponseEntity<List<CategoryDTO>> getAllHandler() {
		List<CategoryDTO> category = this.categoryService.getAllCategory();
		return ResponseEntity.ok(category);
	}

	@GetMapping("/{categoryid}")
	public ResponseEntity<CategoryDTO> getByIdHandler(@PathVariable Integer categoryid) {
		CategoryDTO categoryDTO = this.categoryService.getCategoryID(categoryid);
		return new ResponseEntity<CategoryDTO>(categoryDTO, HttpStatus.OK);

	}

	@PutMapping("/{categoryid}")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<CategoryDTO> updateHandler(@Valid @RequestBody CategoryDTO categoryDTO,
			@PathVariable Integer categoryid) {
		CategoryDTO updatedto = this.categoryService.updateCategory(categoryDTO, categoryid);

		return new ResponseEntity<>(updatedto, HttpStatus.OK);
	}

	@DeleteMapping("/{categoryid}")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<ApiResponse> deleteHandler(@PathVariable Integer categoryid) {
		this.categoryService.deletCategory(categoryid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully", true), HttpStatus.OK);

	}

}
