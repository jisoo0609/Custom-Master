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
public class ReviewRestController {
    private final ReviewService reviewService;

    // 댓글 등록
    @PostMapping("/{shopId}/create")
    public ReviewDto createReview(
            @PathVariable("shopId") Long shopId,
            @RequestParam("comment") String comment
    ) {
        return reviewService.createReview(shopId, comment);
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
}


