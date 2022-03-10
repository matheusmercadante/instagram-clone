package com.clone.instagram.mspost.controller;

import java.util.List;
import com.clone.instagram.mspost.dto.request.PostDTO;
import com.clone.instagram.mspost.entity.Post;
import com.clone.instagram.mspost.exception.PostNotFoundException;
import com.clone.instagram.mspost.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
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

  @GetMapping("/{username}")
  public List<Post> findPostsByUsername(@PathVariable("username") String username,
      @AuthenticationPrincipal Jwt principal) {
    log.info("retrieving posts for user {}", username);

    List<Post> posts = postService.listByUsername(username);
    log.info("found {} posts for user {}", posts.size(), username);

    return posts;
  }

  @GetMapping("/me")
  public List<Post> findCurrentUserPosts(@AuthenticationPrincipal Jwt principal) {
    log.info("retrieving posts for user {}", principal.getClaimAsString("preferred_username"));

    List<Post> posts = postService.listByUsername(principal.getClaimAsString("preferred_username"));
    log.info("found {} posts for user {}", posts.size(),
        principal.getClaimAsString("preferred_username"));

    return posts;
  }

  @PostMapping("/in")
  public ResponseEntity<?> findPostsByIdIn(@RequestBody List<String> ids) {
    log.info("retrieving posts for {} ids", ids.size());

    List<Post> posts = postService.listByIds(ids);
    log.info("found {} posts", posts.size());

    return ResponseEntity.ok(posts);
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Post createPost(@RequestBody PostDTO postDTO, @AuthenticationPrincipal Jwt principal) {
    log.info("received a request o create a post for image url {}", postDTO.getImageUrl());

    return postService.createPost(postDTO, principal.getClaimAsString("preferred_username"));
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable("id") String id, @AuthenticationPrincipal Jwt principal)
      throws PostNotFoundException {
    log.info("received a delete request for post id {} from user {}", id,
        principal.getClaimAsString("preferred_username"));

    postService.delete(id);
  }
}


// sub=ee9efad7-615c-4f9f-aca6-f7de25bdf0e0,
// resource_access={"account":{"roles":["manage-account","manage-account-links","view-profile"]}},
// email_verified=false, iss=http://localhost:8080/realms/instagram-clone, typ=Bearer,
// preferred_username=matheusmercadante, given_name=Matheus,
// sid=4565ea23-c15a-457a-b02e-1d34063f98b6, aud=[account], acr=1,
// realm_access={"roles":["offline_access","default-roles-instagram-clone","uma_authorization","user"]},
// azp=ms-post, scope=email profile, name=Matheus Mercadante, exp=2022-03-10T01:24:47Z,
// session_state=4565ea23-c15a-457a-b02e-1d34063f98b6, iat=2022-03-10T01:19:47Z,
// family_name=Mercadante, jti=74e064cb-09c0-4f8f-a0d1-767e854a44d0,
// email=matheusmercadante2002@gmail.com}
