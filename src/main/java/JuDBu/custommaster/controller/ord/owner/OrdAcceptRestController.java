package JuDBu.custommaster.controller.ord.owner;

import JuDBu.custommaster.dto.ord.OrdDto;
import JuDBu.custommaster.dto.product.ProductDto;
import JuDBu.custommaster.service.ord.owner.OrdAcceptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
