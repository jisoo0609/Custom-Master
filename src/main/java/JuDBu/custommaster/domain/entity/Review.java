package JuDBu.custommaster.domain.entity;

import JuDBu.custommaster.domain.entity.account.Account;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Setter
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Setter
    private String comment;

    @Setter
    private Long orderId;


    @Setter
    @ElementCollection
    private final List<String> images = new ArrayList<>();
}
