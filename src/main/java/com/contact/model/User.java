package com.contact.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Component
public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 12;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
private int id;
	@NotBlank(message = "Name cannot be empty!")
	@Size(min = 3,max=25, message = "Name must be between 3-25 ")
private String name;
@Column(unique = true)
@Email(regexp = "^[a-z0-9.]+@[a-z.]+$",message = "Incorect email format!")
@NotBlank(message = "Email cannot be empty!")
private String 	email;
//@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{4,}$",message = "Min  char, at least one uppercase letter, one lowercase letter and one number")
@NotBlank(message = "Password cannot be empty!")
private String 	password;
private String 	role;
private boolean	enable;
private String 	imageUrl;	
@Column(length = 500)
private String 	about;
@OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
List<Contact> contacts=new ArrayList<>();
public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
public boolean isEnable() {
	return enable;
}
public void setEnable(boolean enable) {
	this.enable = enable;
}
public String getImageUrl() {
	return imageUrl;
}
public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
}
public String getAbout() {
	return about;
}
public void setAbout(String about) {
	this.about = about;
}
public List<Contact> getContacts() {
	return contacts;
}
public void setContacts(List<Contact> contacts) {
	this.contacts = contacts;
}
@Override
public String toString() {
	return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", role=" + role
			+ ", enable=" + enable + ", imageUrl=" + imageUrl + ", about=" + about + ", contacts=" + contacts + "]";
}


}
