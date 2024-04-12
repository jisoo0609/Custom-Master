package JuDBu.custommaster.entity.product;

import JuDBu.custommaster.entity.shop.Shop;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   /* @Setter
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;*/

    private String name;
    private Integer exPrice;
    private Integer resultPrice;
    private Integer quantity;
    private Integer exImage;
}
