package com.clone.instagram.msmedia.repository;

import com.clone.instagram.msmedia.entity.ImageMetadata;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ImageMetadataRepository extends MongoRepository<ImageMetadata, String> {

}
