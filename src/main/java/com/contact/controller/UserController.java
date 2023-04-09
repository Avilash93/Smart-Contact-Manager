package com.contact.controller;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Optional;

import org.aspectj.weaver.NewConstructorTypeMunger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.contact.helper.Message;
import com.contact.model.Contact;
import com.contact.model.User;
import com.contact.repository.ContactRepository;
import com.contact.repository.UserRepo;
import com.contact.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepo repo;
	@Autowired
	private UserService service;
	@Autowired
	private ContactRepository contactRepository;

	@ModelAttribute
	public void commonAttribute(Model model, Principal principal, HttpSession session) {
		// Principle is used to fetch current logged in user details.
		User user = repo.getUserByEmail(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("httpSession", session);
	}

//User Dashboard
	@GetMapping("/")
	public String profile(Model model) {

		return "user/userdashboard";
	}

//User Dashboard
	@GetMapping("/add-contact")
	public String openAddContact(Model model) {
		model.addAttribute("title", "Add Contact . Contact Manager");
		model.addAttribute("contact", new Contact());
		return "user/add-contact";
	}

	@PostMapping("/process-add-contact")
	public String openAddContact(@ModelAttribute Contact contact, @RequestParam("profileImage") MultipartFile file,
			Principal principal, HttpSession session) {
		service.saveImage(file, contact, principal.getName(), session);//// fetch file and store it in disk and store
																		//// file name in contact table
		//// save Contact in User contact list

		return "user/add-contact";
	}

	// View all contacts
	@GetMapping("view-contacts/{currentPage}")
	public String viewContacts(@PathVariable("currentPage") Integer currentPage, Model model, Principal principal) {
		model.addAttribute("title", "View Contacts . Contact Manager");
		String email = principal.getName();
		User user = repo.getUserByEmail(email);
		// we pass
		Pageable pageable = PageRequest.of(currentPage, 8);
		Page<Contact> contacts = contactRepository.findContactsByUserId(user.getId(), pageable);
		model.addAttribute("Contacts", contacts);
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", contacts.getTotalPages());
		return "user/view_contacts";
	}

	// View single contacts
	@GetMapping("/view-contact/{id}")
	public String getContact(@PathVariable("id") Integer id, Model model, Principal principal) {
		String userEmail = principal.getName();
		Optional<Contact> optional = contactRepository.findById(id);
		if (optional.isPresent()) {

			Contact contact = optional.get();
			User user = repo.getUserByEmail(userEmail);
			if (user.getId() == contact.getUser().getId()) {
				model.addAttribute("contact", contact);
				model.addAttribute("title", contact.getName());
			}
		}
		return "user/view_contact_details";
	}

	// Delete single contacts
	@GetMapping("/delete/{id}")
	public String deleteContact(@PathVariable("id") Integer id, Model model, Principal principal) {
		Optional<Contact> optional = contactRepository.findById(id);
		if (optional.isPresent()) {

			Contact contact = optional.get();

			// DElete user to unlink contact and user relation because cascade is set to all
			contact.setUser(null);
			//
			try {

				File file = new File(
						"F:\\JAVA\\springboot-workspace\\smartcontactmanager\\src\\main\\resources\\static\\images\\"
								+ contact.getImageUrl());
				System.out.println(file.getAbsolutePath());
//				File absFile = new ClassPathResource("static/images/"+contact.getImageUrl()).getFile();
				// Delete contact
				contactRepository.delete(contact);
				if (file.delete()) {
					System.out.println("deleted succssfully");

					model.addAttribute("msg", new Message("Deleted Successfully.", "success"));
				} else {
					model.addAttribute("msg", new Message("Something went wrong.", "danger"));
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		return "redirect:/user/view-contacts/0";
	}

	// Update contact
	@PostMapping("/update-contact/{id}")
	public  String updateContact(@PathVariable("id") Integer id, Model model) {
		Contact contact = contactRepository.findById(id).get();
		model.addAttribute("contact", contact);
		model.addAttribute("title", "Update Contact . Contact Manager");
		return "user/update_contact";
	}

}
