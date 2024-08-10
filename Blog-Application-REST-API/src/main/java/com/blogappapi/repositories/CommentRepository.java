package com.blogappapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogappapi.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

	public List<Comment> findByCommentContains(String keyword);
}
