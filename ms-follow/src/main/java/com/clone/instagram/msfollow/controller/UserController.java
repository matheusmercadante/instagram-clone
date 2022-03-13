package com.clone.instagram.msfollow.controller;

import com.clone.instagram.msfollow.dto.request.FollowDTO;
import com.clone.instagram.msfollow.entity.User;
import com.clone.instagram.msfollow.mapper.UserMapper;
import com.clone.instagram.msfollow.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserController {
  private UserService userService;

  private final UserMapper userMapper = UserMapper.INSTANCE;

  @PostMapping("/users/followers")
  public ResponseEntity<String> follow(@RequestBody FollowDTO followDTO) {
    log.info("received a follow request follow {} following {}",
        followDTO.getFollower().getUsername(), followDTO.getFollowing().getUsername());

    User follower = userMapper.toModel(followDTO.getFollower());
    User following = userMapper.toModel(followDTO.getFollowing());

    userService.follow(follower, following);

    String successMessage = String.format("user %s is following user %s",
        followDTO.getFollower().getUsername(), followDTO.getFollowing().getUsername());

    log.info(successMessage);

    return ResponseEntity.ok(successMessage);
  }
}
