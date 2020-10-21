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
		
		/* Checking if Feature and User are already in database, creating them otherwise */
		Feature featureDb = this.getFeatureByName(featureName);
		
		if (featureDb == null) {
			featureDb = new Feature().createWithName(featureName);
		}
		
		User userDb = userService.getUserByEmail(userEmail);
		if (userDb == null) {
			userDb = new User().createWithEmail(userEmail);
		}
		
		if (isEnabled) {
			featureDb.getUsers().add(userDb);
		}
		else {
			featureDb.getUsers().remove(userDb);
		}
		
		/* Save objects to database */ 
		featureRepository.save(featureDb);
		userRepository.save(userDb);
	}

	@Override
	public Feature getFeatureByName(String featureName) {
	
		/* Look feature up and return if it is present, otherwise return null */
		Optional<Feature> featureDb = featureRepository.findByName(featureName);
				
		if (featureDb.isPresent()) {
			return featureDb.get();
		}
		else {
			return null;
		}
	}

	@Override
	public boolean isEnabledForUser(String featureName, String userEmail) {
		
		/* Checking if Feature is already in database */
		Feature featureDb = this.getFeatureByName(featureName);
		
		/* If feature is not present - access can not be defined as well */
		if (featureDb == null) {
			return false;
		}
		
		/* Applying logic as above for User */
		User userDb = userService.getUserByEmail(userEmail);
		
		if (userDb == null) {
			return false;
		}
		
		/* Otherwise, check if user has access to feature */
		Set<User> featureUsers = featureDb.getUsers();
		boolean containsUser = featureUsers.contains(userDb);		
		
		return containsUser;
	}
}
