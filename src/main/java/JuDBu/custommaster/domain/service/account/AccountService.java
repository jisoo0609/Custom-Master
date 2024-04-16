package JuDBu.custommaster.domain.service.account;


import JuDBu.custommaster.domain.dto.account.AccountDto;
import JuDBu.custommaster.domain.entity.account.Account;
import JuDBu.custommaster.domain.repo.account.AccountRepo;
import JuDBu.custommaster.auth.facade.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepo accountRepo;
    private final AuthenticationFacade authFacade;
    private final PasswordEncoder passwordEncoder;

    public AccountDto readOne(Long id){
        Account account = accountRepo.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND));

        return AccountDto.fromEntity(account);
    }

    public AccountDto delete(Long id){
        Account account = accountRepo.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND));

        accountRepo.deleteById(id);
        return AccountDto.fromEntity(account);
    }

    // 유저 정보 가져오기
    public AccountDto readOneAccount() {
        Account account = authFacade.getAccount();

        log.info("auth user: {}", authFacade.getAuth().getName());
        log.info("page username: {}", account.getUsername());

        // 토큰으로 접근 시도한 유저와, 페이지의 유저가 다른경우 예외
        if (!authFacade.getAuth().getName().equals(account.getUsername())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return AccountDto.fromEntity(account);
    }

    public AccountDto readOneAccount(Long id) {
        Account account = accountRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return AccountDto.fromEntity(account);
    }

    // 사용자 정보 수정
    public AccountDto updateAccount(AccountDto dto) {
        Account target = authFacade.getAccount();

        // 토큰의 유저와 수정하는 유저가 똑같은 유저인지
        if(!target.getUsername().equals(dto.getUsername())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        // 비밀번호 확인
        if (!passwordEncoder.matches(dto.getPassword(), target.getPassword())) {
            log.error("비밀번호가 일치하지 않습니다.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        target.updateInfo(dto.getPassword(), dto.getName(), dto.getEmail());

        return AccountDto.fromEntity(accountRepo.save(target));
    }

}
