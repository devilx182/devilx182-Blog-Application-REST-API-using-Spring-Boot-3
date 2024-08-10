package com.blogappapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogappapi.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

}
