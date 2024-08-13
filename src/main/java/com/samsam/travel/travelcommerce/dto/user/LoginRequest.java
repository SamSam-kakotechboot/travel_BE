package com.samsam.travel.travelcommerce.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 로그인 요청 dto
 */
@AllArgsConstructor
@NoArgsConstructor // 기본 생성자 추가
@Getter
public class LoginRequest {
    @NotBlank(message = "ID is mandatory")
    private String id;

    @NotBlank(message = "Password is mandatory")
    private String password;

}
