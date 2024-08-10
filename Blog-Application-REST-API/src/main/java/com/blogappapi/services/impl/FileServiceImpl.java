package com.blogappapi.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.blogappapi.services.FileService;

@Service
public class FileServiceImpl implements FileService {
	
	
	// ************************* Image Upload to DB Method ********************************
	
	@Override
	public String uploadImage(String path, MultipartFile file) throws IOException {
		
		
		
		//File Name
		
		String fileName = file.getOriginalFilename(); //abc.jpg
		
/*		
		//Random File Name Genrator 
		String randomFile = UUID.randomUUID().toString();
		
		String randomFileName = randomFile.concat(fileName.substring(fileName.lastIndexOf(".")));
*/		
		
		
		//File name saved in Folder after upload image in this folder
		
		//String filePath = path + File.separator + randomFileName;
		
		String filePath = path + File.separator +fileName; //same name image store in folder 
		
		//Create Folder if Not Created Automatically
		
		File f = new File(path);
		if(!f.exists()) {
			f.mkdir();
		}
		
		
		// File Copy 
		
		Files.copy(file.getInputStream(),Paths.get(filePath));
		
		
		// Return Original name of file
		
		return fileName; // We can return the randomFilename generated above to the database
	}

	
	
	// ************************* Image Download from database Method ********************************
	
	
	@Override
	public InputStream downloadImage(String path, String fileName) throws FileNotFoundException {
				
		String fullPath = path+File.separator+fileName;
		
		InputStream is = new FileInputStream(fullPath);
		
		return is;
	}

}
