package JuDBu.custommaster.domain.controller;

import JuDBu.custommaster.domain.dto.review.ReviewDto;
import JuDBu.custommaster.domain.entity.Review;
import JuDBu.custommaster.domain.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/review/{shopId}")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping("/read-all")
    public String reviewList(
            @PathVariable("shopId") Long shopId,
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC)
            Pageable pageable,
            Model model
    ) {
        Page<ReviewDto> reviews = reviewService.readReviewPaged(shopId, pageable);
        List<String> reviewNames = reviewService.getAccountName(shopId);

        Collections.reverse(reviewNames);
        Collections.reverse(reviewNames);
        model.addAttribute("reviews", reviews);
        model.addAttribute("names", reviewNames);
        return "review/review-list";
    }

    @GetMapping("/read/{reviewId}")
    public String readOneReview(
            @PathVariable("shopId") Long shopId,
            @PathVariable("reviewId") Long reviewId,
            Model model,
            Review review
    ) {
        //TODO
        // 모델 불러와서 디테일 작성
        model.addAttribute("review",review);
        return "review/review-detail";
    }
}
