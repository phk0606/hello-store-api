INSERT INTO USER (USER_NO, USERNAME, PASSWORD, EMAIL, ACTIVATED, NAME, PHONE_NUMBER, ADDRESS)
VALUES (1, 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'phk0606@naver.com', 1, '홍길동', '01094591234', '경기도 수원시 장안구 천천동 110-12 101호');

INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_USER');
INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_ADMIN');

INSERT INTO USER_AUTHORITY (USER_NO, AUTHORITY_NAME) values (1, 'ROLE_USER');
INSERT INTO USER_AUTHORITY (USER_NO, AUTHORITY_NAME) values (1, 'ROLE_ADMIN');