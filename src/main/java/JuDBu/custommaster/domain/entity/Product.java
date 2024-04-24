package JuDBu.custommaster.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne()
    @JoinColumn(name = "shop_id")
    private Shop shop;

    private String name;
    private Integer exPrice;
    private String exImage;
    private Integer resultPrice; //TODO: 상품의 최종 가격을 상품에 등록할 필요가 있을까?
    private Integer quantity; //TODO: 커스텀 상품의 수량이 필요 있을까?

    public static Product createProduct(Shop shop, String name, Integer exPrice, String exImage) {
        return Product.builder()
                .shop(shop)
                .name(name)
                .exPrice(exPrice)
                .exImage(exImage)
                .build();
    }

    public void updateProduct(String name, Integer exPrice, String exImage) {
        this.name = name;
        this.exPrice = exPrice;
        this.exImage = exImage;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", exPrice=" + exPrice +
                ", exImage='" + exImage + '\'' +
                ", resultPrice=" + resultPrice +
                ", quantity=" + quantity +
                '}';
    }
}
