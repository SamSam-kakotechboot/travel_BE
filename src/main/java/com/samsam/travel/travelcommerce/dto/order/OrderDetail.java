package com.samsam.travel.travelcommerce.dto.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 주문 상세 dto
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderDetail {
    @NotNull(message = "productId is mandatory")
    String productId;

    @Positive(message = "quantity must be greater than zero")
    private int quantity;

    @Min(value = 0, message = "subAmount must be zero or greater")
    private int subAmount;
}
