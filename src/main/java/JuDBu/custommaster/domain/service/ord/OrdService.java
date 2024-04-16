package JuDBu.custommaster.domain.service.ord;

import JuDBu.custommaster.domain.dto.ord.OrdDto;
import JuDBu.custommaster.domain.dto.payment.PaymentConfirmDto;
import JuDBu.custommaster.domain.entity.Ord;
import JuDBu.custommaster.domain.entity.Product;
import JuDBu.custommaster.domain.repo.OrdRepo;
import JuDBu.custommaster.domain.repo.ProductRepo;
import JuDBu.custommaster.domain.service.TossHttpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdService {
    private final TossHttpService tossService;
    private final ProductRepo productRepository;
    private final OrdRepo ordRepository;

    // todo 수정 중 : ord 찾아서 결제 정보 update 하기
    public String /*Object*/ confirmPayment(PaymentConfirmDto dto) {
        // HTTP 요청 보내진다.
        Object tossPaymentObj = tossService.confirmPayment(dto);
        log.info(tossPaymentObj.toString());
        // 1. 결제한 물품 정보를 응답 body에서 찾는다.
        String orderName = ((LinkedHashMap<String, Object>) tossPaymentObj)
                .get("orderName").toString();

        // 2. orderName에서 productId 회수하고, 그에 해당하는 item 엔티티를 조회
        Long productId = Long.parseLong(orderName.split("-")[0]);
        Product product  = productRepository.findById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));

        LocalDateTime now = LocalDateTime.now();
        // todo ord 업데이트
        /*return OrdDto.fromEntity(ordRepository.save(Ord.builder()
                .product(product)
                .ordTime(now)
                .tossPaymentKey(dto.getPaymentKey())
                .tossOrderId(dto.getOrderId())
                .totalPrice(dto.getAmount())
                .status(Ord.Status.CONFIRMED)
                .build()));*/
        return "수정 중";
    }

    public OrdDto ordCreate(OrdDto dto) {
        // 1. 제품 ID를 바탕으로 제품 엔티티 조회
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + dto.getProductId()));

        // 2. 주문 엔티티 생성
        Ord ord = Ord.builder()
                .product(product)
                .totalPrice(product.getExPrice()) // 예시로, 상품 가격을 주문 가격으로 설정
                .status(Ord.Status.CONFIRMED)  // 주문 상태를 CONFIRMED로 설정
                .build();

        // 3. 주문 저장
        Ord savedOrd = ordRepository.save(ord);

        // 4. 저장된 주문 엔티티를 DTO로 변환하여 반환
        return OrdDto.fromEntity(savedOrd);
    }


    // readTossPayment
    public Object readTossPayment(Long id) {
        // 1. id를 가지고 주문정보를 조회한다.
        Ord order = ordRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
        // 2. 주문정보에 포함된 결제 정보키(paymentKey)를 바탕으로
        // Toss에 요청을 보내 결제 정보를 받는다.
        Object response = tossService.getPayment(order.getTossPaymentKey());
        log.info(response.toString());
        // 3. 해당 결제 정보를 반환한다.
        return response;
    }

    // readAll
    public List<OrdDto> readAll() {
        return ordRepository.findAll().stream()
                .map(OrdDto::fromEntity)
                .toList();
    }

    // readOne
    public OrdDto readOne (Long id) {
        return ordRepository.findById(id)
                .map(OrdDto::fromEntity)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
