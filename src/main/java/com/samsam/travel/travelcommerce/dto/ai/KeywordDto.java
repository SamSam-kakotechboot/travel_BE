package com.samsam.travel.travelcommerce.dto.ai;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class KeywordDto {
    private String type;
    private String keyword;

    public Boolean isValid() {
        return type != null && !type.isEmpty() && keyword != null && !keyword.isEmpty();
    }
}