package JuDBu.custommaster;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {
    @GetMapping("/pay")
    public String root() {
        return "redirect:/payment/items.html";
    }
}
