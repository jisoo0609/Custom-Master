package JuDBu.custommaster.entity.ord.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrdController {

    private final OrdService ordService;

    @GetMapping("/{shopId}/{productId}/request")
    public String requestForm(@ModelAttribute OrdRequestDto requestDto) {
        return "request-form";
    }

    @PostMapping("/{shopId}/{productId}/request")
    public String request(
            // 어떤 상점인지
            @PathVariable("shopId") Long shopId,
            // 어떤 아이템 기준인지
            @PathVariable("productId") Long productId,
            // 주문 정보
            @ModelAttribute OrdRequestDto requestDto,
            BindingResult bindingResult,
            @RequestParam("exImage")MultipartFile exImage
    ) {
        log.info("requestDto = {}", requestDto);
        log.info("exImage = {}", exImage.getOriginalFilename());
        ordService.requestOrder(shopId, productId, requestDto, exImage);
        return "request";
    }
}
