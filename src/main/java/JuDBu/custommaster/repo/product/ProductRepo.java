package JuDBu.custommaster.repo.product;

import JuDBu.custommaster.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
}
