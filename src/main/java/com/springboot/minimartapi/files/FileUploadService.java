package com.springboot.minimartapi.files;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileUploadService {
    String uploadProductPictures(MultipartFile files );


}
