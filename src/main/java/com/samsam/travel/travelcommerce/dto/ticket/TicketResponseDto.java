package com.samsam.travel.travelcommerce.dto.ticket;

import com.samsam.travel.travelcommerce.entity.Ticket;
import com.samsam.travel.travelcommerce.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.OptionalDouble;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TicketResponseDto {
    private String ticketId;
    private String userId;
    private String userName;
    private String userPhone;
    private String title;
    private String contents;
    private String place;
    private int price;
    private LocalDate startDate;
    private LocalDate endDate;
    private String deleteYn;
    private String viewYn;
    private LocalDateTime registDate;
    private LocalDateTime updateDate;
    private double avgRating;

    public TicketResponseDto convertEntityToDto(Ticket ticket) {
        return new TicketResponseDto(
                ticket.getTicketId(),
                ticket.getUser().getUserId(),
                ticket.getUser().getName(),
                ticket.getUser().getPhone(),
                ticket.getTitle(),
                ticket.getContents(),
                ticket.getPlace(),
                ticket.getPrice(),
                ticket.getStartDate(),
                ticket.getEndDate(),
                ticket.getDeleteYn(),
                ticket.getViewYn(),
                ticket.getRegistDate(),
                ticket.getUpdateDate(),
                calculateAverageRating(ticket) // avgRating 계산
        );
    }

    private double calculateAverageRating(Ticket ticket) {
        // 리뷰가 없을 경우 0.0 반환
        if (ticket.getReviews().isEmpty()) {
            return 0.0;
        }

        // 리뷰의 평균 평점을 계산
        OptionalDouble averageRating = ticket.getReviews().stream()
                .mapToDouble(Review::getRating)
                .average();

        return averageRating.orElse(0.0);
    }
}