package JuDBu.custommaster.domain.dto.ord;

import JuDBu.custommaster.domain.entity.Ord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class OrdDto {
    private Long id;
    private Long accountId;
    private Long productId;
    private LocalDateTime pickUpDate;
    private LocalDateTime ordTime;
    private Integer totalPrice;
    private Ord.Status status;

    public static OrdDto fromEntity(Ord ord) {
        return OrdDto.builder()
                .id(ord.getId())
                .accountId(ord.getAccount().getId())
                .productId(ord.getProduct().getId())
                .pickUpDate(ord.getPickUpDate())
                .ordTime(ord.getOrdTime())
                .totalPrice(ord.getTotalPrice())
                .status(ord.getStatus())
                .build();
    }

}
