package com.contact.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.contact.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer>{
	@Query("from Contact as c where c.user.id=:userId")
	public Page<Contact> findContactsByUserId(int userId,Pageable pageable);//Return all contact associates with that user ID
}
