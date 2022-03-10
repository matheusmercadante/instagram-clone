package com.clone.instagram.mspost.entity;

import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Document
public class Post {
  @Id
  private String id;

  // TODO: fix audience
  // @CreatedBy
  private String username;

  @NonNull
  private String imageUrl;

  @NonNull
  private String caption;

  @CreatedDate
  private Instant createdAt;

  // TODO: fix audience
  // @LastModifiedDate
  private Instant updatedAt;

  @LastModifiedBy
  private String lastModifiedBy;
}
