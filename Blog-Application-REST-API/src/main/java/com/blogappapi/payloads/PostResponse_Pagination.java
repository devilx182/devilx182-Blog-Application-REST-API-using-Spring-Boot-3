package com.blogappapi.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class PostResponse_Pagination {
	
	private List<PostDTO> content;
	private int pageNumber;
	private int pageSize;
	private Long totalElements;
	private int totalPages;
	private Boolean lastPage;
	
}
