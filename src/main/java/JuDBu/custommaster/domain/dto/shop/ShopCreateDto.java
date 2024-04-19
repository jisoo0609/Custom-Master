package JuDBu.custommaster.domain.dto.shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopCreateDto {


    private String name;
    private String address;
    private String phoneNumber;

    @Override
    public String toString() {
        return "ShopCreateDto{" +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
