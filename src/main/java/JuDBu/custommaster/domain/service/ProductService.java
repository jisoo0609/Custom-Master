package JuDBu.custommaster.domain.service;

import JuDBu.custommaster.domain.dto.product.ProductCreateDto;
import JuDBu.custommaster.domain.dto.product.ProductDto;
import JuDBu.custommaster.domain.dto.product.ProductUpdateDto;
import JuDBu.custommaster.domain.entity.Product;
import JuDBu.custommaster.domain.entity.Shop;
import JuDBu.custommaster.domain.repo.ProductRepo;
import JuDBu.custommaster.domain.repo.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ShopRepository shopRepository;
    private final ProductRepo productRepository;
    private final FileHandlerUtils fileHandlerUtils;

    /*// 상품 임시 생성
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
    }*/

    public List<ProductDto> readAll() {
        return productRepository.findAll().stream()
                .map(ProductDto::fromEntity)
                .toList();
    }

    public ProductDto readOne(Long id) {
        return productRepository.findById(id)
                .map(ProductDto::fromEntity)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Transactional
    public void createProduct(Long shopId, ProductCreateDto createDto, MultipartFile exImage) {

        //TODO: 상점과 상품에 대한 검증 추가 필요

        Shop findShop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // 예시 이미지 저장
        String exImagePath = fileHandlerUtils.saveFile(
                String.format("shops/%d/items/", shopId),
                UUID.randomUUID().toString(),
                exImage
        );
        log.info("exImagePath={}", exImagePath);

        Product product = Product.createProduct(findShop, createDto.getName(), createDto.getExPrice(), exImagePath);
        log.info("product={}", product);

        Product savedProduct = productRepository.save(product);
        log.info("savedProduct={}", savedProduct);
    }

    public ProductUpdateDto findProduct(Long productId) {
        return ProductUpdateDto.fromEntity(productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @Transactional
    public void updateProduct(Long shopId, Long productId, ProductUpdateDto updateDto, MultipartFile exImage) {

        Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // 수정 이미지가 없는 경우 기존 이미지 경로 추가
        String exImagePath = findProduct.getExImage();

        // 수정 이미지가 있는 경우 이미지 저장 후 경로 생성 후 추가
        if (!exImage.isEmpty()) {
            // TODO: 기존 예시 이미지 삭제
            //fileHandlerUtils.deleteFile(findProduct.getExImage());

            // 예시 이미지 저장
            exImagePath = fileHandlerUtils.saveFile(
                    String.format("shops/%d/items/", shopId),
                    UUID.randomUUID().toString(),
                    exImage
            );
            log.info("exImagePath={}", exImagePath);
        }

        findProduct.updateProduct(updateDto.getName(), updateDto.getExPrice(), exImagePath);
        log.info("updateShop={}", findProduct);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
