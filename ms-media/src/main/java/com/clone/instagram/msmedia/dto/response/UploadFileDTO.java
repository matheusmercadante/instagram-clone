package com.clone.instagram.msmedia.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFileDTO {
  private String filename;

  private String uri;

  private String fileType;
}
