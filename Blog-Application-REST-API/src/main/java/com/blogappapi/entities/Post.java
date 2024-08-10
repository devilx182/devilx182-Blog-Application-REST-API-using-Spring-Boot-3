package com.blogappapi.entities;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "User_Post")
@NoArgsConstructor
@Setter
@Getter

public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "post_id", nullable = false)
	private int id;

	@Column(name = "post_titles", nullable = false, length = 100)
	private String title;

	@Column(name = "post_content", nullable = false, length = 2000)
	private String content;

	@Column(name = "post_image", nullable = false, length = 100)
	private String image;

	@Column(name = "post_date")
	private java.util.Date addedDate;

	@ManyToOne
	@JoinColumn(name = "Category_Id")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "User_Id")
	private User user;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Comment> comments = new ArrayList<>();

}
