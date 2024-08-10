package com.blogappapi.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogappapi.entities.Category;
import com.blogappapi.exceptions.ResourceNotFoundException;
import com.blogappapi.payloads.CategoryDTO;
import com.blogappapi.repositories.CategoryRepository;
import com.blogappapi.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	//Save Method
	@Override
	public CategoryDTO createCategory(CategoryDTO categoryDTO) {
		
		Category category=this.CategoryDTOTocategory(categoryDTO);
		
		Category savedCategory = this.categoryRepository.save(category);
		
		CategoryDTO categoryDto = this.categoryTOCategoryDTO(savedCategory);
		
		return categoryDto;
	}

	
	
	//Get ALL :
	@Override
	public List<CategoryDTO> getAllCategory() {
		List<Category> category = this.categoryRepository.findAll();
		List<CategoryDTO> categoryDTO = category.stream().map(cat ->this.categoryTOCategoryDTO(cat)).collect(Collectors.toList());
		return categoryDTO;
	}

	
	
	//Get One :
	@Override
	public CategoryDTO getCategoryID(int categoryID) {
		Category cat = this.categoryRepository.findById(categoryID)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryID));
		CategoryDTO categoryDTO = this.categoryTOCategoryDTO(cat);

		return categoryDTO;
	}

	
	//Update Method : 
	@Override
	public CategoryDTO updateCategory(CategoryDTO categoryDTO, int categoryID) {
		Category category = this.categoryRepository.findById(categoryID).orElseThrow(() -> new ResourceNotFoundException("Category","category id", categoryID));
		category.setTitle(categoryDTO.getTitle());
		
		Category updatedCategory= this.categoryRepository.save(category);
		
		CategoryDTO categoryDto= this.categoryTOCategoryDTO(updatedCategory);
		
		return categoryDto;
	}
	
	// Delete Method : 
	@Override
	public void deletCategory(int categoryID) {
		Category category= this.categoryRepository.findById(categoryID).orElseThrow(() -> new ResourceNotFoundException("Category", "category id", categoryID));
		
		this.categoryRepository.delete(category);
	}

//#######################################################################################################
	
	// Conversion :
	private Category CategoryDTOTocategory(CategoryDTO categoryDTO) {
		
		Category category = this.modelMapper.map(categoryDTO, Category.class);
		return category;
		
	}
	
	private CategoryDTO categoryTOCategoryDTO(Category category) {
		
		CategoryDTO categoryDTO=this.modelMapper.map(category, CategoryDTO.class);
		return categoryDTO;
	}
}
