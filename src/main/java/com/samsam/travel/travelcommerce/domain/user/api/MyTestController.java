package com.samsam.travel.travelcommerce.domain.user.api;

import com.samsam.travel.travelcommerce.domain.user.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 로그인, 회원가입 등 인증 관련 API를 처리하는 컨트롤러입니다.
 *
 * @author lavin
 * @since 1.0
 */
@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/test")
public class MyTestController {

    private final UserAuthService userAuthService;

    @GetMapping
    public String login() {
        log.info("요청 확인 완료");
        return "요창 성공";
    }

}