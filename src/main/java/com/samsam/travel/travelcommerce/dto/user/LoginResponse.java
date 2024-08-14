package com.samsam.travel.travelcommerce.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 로그인 반환 dto
 */
@Getter
@Builder
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String role;
    private String name;
}
