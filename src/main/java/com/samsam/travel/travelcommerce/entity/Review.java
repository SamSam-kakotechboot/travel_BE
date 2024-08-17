package com.samsam.travel.travelcommerce.entity;

import com.samsam.travel.travelcommerce.dto.review.ReviewDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Table(name = "review")
public class Review {

    @Id
    @Column(name = "review_id", length = 255)
    private String reviewId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @NotNull
    private Orders orders;

    @ManyToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    @NotNull
    private Ticket ticket;

    @Column(name = "comment", nullable = false)
    @NotNull
    private String comment;

    @Column(name = "rating", nullable = false)
    @NotNull
    private float rating;

    @Column(name = "delete_yn", nullable = false, length = 1)
    private String deleteYn;

    @CreationTimestamp
    @Column(name = "regist_date", nullable = false, updatable = false)
    private LocalDateTime registDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(reviewId, review.reviewId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reviewId);
    }

    public static Review convertDtoToEntity(ReviewDto reviewDto) {
        // Null check 추가
        if (reviewDto.getOrders() == null || reviewDto.getUser() == null || reviewDto.getTicket() == null) {
            throw new IllegalArgumentException("Orders, User, and Ticket cannot be null");
        }

        return Review.builder()
                .reviewId(reviewDto.getReviewId())
                .user(reviewDto.getUser())
                .orders(reviewDto.getOrders())
                .ticket(reviewDto.getTicket())
                .comment(reviewDto.getComment())
                .rating(reviewDto.getRating())
                .deleteYn("N")
                .build();
    }
}