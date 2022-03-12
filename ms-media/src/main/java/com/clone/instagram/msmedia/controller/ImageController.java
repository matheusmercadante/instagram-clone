package com.clone.instagram.msmedia.controller;

import com.clone.instagram.msmedia.dto.response.UploadFileDTO;
import com.clone.instagram.msmedia.entity.ImageMetadata;
import com.clone.instagram.msmedia.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ImageController {
  private ImageService imageService;

  @PostMapping("/images")
  public UploadFileDTO uploadFile(@RequestParam("image") MultipartFile file,
      @AuthenticationPrincipal Jwt principal) {
    String filename = StringUtils.cleanPath(file.getOriginalFilename());
    log.info("received a request to upload file {} for user {}", filename,
        principal.getClaimAsString("preferred_username"));

    ImageMetadata metadata =
        imageService.upload(file, principal.getClaimAsString("preferred_username"));

    return new UploadFileDTO(metadata.getFilename(), metadata.getUri(), metadata.getFileType());
  }
}
