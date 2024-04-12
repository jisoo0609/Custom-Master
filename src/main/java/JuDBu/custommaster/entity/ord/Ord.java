package JuDBu.custommaster.entity.ord;

import JuDBu.custommaster.entity.account.Account;
import JuDBu.custommaster.entity.product.Product;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Ord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Setter
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private LocalDateTime pickUpDate;
    private LocalDateTime ordTime;
    private Integer totalPrice;

    private String tossPaymentKey;
    private String tossOrderId;

    @Setter
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Status status = Status.OFFERED;

    public enum Status {
        OFFERED,
        DECLINED,
        CONFIRMED
    }
}
