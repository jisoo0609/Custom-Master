package JuDBu.custommaster.domain.controller.account;

import JuDBu.custommaster.domain.entity.account.RefreshToken;
import JuDBu.custommaster.domain.repo.account.AccountRepo;
import JuDBu.custommaster.domain.repo.account.RefreshTokenRepo;
import JuDBu.custommaster.domain.entity.account.Account;
import JuDBu.custommaster.auth.jwt.JwtTokenUtils;
import JuDBu.custommaster.auth.jwt.dto.JwtResponseDto;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

// 단순 토큰 발급 테스트용 컨트롤러
@Slf4j
@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class TokenController {
    private final JwtTokenUtils jwtTokenUtils;
    private final UserDetailsManager manager;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepo tokenRepo;
    private final AccountRepo accountRepo;

    // 액세스 토큰 발급 테스트
    @PostMapping("/issue-access")
    public JwtResponseDto issueAccess(
            @RequestParam
            String username,
            @RequestParam
            String password
    ) {
        // 사용자가 제공한 username(id), password가 저장된 사용자인지 판단
        if (!manager.userExists(username))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        UserDetails userDetails
                = manager.loadUserByUsername(username);

        // 비밀번호 확인
        if (!passwordEncoder.matches(password, userDetails.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        // JWT 발급
        String access = jwtTokenUtils.generateToken(userDetails);
        JwtResponseDto response = new JwtResponseDto();
        response.setAccessToken(access);
        return response;
    }

    // 리프레쉬 토큰 발급 테스트
    @PostMapping("/issue-refresh")
    public JwtResponseDto issueRefresh(
            @RequestParam
            String accessToken
    ) {
        jwtTokenUtils.validate(accessToken);
        String username = jwtTokenUtils.parseClaims(accessToken).getSubject();
        UserDetails userDetails
                = manager.loadUserByUsername(username);
        String refreshToken = jwtTokenUtils.generateToken(userDetails);
        Account account = accountRepo.findByUsername(username).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND));
        RefreshToken token = RefreshToken.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accountId(account.getId())
                .issuedTime(LocalDateTime.now())
                .build();
        tokenRepo.save(token);

        JwtResponseDto response = new JwtResponseDto();
        response.setAccessToken(refreshToken);
        return response;
    }
    // 토큰 발급 보기


    // 발급된 토큰이 유효한지 확인
    @GetMapping("/validate")
    public Claims validateToken(@RequestParam("token") String token) {
        if (!jwtTokenUtils.validate(token))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        return jwtTokenUtils.parseClaims(token);
    }
}

