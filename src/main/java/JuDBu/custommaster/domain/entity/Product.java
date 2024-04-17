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
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    private String name;
    private Integer exPrice;
    private String exImage;
    private Integer resultPrice;
    private Integer quantity;

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
