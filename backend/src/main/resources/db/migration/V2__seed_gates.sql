INSERT INTO gates (code, name)
VALUES ('CAMLICA', 'Çamlıca Gişesi'),
       ('FSM', 'FSM Köprüsü'),
       ('15_TEMMUZ', '15 Temmuz Şehitler Köprüsü'),
       ('YSS', 'Yavuz Sultan Selim Köprüsü'),
       ('OSMANGAZI', 'Osmangazi Köprüsü'),
       ('AVRASYA', 'Avrasya Tüneli')
ON CONFLICT (name) DO UPDATE
SET code = EXCLUDED.code;
