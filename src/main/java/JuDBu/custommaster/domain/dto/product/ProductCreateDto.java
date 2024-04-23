package JuDBu.custommaster.domain.dto.product;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductCreateDto {

    private Long id;
    private Long shopId;
    private String name;
    private Integer exPrice;
    //private Integer quantity; // 커스텀 상품이기에 상품을 추가 할 때 수량이 필요한가?
    private String exImage;
}
