package com.backfcdev.advancedspringrestapis.upload;

import com.backfcdev.advancedspringrestapis.exception.MediaFileNotFoundException;
import com.backfcdev.advancedspringrestapis.exception.StorageException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileSystemStorageService implements StorageService  {

    @Value("${storage.location}")
    private String storageLocation;

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(storageLocation));
        } catch (IOException ex) {
            throw new StorageException("Failed to create file store: " + storageLocation);
        }
    }

    public String store(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String filename = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(originalFilename);
        if (file.isEmpty()) {
            throw new StorageException("Cannot store an empty file " + originalFilename);
        }
        try {
            InputStream inputStream = file.getInputStream();
            Files.copy(inputStream, Paths.get(storageLocation).resolve(filename),
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new StorageException("Failed to save the file " + originalFilename);
        }
        return filename;
    }

    public Resource loadAsResource(String filename) {
        try {
            Path path = Paths.get(storageLocation).resolve(filename);
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new MediaFileNotFoundException("The file has not been found: " + filename);
            }
        } catch (MalformedURLException ex) {
            throw new MediaFileNotFoundException("The file has not been found: " + filename);
        }
    }

    public void delete(String filename) {
        Path path = Paths.get(storageLocation).resolve(filename);
        try {
            FileSystemUtils.deleteRecursively(path);
        } catch (IOException ex) {

        }
    }
}

