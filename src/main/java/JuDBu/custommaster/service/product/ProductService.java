package JuDBu.custommaster.service.product;

import JuDBu.custommaster.dto.product.ProductDto;
import JuDBu.custommaster.entity.product.Product;
import JuDBu.custommaster.repo.product.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
public class ProductService {
    private final ProductRepo productRepository;

    public ProductService(ProductRepo productRepository) {
        this.productRepository = productRepository;
        if (this.productRepository.count() == 0) {
            this.productRepository.saveAll(List.of(
                    Product.builder()
                            .name("mouse")
                            .exPrice(40)
                            .build(),
                    Product.builder()
                            .name("keyboard")
                            .exPrice(50)
                            .build()
            ));
        }
    }

    public List<ProductDto> readAll(){
        return productRepository.findAll().stream()
                .map(ProductDto::fromEntity)
                .toList();
    }

    public ProductDto readOne(Long id) {
        return productRepository.findById(id)
                .map(ProductDto::fromEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
