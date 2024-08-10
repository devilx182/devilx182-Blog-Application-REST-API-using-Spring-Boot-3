package com.blogappapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogappapi.payloads.ApiResponse;
import com.blogappapi.payloads.CommentDTO;
import com.blogappapi.services.CommentService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@Tag(name = "Commenent Controller", description = " ")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@GetMapping("/searchcomments/{keyword}")
	public ResponseEntity<List<CommentDTO>> searchHandler(@PathVariable String keyword) {

		List<CommentDTO> searchComment = this.commentService.searchComment(keyword);

		return new ResponseEntity<List<CommentDTO>>(searchComment, HttpStatus.OK);
	}

	@PostMapping("/user/{userId}/post/{postId}/comments")
	public ResponseEntity<CommentDTO> createHandler(@Valid @RequestBody CommentDTO commentdto,
			@PathVariable Integer userId, @PathVariable Integer postId) {

		CommentDTO createComment = this.commentService.createComment(commentdto, userId, postId);

		return new ResponseEntity<CommentDTO>(createComment, HttpStatus.CREATED);

	}

	@GetMapping("/comments")
	public ResponseEntity<List<CommentDTO>> getAllHandler() {

		List<CommentDTO> allComments = this.commentService.getAllComments();

		return new ResponseEntity<List<CommentDTO>>(allComments, HttpStatus.OK);

	}

	@PutMapping("/editcomment/{commentId}")
	public ResponseEntity<CommentDTO> updateHandler(@Valid @RequestBody CommentDTO commentDTO,
			@PathVariable Integer commentId) {

		CommentDTO updateComment = this.commentService.updateComment(commentDTO, commentId);

		return new ResponseEntity<CommentDTO>(updateComment, HttpStatus.CREATED);
	}

	@DeleteMapping("/deletecomment/{commentId}")

	public ResponseEntity<ApiResponse> deleteHandler(@PathVariable Integer commentId) {

		this.commentService.deleteComment(commentId);

		return new ResponseEntity<ApiResponse>(new ApiResponse("Comment Delete Successfully !!", true), HttpStatus.OK);
	}

}
