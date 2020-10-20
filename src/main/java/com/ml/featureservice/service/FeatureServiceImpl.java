package com.ml.featureservice.service;

import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ml.featureservice.model.Feature;
import com.ml.featureservice.model.User;
import com.ml.featureservice.repositories.FeatureRepository;
import com.ml.featureservice.repositories.UserRepository;

@Service
@Transactional
public class FeatureServiceImpl implements FeatureService {
	
	@Autowired
	private FeatureRepository 	featureRepository;
	@Autowired
	private UserRepository		userRepository;
	@Autowired
	private UserServiceImpl 	userService;

	@Override
	public void createFeature(String featureName, String userEmail, boolean isEnabled) {
		
		// Checking if feature with featureName is already in DB
		Feature featureDb = this.getFeatureByName(featureName);
		
		// If not present - create one
		if (featureDb == null) {
			featureDb = new Feature().createWithName(featureName);
		}
		
		// Checking if user with userEmail is already in DB
		User userDb = userService.getUserByEmail(userEmail);
		
		// If not present - creating one
		if (userDb == null) {
			userDb = new User().createWithEmail(userEmail);
		}
		
		if (isEnabled) {
			featureDb.getUsers().add(userDb);
		}
		else {
			featureDb.getUsers().remove(userDb);
		}
		
		// Save objects, return nothing
		featureRepository.save(featureDb);
		userRepository.save(userDb);
	}

	@Override
	public Feature getFeatureByName(String featureName) {
	
		System.out.println("HEEEEEEEERE 1!");

		Optional<Feature> featureDb = featureRepository.findByName(featureName);
		
		System.out.println("HEEEEEEEERE 2!");
		
		if (featureDb.isPresent()) {
			return featureDb.get();
		}
		else {
			return null;
		}
	}

	@Override
	public boolean isEnabledForUser(String featureName, String userEmail) {
		// TODO Auto-generated method stub
		
		// Checking if feature is present in DB
		Feature featureDb = this.getFeatureByName(featureName);
		
		// If not present - can not have access by definition
		if (featureDb == null) {
			return false;
		}
		
		// Looking user up in the same way
		User userDb = userService.getUserByEmail(userEmail);
		
		if (userDb == null) {
			return false;
		}
		
		// Getting list of users bound to feature and checking if it has our user
		Set<User> featureUsers = featureDb.getUsers();
		boolean containsUser = featureUsers.contains(userDb);		
		
		return containsUser;
	}
}
