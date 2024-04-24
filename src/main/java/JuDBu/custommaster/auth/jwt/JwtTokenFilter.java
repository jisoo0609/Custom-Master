package JuDBu.custommaster.auth.jwt;

import JuDBu.custommaster.auth.jwt.dto.ErrorCode;
import JuDBu.custommaster.auth.jwt.dto.JwtErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenUtils jwtTokenUtils;
    private final UserDetailsManager manager;
    private String refreshToken;

    public JwtTokenFilter(
            JwtTokenUtils jwtTokenUtils,
            UserDetailsManager manager
    ) {
        this.jwtTokenUtils = jwtTokenUtils;
        this.manager = manager;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        log.debug("try jwt filter");
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for(Cookie c : cookies){
                if (c.getName().equals("CMToken")){
                    log.info("cookie value: {}", c.getValue());
                    refreshToken = c.getValue();
                }
            }
        }
        // 1. Authorization 헤더를 회수
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        log.info("authHeader: {}",authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            if(!(request.getRequestURI().equals("/account/logout"))){
                String accessToken = authHeader.split(" ")[1];
                try{
                    jwtTokenUtils.validateAccess(accessToken);

                    SecurityContext context = SecurityContextHolder.createEmptyContext();

                    String username = jwtTokenUtils
                            .parseClaims(accessToken)
                            .getSubject();

                    UserDetails userDetails = manager.loadUserByUsername(username);
                    for (GrantedAuthority authority :userDetails.getAuthorities()) {
                        log.info("authority: {}", authority.getAuthority());
                    }

                    // 인증 정보 생성
                    AbstractAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    accessToken,
                                    userDetails.getAuthorities()
                            );
                    // 인증 정보 등록
                    context.setAuthentication(authentication);
                    SecurityContextHolder.setContext(context);
                    log.info("set security context with jwt");
                }
                catch (ExpiredJwtException e){
                    throw new JwtException("1");
                }catch (UnsupportedJwtException e){
                    throw new JwtException("2");
                }catch (MalformedJwtException e){
                    throw new JwtException("3");
                }catch (SignatureException e){
                    throw new JwtException("4");
                } catch (IllegalArgumentException e){
                    throw new JwtException("5");
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}