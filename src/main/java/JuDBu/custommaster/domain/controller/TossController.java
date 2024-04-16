package JuDBu.custommaster.domain.controller;

import JuDBu.custommaster.domain.dto.ord.OrdDto;
import JuDBu.custommaster.domain.dto.payment.PaymentConfirmDto;
import JuDBu.custommaster.domain.service.ord.OrdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/toss")
@RequiredArgsConstructor
public class TossController {
    private final OrdService ordService;
    // object 는 임시
    @PostMapping("/confirm-payment")
    public Object confirmPayment(
            @RequestBody
            PaymentConfirmDto dto
    ) {
        log.info(dto.toString());
        return ordService.confirmPayment(dto);
    }

    // 임시 주문 생성
    @PostMapping("/ord-create")
    public Object ordCreate(
            @RequestBody
            OrdDto dto
    ){
        return ordService.ordCreate(dto);
    }

    // 주문 전체 읽기
    @GetMapping("/ord-readAll")
    public List<OrdDto> readAll(){
        return ordService.readAll();
    }

    // 주문 상세 정보 조회
    @GetMapping("/ord-detail/{ordId}")
    public Object getOrdDetail(@PathVariable Long ordId) {
        log.info("Fetching order details for ID: {}", ordId);
        OrdDto ordDetail = ordService.readOne(ordId);
        if (ordDetail == null) {
            log.error("Order not found for ID: {}", ordId);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ordDetail);
    }
}
