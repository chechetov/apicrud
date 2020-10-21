package com.ml.featureservice.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * This is a model describing User type
 */

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String email;
	
	/* Joining User to Feature, many users can have many features */
	@ManyToMany(cascade = {CascadeType.ALL})
	@JoinTable(name="JOIN_USER_FEATURE",
	joinColumns= {@JoinColumn(name="featureid")},
	inverseJoinColumns={@JoinColumn(name="userid")})
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
	
	public User(String email, Set<Feature> features) {
		this.email = email;
		this.features = features;
	}
	
	public User() {
	}
	
	public User createWithEmail(String userEmail) {
		this.email = userEmail;
		return this;
	}
}
