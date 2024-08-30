package com.samsam.travel.travelcommerce.domain.image.service;

import com.samsam.travel.travelcommerce.dto.ai.KeywordDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvService {

    // 특정 경로에서 CSV 데이터를 읽어오는 메서드
    public List<KeywordDto> getAllKeywordsFromCsv(String filePath) throws IOException {
        // 파일 존재 여부 확인
        if (!Files.exists(Paths.get(filePath))) {
            throw new IOException("파일이 존재하지 않습니다: " + filePath);
        }

        List<KeywordDto> keywords = new ArrayList<>();

        // 파일을 줄 단위로 읽기
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        // 첫 줄은 헤더이므로 건너뜀
        for (int i = 1; i < lines.size(); i++) {
            String[] fields = lines.get(i).split(",");
            String keywordType = fields[0];
            String keyword = fields[1];

            keywords.add(new KeywordDto(keywordType, keyword));
        }

        return keywords;
    }
}