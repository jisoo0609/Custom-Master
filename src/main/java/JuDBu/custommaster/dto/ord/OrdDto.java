package JuDBu.custommaster.dto.ord;

import JuDBu.custommaster.entity.ord.Ord;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
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
                .status(ord.getStatus())
                .build();
    }
}
