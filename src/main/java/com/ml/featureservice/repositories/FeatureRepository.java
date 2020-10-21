package com.ml.featureservice.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ml.featureservice.model.Feature;

public interface FeatureRepository extends JpaRepository<Feature, Long> {
	Optional<Feature> findByName(String name);
}
