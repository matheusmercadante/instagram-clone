package com.clone.instagram.mspost.mapper;

import com.clone.instagram.mspost.dto.request.PostDTO;
import com.clone.instagram.mspost.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {
  PostMapper INSTANCE = Mappers.getMapper(PostMapper.class);

  Post toModel(PostDTO postDTO);

  PostDTO toDTO(Post post);
}
