package JuDBu.custommaster.domain.service;

import JuDBu.custommaster.domain.dto.product.ProductCreateDto;
import JuDBu.custommaster.domain.dto.product.ProductDto;
import JuDBu.custommaster.domain.entity.Product;
import JuDBu.custommaster.domain.repo.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
public class ProductService {
    private final ProductRepo productRepository;

    // 상품 임시 생성
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

    public void createProduct(ProductCreateDto createDto) {

        //TODO: 상품에 대한 검증 추가 필요

        Product product = Product.createProduct(createDto.getName(), createDto.getExPrice(), createDto.getExImage());
        log.info("product={}", product);

        Product savedProduct = productRepository.save(product);
        log.info("savedProduct={}", savedProduct);
    }
}
