package com.samsam.travel.travelcommerce.domain.image.service;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageService {

    // 티켓 이미지가 저장된 경로
    private final Path ticketImagesLocation = Paths.get("src/main/resources/image/ticket");

    // 리뷰 이미지가 저장된 경로
    private final Path reviewImagesLocation = Paths.get("src/main/resources/image/review");

    public ImageService() throws IOException {
        // 디렉토리가 존재하지 않을 경우 생성
        Files.createDirectories(ticketImagesLocation);
        Files.createDirectories(reviewImagesLocation);
    }

    public Resource loadImage(String filename) throws MalformedURLException {
        Path file = ticketImagesLocation.resolve(filename);
        Resource resource = new UrlResource(file.toUri());

        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            return null;
        }
    }

    public Resource loadReviewImage(String reviewId) throws MalformedURLException {
        Path file = reviewImagesLocation.resolve(reviewId + ".png");
        Resource resource = new UrlResource(file.toUri());

        if (resource.exists() || resource.isReadable()) {
            return resource;
        } else {
            return null;
        }
    }

    public void saveReviewImage(String reviewId, MultipartFile image) throws IOException {
        // 특수문자를 제거한 리뷰 ID로 파일 이름을 설정하고 확장자를 추가
        String sanitizedReviewId = reviewId.replaceAll("[^a-zA-Z0-9-_]", "");
        Path filePath = reviewImagesLocation.resolve(sanitizedReviewId + ".png");

        // 이미지 저장
        Files.copy(image.getInputStream(), filePath);
    }
}