package com.ml.featureservice.service;

import com.ml.featureservice.model.User;

public interface UserService {
	
	/* Defining methods for UserService here */
	public User createUser(User user);
	public User getUserByEmail(String email);

}
