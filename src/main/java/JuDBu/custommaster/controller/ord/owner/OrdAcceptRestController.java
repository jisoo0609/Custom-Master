package JuDBu.custommaster.controller.ord.owner;

import JuDBu.custommaster.dto.ord.OrdDto;
import JuDBu.custommaster.dto.product.ProductDto;
import JuDBu.custommaster.service.ord.owner.OrdAcceptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/order-accept/{shopId}")
@RequiredArgsConstructor
public class OrdAcceptRestController {
    private final OrdAcceptService ordAcceptService;

    @GetMapping("/read-all")
    public List<OrdDto> ordList(@PathVariable("shopId") Long shopId) {
        return ordAcceptService.readAllOrdByShop(shopId);
    }

    @GetMapping("/read/{id}")
    public OrdDto readOrd(
            @PathVariable("shopId") Long shopId,
            @PathVariable("id") Long ordId
    ) {
        return ordAcceptService.readDetails(shopId, ordId);
    }

    @GetMapping("/read/{id}/details")
    public ProductDto readProductDtails(
            @PathVariable("shopId") Long shopId,
            @PathVariable("id") Long ordId
    ) {
        return ordAcceptService.getOrdProductDetails(shopId, ordId);
    }

    // 주문 요청 승낙
    @PostMapping("/accept/{ordId}")
    public OrdDto accept(
            @PathVariable("shopId") Long shopId,
            @PathVariable("ordId") Long ordId,
            @RequestParam("price") Integer price
    ) {
        return ordAcceptService.accept(shopId, ordId, price);
    }

    // 주문 요청 거절
    @DeleteMapping("/delete/{ordId}")
    public void delete(
            @PathVariable("shopId") Long shopId,
            @PathVariable("ordId") Long ordId
    ) {
        ordAcceptService.deleteOrd(shopId, ordId);
    }
}
