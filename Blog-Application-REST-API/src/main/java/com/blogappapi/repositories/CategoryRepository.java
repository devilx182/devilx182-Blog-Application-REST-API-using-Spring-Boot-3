package com.blogappapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogappapi.entities.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
