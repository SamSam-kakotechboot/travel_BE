package com.samsam.travel.travelcommerce.dto.order;

import com.samsam.travel.travelcommerce.entity.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 주문 리스트 조회 DTO
 */
@AllArgsConstructor
@Getter
@Builder
public class OrderListResponse {
    String orderId;
    String userId;
    LocalDateTime orderDate;
    int totalAmount;
    int quantity;
    OrderStatus status;
    String ticketId;
    String ticketTitle;
}
