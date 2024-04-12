package JuDBu.custommaster.entity.shop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;

    // 상정 리스트 조회
    public Page<ShopReadDto> readPage(Pageable pageable) {
        return shopRepository.findAll(pageable)
                .map(ShopReadDto::fromEntity);
    }

    // 상점 상세 조회
    public ShopReadDto readOne(Long shopId) {
        return ShopReadDto.fromEntity(shopRepository.findById(shopId).orElseThrow());
    }

    // 상점 생성
    public Long createShop(ShopCreateDto createDto) {
        Shop shop = Shop.createShop(createDto.getName(), createDto.getAddress(), createDto.getPhoneNumber());
        Shop savedShop = shopRepository.save(shop);
        return savedShop.getId();
    }

    // 상점 정보 수정
    public Long updateShop(Long shopId, ShopUpdateDto updateDto) {
        Shop findShop = shopRepository.findById(shopId).orElseThrow();
        findShop.updateShop(updateDto.getName(), updateDto.getAddress(), updateDto.getPhoneNumber());
        return findShop.getId();
    }

    // 상점 삭제
    public void deleteShop(Long shopId) {
        shopRepository.deleteById(shopId);
    }
}
