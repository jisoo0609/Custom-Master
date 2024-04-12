package JuDBu.custommaster.entity.shop;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShopCreateDto {

    private Long id;
    private String name;
    private String address;
    private String phoneNumber;

    @Override
    public String toString() {
        return "ShopCreateDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
