package JuDBu.custommaster.domain.controller;

import JuDBu.custommaster.domain.service.ReviewService;
import JuDBu.custommaster.domain.dto.review.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    // 댓글 등록
    @PostMapping
    public ReviewDto createReview(
            @RequestParam("comment") String comment,
            @RequestParam("orderId") Long orderId,
            @RequestParam("images") MultipartFile[] images
    ) {
        return reviewService.createReview(comment, orderId, images);
    }

    @GetMapping
    public Page<ReviewDto> readReviewPaged(
            @RequestParam(value = "page", defaultValue = "1")
            Integer page,
            @RequestParam(value = "size", defaultValue = "10")
            Integer size
    ) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return reviewService.readReviewPaged(pageable);
    }

    @GetMapping("{reviewId}")
    public ReviewDto readReview(
            @PathVariable("reviewId")
            Long reviewId
    ) {
        return reviewService.readReview(reviewId);
    }

    // 댓글 수정
    @PutMapping("{reviewId}")
    public ReviewDto updateReview(
            @PathVariable("accountId") Long accountId,
            @RequestParam("comment") String comment,
            @RequestParam("images") MultipartFile[] images
    ) {
        return reviewService.updateReview(accountId, comment, images,accountId);
    }

    // 댓글 삭제
    @DeleteMapping("{reviewId}")
    public void deleteReview(
            @PathVariable("reviewId")
            Long reviewId
    ) {
        reviewService.deleteReview(reviewId);
    }

//    // 좋아요 처리
//    @PostMapping("{articleId}/like")
//    public void toggleArticleLike(
//            @PathVariable("articleId")
//            Long articleId
//    ) {
//        articleService.toggleArticleLike(articleId);
//    }
//
//    @GetMapping("{articleId}/like")
//    public Boolean isCurrentMemberLikedArticle(
//            @PathVariable("articleId")
//            Long articleId
//    ) {
//        return articleService.isCurrentMemberLikedArticle(articleId);
//    }
}


