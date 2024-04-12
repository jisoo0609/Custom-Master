package JuDBu.custommaster.entity.review;

import JuDBu.custommaster.entity.account.Account;
import JuDBu.custommaster.entity.review.Review;
import JuDBu.custommaster.entity.shop.Shop;
import lombok.*;


@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {

    private Long id;
    @Setter
    private Account account;
    @Setter
    private Shop shop;
    @Setter
    private String comment;
    @Setter
    private Long orderId;

    public static ReviewDto fromEntity(Review entity){
        ReviewDto.ReviewDtoBuilder builder = ReviewDto.builder()
                .id(entity.getId())
                .account(entity.getAccount())
                .shop(entity.getShop())
                .comment(entity.getComment())
                .orderId(entity.getOrderId());

        return builder.build();
    }
}

