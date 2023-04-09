package com.contact.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.time.Year;

import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.contact.model.Contact;
import com.contact.model.User;
import com.contact.repository.UserRepo;

import jakarta.servlet.http.HttpSession;

@Service
public class UserService {
	private static long imageNameIncrementer=10000; //To make all image name unique.
	@Autowired
	private UserRepo userRepo;

	// process register form service handler
	public User register(User user) {
		return userRepo.save(user);
	}

	// fetch file and store it in disk and store file name in contact table
	public void saveImage(MultipartFile file, Contact contact, String userName, HttpSession session) {
		try {
			if (file.isEmpty()) {
				contact.setImageUrl("default.png");
				File absFile = new ClassPathResource("static/images").getFile();
				Path path = Paths.get(absFile.getAbsolutePath() + File.separator + contact.getImageUrl()).getFileName();
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				User user = userRepo.getUserByEmail(userName);
				contact.setUser(user); // save user in contact table
				user.getContacts().add(contact); // save Contact in User contact list
				userRepo.save(user);
				System.out.println("User added !");
				session.setAttribute("msg", new com.contact.helper.Message("One contact added.", "alert-success"));
			}else {
//				String originalFilename = file.getOriginalFilename();
				int year=Year.now().getValue();
				System.out.println(year);
				contact.setImageUrl("Img_"+year+""+(imageNameIncrementer++)+"");
				File absFile = new ClassPathResource("static/images").getFile();
				Path path = Paths.get(absFile.getAbsolutePath() + File.separator + contact.getImageUrl());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				User user = userRepo.getUserByEmail(userName);
				contact.setUser(user); // save user in contact table
				user.getContacts().add(contact); // save Contact in User contact list
				userRepo.save(user);
				System.out.println("User added !");
				session.setAttribute("msg", new com.contact.helper.Message("One contact added.", "alert-success"));
			}

			

		} catch (Exception e) {
			session.setAttribute("msg",
					new com.contact.helper.Message("Something went wrong ! try again.", "alert-danger"));
			e.printStackTrace();
		}
	}

}
