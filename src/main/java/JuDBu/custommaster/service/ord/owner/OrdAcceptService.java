package JuDBu.custommaster.service.ord.owner;

import JuDBu.custommaster.dto.ord.OrdDto;
import JuDBu.custommaster.entity.ord.Ord;
import JuDBu.custommaster.entity.shop.Shop;
import JuDBu.custommaster.repo.ord.OrdRepo;
import JuDBu.custommaster.repo.product.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdAcceptService {
    private final OrdRepo ordRepo;
    private final ProductRepo productRepo;

    // Shop에 있는 주문 전체 불러오기
    public List<OrdDto> readAllOrdByShop(Long shopId) {
        // 매장 주인인지 확인

        // 해당 매장의 주문 불러오기
        List<Ord> ords = ordRepo.findByShop_Id(shopId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        log.info(ords.toString());

        return ords.stream()
                .map(OrdDto::fromEntity)
                .collect(Collectors.toList());
    }

    // Shop에 있는 주문 상세 확인
    public OrdDto readDetails(Long shopId, Long ordId) {
        // 매장 주인인지 확인

        // 매장에 속한 주문인지 확인
        Ord ord = ordRepo.findByShop_IdAndId(shopId, ordId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        return OrdDto.fromEntity(ord);
    }

    // 주문 승락
    public OrdDto accept(Long shopId, Long ordId, Integer totalPrice) {
        // 매장 주인인지 확인

        // 매장에 속한 주문인지 확인
        Ord target = ordRepo.findByShop_IdAndId(shopId, ordId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // 주문 승락
        if (target.getStatus().equals(Ord.Status.CONFIRMED)) {
            log.error("이미 완료된 주문입니다.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        target.setStatus(Ord.Status.CONFIRMED);
        if (totalPrice < target.getProduct().getExPrice()) {
            log.error("요청 메뉴 가격보다 작은 가격을 설정할 수 없습니다.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        target.setStatus(Ord.Status.CONFIRMED);
        target.setTotalPrice(totalPrice);
        ordRepo.save(target);

        return OrdDto.fromEntity(target);
    }

    // 주문 거절
    public void deleteOrd(Long shopId, Long ordId) {
        // 매장 주인인지 확인

        // 매장에 속한 주문인지 확인
        Ord target = ordRepo.findById(ordId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Shop shop = target.getProduct().getShop();

        if (!shopId.equals(shop.getId())) {
            log.error("해당 매장의 주문이 아닙니다.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 주문 거절
        target.setStatus(Ord.Status.DECLINED);
        log.info("Status: {}", target.getStatus());
        ordRepo.delete(target);
    }
}
