package com.samsam.travel.travelcommerce.domain.image.api;

import com.samsam.travel.travelcommerce.domain.image.service.ImageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> serveImage(@PathVariable("filename") String filename) {
        try {
            Resource resource = imageService.loadImage(filename);

            if (resource != null) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/uploadReviewImage")
    public ResponseEntity<?> uploadReviewImage(
            @RequestParam("reviewId") String reviewId,
            @RequestParam("image") MultipartFile image) {
        try {
            imageService.saveReviewImage(reviewId, image);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(500).body("이미지 업로드 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<Resource> serveReviewImage(@PathVariable("reviewId") String reviewId) {
        try {
            Resource resource = imageService.loadReviewImage(reviewId);

            if (resource != null) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}