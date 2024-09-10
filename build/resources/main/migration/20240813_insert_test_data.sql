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
        'user1',
        '1234',
        '임꺽정',
        '010-0000-0000',
        'NORMAL'
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
        'TICKET-1A2B3C4D-5E6F-7G8H-9I0J-KLMNOPQRSTU1',
        'test',
        'Disneyland',
        '세상에서 가장 행복한 곳, 마법 같은 놀이기구와 캐릭터들이 가득한 곳입니다.',
        'Anaheim, California, USA',
        50000,
        '2024-09-01',
        '2024-09-10'
    ),
    (
        'TICKET-2B3C4D5E-6F7G-8H9I-0J1K-LMNOPQRSTU2',
        'test',
        'Universal Studios',
        '영화를 새로운 방식으로 체험할 수 있는 짜릿한 놀이기구와 쇼들이 있습니다.',
        'Orlando, Florida, USA',
        75000,
        '2024-08-15',
        '2024-08-25'
    ),
    (
        'TICKET-3C4D5E6F-7G8H-9I0J-1K2L-MNOPQRSTU3',
        'test',
        'Everland',
        '다양한 신나는 놀이기구가 있는 한국 최대의 테마파크입니다.',
        'Yongin, Gyeonggi-do, South Korea',
        60000,
        '2024-07-20',
        '2024-07-30'
    ),
    (
        'TICKET-4D5E6F7G-8H9I-0J1K-2L3M-NOPQRSTU4',
        'test',
        'Europa Park',
        '유럽 테마의 다양한 구역으로 유명한 독일의 대규모 테마파크입니다.',
        'Rust, Baden-Württemberg, Germany',
        80000,
        '2024-06-10',
        '2024-06-20'
    ),
    (
        'TICKET-5E6F7G8H-9I0J-1K2L-3M4N-OPQRSTU5',
        'test',
        'Lotte World',
        '실내 스케이트장과 퍼레이드로 유명한 한국의 실내 테마파크입니다.',
        'Seoul, South Korea',
        45000,
        '2024-10-05',
        '2024-10-15'
    ),
    (
        'TICKET-6F7G8H9I-0J1K-2L3M-4N5O-PQRSTU6',
        'test',
        'Six Flags Magic Mountain',
        '세계에서 가장 많은 롤러코스터를 자랑하는 스릴 넘치는 테마파크입니다.',
        'Valencia, California, USA',
        65000,
        '2024-07-01',
        '2024-07-12'
    ),
    (
        'TICKET-7G8H9I0J-1K2L-3M4N-5O6P-QRSTU7',
        'test',
        'PortAventura',
        '문화와 아드레날린을 동시에 느낄 수 있는 스페인의 인기 테마파크입니다.',
        'Salou, Catalonia, Spain',
        70000,
        '2024-05-20',
        '2024-05-30'
    ),
    (
        'TICKET-8H9I0J1K-2L3M-4N5O-6P7Q-RSTU8',
        'test',
        'Tokyo Disneyland',
        '일본에서 디즈니의 마법을 체험할 수 있는 멋진 테마파크입니다.',
        'Tokyo, Japan',
        90000,
        '2024-04-10',
        '2024-04-20'
    ),
    (
        'TICKET-9I0J1K2L-3M4N-5O6P-7Q8R-STU9',
        'test',
        'Gardaland',
        '이탈리아에서 가장 큰 테마파크로, 가족 여행에 최적입니다.',
        'Castelnuovo del Garda, Italy',
        55000,
        '2024-06-15',
        '2024-06-25'
    ),
    (
        'TICKET-0J1K2L3M-4N5O-6P7Q-8R9S-TUA0',
        'test',
        'Busch Gardens',
        '동물과 테마파크가 결합된, 자연과의 조화로운 어트랙션을 제공합니다.',
        'Tampa, Florida, USA',
        60000,
        '2024-03-01',
        '2024-03-10'
    ),
    (
        'TICKET-1K2L3M4N-5O6P-7Q8R-9S0T-UBV1',
        'test',
        'Ocean Park',
        '홍콩의 바다 생물과 놀이기구가 결합된 테마파크입니다.',
        'Hong Kong, China',
        50000,
        '2024-08-10',
        '2024-08-20'
    ),
    (
        'TICKET-2L3M4N5O-6P7Q-8R9S-0T1U-VCW2',
        'test',
        'SeaWorld',
        '해양 동물과 함께하는 특별한 경험을 제공하는 테마파크입니다.',
        'San Diego, California, USA',
        40000,
        '2024-09-15',
        '2024-09-25'
    ),
    (
        'TICKET-3M4N5O6P-7Q8R-9S0T-1U2V-WDX3',
        'test',
        'Legoland',
        '레고 팬이라면 누구나 즐길 수 있는, 레고 테마의 놀이공원입니다.',
        'Billund, Denmark',
        55000,
        '2024-07-05',
        '2024-07-15'
    ),
    (
        'TICKET-4N5O6P7Q-8R9S-0T1U-2V3W-XEY4',
        'test',
        'Fuji-Q Highland',
        '일본의 멋진 후지산 경치를 배경으로 한 스릴 넘치는 테마파크입니다.',
        'Yamanashi, Japan',
        48000,
        '2024-10-01',
        '2024-10-10'
    );