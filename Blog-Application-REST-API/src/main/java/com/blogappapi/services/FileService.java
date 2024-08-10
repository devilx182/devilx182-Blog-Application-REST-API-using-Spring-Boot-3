package com.blogappapi.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileService {
	
	public String uploadImage(String path, MultipartFile file)throws IOException;
	
	public InputStream downloadImage(String path, String fileName) throws FileNotFoundException;
}
