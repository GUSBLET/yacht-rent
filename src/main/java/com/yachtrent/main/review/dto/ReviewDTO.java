package com.yachtrent.main.review.dto;

import com.yachtrent.main.account.Account;
import com.yachtrent.main.techniacal.mapper.Mapper;
import com.yachtrent.main.review.Review;
import com.yachtrent.main.review.ReviewRating;
import com.yachtrent.main.yacht.Yacht;
import lombok.*;

import java.util.Date;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class ReviewDTO implements Mapper<ReviewDTO, Review> {

    private long yachtId;
    private long accountId;
    private String description;
    private String title;
    private Date postingDate;
    private ReviewRating reviewRating;

    @Override
    public ReviewDTO toDto(Review entity) {

        return ReviewDTO.builder()
                .accountId(entity.getId())
                .yachtId(entity.getYacht().getId())
                .postingDate(entity.getPostingDate())
                .description(entity.getDescription())
                .title(entity.getTitle())
                .reviewRating(entity.getReviewRating())
                .build();
    }

    @Override
    public Review toEntity(ReviewDTO dto) {

        return Review.builder()
                .yacht(Yacht.builder().id(dto.getYachtId()).build())
                .account(Account.builder().id(dto.getAccountId()).build())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .reviewRating(dto.getReviewRating())
                .postingDate(dto.getPostingDate())
                .build();
    }
}
