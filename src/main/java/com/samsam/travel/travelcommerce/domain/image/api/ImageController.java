package com.samsam.travel.travelcommerce.domain.image.api;

import com.samsam.travel.travelcommerce.domain.image.service.CsvService;
import com.samsam.travel.travelcommerce.domain.image.service.ImageService;
import com.samsam.travel.travelcommerce.dto.ai.KeywordDto;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    private final ImageService imageService;
    private final CsvService csvService;

    public ImageController(ImageService imageService, CsvService csvService) {
        this.imageService = imageService;
        this.csvService = csvService;
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

    @PostMapping("/uploadTicketImage")
    public ResponseEntity<?> uploadTicketImage(
            @RequestParam("title") String title,
            @RequestParam("image") MultipartFile image) {
        try {
            System.out.println("Title: " + title);
            System.out.println("Image Original Filename: " + image.getOriginalFilename());
            imageService.saveTicketImage(title, image);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            return ResponseEntity.status(500).body("티켓 이미지 업로드 중 오류가 발생했습니다.");
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

    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity<?> deleteReviewImage(@PathVariable("reviewId") String reviewId) {
        try {
            boolean isDeleted = imageService.deleteReviewImage(reviewId);

            if (isDeleted) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.ok().body("파일이 존재하지 않거나 이미 삭제되었습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("이미지 삭제 중 오류가 발생했습니다.");
        }
    }

    @DeleteMapping("/ticket/{title}")
    public ResponseEntity<?> deleteTicketImage(@PathVariable("title") String title) {
        try {
            boolean isDeleted = imageService.deleteTicketImage(title);

            if (isDeleted) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.ok().body("파일이 존재하지 않거나 이미 삭제되었습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body("티켓 이미지 삭제 중 오류가 발생했습니다.");
        }
    }


    // 오직 csv를 위한 메서드
    @GetMapping("/keywords/{filename}")
    public ResponseEntity<?> getAllKeywordsByFile(
            @PathVariable("filename") String filename) {
        try {
            // 파일 경로 동적 설정
            String filePath = "src/main/resources/ai/" + filename + ".csv";
            System.out.println("!!!!!!!");
            System.out.println(filePath);

            // CSV 파일에서 전체 데이터를 가져옴
            List<KeywordDto> keywords = csvService.getAllKeywordsFromCsv(filePath);

            if (keywords.isEmpty()) {
                return ResponseEntity.ok("AI 리뷰가 존재하지 않습니다.");
            } else {
                return ResponseEntity.ok(keywords);
            }
        } catch (IOException e) {
            return ResponseEntity.ok("AI 리뷰가 존재하지 않습니다.");
        }
    }

}