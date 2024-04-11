package JuDBu.custommaster.entity.shop;

import JuDBu.custommaster.entity.account.Account;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String namm;

    private Account account
}
