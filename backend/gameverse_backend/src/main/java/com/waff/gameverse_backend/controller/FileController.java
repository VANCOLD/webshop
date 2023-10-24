package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.dto.FileUploadResponse;
import com.waff.gameverse_backend.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@EnableMethodSecurity
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }


    @PostMapping
    @PreAuthorize("@tokenService.hasPrivilege('edit_products')")
    public ResponseEntity<FileUploadResponse>  upload(@RequestParam("file") MultipartFile file) {
        String reference = fileService.upload(file);
        return ResponseEntity.ok(new FileUploadResponse(true, reference));
    }

    @DeleteMapping("/{reference}")
    @PreAuthorize("@tokenService.hasPrivilege('edit_products')")
    public ResponseEntity<Void> deleteFile(@PathVariable String reference) {
        boolean deleted = fileService.delete(reference);

        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
