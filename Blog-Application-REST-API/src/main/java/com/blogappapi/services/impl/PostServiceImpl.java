package com.blogappapi.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import com.blogappapi.entities.Category;
import com.blogappapi.entities.Post;
import com.blogappapi.entities.User;
import com.blogappapi.exceptions.ResourceNotFoundException;
import com.blogappapi.payloads.PostDTO;
import com.blogappapi.payloads.PostResponse_Pagination;
import com.blogappapi.repositories.CategoryRepository;
import com.blogappapi.repositories.PostRepository;
import com.blogappapi.repositories.UserRepository;
import com.blogappapi.services.PostService;

@Service
public class PostServiceImpl implements PostService{
	
	// CTR + 1 + Enter : To auto return the method
		
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	

	
	
	// ******************************** Custom Method *************************************************
	
	// Get All Post By searching Category (This post write in PostService Interface and implimatation is here )
	@Override
	public List<PostDTO> getPostByCategory(Integer categoryId) {
		
		// We get All category by passing category id here from catgory API
		Category category = this.categoryRepository.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "category_Id", categoryId));

		// We get posts of perticuler category

		List<Post> posts = this.postRepository.findByCategory(category); // Custom method write in postRepository to get data from database
																			

		// Now converting the post to postDTO bz return type is PostDTO of this method

		List<PostDTO> postDtos = posts.stream().map(p -> this.PostToPostDTO(p)).collect(Collectors.toList());

		// Return the postDtos

		return postDtos;
	}

	
	// Get All Users Post By userId  
	@Override
	public List<PostDTO> getPostByUser(Integer userId) {
		
		User user= this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User_Id", userId));
		
		List<Post> posts = this.postRepository.findByUser(user); // Custom Method write in PostRepository
		
		List<PostDTO> postDTO = posts.stream().map((p)->this.PostToPostDTO(p)).collect(Collectors.toList());
		
		return postDTO;
	}

	//******************************** Searching Algorithm *********************************************
	
	// Find All Posts By searching entire post title 
	// Type Entire title/Id/Content name to search post. Title, Id, Content this is field names of Entity/DTO Class
	@Override
	public List<PostDTO> searchPost(String entire_title) {
		
		List<Post> posts= this.postRepository.findByTitle(entire_title);
		List<PostDTO> postDTO = posts.stream().map((p)->this.PostToPostDTO(p)).collect(Collectors.toList());
		
		return postDTO;
	}
	
	// Find All posts where title= some keyword of title we pass
	
	@Override 
	public List<PostDTO> searchPostContaining(String title_keyword) {
		
		
//		if(title_keyword == null) {
//			 new ResourceNotFoundException("Category", "category_Id", 0);
//		}
		List<Post> posts = this.postRepository.findByTitleContains(title_keyword);
		List<PostDTO> postDTO = posts.stream().map((p) -> this.PostToPostDTO(p)).collect(Collectors.toList());

		return postDTO;
		
	}
	
	// Type some keyword of title/Id/Content to search post. Title, Id, Content this is field names of Entity/DTO Class
	
	
	
	
	// **************************** CRUD Methods *******************************************************


	//Create Post Method :
	
	@Override
	public PostDTO createPost(PostDTO postDTO,Integer userId, Integer categoryId){
		
		// Getting User & Category ID 
		User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "user_id", userId));
		
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "category_id", categoryId));;

		
		//Convert PostDTO To Post to set properties
		 Post post = this.PostDTOToPost(postDTO);
		 
		 //Setting parameters to post object bz this parameters value we taking dynamically
		 //post.setImage(postDTO.getImage());
		 post.setAddedDate(new java.util.Date());
		 
		//Setting User id & Category id to post in perticularcategory & by user
		 
		 //We can find the which user posted this post and in which category this post posted
		 post.setUser(user);
		 post.setCategory(category);
		 
		 
		 // Saving post object in Database
		 Post savedPost = this.postRepository.save(post);
		 
		 PostDTO dto = this.PostToPostDTO(savedPost);
		 
		return dto;
	}

	
	//Get All Method with Pagination & Sorting :
	
	@Override
	public PostResponse_Pagination getAllPost(Integer pageNumber,Integer pageSize, String sortBy,String sortDirection) {
		
		// Paggination and Sorting the post by Id (we take a defaultValue = "id" in controller )
		
		//Logic for dynamic sort post Ascending / Descending Order what parameter we pass through the url 
		
		Sort sort = null;
		
		
		if(sortDirection.equalsIgnoreCase("descending")) {
			sort=Sort.by(sortBy).descending(); // If user type to sort post in descending order then sort in descending order
											// in postman url add parameter like : &sortBy=id/title/date &sortDirection=descending type forcefully to descending order
		}else{
			sort=Sort.by(sortBy).ascending(); // Otherwise always sort post in assending order
		}

		PageRequest p = PageRequest.of(pageNumber, pageSize,sort);
		
		//Logic for Bydefault sort post Ascending Order
		//PageRequest p = PageRequest.of(pageNumber, pageSize,Sort.by(sortBy).ascending()); // import the org.springframework.data.domain.Sort;
		
		// Get all post 
		Page<Post> pagePost = this.postRepository.findAll(p);
		
		// Get All posts content / data 
		List<Post> content = pagePost.getContent();
		
		
		// Convert post to PostDTO
		List<PostDTO> postDTOs=content.stream().map(post ->this.PostToPostDTO(post)).collect(Collectors.toList());
		
		// Creating object to Setting PostDTO to PostResponse to show on HTML Page 
		PostResponse_Pagination postResponse = new PostResponse_Pagination();
		
		postResponse.setContent(postDTOs);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		// Return postResponse object
		return postResponse;
	}


	//Get Single Method :
	
	@Override
	public PostDTO getPostById(int postid) {
		Post post= this.postRepository.findById(postid).orElseThrow(() -> new ResourceNotFoundException("Post", "post_id", postid));
		PostDTO dto = this.PostToPostDTO(post);
		return dto;
	}

	
	// Update post Method :
	
	@Override
	public PostDTO updatePost(PostDTO postDTO, int postid) {
		
		//Find Post to update
		Post post= this.postRepository.findById(postid).orElseThrow(() -> new ResourceNotFoundException("Post", "post_id", postid));
		
		//Setting new Value to the exiting post
		post.setTitle(postDTO.getTitle());
		post.setContent(postDTO.getContent());
		post.setImage(postDTO.getImage());
		post.setAddedDate(new java.util.Date());//New Fresh date when we update the date 
		
		//Save the updated post
		Post savePost = this.postRepository.save(post);
		
		//Converting the post To postDTO bz return type is PostDTO
		PostDTO updatedPost = this.PostToPostDTO(savePost);
		
		return updatedPost;
	}

	
	

	//Delete Post Method :
	
	@Override
	public void deletePost(int postid) {
		 Post post = this.postRepository.findById(postid).orElseThrow(() -> new ResourceNotFoundException("Post", "post_id", postid));
		
		this.postRepository.delete(post);
	}
	

	
//#######################################################################################################
	
		// ******************* Conversion *******************
	
		private PostDTO PostToPostDTO(Post post) {

			PostDTO postDTO = this.modelMapper.map(post, PostDTO.class);
			return postDTO;

		}
		private Post PostDTOToPost(PostDTO postDTO) {
			
			Post post = this.modelMapper.map(postDTO, Post.class);
			return post;
			
		}
		
		
}
