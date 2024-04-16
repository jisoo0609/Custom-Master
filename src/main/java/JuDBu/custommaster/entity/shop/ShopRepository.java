package JuDBu.custommaster.entity.shop;

import JuDBu.custommaster.entity.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Long> {
    Shop findByAccount(Account account);
}
