package com.contact.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.contact.model.User;
import com.contact.repository.UserRepo;
@Component
public class UserDetailServiceImpl implements UserDetailsService{
	@Autowired
	private UserRepo userRepo;
	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		User userByEmail = userRepo.getUserByEmail(userEmail);
		if(userByEmail == null) {
			throw new UsernameNotFoundException("User not found !");
		}
		return new UserDetailsImpl(userByEmail);
		
	}

}
