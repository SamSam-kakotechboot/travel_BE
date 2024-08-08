package com.samsam.travel.travelcommerce.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * 주문 요청 dto
 */
@AllArgsConstructor
@Getter
public class OrderRequest {
    List<OrderDetail> orders;

    @NotNull(message = "totalAmount is mandatory")
    int totalAmount;
}