package JuDBu.custommaster.domain.service;

import JuDBu.custommaster.auth.facade.AuthenticationFacade;
import JuDBu.custommaster.domain.dto.shop.ShopCreateDto;
import JuDBu.custommaster.domain.dto.shop.ShopDto;
import JuDBu.custommaster.domain.dto.shop.ShopReadDto;
import JuDBu.custommaster.domain.dto.shop.ShopUpdateDto;
import JuDBu.custommaster.domain.entity.Shop;
import JuDBu.custommaster.domain.entity.account.Account;
import JuDBu.custommaster.domain.entity.account.Authority;
import JuDBu.custommaster.domain.repo.ShopRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
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
    @Transactional
    public Long createShop(ShopCreateDto createDto) {

        // TODO: 인증된 사용자 정보 필요
        //// 인증된 Account가 BusinessAccount 인지
        //Account account = authenticationFacade.getAccount();
        //log.info("account={}", account);
        //if (!account.getAuthority().equals(Authority.ROLE_BUSINESS_USER)) {
        //    throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        //}
        //
        //// 인증된 Account의 상점이 없는지
        //if (shopRepository.findByAccount(account) != null) {
        //    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        //}

        Shop shop = Shop.createShop(null, createDto.getName(), createDto.getAddress(), createDto.getPhoneNumber());
        log.info("createShop={}", shop);

        Shop savedShop = shopRepository.save(shop);
        log.info("savedShop={}", savedShop);

        return savedShop.getId();
    }

    // 상점 정보 수정
    @Transactional
    public Long updateShop(Long shopId, ShopUpdateDto updateDto) {
        // TODO: 인증된 사용자 정보 필요
        //Account account = authenticationFacade.getAccount();
        Shop findShop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // TODO: 인증된 사용자 정보 필요
        //hasShopAccount(findShop, account);

        findShop.updateShop(updateDto.getName(), updateDto.getAddress(), updateDto.getPhoneNumber());

        return findShop.getId();
    }

    // 상점 삭제
    @Transactional
    public void deleteShop(Long shopId) {
        Shop findShop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // TODO: 인증된 사용자 정보 필요
        //Account account = authenticationFacade.getAccount();

        // TODO: 인증된 사용자 정보 필요
        //hasShopAccount(findShop, account);

        shopRepository.deleteById(shopId);
    }

    public ShopDto findShop(Long shopId) {
        Shop findShop = shopRepository.findById(shopId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // TODO: 인증된 사용자 정보 필요
        //hasShopAccount(findShop, account);

        return ShopDto.fromEntity(findShop);
    }

    private static void hasShopAccount(Shop findShop, Account account) {
        // 인증된 Account의 상점이 맞는지
        if (findShop.getAccount().equals(account)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }
}
