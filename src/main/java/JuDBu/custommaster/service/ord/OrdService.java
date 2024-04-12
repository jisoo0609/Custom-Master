package JuDBu.custommaster.service.ord;

import JuDBu.custommaster.dto.ord.OrdDto;
import JuDBu.custommaster.dto.payment.PaymentConfirmDto;
import JuDBu.custommaster.entity.ord.Ord;
import JuDBu.custommaster.entity.product.Product;
import JuDBu.custommaster.repo.ord.OrdRepo;
import JuDBu.custommaster.repo.product.ProductRepo;
import JuDBu.custommaster.service.payment.TossHttpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrdService {
    private final TossHttpService tossService;
    private final ProductRepo productRepository;
    private final OrdRepo ordRepository;

    public Object confirmPayment(PaymentConfirmDto dto) {
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

        // 3. product 엔티티를 바탕으로 ord를 만들자
        return OrdDto.fromEntity(ordRepository.save(Ord.builder()
                .product(product)
                .tossPaymentKey(dto.getPaymentKey())
                .tossOrderId(dto.getOrderId())
                .totalPrice(dto.getAmount())
                .status(Ord.Status.CONFIRMED)
                .build()));
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
