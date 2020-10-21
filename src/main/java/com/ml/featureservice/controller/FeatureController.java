package com.ml.featureservice.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ml.featureservice.service.FeatureServiceImpl;

@RestController
public class FeatureController {
	
	@Autowired
	private FeatureServiceImpl featureService;
	
	@GetMapping("/feature")
	public ResponseEntity<?> featureResponse (@RequestParam String email, @RequestParam String featureName){
		
		/* Using HashMap to collect response fields into object */
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		result.put("canAccess", featureService.isEnabledForUser(featureName, email));
		return ResponseEntity.ok(result);	
	}
	
	@PostMapping("/feature")
	public ResponseEntity<Void> createFeature(@RequestBody Map<String, Object> requestBody){
		
		/* Using HashMap to collect request fields into object */
		String featureName = (String) requestBody.get("featureName");
		String email 	   = (String) requestBody.get("email");
		Boolean enable 	   = (Boolean) requestBody.get("enable");
		
		/* Try to create feature */
		boolean featureCreated = false;
		try {
			featureCreated = featureService.createFeature(featureName, email, enable);
		}
		catch (Exception e) {
			/* If something bad happened - I want to know */
			e.printStackTrace();

		}
		
		if (featureCreated) {
			/* Return empty HTTP 200 */
			return ResponseEntity.ok(null);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
	}
}
