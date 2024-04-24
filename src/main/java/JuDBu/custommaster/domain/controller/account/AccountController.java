package JuDBu.custommaster.domain.controller.account;

import JuDBu.custommaster.auth.facade.AuthenticationFacade;
import JuDBu.custommaster.domain.dto.account.CustomAccountDetails;
import JuDBu.custommaster.domain.entity.account.Authority;
import JuDBu.custommaster.domain.service.account.AccountService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final UserDetailsManager manager;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String registerForm() {
        return "account/register";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "account/login-form";
    }

    @GetMapping("/profile")
    public String profileForm() { return  "account/my-profile"; }

    @GetMapping("/update")
    public String updateForm() { return  "account/update"; }

    @GetMapping("/logout")
    public String logoutForm() {
        return "account/logout";
    }

    @GetMapping("/mail-auth")
    public String mailAuthForm(){ return "account/mail-auth";}
}