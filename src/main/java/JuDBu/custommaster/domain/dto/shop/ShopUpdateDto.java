package JuDBu.custommaster.domain.dto.shop;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ShopUpdateDto {

    private Long id;
    private String name;
    private String address;
    private String phoneNumber;
}
