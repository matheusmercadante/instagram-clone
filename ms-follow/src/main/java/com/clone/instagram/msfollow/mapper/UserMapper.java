package com.clone.instagram.msfollow.mapper;

import com.clone.instagram.msfollow.dto.UserDTO;
import com.clone.instagram.msfollow.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  @Mapping(source = "id", target = "userId")
  User toModel(UserDTO userDTO);
}
