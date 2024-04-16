package JuDBu.custommaster.auth.jwt.dto;

import lombok.Data;

@Data
public class JwtResponseDto {
    private String accessToken;
    private String refreshToken;
}
