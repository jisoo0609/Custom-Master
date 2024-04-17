package JuDBu.custommaster.domain.dto.ord;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdRequestDto {

    private String name;
    private String phoneNumber;
    private String pickupDate; // 픽업 날짜
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
