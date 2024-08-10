package com.blogappapi.services;

import java.util.List;

import org.springframework.stereotype.Service;
import com.blogappapi.payloads.PostDTO;
import com.blogappapi.payloads.PostResponse_Pagination;

@Service
public interface PostService {

	//***************************** Custom Method *******************************************************
	
	//**** Get all posts by the Category of perticular category
		
	public List<PostDTO> getPostByCategory(Integer categoryId);
	
	//*** Get all posts by the userof perticular user
	
	public List<PostDTO> getPostByUser(Integer userId);
	
	
	
	//****************************** Searching Algorithm ************************************************
	
	//*** Search Post by the entire title keyword
	
	public List<PostDTO> searchPost(String entire_title);
	
	
	public List<PostDTO> searchPostContaining(String title_keyword);
	
	
	
	//****************************** CRUD Method ********************************************************	
	
	//Create Post

	public PostDTO createPost(PostDTO postDTO,Integer userId, Integer categoryI);
	
	//Get All Post with Pagination
	
	public PostResponse_Pagination getAllPost(Integer pageNumber,Integer pageSize,String sortBy,String sortDirection);
	
	//Get Single Post
	
	public PostDTO getPostById(int postid);
	
	//Update Post
	
	public PostDTO updatePost(PostDTO postDTO, int postid);
	
	//Delete Post
	
	public void deletePost(int postid);

	

	

	
	
}
