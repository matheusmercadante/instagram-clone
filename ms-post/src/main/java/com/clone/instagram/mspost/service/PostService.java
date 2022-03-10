package com.clone.instagram.mspost.service;

import java.util.List;
import com.clone.instagram.mspost.dto.request.PostDTO;
import com.clone.instagram.mspost.entity.Post;
import com.clone.instagram.mspost.exception.PostNotFoundException;
import com.clone.instagram.mspost.mapper.PostMapper;
import com.clone.instagram.mspost.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class PostService {
  private PostRepository postRepository;

  private final PostMapper postMapper = PostMapper.INSTANCE;

  public List<Post> listByUsername(String username) {
    return postRepository.findByUsername(username);
  }

  public List<Post> listByIds(List<String> ids) {
    return postRepository.findByIdInOrderByCreatedAtDesc(ids);
  }

  public Post createPost(PostDTO postDTO, String username) {
    log.info("creating post for image url {}", postDTO.getImageUrl());

    Post postToSave = postMapper.toModel(postDTO);

    // TODO: remove when audience is fixed 
    postToSave.setUsername(username);
    postToSave.setLastModifiedBy(username);

    Post savedPost = postRepository.save(postToSave);

    log.info("post {} is saved successfully for user {}", savedPost.getId(),
        savedPost.getUsername());

    return savedPost;
  }

  public void delete(String id) throws PostNotFoundException {
    log.info("deleting post {}", id);

    postRepository.findById(id).orElseThrow(() -> {
      log.warn("post not found id {}", id);

      return new PostNotFoundException(id);
    });

    postRepository.deleteById(id);
  }
}
