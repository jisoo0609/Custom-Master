package JuDBu.custommaster.domain.controller;

import JuDBu.custommaster.domain.dto.account.AccountDto;
import JuDBu.custommaster.domain.dto.review.ReviewDto;
import JuDBu.custommaster.domain.dto.shop.ShopReadDto;
import JuDBu.custommaster.domain.entity.Shop;
import JuDBu.custommaster.domain.repo.ShopRepository;
import JuDBu.custommaster.domain.service.ReviewService;
import JuDBu.custommaster.domain.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final ShopService shopService;

    @GetMapping("/{shopId}/create")
    public String reviewCreateView(
            @PathVariable("shopId") Long shopId,
            Model model
    ) {
        ShopReadDto shop = shopService.readOne(shopId);
        model.addAttribute("shop", shop);
        return "review/create";
    }

    @PostMapping("/{shopId}/create")
    public String create(
            @PathVariable("shopId") Long shopId,
            @RequestParam("comment") String comment
    ) {
        reviewService.createReview(shopId, comment);
        return String.format("redirect:/review/%d/read-all", shopId);
    }

    @GetMapping("/{shopId}/read-all")
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

    @GetMapping("/{shopId}/read/{reviewId}")
    public String readOneReview(
            @PathVariable("shopId") Long shopId,
            @PathVariable("reviewId") Long reviewId,
            Model model
    ) {
        ReviewDto review = reviewService.readReview(shopId, reviewId);
        AccountDto account = reviewService.getReviewName(shopId, reviewId);

        model.addAttribute("review", review);
        model.addAttribute("account", account);
        return "review/review-detail";
    }

    @GetMapping("/update")
    public String updateReview() {
        return "review/update";
    }
}
