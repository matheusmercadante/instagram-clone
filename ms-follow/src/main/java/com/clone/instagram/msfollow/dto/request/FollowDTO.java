package com.clone.instagram.msfollow.dto.request;

import com.clone.instagram.msfollow.dto.UserDTO;
import lombok.Data;

@Data
public class FollowDTO {
  UserDTO follower;
  UserDTO following;
}
