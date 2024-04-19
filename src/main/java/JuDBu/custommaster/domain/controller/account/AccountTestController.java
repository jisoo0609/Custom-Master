package JuDBu.custommaster.domain.controller.account;

import JuDBu.custommaster.auth.facade.AuthenticationFacade;
import JuDBu.custommaster.domain.service.account.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account-test")
@RequiredArgsConstructor
public class AccountTestController {
    private final AccountService accountService;
    private final AuthenticationFacade authFacade;

    @PostMapping("/test")
    public String test(
            Model model
    ){
        model.addAttribute("accounts",accountService.readAll());
        return "account/accountTest";
    }
}
