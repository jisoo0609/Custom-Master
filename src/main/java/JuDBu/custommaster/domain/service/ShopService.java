package JuDBu.custommaster.domain.service;

import JuDBu.custommaster.domain.dto.shop.ShopCreateDto;
import JuDBu.custommaster.domain.entity.Shop;
import JuDBu.custommaster.domain.entity.account.Account;
import JuDBu.custommaster.domain.entity.account.Authority;
import JuDBu.custommaster.domain.repo.ShopRepository;
import JuDBu.custommaster.domain.dto.shop.ShopReadDto;
import JuDBu.custommaster.domain.dto.shop.ShopUpdateDto;
import JuDBu.custommaster.auth.facade.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;
    private final AuthenticationFacade authenticationFacade;

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

        // 인증된 Account가 BusinessAccount 인지
        Account account = authenticationFacade.getAccount();
        if (!account.getAuthority().equals(Authority.ROLE_BUSINESS_USER)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        // 인증된 Account의 상점이 없는지
        if (shopRepository.findByAccount(account) != null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        Shop shop = Shop.createShop(account, createDto.getName(), createDto.getAddress(), createDto.getPhoneNumber());
        Shop savedShop = shopRepository.save(shop);
        return savedShop.getId();
    }

    // 상점 정보 수정
    public Long updateShop(Long shopId, ShopUpdateDto updateDto) {
        Account account = authenticationFacade.getAccount();
        Shop findShop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        hasShopAccount(findShop, account);

        findShop.updateShop(updateDto.getName(), updateDto.getAddress(), updateDto.getPhoneNumber());
        return findShop.getId();
    }

    // 상점 삭제
    public void deleteShop(Long shopId) {
        Shop findShop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Account account = authenticationFacade.getAccount();

        hasShopAccount(findShop, account);

        shopRepository.deleteById(shopId);
    }

    private static void hasShopAccount(Shop findShop, Account account) {
        // 인증된 Account의 상점이 맞는지
        if (findShop.getAccount().equals(account)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
