package JuDBu.custommaster.controller.ord.accept;

import JuDBu.custommaster.dto.ord.OrdDto;
import JuDBu.custommaster.entity.ord.Ord;
import JuDBu.custommaster.service.ord.accept.OrdAcceptService;
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

    // 주문 리스트 불러오기
/*    @GetMapping("/read-all")
    public List<OrdDto> ordList(
            @PathVariable("shopId") Long shopId
    ) {
        return ordAcceptService.readAllOrdByShop(shopId);
    }*/

    // 주문 리스트에서 Product name 불러오기
    @GetMapping("/read-name")
    public List<String> orderProductName(
            @PathVariable("shopId") Long shopId
    ) {
        return ordAcceptService.orderProductName(shopId);
    }

    // 주문 리스트에서 주문 상태 불러오기
    @GetMapping("/status")
    public List<Ord.Status> getOrdStatus(
            @PathVariable("shopId") Long shopId
    ) {
        return ordAcceptService.getOrdStatus(shopId);
    }

    // READ ONE ord
    @GetMapping("/read/{id}")
    public OrdDto readOrd(
            @PathVariable("shopId") Long shopId,
            @PathVariable("id") Long ordId
    ) {
        return ordAcceptService.readDetails(shopId, ordId);
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
