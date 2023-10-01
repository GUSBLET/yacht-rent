package com.yachtrent.main.review;

import com.yachtrent.main.review.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("review")
@RequiredArgsConstructor
public class ReviewController {
    public final ReviewService reviewService;

    public String creatingReviewPage(){
        return "layout";
    }
}
