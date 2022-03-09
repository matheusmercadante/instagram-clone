package com.clone.instagram.mspost.controller;

import java.security.Principal;
import java.util.List;
import com.clone.instagram.mspost.dto.request.PostDTO;
import com.clone.instagram.mspost.entity.Post;
import com.clone.instagram.mspost.exception.PostNotFoundException;
import com.clone.instagram.mspost.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class PostController {

  PostService postService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Post createPost(@RequestBody PostDTO postDTO) {
    log.info("received a request o create a post for image url {}", postDTO.getImageUrl());

    return postService.createPost(postDTO);
  }

  @GetMapping("/{username}")
  public List<Post> findPostsByUsername(@PathVariable("username") String username, Principal principal) {
    log.info("retrieving posts for user {}", username);

    List<Post> posts = postService.listByUsername(username);
    log.info("found {} posts for user {}", posts.size(), username);

    return posts;
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable("id") String id) throws PostNotFoundException {
    log.info("received a delete request for post id {}", id);

    postService.delete(id);
  }
}
