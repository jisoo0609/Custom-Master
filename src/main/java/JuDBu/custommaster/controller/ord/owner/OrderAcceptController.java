package JuDBu.custommaster.controller.ord.owner;

import JuDBu.custommaster.dto.ord.OrdDto;
import JuDBu.custommaster.service.ord.owner.OrdAcceptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/order-accept/{shopId}")
@RequiredArgsConstructor
public class OrderAcceptController {
    private final OrdAcceptService ordAcceptService;

    @GetMapping("/read-all")
    public String ordList(
            @PathVariable("shopId") Long shopId,
            Model model
    ) {
        List<OrdDto> ords = ordAcceptService.readAllOrdByShop(shopId);
        List<String> productNames = ordAcceptService.orderProductName(shopId);
        List<String> accountNames = ordAcceptService.getAccountName(shopId);

        log.info(productNames.toString());
        log.info(accountNames.toString());

        Collections.reverse(ords);
        Collections.reverse(productNames);
        Collections.reverse(accountNames);

        model.addAttribute("ords", ords);
        model.addAttribute("names", productNames);
        model.addAttribute("accounts", accountNames);
        return "ord/shop-order-list";
    }

    @GetMapping("/read/{id}")
    public String readOrd(
            @PathVariable("shopId") Long shopId,
            @PathVariable("id") Long ordId,
            Model model
    ) {
        OrdDto ord = ordAcceptService.readDetails(shopId, ordId);
        model.addAttribute("ord", ord);
        return "ord/ord-detail";
    }
}
