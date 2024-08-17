package com.samsam.travel.travelcommerce.domain.review.service;

import com.samsam.travel.travelcommerce.domain.review.repository.ReviewRepository;
import com.samsam.travel.travelcommerce.dto.review.ReviewDto;
import com.samsam.travel.travelcommerce.dto.review.ReviewResponseDto;
import com.samsam.travel.travelcommerce.entity.Review;
import com.samsam.travel.travelcommerce.utils.Common;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository repository;

    private final Common common;

    @Override
    public ReviewResponseDto addReview(ReviewDto reviewDto) {
        String reviewId = common.getTargetUuid("review");
        reviewDto.setReviewId(reviewId);

         return ReviewResponseDto.convertEntityToDto(repository.save(Review.convertDtoToEntity(reviewDto)));
    }

    @Override
    public boolean removeReview(ReviewDto reviewDto) {
        return repository.deleteMyReview(Review.convertDtoToEntity(reviewDto)) > 0;
    }

    @Override
    public ReviewResponseDto getMyOrderReview(String userId, String orderId, String ticketId) {
        Review review = repository.findMyReview(userId, orderId, ticketId);
        if (review == null) {
            return null;
        }
        return ReviewResponseDto.convertEntityToDto(review);
    }

    @Override
    public List<ReviewResponseDto> getMyAllReview(ReviewDto reviewDto) {
        return repository.findMyAllReview(reviewDto.getUser().getUserId()).stream()
                .map(ReviewResponseDto::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReviewResponseDto> getAllReviewByTicket(String ticketId) {
        return repository.findAllReviewByTicket(ticketId).stream()
                .map(ReviewResponseDto::convertEntityToDto)
                .collect(Collectors.toList());
    }
}
