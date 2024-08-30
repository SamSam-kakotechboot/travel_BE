package com.samsam.travel.travelcommerce.global.status;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * 이 열거형은 다양한 상태 코드와 해당 메시지를 나타냅니다.
 *
 * @author lavin
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CommonCode {

    //User
    /**
     * 회원가입이 성공적으로 완료되었음을 나타내는 상태 코드.
     */
    SUCCESS_SIGN_UP(HttpStatus.OK, "2000", "%s 회원가입이 완료되었습니다."),

    /**
     * 로그인이 성공적으로 완료되었음을 나타내는 상태 코드.
     */
    SUCCESS_LOGIN(HttpStatus.OK, "2001", "로그인이 완료되었습니다."),

    /**
     * 유저 정보 조회가 성공적으로 완료되었음을 나타내는 상태 코드.
     */
    SUCCESS_USER_INFO(HttpStatus.OK, "2002", "유저 정보 조회가 완료되었습니다."),

    /**
     * 전체 유저 정보 조회가 성공적으로 완료되었음을 나타내는 상태 코드.
     */
    SUCCESS_ALL_USER_INFO(HttpStatus.OK, "2003", "전체 유저 정보 조회가 완료되었습니다."),

    /**
     * 관리자 권한이 성공적으로 부여되었음을 나타내는 상태 코드.
     * %s는 계정 이름으로 대체됩니다.
     */
    SUCCESS_EMPOWERMENT(HttpStatus.OK, "2004", "%s 계정에 관리자 권한이 부여됐습니다."),

    /**
     * 관리자 권한이 성공적으로 박탈되었음을 나타내는 상태 코드.
     * %s는 계정 이름으로 대체됩니다.
     */
    SUCCESS_DEPRIVATION(HttpStatus.OK, "2005", "%s 계정에 관리자 권한이 박탈됐습니다."),

    /**
     * 역할 할당 성공 상태를 나타내는 상태 코드.
     * %s는 아이디, 변경된 권한으로 대체됩니다.
     */
    SUCCESS_ASSIGN_ROLE(HttpStatus.OK, "2006", "%s 계정의 권한이 %s로 변경되었습니다."),

    /**
     * 상품 등록이 성공적으로 등록되었음을 나타내는 상태 코드.
     */
    SUCCESS_ADD_TICKET(HttpStatus.OK, "2100", "상품이 등록 되었습니다."),

    /**
     * 상품 수정이 성공적으로 수정되었음을 나타내는 상태 코드.
     */
    SUCCESS_MODIFY_TICKET(HttpStatus.OK, "2101", "상품이 수정 되었습니다."),

    /**
     * 상품 삭제가 성공적으로 수정되었음을 나타내는 상태 코드.
     */
    SUCCESS_DELETE_TICKET(HttpStatus.OK, "2102", "상품이 삭제 되었습니다."),

    /**
     * 상품 조회가 성공적으로 수정되었음을 나타내는 상태 코드.
     */
    SUCCESS_VIEW_TICKET(HttpStatus.OK, "2103", "상품이 조회 되었습니다."),

    /**
     * 상품 상세 조회가 성공적으로 수정되었음을 나타내는 상태 코드.
     */
    SUCCESS_VIEW_DETAIL_TICKET(HttpStatus.OK, "2104", "상품 상세 조회가 되었습니다."),

    /**
     * 장바구니에 담긴 상품을 성공적으로 조회했음을 나타내는 상태 코드.
     */
    SUCCESS_VIEW_CART(HttpStatus.OK, "2110", "장바구니 내부 상품이 조회되었습니다."),

    /**
     * 장바구니에 상품을 성공적으로 담았음을 나타내는 상태 코드.
     */
    SUCCESS_ADD_CART(HttpStatus.OK, "2111", "장바구니에 상품이 담겼습니다."),

    /**
     * 장바구니에서 상품을 성공적으로 삭제 되었음을 나타내는 상태 코드.
     */
    SUCCESS_DELETE_CART(HttpStatus.OK, "2112", "장바구니에서 상품이 삭제 되었습니다."),

    /**
     * 리뷰 등록이 성공적으로 등록되었음을 나타내는 상태 코드.
     */
    SUCCESS_ADD_REVIEW(HttpStatus.OK, "2120", "리뷰가 등록 되었습니다."),

    /**
     * 리뷰 삭제가 성공적으로 수행되었음을 나타내는 상태 코드.
     */
    SUCCESS_DELETE_REVIEW(HttpStatus.OK, "2121", "리뷰가 삭제 되었습니다."),

    /**
     * 내가 쓴 리뷰가 성공적으로 조회되었음을 나타내는 상태 코드.
     */
    SUCCESS_VIEW_MY_ALL_REVIEW(HttpStatus.OK, "2122", "내 리뷰 전체 조회를 성공하였습니다."),

    /**
     * 내가 쓴 리뷰가 성공적으로 조회되었음을 나타내는 상태 코드.
     */
    SUCCESS_VIEW_ALL_REVIEW_BY_TICKET(HttpStatus.OK, "2123", "티겟에 대한 리뷰 조회를 성공하였습니다."),


    /**
     * 주문 접수 성공 상태를 나타내는 상태 코드.
     */
    SUCCESS_ORDER_CREATE(HttpStatus.OK, "2200", "주문이 성공적으로 접수되었습니다."),

    /**
     * 모든 주문 리스트 조회가 성공적으로 완료되었음을 나타내는 상태 코드.
     */
    SUCCESS_ALL_ORDER_LIST(HttpStatus.OK, "2201", "모든 주문 리스트 조회가 완료되었습니다."),

    /**
     * 주문 취소가 성공적으로 완료되었음을 나타내는 상태 코드.
     */
    SUCCESS_ORDER_CANCEL(HttpStatus.OK, "2202", "%s 주문이 성공적으로 취소되었습니다."),

    /**
     * 주문 승인 요청이 성공적으로 완료되었음을 나타내는 상태 코드.
     */
    SUCCESS_ORDER_APPROVE(HttpStatus.OK, "2203", "%s 주문이 성공적으로 승인되었습니다.");


    private final HttpStatus httpStatus;
    private final String statusCode;
    private final String message;
}