package com.springboot.minimartapi.files;

import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public interface FilesMapper {
    List<PictureDto> toFileListDto(List<PictureDto> files);
}
