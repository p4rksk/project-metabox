-- 관리자 생성
insert into admin_tb(login_id, password)
values ('metabox', 'metabox1234');

-- 극장 생성
-- TODO : url, parkingInfo 추가 해야함
-- insert into theater_tb(name, img_filename, address, number, loginId, password)
-- values ('METABOX 강남', 'theater.png', '', '02-2222-3333', 'metabox1', 'metabox1234');

-- 영화 생성
insert into movie_tb(title, date, img_filename)
values ('스파이더맨 : 홈커밍', '2024-06-22', 'movie1.png');
