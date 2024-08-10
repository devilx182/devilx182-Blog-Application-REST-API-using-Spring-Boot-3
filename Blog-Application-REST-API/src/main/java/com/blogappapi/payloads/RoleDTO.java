package com.blogappapi.payloads;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class RoleDTO {

	private int id;

	private String name;
}
