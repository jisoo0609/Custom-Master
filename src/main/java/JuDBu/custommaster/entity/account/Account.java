package JuDBu.custommaster.entity.account;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;    // 유저 아이디
    @Column(nullable = false)
    private String password;    // 비밀번호
    @Setter
    @Column(nullable = false)
    private String name;        // 유저 이름
    @Setter
    @Column(nullable = false)
    private String email;       // 이메일
    @Setter
    private String businessNumber;  // 사업자 등록번호

    @Setter
    @Enumerated(EnumType.STRING)
    private Authority authority;    // 권한
}
