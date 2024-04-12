package JuDBu.custommaster.service.ord.owner;

import JuDBu.custommaster.dto.ord.OrdDto;
import JuDBu.custommaster.dto.product.ProductDto;
import JuDBu.custommaster.entity.ord.Ord;
import JuDBu.custommaster.entity.product.Product;
import JuDBu.custommaster.entity.shop.Shop;
import JuDBu.custommaster.repo.ord.OrdRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdAcceptService {
    private final OrdRepo ordRepo;

    // Shop에 있는 주문 전체 불러오기
    public List<OrdDto> readAllOrdByShop(Long shopId) {
        // 매장 주인인지 확인

        // 전체 주문 가져옴
        List<Ord> ordList = ordRepo.findAll();

        // 해당 매장의 주문인 경우 List에 저장
        List<Ord> ords = new ArrayList<>();
        for (Ord ord : ordList) {
            if (ord.getProduct().getShop().getId().equals(shopId)) {
                ords.add(ord);
            }
        }
        log.info(ords.toString());

        return ords.stream()
                .map(OrdDto::fromEntity)
                .collect(Collectors.toList());
    }

    // Shop에 있는 주문 상세 확인
    public OrdDto readDetails(Long shopId, Long ordId) {
        // 매장 주인인지 확인

        // 매장에 속한 주문인지 확인
        Ord ord = ordRepo.findById(ordId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Shop shop = ord.getProduct().getShop();

        if (!shopId.equals(shop.getId())) {
            log.error("해당 매장의 주문이 아닙니다.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return OrdDto.fromEntity(ord);
    }

    // 해당 주문의 product 내용 가져오기
    public ProductDto getOrdProductDetails(Long shopId, Long ordId) {
        // 매장 주인인지 확인

        // 매장에 속한 주문인지 확인
        Ord ord = ordRepo.findById(ordId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        Shop shop = ord.getProduct().getShop();

        if (!shopId.equals(shop.getId())) {
            log.error("해당 매장의 주문이 아닙니다.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        // 매장의 주문이 맞다면 해당 주문의 Product 가져오기
        Product product = ord.getProduct();

        return ProductDto.fromEntity(product);
    }
}
