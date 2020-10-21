package com.ml.featureservice.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * This is a model describing User type
 */

@Entity(name="User")
@Table(name="user")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String email;
	
	/* Mapping users to features */
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "users")
	private Set<Feature> features = new HashSet<Feature>();
	

	public Long getId() {
		return id;
	}
	public String getEmail() {
		return email;
	}
	
	public Set<Feature> getFeatures() {
		return features;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public void setFeatures(Set<Feature> features) {
		this.features = features;
	}
	
	public User(String email) {
		this.email = email;
	}
	
	public User() {
	}
	
	public User createWithEmail(String userEmail) {
		this.email = userEmail;
		return this;
	}
//	@Override
//	public String toString() {
//		return "User [id=" + id + ", email=" + email + ", features=" + features + "]";
//	}
}
