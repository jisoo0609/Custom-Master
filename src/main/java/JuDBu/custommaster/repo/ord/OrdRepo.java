package JuDBu.custommaster.repo.ord;

import JuDBu.custommaster.entity.ord.Ord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrdRepo extends JpaRepository<Ord, Long> {
    Optional<List<Ord>> findByProductShop_Id(Long ShopId);
    Optional<List<Ord>> findByShop_Id(Long shopId);
    // Ord의 ShopId로 해당하는 주문 불러오기
    Optional<Ord> findByShop_IdAndId(Long shopId, Long ordId);
}
