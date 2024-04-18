package JuDBu.custommaster.domain.controller.ord.request;

import JuDBu.custommaster.domain.dto.ord.OrdRequestDto;
import JuDBu.custommaster.domain.service.ord.request.OrdRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrdRequestController {

    private final OrdRequestService ordService;

    @GetMapping("/{shopId}/{productId}/request")
    public String requestForm(Model model) {
        model.addAttribute("requestDto", new OrdRequestDto());
        return "ord/order-form";
    }

    @PostMapping("/{shopId}/{productId}/request")
    public String request(
            // 어떤 상점인지
            @PathVariable("shopId") Long shopId,
            // 어떤 아이템 기준인지
            @PathVariable("productId") Long productId,
            // 주문 정보
            //@Validated
            @ModelAttribute OrdRequestDto requestDto,
            BindingResult bindingResult,
            @RequestParam("exImage") MultipartFile exImage
    ) {
        log.info("hasError={}", bindingResult.getAllErrors());
        if (bindingResult.hasErrors()) {
            log.info("hasError={}", bindingResult.getAllErrors());
            return "ord/order-form";
        }

        log.info("requestDto = {}", requestDto);
        log.info("exImage = {}", exImage.getOriginalFilename());
        ordService.requestOrder(shopId, productId, requestDto, exImage);

        // TODO 주문 완료 페이지
        return "redirect:/{shopId}/{productId}/request";
    }
}
