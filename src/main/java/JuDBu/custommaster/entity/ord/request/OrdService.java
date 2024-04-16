package JuDBu.custommaster.entity.ord.request;

import JuDBu.custommaster.entity.account.Account;
import JuDBu.custommaster.entity.ord.Ord;
import JuDBu.custommaster.entity.product.Product;
import JuDBu.custommaster.entity.shop.Shop;
import JuDBu.custommaster.entity.shop.ShopRepository;
import JuDBu.custommaster.facade.AuthenticationFacade;
import JuDBu.custommaster.repo.product.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdService {

    private final OrdRepository ordRepository;
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
        Account account = authenticationFacade.getAccount();
        log.info("account={}", account);

        // 예시 이미지 저장
        String exImagePath = fileHandlerUtils.saveFile(
                String.format("shops/%d/items/%d/", shop.getId(), product.getId()),
                "image",
                exImage
        );
        log.info("exImagePath={}", exImagePath);

        // 주문 요청 생성
        Ord requestOrder = Ord.createOrd(account, product, requestDto.getPickupDate(), exImagePath);
        log.info("requestOrder={}", requestOrder);

        // 주문 요청 저장
        Ord savedOrder = ordRepository.save(requestOrder);
        log.info("savedOrder={}", savedOrder);
    }
}
