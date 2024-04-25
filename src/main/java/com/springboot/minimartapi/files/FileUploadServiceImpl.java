package com.springboot.minimartapi.files;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${file-upload.server-path}")
    private String serverPath;
    @Value("${file-upload.base-uri}")
    private String baseUri;
    @Value("${file-upload.client-path}")
    private String clientPath;

    @Override
    public String uploadProductPictures(MultipartFile file) {
            String extension = this.extractExtension(Objects.requireNonNull(file.getOriginalFilename()));
            String newFileName = UUID.randomUUID() +"."+ extension;
            String absolutePath = serverPath + newFileName;
            Path path = Paths.get(absolutePath);
            try {
                Files.copy(file.getInputStream(), path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return baseUri+newFileName;

    }
    private String extractExtension(String fileName){
        int lastIndexOfDot = fileName.lastIndexOf(".");
        return  fileName.substring(lastIndexOfDot + 1);
    }
}
