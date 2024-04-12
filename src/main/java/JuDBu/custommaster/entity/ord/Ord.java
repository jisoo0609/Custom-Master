package JuDBu.custommaster.entity.ord;

import JuDBu.custommaster.entity.account.Account;
import JuDBu.custommaster.entity.product.Product;
import JuDBu.custommaster.entity.shop.Shop;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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

    @Setter
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    private LocalDateTime pickUpDate;

    @CreatedDate
    private LocalDateTime ordTime;
    @Setter
    private Integer totalPrice;
    private String exImagePath;

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

    public static Ord createOrd(Account account, Product product, String pickupDate, String exImagePath) {
        return Ord.builder()
                //.account(account)
                //.product(product)
                .pickUpDate(LocalDateTime.parse(pickupDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .exImagePath(exImagePath)
                .status(Ord.Status.OFFERED)
                .build();
    }

    @Override
    public String toString() {
        return "Ord{" +
                "id=" + id +
                ", pickUpDate=" + pickUpDate +
                ", ordTime=" + ordTime +
                ", exImagePath='" + exImagePath + '\'' +
                ", status=" + status +
                '}';
    }
}