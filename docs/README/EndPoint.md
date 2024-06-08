# Custom Master - End Point
## Account

### Register

- 일반회원가입: `get` `/account/register`

### Login

- 로그인: `get` `/account/register`
- 유저 정보수정: `get` `/account/update`
- 로그아웃: `get` `/account/logout`
- 메일인증: `get` `/account/mail-auth`

### Profile

- 프로필 페이지: `GET` `/profile`
- 사용자가 요청한 주문 전체 확인: `GET` `/profile/ord-list`
- 사용자가 요청한 주문 상세 확인: `GET` `/profile/read/{ordId}`

### Profile

- 프로필 페이지: `GET` `/profile`
- 사용자가 요청한 주문 전체 확인: `GET` `/profile/ord-list`
- 사용자가 요청한 주문 상세 확인: `GET` `/profile/read/{ordId}`

## Shop

- 상점 개설 Form : `GET` `/shop/create`
- 상점 개설 : `POST` `/shop/create`
- 상점 리스트 조회 : `GET` `/shop`
- 상점 상세 조회 : `GET` `/shop/{shopId}`
- 상점 수정 Form : `GET` `/shop/update`
- 상점 수정 : `PUT` `/shop/{shopId}`
- 상점 삭제 : `DELETE` `/shop/{shopId}`

## Order

### Order-Request

- 주문 요청 From : `GET`  `/{shopId}/{productId}/request`
- 주문 요청 : `POST` `/{shopId}/{productId}/request`

### Order-accept

- 상점 주문 리스트 확인: `GET` `/order-accept/{shopId}/read-all`
- 상점 주문 상세보기: `GET` `/order-accept/{shopId}/read/{ordId}`
- 주문 요청 승락: `POST` `/order-accpet/{shopId}/accept/{ordId}`
- 주문 요청 거절: `POST` `/order-accept/{shopId}/delete/{ordId}`

## Pay

- 임시 홈 화면: `GET` `/pay`
- 임시 주문 생성 : `POST` `/toss/ord-create`
- 임시 주문 전체 읽기 : `GET` `/toss/ord-readAll`
- 임시 주문 한개 읽기: `GET` `/toss/ord-detail/{ordId}`
- 임시 주문 상품 정보 조회: `GET` `/toss/product-detail/{productId}`
- 결제 : `POST` `/toss/confirm-payment/{ordId}`

## Review

- 리뷰 등록하기: `POST` `/review/{shopId}/create`
- 리뷰 등록 페이지: `GET` `/review/{shopId}/create-view`
- 리뷰 수정하기: `POST` `/review/{shopId}/update/{reviewId}`
- 리뷰 수정 페이지: `POST` `/review/{shopId}/update-view/{reviewId}`
- 리뷰 전체 읽기: `GET` `/review/{shopId}/read-all`
- 리뷰 상세보기: `GET` `/review/{shopId}/read/{reviewId}`
- 등록된 리뷰 삭제: `POST` `/review/{shopId}/delete/{reviewId}`