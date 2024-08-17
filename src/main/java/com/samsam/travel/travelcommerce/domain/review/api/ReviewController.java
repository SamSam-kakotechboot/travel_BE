package com.samsam.travel.travelcommerce.domain.review.api;

import com.samsam.travel.travelcommerce.domain.review.service.ReviewService;
import com.samsam.travel.travelcommerce.dto.review.ReviewAddDto;
import com.samsam.travel.travelcommerce.dto.review.ReviewDto;
import com.samsam.travel.travelcommerce.dto.review.ReviewResponseDto;
import com.samsam.travel.travelcommerce.entity.Orders;
import com.samsam.travel.travelcommerce.entity.Ticket;
import com.samsam.travel.travelcommerce.entity.User;
import com.samsam.travel.travelcommerce.global.error.exception.ReviewDuplicatedException;
import com.samsam.travel.travelcommerce.global.error.exception.ReviewInvalidInputException;
import com.samsam.travel.travelcommerce.utils.ApiResponse;
import com.samsam.travel.travelcommerce.utils.ResponseUtil;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.samsam.travel.travelcommerce.global.status.CommonCode.*;
import static com.samsam.travel.travelcommerce.global.status.ErrorCode.BAD_REQUEST_INVALID_REVIEW_VALUES;
import static com.samsam.travel.travelcommerce.global.status.ErrorCode.DUPLICATED_REVIEW_VALUES;

/**
 * 상품에 대한 리뷰에 관련된 API를 수행하는 컨트롤러입니다.
 *
 * @author colton
 * @since 1.0.0
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리뷰 등록 API
     *
     * @param userDetails  인증된 관리자의 세부 정보. Spring Security 컨텍스트에서 가져옵니다.
     * @param reviewAddDto 리뷰 추가에 필요한 정보
     * @return 리뷰 등록 성공 여부, 문구와 리뷰 데이터
     */
    @PostMapping("/regist")
    public ResponseEntity<ApiResponse<ReviewResponseDto>> addReview(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ReviewAddDto reviewAddDto) {
        if (reviewAddDto.isValidate()) {
            throw new ReviewInvalidInputException(BAD_REQUEST_INVALID_REVIEW_VALUES);
        }

        String userId = userDetails.getUsername(); // 사용자 ID 가져오기
        String orderId = reviewAddDto.getOrderId();
        String ticketId = reviewAddDto.getTicketId();

        if (reviewService.getMyOrderReview(userId, orderId, ticketId) != null) {
            throw new ReviewDuplicatedException(DUPLICATED_REVIEW_VALUES);
        }

        ReviewDto reviewDto = new ReviewDto();
        setUser(userDetails, reviewDto);
        setTicket(reviewDto, ticketId);
        setOrder(reviewDto, orderId);
        reviewDto.setReviewAddData(reviewAddDto);

        return ResponseUtil.createApiResponse(SUCCESS_ADD_REVIEW, reviewService.addReview(reviewDto));
    }

    /**
     * 리뷰 삭제 API
     *
     * @param reviewId 리뷰 번호
     * @return 리뷰 등록 성공 여부, 문구와 리뷰 데이터
     */
    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponse<Boolean>> removeReview(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String reviewId) {
        if (StringUtils.isBlank(reviewId)) {
            throw new ReviewInvalidInputException(BAD_REQUEST_INVALID_REVIEW_VALUES);
        }

        ReviewDto reviewDto = new ReviewDto();
        setUser(userDetails, reviewDto);
        reviewDto.setReviewId(reviewId);

        return ResponseUtil.createApiResponse(SUCCESS_DELETE_REVIEW, reviewService.removeReview(reviewDto));
    }

    /**
     * 내 리뷰 전체 조회 API
     *
     * @param userDetails 인증된 관리자의 세부 정보. Spring Security 컨텍스트에서 가져옵니다.
     * @return 리뷰 등록 성공 여부, 문구와 리뷰 데이터
     */
    @GetMapping("/view/my")
    public ResponseEntity<ApiResponse<List<ReviewResponseDto>>> getMyAllReview(@AuthenticationPrincipal UserDetails userDetails) {
        ReviewDto reviewDto = new ReviewDto();
        setUser(userDetails, reviewDto);

        return ResponseUtil.createApiResponse(SUCCESS_VIEW_MY_ALL_REVIEW, reviewService.getMyAllReview(reviewDto));
    }

    /**
     * 티켓에 해당하는 전체 리뷰 조회 API
     *
     * @param ticketId 티켓 번호
     * @return 리뷰 등록 성공 여부, 문구와 리뷰 데이터
     */
    @GetMapping("/view/ticket")
    public ResponseEntity<ApiResponse<List<ReviewResponseDto>>> getAllReviewByTicket(@RequestParam("ticketId") String ticketId) {
        return ResponseUtil.createApiResponse(SUCCESS_VIEW_ALL_REVIEW_BY_TICKET, reviewService.getAllReviewByTicket(ticketId));
    }

    /**
     * Respose
     *
     * @param reviewDto 리뷰에 대한 정보
     * @param ticketId  티켓 아이디
     * @return 상품 수정 여부, 문구와 상품 데이터
     */
    public void setTicket(ReviewDto reviewDto, String ticketId) {
        Ticket ticket = Ticket.builder()
                .ticketId(ticketId)
                .build();
        reviewDto.setTicket(ticket);
    }

    /**
     * Respose
     *
     * @param reviewDto 리뷰에 대한 정보
     * @param orderId   주문 아이디
     * @return 상품 수정 여부, 문구와 상품 데이터
     */
    public void setOrder(ReviewDto reviewDto, String orderId) {
        Orders order = Orders.builder()
                .orderId(orderId)
                .build();
        reviewDto.setOrders(order);
    }

    /**
     * Respose
     *
     * @param userDetails 인증된 관리자의 세부 정보. Spring Security 컨텍스트에서 가져옵니다.
     * @param reviewDto   리뷰에 대한 정보
     * @return 상품 수정 여부, 문구와 상품 데이터
     */
    public void setUser(UserDetails userDetails, ReviewDto reviewDto) {
        reviewDto.setUser(getUser(userDetails));
    }

    /**
     * 유저 정보를 User Entity에 담는 메서드
     *
     * @param userDetails 인증된 관리자의 세부 정보. Spring Security 컨텍스트에서 가져옵니다.
     * @return User 정보를 담은 user Entity 반환
     */
    public User getUser(UserDetails userDetails) {
        return User
                .builder()
                .userId(userDetails.getUsername())
                .build();
    }
}