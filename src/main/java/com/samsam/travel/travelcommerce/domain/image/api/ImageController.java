package com.samsam.travel.travelcommerce.domain.image.api;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    // 이미지 파일이 저장된 경로를 resources/image로 설정합니다.
    private final Path imagesLocation = Paths.get("src/main/resources/image");

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> serveImage(@PathVariable("filename") String filename) {
        try {
            // 파일 경로를 설정하고, 해당 파일을 읽어들입니다.
            Path file = imagesLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            // 파일이 존재하고, 읽을 수 있으면 해당 파일을 반환합니다.
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                // 파일이 존재하지 않거나 읽을 수 없는 경우 404 Not Found를 반환합니다.
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            // 경로에 문제가 있는 경우 400 Bad Request를 반환합니다.
            return ResponseEntity.badRequest().build();
        }
    }
}