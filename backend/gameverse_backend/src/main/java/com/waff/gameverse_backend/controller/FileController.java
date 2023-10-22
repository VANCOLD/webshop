package com.waff.gameverse_backend.controller;

import com.waff.gameverse_backend.dto.FileUploadResponse;
import com.waff.gameverse_backend.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

    @GetMapping("/{reference}")
    public ResponseEntity<Resource> getFile(@PathVariable String reference) {
        Resource fileResource = fileService.get(reference);

        if (fileResource != null) {
            String filename = fileResource.getFilename();
            String fileExtension = filename.substring(filename.lastIndexOf(".") + 1);

            MediaType mediaType = MediaType.APPLICATION_OCTET_STREAM;
            if (fileExtension.equalsIgnoreCase("png")) {
                mediaType = MediaType.IMAGE_PNG;
            } else if (fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("jpeg")) {
                mediaType = MediaType.IMAGE_JPEG;
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(mediaType);
            headers.setContentDispositionFormData("attachment", filename);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileResource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{reference}")
    public ResponseEntity<Void> deleteFile(@PathVariable String reference) {
        boolean deleted = fileService.delete(reference);

        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
