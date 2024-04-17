package JuDBu.custommaster.domain.controller.account;

import JuDBu.custommaster.auth.facade.AuthenticationFacade;
import JuDBu.custommaster.domain.entity.account.Account;
import JuDBu.custommaster.domain.service.account.AccountService;
import JuDBu.custommaster.domain.dto.account.AccountDto;
import JuDBu.custommaster.domain.dto.account.CustomAccountDetails;
import JuDBu.custommaster.domain.entity.account.Authority;
import JuDBu.custommaster.auth.jwt.dto.JwtResponseDto;
import JuDBu.custommaster.domain.service.account.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequestMapping("/api/account")
@RequiredArgsConstructor
public class AccountRestController {
    private final UserDetailsManager manager;
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationFacade authFacade;
    private final MailService mailService;

    @PostMapping("/business-register")
    public JwtResponseDto businessRegister(){
        return null;
    }

    @PostMapping("/register")
    public String register(
            @RequestParam("username")
            String username,
            @RequestParam("password")
            String password,
            @RequestParam("password-check")
            String passwordCheck,
            @RequestParam("name")
            String name,
            @RequestParam("email")
            String email
    ){
        if (password.equals(passwordCheck)) {
            manager.createUser(CustomAccountDetails.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .name(name)
                    .email(email)
                    .authority(Authority.ROLE_INACTIVE_USER)
                    .build());
        }
        return "register done";
    }

    @GetMapping("/read/{id}")
    public AccountDto readOne(
            @PathVariable("id")
            Long id
    ){
        return accountService.readOne(id);
    }

    @PostMapping("/update")
    public String update(
            @RequestParam("username")
            String username,
            @RequestParam("password-check")
            String passwordCheck,
            @RequestParam("name")
            String name,
            @RequestParam("email")
            String email
    ){
        AccountDto dto = AccountDto.builder()
                .username(username)
                .password(passwordCheck)
                .name(name)
                .email(email)
                .build();
        accountService.updateAccount(dto);
        return "register done";
    }

    @PostMapping("/delete/{id}")
    public AccountDto deleteOne(
            @PathVariable("id")
            Long id
    ){
        return accountService.delete(id);
    }

    @PostMapping("/send-mail")
    public String sendMail(){
        Account account = authFacade.getAccount();
        mailService.send(account.getUsername(), account.getEmail());
        return "send mail";
    }

    @PostMapping("/check-mail")
    public String checkCode(
            @RequestParam
            String authString
    ){
        Account account = authFacade.getAccount();

        if (!mailService.checkTimeLimit5L(account.getUsername())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
        if(!mailService.checkInfo(account.getUsername(), account.getEmail())){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if(!mailService.checkMailAuth(account.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        if (!mailService.checkCode(account.getUsername(), authString)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return "mailauth done";
    }
}
