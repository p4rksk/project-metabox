-- 관리자 생성
insert into admin_tb(login_id, password)
values ('metabox', 'metabox1234');

-- 극장 생성(서울 5개)
insert into theater_tb(name, img_filename, address, number, login_id, password, url, parking_info)
values ('METABOX 강남', 'theater1.png', '서울특별시 강남구 역삼동 814-6 스타플렉스', '1988-3652', 'metabox1', 'metabox1234',
        'https://map.naver.com/p?title=CGV%EA%B0%95%EB%82%A8&lng=127.026325&lat=37.501528&zoom=15&type=0&c=15.00,0,0,0,dh',
        ''),
       ('METABOX 강변', 'theater2.png', '서울특별시 광진구 구의동 546-4 테크노마트 10층', '1988-3652', 'metabox2', 'metabox1234',
        'https://map.naver.com/p?title=CGV%EA%B0%95%EB%B3%80&lng=127.0957213&lat=37.5353388&zoom=15&type=0&c=15.00,0,0,0,dh',
        ''),
       ('METABOX 건대입구', 'theater3.png', '서울시 광진구 자양동 9-4 몰오브케이 3층', '1988-3652', 'metabox3', 'metabox1234',
        'https://map.naver.com/p?title=CGV%EA%B1%B4%EB%8C%80%EC%9E%85%EA%B5%AC&lng=127.066794&lat=37.540038&zoom=15&type=0&c=15.00,0,0,0,dh',
        ''),
       ('METABOX 구로', 'theater4.png', '서울특별시 구로구 구로5동 573번지 NC신구로점 6층', '1988-3652', 'metabox4', 'metabox1234',
        'https://map.naver.com/p?title=CGV%EA%B5%AC%EB%A1%9C&lng=126.8831273&lat=37.5008982&zoom=15&type=0&c=15.00,0,0,0,dh',
        ''),
       ('METABOX 대학로', 'theater5.png', '서울특별시 종로구 명륜2가 41-9', '1988-3652', 'metabox5', 'metabox1234',
        'https://map.naver.com/p?title=CGV%EB%8C%80%ED%95%99%EB%A1%9C&lng=127.0000119&lat=37.5833895&zoom=15&type=0&c=15.00,0,0,0,dh',
        '');
-- 극장 생성(경기 5개)
insert into theater_tb(name, img_filename, address, number, login_id, password, url, parking_info)
values ('METABOX 경기광주', 'theater1.png', '경기도 광주시 역동16-1 2층', '1988-3652', 'metabox6', 'metabox1234',
        'https://map.naver.com/p?title=CGV%EA%B2%BD%EA%B8%B0%EA%B4%91%EC%A3%BC&lng=127.258553&lat=37.407655&zoom=15&type=0&c=15.00,0,0,0,dh',
        ''),
       ('METABOX 고양백석', 'theater2.png', '경기도 고양시 일산동구 백석동 1242 고양종합터미널 5~7F', '1988-3652', 'metabox7', 'metabox1234',
        'https://map.naver.com/p/?title=CGV%EA%B3%A0%EC%96%91%EB%B0%B1%EC%84%9D&lng=126.78966422212565&lat=37.64307412660345&zoom=15&type=0',
        ''),
       ('METABOX 고양행신', 'theater3.png', '경기도 고양시 덕양구 행신동 762-2,3 로터스플레이스 5F', '1988-3652', 'metabox8', 'metabox1234',
        'https://map.naver.com/p?title=CGV%EA%B3%A0%EC%96%91%ED%96%89%EC%8B%A0&lng=126.83448836034631&lat=37.613572928517115&zoom=15&type=0&c=15.00,0,0,0,dh',
        ''),
       ('METABOX 광교', 'theater4.png', '경기도 수원시 영통구 하동 1017-2 갤러리아백화점 10층', '1988-3652', 'metabox9', 'metabox1234',
        'https://map.naver.com/p?title=CGV%EA%B4%91%EA%B5%90&lng=127.0571731324729&lat=37.285289431827294&zoom=15&type=0&c=15.00,0,0,0,dh',
        ''),
       ('METABOX 광교상현', 'theater5.png', '경기도 용인시 수지구 상현동 1116-4 W스퀘어 2층', '1988-3652', 'metabox10', 'metabox1234',
        'https://map.naver.com/p/?title=CGV%EA%B4%91%EA%B5%90%EC%83%81%ED%98%84&lng=127.06817999999998&lat=37.2973776&zoom=15&type=0',
        '');

-- 영화 생성
-- insert into movie_tb(title, date, img_filename)
-- values ('', '', '');
