package JuDBu.custommaster.dto.product;

import JuDBu.custommaster.entity.product.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private Integer exPrice;
    private Integer resultPrice;
    private Integer quantity;
    private Integer exImage;
    private Long shopId;

    public static ProductDto fromEntity(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .exPrice(product.getExPrice())
                .resultPrice(product.getResultPrice())
                .quantity(product.getQuantity())
                .exImage(product.getExImage())
//                .shopId(product.getShop().getId())
                .build();
    }

}
