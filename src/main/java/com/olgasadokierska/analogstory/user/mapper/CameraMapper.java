package com.olgasadokierska.analogstory.user.mapper;

import com.olgasadokierska.analogstory.user.dtos.CameraDTO;
import com.olgasadokierska.analogstory.user.model.Camera;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CameraMapper {

    CameraMapper INSTANCE = Mappers.getMapper(CameraMapper.class);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "productId", source = "product.id")
    CameraDTO cameraToCameraDTO(Camera camera);
}
