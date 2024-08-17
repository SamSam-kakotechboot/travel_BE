USE ktb_samsam;

-- user 테이블 생성
DROP TABLE IF EXISTS user;
CREATE TABLE IF NOT EXISTS user (
    user_id     VARCHAR(255)    PRIMARY KEY                                             COMMENT '상품 아이디',
    password    VARCHAR(255)                    NOT NULL                                COMMENT '비밀번호',
    name        VARCHAR(255)                    NOT NULL                                COMMENT '이름',
    phone       VARCHAR(255)                    NOT NULL                                COMMENT '전화번호',
    role        VARCHAR(255)                    NOT NULL                                COMMENT '권한(ADMIN : 판매자, NORMAL : 일반 사용자, MASTER : 관리자)',
    regist_date DATETIME                        NOT NULL    DEFAULT CURRENT_TIMESTAMP   COMMENT '등록일'
) COMMENT '유저 정보를 저장하는 테이블';

-- ticket 테이블 생성
DROP TABLE IF EXISTS ticket;
CREATE TABLE IF NOT EXISTS ticket (
    ticket_id       VARCHAR(255)    PRIMARY KEY                                                                         COMMENT '상품 아이디',
    user_id         VARCHAR(255)                    NOT NULL                                                            COMMENT '상품 등록자',
    title           VARCHAR(255)                    NOT NULL                                                            COMMENT '제목',
    contents        TEXT                            NOT NULL                                                            COMMENT '내용',
    place           VARCHAR(255)                    NOT NULL                                                            COMMENT '장소',
    price           INT                             NOT NULL                                                            COMMENT '금액',
    start_date      DATE                            NOT NULL                                                            COMMENT '시작일',
    end_date        DATE                            NOT NULL                                                            COMMENT '종료일',
    delete_yn       VARCHAR(1)                      NOT NULL    DEFAULT 'N'                                             COMMENT '삭제여부',
    view_yn         VARCHAR(1)                      NOT NULL    DEFAULT 'N'                                             COMMENT '노출여부',
    regist_date     DATETIME                        NOT NULL    DEFAULT CURRENT_TIMESTAMP                               COMMENT '등록일',
    update_date     DATETIME                        NOT NULL    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP   COMMENT '수정일',
    FOREIGN KEY (user_id) REFERENCES user(user_id)
) COMMENT '상품 정보를 저장하는 테이블';


-- order 테이블 생성
DROP TABLE IF EXISTS orders;
CREATE TABLE IF NOT EXISTS orders (
    order_id        VARCHAR(255)    PRIMARY KEY                                             COMMENT '주문 아이디',
    user_id         VARCHAR(255)                    NOT NULL                                COMMENT '유저 아이디',
    ticket_id       VARCHAR(255)                    NOT NULL                                COMMENT '상품 아이디',
    order_date      DATETIME                        NOT NULL    DEFAULT CURRENT_TIMESTAMP   COMMENT '주문 일자',
    total_amount    INT                             NOT NULL                                COMMENT '주문 가격',
    quantity        INT                             NOT NULL                                COMMENT '주문 수량',
    status          VARCHAR(1)                      NOT NULL    DEFAULT 'P'                 COMMENT '주문 상태(P : 대기, C : 주문완료)',
    FOREIGN KEY (user_id) REFERENCES user(user_id),
    FOREIGN KEY (ticket_id) REFERENCES ticket(ticket_id)
) COMMENT '주문 정보를 저장하는 테이블';

-- cart 테이블 생성
DROP TABLE IF EXISTS cart;
CREATE TABLE IF NOT EXISTS cart (
    cart_id         VARCHAR(255)    PRIMARY KEY                                                                         COMMENT '장바구니 아이디',
    user_id         VARCHAR(255)                    NOT NULL                                                            COMMENT '유저 아이디',
    ticket_id       VARCHAR(255)                    NOT NULL                                                            COMMENT '상품 아이디',
    quantity        INT                             NOT NULL                                                            COMMENT '수량',
    delete_yn       VARCHAR(1)                      NOT NULL    DEFAULT 'N'                                             COMMENT '삭제여부',
    regist_date     DATETIME                        NOT NULL    DEFAULT CURRENT_TIMESTAMP                               COMMENT '등록일',
    update_date     DATETIME                        NOT NULL    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP   COMMENT '수정일',
    FOREIGN KEY (user_id)   REFERENCES user(user_id),
    FOREIGN KEY (ticket_id) REFERENCES ticket(ticket_id)
) COMMENT '장바구니 정보를 저장하는 테이블';

-- review 테이블 생성
DROP TABLE IF EXISTS review;
CREATE TABLE IF NOT EXISTS review (
    review_id       VARCHAR(255)    PRIMARY KEY                     NOT NULL                                    COMMENT '리뷰 아이디',
    user_id         VARCHAR(255)                                    NOT NULL                                    COMMENT '유저 아이디',
    order_id        VARCHAR(255)                                    NOT NULL                                    COMMENT '주문 아이디',
    ticket_id       VARCHAR(255)                                    NOT NULL                                    COMMENT '상품 아이디',
    comment         text                                            NOT NULL                                    COMMENT '후기',
    rating          FLOAT                                           NOT NULL                                    COMMENT '별점',
    regist_date     DATETIME                                        NOT NULL    DEFAULT CURRENT_TIMESTAMP       COMMENT '작성일',
    FOREIGN KEY (user_id)   REFERENCES user(user_id),
    FOREIGN KEY (ticket_id) REFERENCES ticket(ticket_id)
) COMMENT '리뷰 정보를 저장하는 테이블';

