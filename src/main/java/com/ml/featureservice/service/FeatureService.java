package com.ml.featureservice.service;

import com.ml.featureservice.model.Feature;

public interface FeatureService {
	
	/* Defining methods for FeatureService here */
	public boolean createFeature(String featureName, String userEmail, boolean isEnabled);
	public Feature getFeatureByName(String featureName);
	public boolean isEnabledForUser(String featureName, String userEmail);
	
}
