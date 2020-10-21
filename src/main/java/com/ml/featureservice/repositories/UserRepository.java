package com.ml.featureservice.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ml.featureservice.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public Optional<User> findUserByEmail(String email);
}
