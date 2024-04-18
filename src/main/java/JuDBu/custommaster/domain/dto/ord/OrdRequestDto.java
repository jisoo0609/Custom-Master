package JuDBu.custommaster.domain.dto.ord;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrdRequestDto {

    @NotNull(message = "이름은 필수입니다.")
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotNull(message = "전화번호는 필수입니다.")
    @NotBlank(message = "전화번호를 입력해주세요.")
    @Size(min = 11, max = 13)
    private String phoneNumber;

    @NotNull(message = "픽업 날짜는 필수입니다.")
    @Future
    private LocalDateTime pickupDate; // 픽업 날짜

    @NotNull(message = "요구사항은 필수입니다.")
    @NotBlank(message = "요구사항을 입력해주세요.")
    private String Requirements; // 요구사항

    @Override
    public String toString() {
        return "OrdRequestDto{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", pickupDate='" + pickupDate + '\'' +
                ", Requirements='" + Requirements + '\'' +
                '}';
    }
}
