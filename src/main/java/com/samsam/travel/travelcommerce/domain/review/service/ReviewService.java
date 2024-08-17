package com.samsam.travel.travelcommerce.domain.review.service;

import com.samsam.travel.travelcommerce.dto.review.ReviewDto;
import com.samsam.travel.travelcommerce.dto.review.ReviewResponseDto;

import java.util.List;

public interface ReviewService {

    public ReviewResponseDto addReview(ReviewDto reviewDto);

    public boolean removeReview(ReviewDto reviewDto);

    ReviewResponseDto getMyOrderReview(String userId, String orderId, String ticketId);

    public List<ReviewResponseDto> getMyAllReview(ReviewDto reviewDto);

    public List<ReviewResponseDto> getAllReviewByTicket(String ticketId);

}
