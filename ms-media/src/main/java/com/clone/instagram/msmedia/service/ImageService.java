package com.clone.instagram.msmedia.service;

import com.clone.instagram.msmedia.entity.ImageMetadata;
import com.clone.instagram.msmedia.repository.ImageMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ImageService {
  private ImageMetadataRepository imageMetadataRepository;

  private FileStorageService fileStorageService;

  public ImageMetadata upload(MultipartFile file, String username) {
    String filename = StringUtils.cleanPath(file.getOriginalFilename());
    log.info("storing file {}", filename);

    ImageMetadata metadata = fileStorageService.store(file, username);

    return imageMetadataRepository.save(metadata);
  }
}
