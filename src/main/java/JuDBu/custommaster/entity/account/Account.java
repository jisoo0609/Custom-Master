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
    @Column(nullable = false)
    private String name;        // 유저 이름
    @Column(nullable = false)
    private String email;       // 이메일
    private String businessNumber;  // 사업자 등록번호
    @Enumerated(EnumType.STRING)
    @Setter
    private Authority authority;    // 권한

    public void updateInfo(
                       String password,
                       String name,
                       String email
    ){
        this.password = password;
        this.name = name;
        this.email = email;
    }
    public void updateBusinessNumber(
                           String businessNumber
    ){

        this.businessNumber = businessNumber;
    }
}
