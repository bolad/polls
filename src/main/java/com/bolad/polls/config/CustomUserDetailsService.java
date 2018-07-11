package com.bolad.polls.config;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bolad.polls.model.User;
import com.bolad.polls.repository.UserRepository;
import com.bolad.polls.security.UserPrincipal;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername (String usernameOrEmail)
		throws UsernameNotFoundException { 
		
			//Let people login with either username or email
			User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
					.orElseThrow(()->
							new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)
			);
			
			return UserPrincipal.create(user);
	}
	

}
