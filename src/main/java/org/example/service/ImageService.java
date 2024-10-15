package org.example.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Optional;

public interface ImageService {
    void upload(String imagePath, InputStream data);

    void uploadImage(MultipartFile image);

    Optional<byte[]> getImage(String imagePath);
}
