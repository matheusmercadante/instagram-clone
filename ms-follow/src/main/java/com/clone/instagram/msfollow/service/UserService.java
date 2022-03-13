package com.clone.instagram.msfollow.service;

import java.util.HashSet;
import com.clone.instagram.msfollow.entity.User;
import com.clone.instagram.msfollow.exception.UsernameAlreadyExistsException;
import com.clone.instagram.msfollow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserService {
  private UserRepository userRepository;

  @Transactional
  public User follow(User follower, User following) {
    log.info("user {} will follow {}", follower.getUsername(), following.getUsername());

    User userFollower = userRepository.findByUserId(follower.getUserId()).orElseGet(() -> {
      log.info("user {} not exists", follower.getUsername());

      return createUser(follower);
    });

    User userFollowing = userRepository.findByUserId(following.getUserId()).orElseGet(() -> {
      log.info("user {} not exists", following.getUsername());

      return createUser(following);
    });

    if (userFollower.getFriendships() == null)
      userFollower.setFriendships(new HashSet<>());

    userFollower.getFriendships().add(userFollowing);

    return userRepository.save(userFollower);
  }

  private User createUser(User user) {
    if (userRepository.findByUsername(user.getUsername()).isPresent()) {
      String userIsAlreadyMessage =
          String.format("username %s is already exists", user.getUsername());

      log.warn(userIsAlreadyMessage);

      throw new UsernameAlreadyExistsException(userIsAlreadyMessage);
    }

    User savedUser = userRepository.save(user);

    log.info("user {} save successfully", savedUser.getUsername());

    return savedUser;
  }
}
