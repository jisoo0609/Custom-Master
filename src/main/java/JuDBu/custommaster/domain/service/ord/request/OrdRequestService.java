package JuDBu.custommaster.domain.service.ord.request;

import JuDBu.custommaster.domain.dto.ord.OrdRequestDto;
import JuDBu.custommaster.domain.entity.account.Account;
import JuDBu.custommaster.domain.entity.Ord;
import JuDBu.custommaster.domain.entity.Product;
import JuDBu.custommaster.domain.entity.Shop;
import JuDBu.custommaster.domain.repo.ShopRepository;
import JuDBu.custommaster.auth.facade.AuthenticationFacade;
import JuDBu.custommaster.domain.repo.OrdRepo;
import JuDBu.custommaster.domain.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdRequestService {

    private final OrdRepo ordRepo;
    private final ShopRepository shopRepository;
    private final ProductRepo productRepository;
    private final AuthenticationFacade authenticationFacade;
    private final FileHandlerUtils fileHandlerUtils;

    public void requestOrder(Long shopId, Long productId, OrdRequestDto requestDto, MultipartFile exImage) {

        // 상점 조회
        Shop shop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        log.info("shop={}", shop);

        // 상품 조회
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        log.info("product={}", product);

        // 사용자 조회
        //Account account = authenticationFacade.getAccount();
        //log.info("account={}", account);

        // 예시 이미지 저장
        String exImagePath = fileHandlerUtils.saveFile(
                String.format("shops/%d/items/%d/", shop.getId(), product.getId()),
                UUID.randomUUID().toString(),
                exImage
        );
        log.info("exImagePath={}", exImagePath);

        // TODO: 픽업 날짜 검증
        // 주문 후 최소 소요 기간 3일
        requestDto.getPickupDate();
        log.info("localDateTime={}", LocalDateTime.now().plusDays(3));

        // TODO: 전화번호 검증
        // 010-0000-0000
        // 01000000000
        requestDto.getPhoneNumber();

        // 주문 요청 생성
        Ord requestOrder = Ord.createOrd(null, shop, product, null, requestDto.getPickupDate(), requestDto.getRequirements(), exImagePath);
        log.info("requestOrder={}", requestOrder);

        // 주문 요청 저장
        Ord savedOrder = ordRepo.save(requestOrder);
        log.info("savedOrder={}", savedOrder);
    }
}
