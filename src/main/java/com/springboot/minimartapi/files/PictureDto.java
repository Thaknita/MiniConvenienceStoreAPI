package com.springboot.minimartapi.files;

import lombok.Builder;

@Builder
public record PictureDto(
        String name,
        String extension,
        Long size,
        String uri
) {
}
