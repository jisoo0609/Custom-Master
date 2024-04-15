package JuDBu.custommaster.dto.account;

import JuDBu.custommaster.entity.account.Account;
import JuDBu.custommaster.entity.account.Authority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AccountDto {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String businessNumber;
    private Authority authority;

    public static AccountDto fromEntity(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .username(account.getUsername())
                .password(account.getPassword())
                .name(account.getName())
                .email(account.getEmail())
                .authority(account.getAuthority())
                .businessNumber(account.getBusinessNumber())
                .build();
    }
}