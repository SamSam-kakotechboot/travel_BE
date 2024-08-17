package com.samsam.travel.travelcommerce.dto.review;

import com.samsam.travel.travelcommerce.entity.Orders;
import com.samsam.travel.travelcommerce.entity.Ticket;
import com.samsam.travel.travelcommerce.entity.User;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class ReviewAddDto {
    private String orderId;
    private String ticketId;
    private String comment;
    private float rating;
    private String userId;

    public boolean isValidate() {
        // 필드가 모두 유효한 경우 true를 반환
        return !(StringUtils.isNotBlank(orderId)
                && StringUtils.isNotBlank(ticketId)
                && StringUtils.isNotBlank(comment)
                && rating > 0);
    }
}