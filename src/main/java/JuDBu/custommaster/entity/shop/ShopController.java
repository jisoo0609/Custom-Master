package JuDBu.custommaster.entity.shop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// 상점 개설
@Slf4j
@RestController
@RequestMapping("shop")
@RequiredArgsConstructor
public class ShopController {

    private final ShopService shopService;

    // 상점에 대한 정보 입력 폼
    @GetMapping("create")
    public String createForm(@ModelAttribute ShopCreateDto createDto) {
        return "shop-create-form";
    }

    // 상점에 대한 정보 입력
    @PostMapping("create")
    public String create(
            @ModelAttribute ShopCreateDto createDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        log.info("shopCreateDto = {}", createDto);
        Long shopId = shopService.createShop(createDto);
        redirectAttributes.addAttribute("shopId", shopId);
        return "redirect:/shop/{shopId}";
    }

    // 상점 리스트 조회
    @GetMapping
    public String readPage(Pageable pageable, Model model) {
        Page<ShopReadDto> shopReadPageDto = shopService.readPage(pageable);
        model.addAttribute("shopReadPageDto", shopReadPageDto);
        return "readPage";
    }

    // 상점 상세 조회
    @GetMapping("{shopId}")
    public String readOne(@PathVariable("shopId") Long shopId, Model model) {
        ShopReadDto readDto = shopService.readOne(shopId);
        model.addAttribute("readDto", readDto);
        return "shop";
    }

    // 상점 수정 입력 폼
    @GetMapping("update")
    public String updateForm() {
        return "shop-update-form";
    }

    // 상점 수정 정보 입력
    @PutMapping("{shopId}")
    public String update(
            @PathVariable("shopId") Long shopId,
            ShopUpdateDto updateDto,
            RedirectAttributes redirectAttributes
    ) {
        Long updateShopId = shopService.updateShop(shopId, updateDto);
        redirectAttributes.addAttribute("shopId", updateShopId);
        return "redirect:/{shopId}";
    }

    // 상점 삭제
    @DeleteMapping("{shopId}")
    public void delete(@PathVariable("shopId") Long shopId) {
        shopService.deleteShop(shopId);
    }
}
