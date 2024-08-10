package com.blogappapi.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogappapi.entities.Comment;
import com.blogappapi.entities.Post;
import com.blogappapi.entities.User;
import com.blogappapi.exceptions.ResourceNotFoundException;
import com.blogappapi.payloads.CommentDTO;
import com.blogappapi.repositories.CommentRepository;
import com.blogappapi.repositories.PostRepository;
import com.blogappapi.repositories.UserRepository;
import com.blogappapi.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	

	@Autowired
	private ModelMapper modelMapper;
	

	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	
	
	//Search Comment by keyword

	@Override
	public List<CommentDTO> searchComment(String keyword) {
		List<Comment> findByCommentContains = this.commentRepository.findByCommentContains(keyword);
		List<CommentDTO> commentDTO = findByCommentContains.stream().map((p)->this.commentTocommentDTO(p)).collect(Collectors.toList());
		return commentDTO;
	}


	
	//Create Comment :
	@Override
	public CommentDTO createComment(CommentDTO commentDto,Integer userId, Integer postId) {
		
		// Getting Actual Post & Actual User from DB to comment on which post and by which user 
		User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "user_id", userId));
		
		Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post_id", postId));
		
		
		//Convert CommentDTO To Comment to set properties
		Comment comment = this.commentDTOtoComment(commentDto);
		
		//Setting Actual Post & Actual User for commenting in perticular post & by user
		
		//Setting Comment Entity Class Parameter
		
		comment.setCommentedDate(new java.util.Date()); //Dynamic Date not given by user
		
		comment.setUser(user);
		comment.setPost(post);
		
		
		
		//Save Comment in DataBase
		Comment saveComment = this.commentRepository.save(comment);// get comment from url and save that comment in db
		
		//Convert Comment to CommentDTO
		
		CommentDTO commentDTO = this.commentTocommentDTO(saveComment);
		
		return commentDTO;
	}

	
	
	
	// Get All Comments Method
	
	@Override
	public List<CommentDTO> getAllComments() {
		List<Comment> comment = this.commentRepository.findAll();
		
		List<CommentDTO> commentDTO = comment.stream().map((c)-> this.commentTocommentDTO(c)).collect(Collectors.toList());
		
		return commentDTO;
	}
	
	
	
	
	//Update/Edit Comment :

	@Override
	public CommentDTO updateComment(CommentDTO commentDTO, int commentId) {
		
		//Get Exicting Comment 
		Comment comment = this.commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));
		
		//Set New Comment on Exicting Comment getting from Parameter Object   
		comment.setComment(commentDTO.getComment());
		
		//Save New Comment with new data
		Comment savedComment = this.commentRepository.save(comment);
		
		//Convert comment into commentDTO
		CommentDTO commentTocommentDTO = this.commentTocommentDTO(savedComment);
		
		//Return CommentDTO
		return commentTocommentDTO;
	}

	
	
	//Delete Comment :
	
	@Override
	public void deleteComment(int commentId) {
		
		Comment comment = this.commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));
		this.commentRepository.delete(comment);
	
	}
	
	
	
	
//#######################################################################################################
	
			// ******************* Conversion *******************
		
			private CommentDTO commentTocommentDTO(Comment comment) {

				CommentDTO commentDTO = this.modelMapper.map(comment, CommentDTO.class);
				return commentDTO;

			}
			private Comment commentDTOtoComment (CommentDTO commentDTO){
				
				Comment comment = this.modelMapper.map(commentDTO,Comment.class);
				return comment;
				
			}
			
}
