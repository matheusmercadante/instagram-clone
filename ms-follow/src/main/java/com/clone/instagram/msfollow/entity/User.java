package com.clone.instagram.msfollow.entity;

import java.util.Set;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import org.springframework.data.neo4j.core.schema.Relationship.Direction;
import lombok.Data;

@Data
@Node
public class User {
  @Id
  @GeneratedValue
  private Long id;

  private String userId;

  private String username;

  private String name;

  private String profilePhoto;

  @Relationship(type = "IS_FOLLOWING", direction = Direction.OUTGOING)
  private Set<User> friendships;
}
