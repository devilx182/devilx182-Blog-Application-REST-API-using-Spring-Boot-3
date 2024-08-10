package com.blogappapi.services;

import java.util.List;

import org.springframework.stereotype.Service;


import com.blogappapi.payloads.CategoryDTO;

@Service
public interface CategoryService {
	
	//Create Category
	
	public CategoryDTO createCategory(CategoryDTO categoryDTO); 
	
	//Get All Category
	public List<CategoryDTO> getAllCategory();
	
	
	//Get Single Category
	public CategoryDTO getCategoryID(int categoryID);
	
	
	//Update Category
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, int categoryID);
	
	//Delete Category
	public void deletCategory(int categoryID);
}
