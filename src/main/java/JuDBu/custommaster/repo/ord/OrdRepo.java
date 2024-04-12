package JuDBu.custommaster.repo.ord;

import JuDBu.custommaster.entity.ord.Ord;
import JuDBu.custommaster.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
public interface OrdRepo extends JpaRepository<Ord, Long> {
    Optional<List<Ord>> findByProductIn(List<Product> products);
}
