package com.blogappapi.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.blogappapi.payloads.CommentDTO;

@Service
public interface CommentService {
	
	//Create Comment
	public CommentDTO createComment(CommentDTO commentDto,Integer userId,Integer postId);
	
	//Get All Comment
	
	public List<CommentDTO> getAllComments();
	
	//Get Single Commnet by Id
	//public CommentDTO getCommentById(int comment_Id); We Dont Need This Method
	
	
	//Update Comment
	public CommentDTO updateComment(CommentDTO commentDTO, int commentId);
	
	
	//Delete Comment
	
	public void deleteComment(int commentId);
	
	//Searching Method calles as searchComment(String keyword);
	
	public List<CommentDTO> searchComment(String keyword);
	
}
