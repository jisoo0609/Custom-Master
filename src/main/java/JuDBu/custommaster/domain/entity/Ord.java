package JuDBu.custommaster.domain.entity;

import JuDBu.custommaster.domain.entity.account.Account;
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

    private Integer phoneNumber;
    private LocalDateTime pickUpDate;

    @CreatedDate
    private LocalDateTime ordTime;

    @Setter
    private Integer totalPrice;
    private String exImagePath;
    private String requirements;
    private String tossPaymentKey;
    private String tossOrderId;

    @Setter
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Status status = Status.OFFERED;

    public enum Status {
        OFFERED,
        DECLINED,
        CONFIRMED,
        PAID
    }

    public static Ord createOrd(Account account, Shop shop, Product product, Integer phoneNumber, LocalDateTime pickupDate, String requirements, String exImagePath) {
        return Ord.builder()
                .account(account)
                .shop(shop)
                .product(product)
                .phoneNumber(phoneNumber)
                //.pickUpDate(LocalDateTime.parse(pickupDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")))
                .pickUpDate(pickupDate)
                .requirements(requirements)
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
                ", totalPrice=" + totalPrice +
                ", exImagePath='" + exImagePath + '\'' +
                ", requirements='" + requirements + '\'' +
                ", status=" + status +
                '}';
    }
}