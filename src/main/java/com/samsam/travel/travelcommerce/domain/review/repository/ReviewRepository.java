package com.samsam.travel.travelcommerce.domain.review.repository;

import com.samsam.travel.travelcommerce.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, String> {

    @Query(
            "SELECT r FROM Review r " +
                    "WHERE r.user.userId = :userId " +
                    "AND r.orders.orderId = :orderId " +
                    "AND r.ticket.ticketId = :ticketId " +
                    "AND r.deleteYn = 'N'"
    )
    Review findMyReview(
            @Param("userId") String userId,
            @Param("orderId") String orderId,
            @Param("ticketId") String ticketId
    );

    @Query(
            "SELECT r FROM Review r " +
                    "WHERE r.user.userId = :userId " +
                    "AND r.deleteYn = 'N' " +
                    "ORDER BY r.registDate DESC"
    )
    List<Review> findMyAllReview(@Param("userId") String userId);

    @Query(
            "SELECT " +
                    "r " +
                    "FROM Review r " +
                    "WHERE r.ticket.ticketId = :ticketId " +
                    "AND r.deleteYn         =   'N' " +
                    "ORDER BY r.registDate DESC "
    )
    List<Review> findAllReviewByTicket(@Param("ticketId") String ticketId);

    @Modifying
    @Transactional
    @Query(
            "UPDATE Review r " +
                    "SET " +
                    "r.deleteYn         =   'Y' " +
                    "WHERE r.reviewId       =   :#{#review.reviewId} " +
                    "AND r.user.userId  =   :#{#review.user.userId} "
    )
    int deleteMyReview(Review review);

}
