package com.clone.instagram.msmedia.entity;

import java.time.Instant;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Document
public class ImageMetadata {
  @Id
  private String id;

  @NonNull
  private String filename;

  @NonNull
  private String uri;

  @NonNull
  private String fileType;

  // TODO: fix audience (keycloak) -- ms-post
  @CreatedBy
  private String username;

  @CreatedDate
  private Instant createdAt;
}
