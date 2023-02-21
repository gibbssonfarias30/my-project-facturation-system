package com.backfcdev.advancedspringrestapis.upload;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
    void init();
    String store(MultipartFile file);
    Resource loadAsResource(String filename);
    void delete(String filename);

}
