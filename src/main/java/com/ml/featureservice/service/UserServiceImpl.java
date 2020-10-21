package com.ml.featureservice.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ml.featureservice.model.User;
import com.ml.featureservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User createUser(User user) {
		
		User createdUser = userRepository.save(user);
		return createdUser;
	}

	@Override
	public User getUserByEmail(String email) {
		
		/* Look user up and return if it is present, otherwise return null */
		Optional<User> userDb = userRepository.findUserByEmail(email);
		
		if (userDb.isPresent()) {
			return userDb.get();
		}
		else {
			return null;
		}
	}
}
