package JuDBu.custommaster.entity.payment;

import JuDBu.custommaster.entity.account.Account;
import JuDBu.custommaster.entity.order.Ord;
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
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "ord_id")
    private Ord ord;
}
