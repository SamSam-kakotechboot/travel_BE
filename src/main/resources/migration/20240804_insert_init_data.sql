USE ktb_samsam;

INSERT INTO user
(
    user_id,
    password,
    name,
    phone,
    role
)
VALUES
    (
        'test',
        'test',
        'test',
        '010-0000-0000',
        'MASTER'
    );

INSERT INTO ticket
(
    ticket_id,
    user_id,
    title,
    contents,
    place,
    price,
    start_date,
    end_date
)
VALUES
    (
        'TICKET-69DC4A0B-4F01-4D9F-BA65-1DC9FCA814B1',
        'test',
        '테스트 제목',
        '테스트 내용',
        '테스트 장소',
        9000000,
        '2024-08-03',
        '2024-08-13'
    );



INSERT INTO cart
(
    cart_id,
    user_id,
    ticket_id,
    quantity
)
VALUES
    (
        'CART-69DC4A0B-4F01-4D9F-BA65-1DC9FCA814B1',
        'test',
        'TICKET-69DC4A0B-4F01-4D9F-BA65-1DC9FCA814B1',
        1
    );

INSERT INTO orders
(
    order_id,
    user_id,
    ticket_id,
    total_amount,
    quantity,
    status
)
VALUES
    (
        'ORDER-69DC4A0B-4F01-4D9F-BA65-1DC9FCA814B1',
        'test',
        'TICKET-69DC4A0B-4F01-4D9F-BA65-1DC9FCA814B1',
        9000000,
        1,
        'P'
    );

INSERT INTO review
(
    review_id,
    user_id,
    order_id,
    ticket_id,
    comment,
    rating
)
VALUES
    (
        'REVIEW-69DC4G0B-4F01-4D9F-BA65-1DC9FCA814B1',
        'test',
        'ORDER-69DC4A0B-4F01-4D9F-BA65-1DC9FCA814B1',
        'TICKET-69DC4A0B-4F01-4D9F-BA65-1DC9FCA814B1',
        '좋아요! 재밌어요!',
        4.5
    );