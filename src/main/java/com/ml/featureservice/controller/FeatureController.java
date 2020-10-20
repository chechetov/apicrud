package com.ml.featureservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ml.featureservice.service.FeatureServiceImpl;

@RestController
public class FeatureController {
	
	@Autowired
	private FeatureServiceImpl featureService;
	
	@GetMapping("/feature")
	public ResponseEntity<?> featureResponse (@RequestParam String email, @RequestParam String featureName){
		
		// Since I did not create wrapper object for Request and Response I have to work around
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		result.put("canAccess", featureService.isEnabledForUser(featureName, email));
		return ResponseEntity.ok(result);	
	}
	
	@PostMapping("/feature")
	public ResponseEntity<Void> createFeature(@RequestParam String featureName, @RequestParam String email, @RequestParam boolean enable){
		
		try {
			featureService.createFeature(featureName, email, enable);
			return ResponseEntity.ok(null);
		}
		catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
			
		}
	}
}
