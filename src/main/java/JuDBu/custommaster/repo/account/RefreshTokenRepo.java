package JuDBu.custommaster.repo.account;


import JuDBu.custommaster.entity.account.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, String> {
    RefreshToken findTopByAccessTokenOrderByIssuedTimeDesc(String msg);
}
