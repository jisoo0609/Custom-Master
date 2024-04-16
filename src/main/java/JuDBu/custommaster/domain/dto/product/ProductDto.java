package JuDBu.custommaster.domain.dto.product;

import JuDBu.custommaster.domain.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private Long shopId;
    private String name;
    private Integer exPrice;
    private Integer resultPrice;
    private Integer quantity;
    private Integer exImage;

    public static ProductDto fromEntity(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .shopId(product.getShop().getId())
                .name(product.getName())
                .exPrice(product.getExPrice())
                .resultPrice(product.getResultPrice())
                .quantity(product.getQuantity())
                //.exImage(product.getExImage())
                .build();
    }
}
