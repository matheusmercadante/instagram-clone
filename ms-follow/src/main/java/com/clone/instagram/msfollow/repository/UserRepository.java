package com.clone.instagram.msfollow.repository;

import java.util.Optional;
import com.clone.instagram.msfollow.entity.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserRepository extends Neo4jRepository<User, Long> {

  Optional<User> findByUserId(String userId);

  Optional<User> findByUsername(String username);
}
