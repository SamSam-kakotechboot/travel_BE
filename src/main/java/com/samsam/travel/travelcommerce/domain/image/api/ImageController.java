package com.samsam.travel.travelcommerce.domain.image.api;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    // 티켓 이미지가 저장된 경로
    private final Path ticketImagesLocation = Paths.get("src/main/resources/image/ticket");

    // 리뷰 이미지가 저장된 경로
    private final Path reviewImagesLocation = Paths.get("src/main/resources/image/review");

    // 이미지 제공 메서드
    @GetMapping("/{filename}")
    public ResponseEntity<Resource> serveImage(@PathVariable("filename") String filename) {
        try {
            // 파일 경로 설정 (티켓 이미지를 기본 경로로 사용)
            Path file = ticketImagesLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            // 파일이 존재하고 읽을 수 있는지 확인
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                // 파일이 없거나 읽을 수 없는 경우 404 응답
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            // URL이 잘못된 경우 400 응답
            return ResponseEntity.badRequest().build();
        }
    }

    // 리뷰 이미지 업로드 메서드
    @PostMapping("/uploadReviewImage")
    public ResponseEntity<?> uploadReviewImage(
            @RequestParam("reviewId") String reviewId,
            @RequestParam("image") MultipartFile image) {
        try {
            // 특수문자를 제거한 리뷰 ID로 파일 이름을 설정하고 확장자를 추가
            String sanitizedReviewId = reviewId.replaceAll("[^a-zA-Z0-9-_]", "");
            Path filePath = reviewImagesLocation.resolve(sanitizedReviewId + ".png");

            // 이미지 저장
            Files.copy(image.getInputStream(), filePath);

            // 업로드 성공 응답
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            // 저장 중 오류 발생 시 500 응답
            return ResponseEntity.status(500).body("이미지 업로드 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<Resource> serveReviewImage(@PathVariable("reviewId") String reviewId) {
        try {
            // 파일 경로 설정
            Path file = reviewImagesLocation.resolve(reviewId + ".png"); // 파일 확장자가 png로 저장된다고 가정
            Resource resource = new UrlResource(file.toUri());

            // 파일이 존재하고 읽을 수 있는지 확인
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                // 파일이 없거나 읽을 수 없는 경우 404 응답
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            // URL이 잘못된 경우 400 응답
            return ResponseEntity.badRequest().build();
        }
    }
}