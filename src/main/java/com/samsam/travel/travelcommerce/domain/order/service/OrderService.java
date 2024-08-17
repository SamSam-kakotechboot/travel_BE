package com.samsam.travel.travelcommerce.domain.order.service;

import com.samsam.travel.travelcommerce.domain.order.repository.OrderRepository;
import com.samsam.travel.travelcommerce.domain.ticket.repository.TicketRepository;
import com.samsam.travel.travelcommerce.domain.user.repository.UserRepository;
import com.samsam.travel.travelcommerce.dto.order.OrderDetail;
import com.samsam.travel.travelcommerce.dto.order.OrderListResponse;
import com.samsam.travel.travelcommerce.dto.order.OrderRequest;
import com.samsam.travel.travelcommerce.entity.Orders;
import com.samsam.travel.travelcommerce.entity.Ticket;
import com.samsam.travel.travelcommerce.entity.User;
import com.samsam.travel.travelcommerce.entity.model.OrderStatus;
import com.samsam.travel.travelcommerce.entity.model.Role;
import com.samsam.travel.travelcommerce.global.error.exception.ResourceNotFoundException;
import com.samsam.travel.travelcommerce.global.error.exception.TicketNotFoundException;
import com.samsam.travel.travelcommerce.global.error.exception.UserNotFoundException;
import com.samsam.travel.travelcommerce.global.error.exception.UserUnauthorizedException;
import com.samsam.travel.travelcommerce.utils.Common;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.samsam.travel.travelcommerce.entity.model.OrderStatus.P;
import static com.samsam.travel.travelcommerce.global.status.ErrorCode.*;

/**
 * 주문 관련 작업을 담당하는 서비스 클래스입니다.
 * * @author lavin
 * * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    private final Common common;

    /**
     * 사용자 ID와 주문 요청 DTO를 받아서 새로운 주문을 생성합니다.
     *
     * @param userId       사용자 ID
     * @param orderRequest 주문 요청 DTO
     */
    @Transactional
    public void createOrder(String userId, OrderRequest orderRequest) {
        User user = findUserById(userId);
        Map<String, Ticket> ticketsMap = findTicketsByIds(orderRequest);

        List<Orders> ordersList = orderRequest.getOrders().stream()
                .map(orderDetail -> buildOrder(user, ticketsMap, orderDetail))
                .collect(Collectors.toList());

        orderRepository.saveAll(ordersList);
    }

    /**
     * 사용자 ID를 받아서 모든 주문한 상품을 조회합니다,
     *
     * @param userId       사용자 ID
     */
    public List<OrderListResponse> getAllOrders(String userId) {
        return orderRepository.findOrdersByUserId(userId)
                .stream()
                .map(this::buildOrderListResponse)
                .collect(Collectors.toList());
    }

    /**
     * 지정된 주문 ID와 사용자 ID를 기반으로 주문을 취소합니다.
     *
     * @param orderId 취소할 주문의 고유 ID
     * @param userId  취소할 주문을 요청한 사용자의 ID
     * @throws ResourceNotFoundException 존재하지 않는 주문 ID로 취소하려고 할 때
     * @throws UserUnauthorizedException 사용자가 취소할 권한이 없을 때
     */
    public void cancelOrder(String orderId, String userId) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_EXIST_ORDER));

        if (!userId.equals(order.getUser().getUserId())) {
            throw new UserUnauthorizedException(NO_AUTH);
        }

        orderRepository.delete(order);
    }

    /**
     * 지정된 주문 ID와 관리자 ID를 기반으로 주문을 승인합니다.
     * 승인된 주문은 '완료'(C) 상태로 변경됩니다.
     *
     * @param orderId 승인할 주문의 고유 ID
     * @param adminId 승인할 주문을 요청한 관리자의 ID
     * @throws UserUnauthorizedException 사용자가 승인할 권한이 없는 경우
     */
    public void approveOrder(String orderId, String adminId) {
        validateMasterRole(adminId);

        orderRepository.completeOrderById(orderId);
    }

    /**
     * 사용자 ID로 사용자를 찾아서 반환합니다.
     *
     * @param userId 사용자 ID
     * @return 찾은 사용자
     * @throws UserNotFoundException 사용자를 찾을 수 없는 경우
     */
    private User findUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(NOT_EXIST_USER));
    }

    /**
     * 주문 요청 DTO로부터 상품 ID 목록을 추출하여 상품 목록을 찾아서 맵으로 반환합니다.
     *
     * @param orderRequest 주문 요청 DTO
     * @return 상품 ID를 키로, 상품을 값으로 갖는 맵
     * @throws TicketNotFoundException 상품을 찾을 수 없는 경우
     */
    private Map<String, Ticket> findTicketsByIds(OrderRequest orderRequest) {
        List<String> productIds = orderRequest.getOrders().stream()
                .map(OrderDetail::getProductId)
                .distinct()
                .collect(Collectors.toList());

        List<Ticket> tickets = ticketRepository.findAllById(productIds);

        if (tickets.size() != productIds.size()) {
            throw new TicketNotFoundException(NOT_EXIST_TICKET);
        }

        return tickets.stream()
                .collect(Collectors.toMap(Ticket::getTicketId, ticket -> ticket));
    }

    /**
     * 사용자, 상품 맵, 주문 상세 정보로부터 주문 객체를 생성합니다.
     *
     * @param user        사용자
     * @param ticketsMap  상품 맵
     * @param orderDetail 주문 상세 정보
     * @return 생성된 주문 객체
     * @throws TicketNotFoundException 상품을 찾을 수 없는 경우
     */
    private Orders buildOrder(User user, Map<String, Ticket> ticketsMap, OrderDetail orderDetail) {
        Ticket ticket = Optional.ofNullable(ticketsMap.get(orderDetail.getProductId()))
                .orElseThrow(() -> new TicketNotFoundException(NOT_EXIST_TICKET));

        int totalAmount = orderDetail.getQuantity() * ticket.getPrice();

        return Orders.builder()
                .orderId(common.getTargetUuid("order"))
                .user(user)
                .ticket(ticket)
                .ticketTitle(ticket.getTitle())  // 티켓의 제목을 설정
                .orderDate(LocalDateTime.now())
                .totalAmount(totalAmount)
                .quantity(orderDetail.getQuantity())
                .status(OrderStatus.P)
                .build();
    }

    /**
     * 관리자 역할을 확인합니다.
     *
     * @param adminId 인증된 관리자의 사용자 ID.
     * @throws UserUnauthorizedException 사용자가 관리자가 아닌 경우 발생합니다.
     */
    private void validateMasterRole(String adminId) {
        if (!userRepository.findRoleByUserId(adminId).equals(Role.MASTER)) {
            throw new UserUnauthorizedException(USER_NOT_MASTER);
        }
    }

    /**
     * 주문 정보를 통해 객체를 생성합니다.
     *
     * @param order 주문
     */
    private OrderListResponse buildOrderListResponse(Orders order) {
        return OrderListResponse.builder()
                .orderId(order.getOrderId())
                .userId(order.getUser().getUserId())
                .ticketId(order.getTicket().getTicketId())
                .ticketTitle(order.getTicketTitle())  // 티켓 제목 추가
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .quantity(order.getQuantity())
                .status(order.getStatus())
                .build();
    }
}
