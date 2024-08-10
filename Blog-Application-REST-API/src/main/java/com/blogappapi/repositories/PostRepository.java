package com.blogappapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogappapi.entities.Category;
import com.blogappapi.entities.Post;
import com.blogappapi.entities.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

	public List<Post> findByUser(User user);

	public List<Post> findByCategory(Category category);

	public List<Post> findByTitle(String entire_title);

	public List<Post> findByTitleContains(String title_keyword);

}
