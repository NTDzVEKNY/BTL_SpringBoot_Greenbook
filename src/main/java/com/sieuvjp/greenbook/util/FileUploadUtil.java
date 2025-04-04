package com.sieuvjp.greenbook.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Component
public class FileUploadUtil {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String saveFile(MultipartFile file, String subdirectory) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Failed to store empty file");
        }

        // Generate a unique file name to avoid conflicts
        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + extension;

        // Create target directory if it doesn't exist
        Path targetDir = Paths.get(uploadDir, subdirectory);
        if (!Files.exists(targetDir)) {
            Files.createDirectories(targetDir);
        }

        // Save the file
        Path targetLocation = targetDir.resolve(newFileName);
        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, targetLocation, StandardCopyOption.REPLACE_EXISTING);
        }

        return newFileName;
    }

    public void deleteFile(String filePath) throws IOException {
        Path targetLocation = Paths.get(uploadDir, filePath);
        Files.deleteIfExists(targetLocation);
    }
}