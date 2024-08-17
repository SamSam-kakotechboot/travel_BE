package com.samsam.travel.travelcommerce.domain.ticket.api;

import com.samsam.travel.travelcommerce.domain.ticket.service.TicketService;
import com.samsam.travel.travelcommerce.dto.ticket.SearchDto;
import com.samsam.travel.travelcommerce.dto.ticket.TicketDto;
import com.samsam.travel.travelcommerce.dto.ticket.TicketResponseDto;
import com.samsam.travel.travelcommerce.dto.ticket.TicketSearchResponseDto;
import com.samsam.travel.travelcommerce.entity.User;
import com.samsam.travel.travelcommerce.global.error.exception.TicketInvalidInputException;
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
import static com.samsam.travel.travelcommerce.global.status.ErrorCode.BAD_REQUEST_INVALID_TICKET_VALUES;

/**
 * 상품(티켓)에 관련된 API를 수행하는 컨트롤러입니다.
 *
 * @author colton
 * @since 1.0.0
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    /**
     * 상품 리스트 조회 API
     *
     * @param searchDto 검색 정보가 담겨져 있음(keyword, pageNum, pageSize)
     * @return 상품 조회 성공 여부, 문구와 상품 데이터
     */
    @GetMapping("/view/all")
    public ResponseEntity<ApiResponse<List<TicketSearchResponseDto>>> searchAllTicket(@ModelAttribute SearchDto searchDto) {
        if (searchDto.isValidate()) {
            throw new TicketInvalidInputException(BAD_REQUEST_INVALID_TICKET_VALUES);
        }

        return ResponseUtil.createApiResponse(SUCCESS_VIEW_TICKET, ticketService.getAllTicket(searchDto));
    }

    /**
     * 상품 상세보기 조회 API
     *
     * @param ticketId 상품의 키 값
     * @return 상품 상세 조회 성공 여부, 문구와 상품 데이터
     */
    @GetMapping("/view/detail")
    public ResponseEntity<ApiResponse<TicketResponseDto>> searchTicketDetail(@RequestParam("ticketId") String ticketId) {
        if(StringUtils.isBlank(ticketId)) {
            throw new TicketInvalidInputException(BAD_REQUEST_INVALID_TICKET_VALUES);
        }

        return ResponseUtil.createApiResponse(SUCCESS_VIEW_DETAIL_TICKET, ticketService.getTicketDetail(ticketId));
    }

    /**
     * 상품 등록 API
     *
     * @param userDetails 인증된 관리자의 세부 정보. Spring Security 컨텍스트에서 가져옵니다.
     * @param ticketDto   상품에 대한 정보
     * @return 상품 등록 성공 여부, 문구와 상품 데이터
     */
    @PostMapping("/regist")
    public ResponseEntity<ApiResponse<TicketResponseDto>> addTicket(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute TicketDto ticketDto) {
        if (ticketDto.isValidate()) {
            throw new TicketInvalidInputException(BAD_REQUEST_INVALID_TICKET_VALUES);
        }

        setUser(userDetails, ticketDto);
        TicketResponseDto responseDto = ticketService.addTicket(ticketDto);
        return ResponseUtil.createApiResponse(SUCCESS_ADD_TICKET, responseDto);
    }

    /**
     * 상품 수정 API
     *
     * @param userDetails 인증된 관리자의 세부 정보. Spring Security 컨텍스트에서 가져옵니다.
     * @param ticketDto   상품에 대한 정보
     * @return 상품 수정 성공 여부, 문구와 상품 데이터
     */
    @PutMapping("/modify")
    public ResponseEntity<ApiResponse<Boolean>> updateTicket(@AuthenticationPrincipal UserDetails userDetails, @ModelAttribute TicketDto ticketDto) {
        if (ticketDto.isValidate()) {
            throw new TicketInvalidInputException(BAD_REQUEST_INVALID_TICKET_VALUES);
        }

        setUser(userDetails, ticketDto);
        boolean updateYn = ticketService.updateTicket(ticketDto) > 0;
        return ResponseUtil.createApiResponse(SUCCESS_MODIFY_TICKET, updateYn);
    }

    /**
     * 상품 삭제 API
     *
     * @param userDetails 인증된 관리자의 세부 정보. Spring Security 컨텍스트에서 가져옵니다.
     * @param ticketId    상품 pk 값을 담는 변수
     * @return 상품 삭제 성공 여부, 문구와 상품 데이터
     */
    @DeleteMapping("/remove")
    public ResponseEntity<ApiResponse<Boolean>> removeTicket(@AuthenticationPrincipal UserDetails userDetails, @RequestParam String ticketId) {
        if (StringUtils.isBlank(ticketId)) {
            throw new TicketInvalidInputException(BAD_REQUEST_INVALID_TICKET_VALUES);
        }

        TicketDto ticketDto = new TicketDto();
        ticketDto.setTicketId(ticketId);
        setUser(userDetails, ticketDto);

        ticketService.removeTicket(ticketDto);
        return ResponseUtil.createApiResponse(SUCCESS_DELETE_TICKET, true);
    }

    /**
     * Respose
     *
     * @param userDetails 인증된 관리자의 세부 정보. Spring Security 컨텍스트에서 가져옵니다.
     * @param ticketDto   상품에 대한 정보
     * @return 상품 수정 여부, 문구와 상품 데이터
     */
    public TicketDto setUser(UserDetails userDetails, TicketDto ticketDto) {
        ticketDto.setUser(getUser(userDetails));
        return ticketDto;
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
                .password(userDetails.getPassword())
                .build();
    }
}