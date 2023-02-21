package com.backfcdev.advancedspringrestapis.controller;

import com.backfcdev.advancedspringrestapis.upload.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/media")
public class MediaController {

    private final StorageService storageService;

    @GetMapping("{filename:.+}")
    ResponseEntity<Resource> getFilename(@PathVariable String filename) throws IOException {
        Resource resource = storageService.loadAsResource(filename);
        String contentType = Files.probeContentType(resource.getFile().toPath());
        return ResponseEntity
                .ok()
                .header("Content-Type", contentType)
                .body(resource);
    }

    @PostMapping("/upload")
    Map<String, String> upload(@RequestParam MultipartFile file){
        String ruta = storageService.store(file);
        Map<String, String> response = new HashMap<>();
        response.put("ruta", ruta);
        return response;
    }
}
