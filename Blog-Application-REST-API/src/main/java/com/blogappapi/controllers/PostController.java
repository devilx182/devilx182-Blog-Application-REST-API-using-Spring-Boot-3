package com.blogappapi.controllers;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blogappapi.config.AppConstantValues;
import com.blogappapi.payloads.ApiResponse;
import com.blogappapi.payloads.PostDTO;
import com.blogappapi.payloads.PostResponse_Pagination;
import com.blogappapi.services.FileService;
import com.blogappapi.services.PostService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/")
@Tag(name = "Post Controller", description = " ")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	@GetMapping("/user/{userId}/posts")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<List<PostDTO>> getPostByUser(@PathVariable Integer userId) {

		List<PostDTO> posts = this.postService.getPostByUser(userId);

		return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);

	}

	@GetMapping("/category/{categoryId}/posts")
	@PreAuthorize("hasAuthority('USER')")

	public ResponseEntity<List<PostDTO>> getPostByCategory(@PathVariable Integer categoryId) {

		List<PostDTO> posts = this.postService.getPostByCategory(categoryId);

		return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
	}

	@GetMapping("/posts/searching/{entire_title}")
	public ResponseEntity<List<PostDTO>> getPostByEntireTitle(@PathVariable(required = true) String entire_title) {

		List<PostDTO> posts = this.postService.searchPost(entire_title);

		return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);
	}

	@GetMapping("/posts/search/{title_keyword}")
	public ResponseEntity<List<PostDTO>> getPostByTitleKeyword(@PathVariable String title_keyword) {

		List<PostDTO> posts = this.postService.searchPostContaining(title_keyword);

		return new ResponseEntity<List<PostDTO>>(posts, HttpStatus.OK);

	}

	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<PostDTO> createHandler(@Valid @RequestBody PostDTO postDTO, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {

		PostDTO savePostdto = this.postService.createPost(postDTO, userId, categoryId);

		return new ResponseEntity<>(savePostdto, HttpStatus.CREATED);
	}

	@GetMapping("/posts")
	public ResponseEntity<PostResponse_Pagination> getAllHandler(
			@RequestParam(value = "pageNumber", defaultValue = AppConstantValues.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstantValues.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstantValues.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDirection", defaultValue = AppConstantValues.SORT_DIRECTION, required = false) String sortDirection) {

		PostResponse_Pagination allPost = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDirection);

		return new ResponseEntity<PostResponse_Pagination>(allPost, HttpStatus.OK);

	}

	@GetMapping("/posts/{postid}")
	public ResponseEntity<PostDTO> getByIdHandler(@PathVariable Integer postid) {
		PostDTO post = this.postService.getPostById(postid);
		return new ResponseEntity<PostDTO>(post, HttpStatus.OK);

	}

	@PutMapping("/posts/{postid}")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<PostDTO> updateHandler(@Valid @RequestBody PostDTO postDTO, @PathVariable Integer postid) {
		PostDTO updatedto = this.postService.updatePost(postDTO, postid);
		return new ResponseEntity<>(updatedto, HttpStatus.OK);
	}

	
	@DeleteMapping("/posts/{postid}")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<ApiResponse> deleteHandler(@PathVariable Integer postid) {
		this.postService.deletePost(postid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post Deleted Successfully", true), HttpStatus.OK);

	}

	@PostMapping("/post/{postId}/upload-file")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<PostDTO> uploadImage(@RequestParam("image") MultipartFile image, @PathVariable Integer postId)
			throws IOException {
		PostDTO postDTO = this.postService.getPostById(postId);

		String fileName = this.fileService.uploadImage(path, image);

		postDTO.setImage(fileName);

		PostDTO updatePost = this.postService.updatePost(postDTO, postId);

		return new ResponseEntity<PostDTO>(updatePost, HttpStatus.OK);

	}

}
