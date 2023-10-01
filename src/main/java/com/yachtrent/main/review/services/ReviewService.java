package com.yachtrent.main.review.services;

import com.yachtrent.main.review.Review;
import com.yachtrent.main.review.ReviewRepository;
import com.yachtrent.main.review.dto.ReviewDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ReviewService {
    private  final ReviewRepository reviewRepository;

    public void createReview(ReviewDTO dto){
        Review review = dto.toEntity(dto);
        reviewRepository.save(review);
    }

    public List<ReviewDTO> getAllReviewByAccountId(long id){
        List<ReviewDTO> result = new ArrayList<>();
        List<Review> reviews = reviewRepository.getAllByAccountId(id);

        for ( Review review : reviews) {
            ReviewDTO buffer = new ReviewDTO();
            result.add(buffer.toDto(review));
        }
        return result;
    }

    public ResponseEntity<String> removeReviewById(long id){
        Optional<Review> review = reviewRepository.findById(id);
        if(review.isPresent()){

            return ResponseEntity.internalServerError().body("review removed");
        }
        return ResponseEntity.internalServerError().body("review does not exist");
    }
}
