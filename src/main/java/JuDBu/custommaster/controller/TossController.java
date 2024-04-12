package JuDBu.custommaster.controller;

import JuDBu.custommaster.dto.payment.PaymentConfirmDto;
import JuDBu.custommaster.service.ord.OrdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
