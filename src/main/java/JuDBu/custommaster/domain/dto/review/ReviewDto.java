package JuDBu.custommaster.domain.dto.review;

import JuDBu.custommaster.domain.entity.Review;
import JuDBu.custommaster.domain.entity.Shop;
import JuDBu.custommaster.domain.entity.account.Account;
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

