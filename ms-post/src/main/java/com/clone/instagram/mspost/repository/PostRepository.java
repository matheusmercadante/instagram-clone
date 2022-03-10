package com.clone.instagram.mspost.repository;

import java.util.List;
import com.clone.instagram.mspost.entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, String> {
  List<Post> findByUsername(String username);

  List<Post> findByIdInOrderByCreatedAtDesc(List<String> ids);
}
