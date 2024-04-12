package JuDBu.custommaster.entity.ord.request;

import JuDBu.custommaster.entity.ord.Ord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdService {

    private final OrdRepository ordRepository;
    //private final ShopRepository shopRepository;
    //private final AccountRepository accountRepository;
    //private final ProductRepository productRepository;

    public void requestOrder(Long shopId, Long productId, OrdRequestDto requestDto, MultipartFile exImage) {

        // 상점 조회
        //Shop shop = shopRepository.findById(shopId);

        // 상품 조회
        //Product product = productRepository.findById(productId);

        // 유저 조회
        //Account account = accountRepository.findByName(requestDto.getName());

        // 예시 이미지 저장
        String exImagePath = "ex-image/" + UUID.randomUUID();
        try {
            exImage.transferTo(Path.of(exImagePath));
        } catch (IOException e) {
            log.info("예시 이미지 저장 오류");
        }

        // 주문 요청 생성
        Ord requestOrder = Ord.createOrd(null, null, requestDto.getPickupDate(), exImagePath);
        Ord savedOrder = ordRepository.save(requestOrder);

        log.info("savedOrder = {}", savedOrder);
    }
}
