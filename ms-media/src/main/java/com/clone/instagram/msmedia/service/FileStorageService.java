package com.clone.instagram.msmedia.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import com.clone.instagram.msmedia.entity.ImageMetadata;
import com.clone.instagram.msmedia.exception.InvalidFileException;
import com.clone.instagram.msmedia.exception.InvalidFileNameException;
import com.clone.instagram.msmedia.exception.StorageException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileStorageService {
  @Value("${file.upload-dir}")
  private String uploadDirectory;

  @Value("${file.path.prefix}")
  private String filePathDirectory;

  @Autowired
  private Environment environment;

  public ImageMetadata store(MultipartFile file, String username) {
    String filename = StringUtils.cleanPath(file.getOriginalFilename());
    log.info("storing file {}", filename);

    try {
      if (file.isEmpty()) {
        log.warn("failed to store empty file {}", filename);

        throw new InvalidFileException("Failed to store empty file " + filename);
      }

      if (filename.contains("..")) {
        log.warn("cannot store file with relative patth {}", filename);

        throw new InvalidFileNameException("Cannot store file with relaive path " + filename);
      }

      String extension = FilenameUtils.getExtension(filename);
      String newFilename = UUID.randomUUID() + "." + extension;

      try (InputStream inputStream = file.getInputStream()) {
        Path userDir = Paths.get(uploadDirectory, username);

        if (Files.notExists(userDir))
          Files.createDirectory(userDir);

        Files.copy(inputStream, userDir.resolve(newFilename), StandardCopyOption.REPLACE_EXISTING);
      }

      String port = environment.getProperty("local.server.port");
      String hostname = InetAddress.getLocalHost().getHostName();

      String fileUrl = String.format("http://%s:%s%s/%s/%s", hostname, port, filePathDirectory,
          username, newFilename);

      log.info("successfully stored file {} locatiton {}", filename, fileUrl);

      return new ImageMetadata(filename, fileUrl, file.getContentType());
    } catch (IOException e) {
      log.error("failed to store file {} error: {}", filename, e);

      throw new StorageException("Failed to store file " + filename, e);
    }
  }
}
