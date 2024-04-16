package JuDBu.custommaster.domain.controller;

import JuDBu.custommaster.domain.dto.product.ProductDto;
import JuDBu.custommaster.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("items")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<ProductDto> readAll() {
        return productService.readAll();
    }

    @GetMapping("{id}")
    public ProductDto readOne(
            @PathVariable("id")
            Long id
    ) {
        return productService.readOne(id);
    }
}
