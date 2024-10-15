package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    @Value(value = "${app.image.bucket}")
    private String bucket;

    @Override
    public void upload(String imagePath, InputStream data) {
        Path fullImagePath = Path.of(bucket, imagePath);
        try (data) {
            Files.createDirectories(fullImagePath.getParent());
            Files.write(fullImagePath,
                    data.readAllBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    @Override
    public void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            upload(image.getOriginalFilename(), image.getInputStream());
        } else {
            throw new NullPointerException();
        }
    }


    @SneakyThrows
    @Override
    public Optional<byte[]> getImage(String imagePath) {
        Path fullImagePath = Path.of(bucket, imagePath);
        return Files.exists(fullImagePath) ?
                Optional.of(Files.readAllBytes(fullImagePath)) :
                Optional.empty();
    }
}
