package JuDBu.custommaster.domain.controller;

import JuDBu.custommaster.domain.dto.product.ProductCreateDto;
import JuDBu.custommaster.domain.dto.product.ProductDto;
import JuDBu.custommaster.domain.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // 상품에 대한 정보 입력 폼
    @GetMapping("create")
    public String createForm(@ModelAttribute("createDto") ProductCreateDto createDto) {
        //TODO 인증된 사용자 정보 확인
        return "product/product-create-form";
    }

    // 상품에 대한 정보 입력
    @PostMapping("create")
    public String create(
            @Validated
            @ModelAttribute("createDto")
            ProductCreateDto createDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult.getAllErrors());
            return "product/product-create-form";
        }

        log.info("productCreateDto={}", createDto);
        productService.createProduct(createDto);
        redirectAttributes.addAttribute("shop", null);
        return "redirect:/shop/{shopId}";
    }

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
