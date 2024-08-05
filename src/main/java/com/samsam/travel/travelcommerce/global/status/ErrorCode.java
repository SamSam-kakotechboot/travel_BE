package com.samsam.travel.travelcommerce.global.status;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 이 열거형은 다양한 에러 코드와 해당 메시지를 나타냅니다.
 *
 * @author lavin
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {

    // User
    /**
     * 존재하지 않는 유저를 나타내는 에러 코드.
     */
    NOT_EXIST_USER(HttpStatus.NOT_FOUND, "4040", "존재하지 않는 유저입니다."),

    /**
     * 존재하지 않는 관리자를 나타내는 에러 코드.
     */
    NOT_EXIST_ADMIN(HttpStatus.NOT_FOUND, "4041", "존재하지 않는 관리자입니다."),

    /**
     * 존재하지 않는 마스터 관리자를 나타내는 에러 코드.
     */
    NOT_EXIST_MASTER_ADMIN(HttpStatus.NOT_FOUND, "4042", "존재하지 않는 마스터 관리자입니다."),

    /**
     * 권한이 없는 유저를 나타내는 에러 코드.
     */
    NO_AUTH(HttpStatus.FORBIDDEN, "4030", "권한이 없는 유저입니다."),

    /**
     * id가 중복된 것을 나타내는 에러 코드.
     */
    USER_ID_DUPLICATE(HttpStatus.NOT_ACCEPTABLE, "4060", "id가 중복되었습니다."),

    /**
     * 비밀번호가 틀린 것을 나타내는 에러 코드.
     */
    USER_PASSWORD_NOT_MATCHED(HttpStatus.NOT_ACCEPTABLE, "4061", "비밀번호가 틀렸습니다."),

    // Common
    /**
     * 허용되지 않은 메소드를 나타내는 에러 코드.
     */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "4050", "허용되지 않은 메소드입니다."),

    /**
     * 서버 내부 에러를 나타내는 에러 코드.
     */
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "5000", "서버 내부 에러가 발생하였습니다."),

    // Jwt
    /**
     * JWT 토큰이 만료된 것을 나타내는 에러 코드.
     */
    JWT_EXPIRED(HttpStatus.FORBIDDEN, "4031", "JWT 토큰이 만료되었습니다."),

    /**
     * JWT 시그니처가 잘못된 것을 나타내는 에러 코드.
     */
    JWT_INVALID_SIGNATURE(HttpStatus.FORBIDDEN, "4032", "JWT 시그니처가 잘못되었습니다."),

    /**
     * JWT 토큰이 유효하지 않은 것을 나타내는 에러 코드.
     */
    JWT_MALFORMED(HttpStatus.FORBIDDEN, "4033", "JWT 토큰이 유효하지 않습니다."),

    /**
     * 지원하지 않는 JWT 토큰 형식을 나타내는 에러 코드.
     */
    JWT_UNSUPPORTED(HttpStatus.FORBIDDEN, "4034", "지원하지 않는 JWT 토큰 형식입니다."),

    ;

    private final HttpStatus httpStatus;
    private final String statusCode;
    private final String message;
}