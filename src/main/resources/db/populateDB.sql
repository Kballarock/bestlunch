DELETE FROM user_roles WHERE user_id;
DELETE FROM users WHERE id;
DELETE FROM menu WHERE restaurant_id;
DELETE FROM votes WHERE id;
DELETE FROM restaurant WHERE id;

ALTER TABLE users AUTO_INCREMENT = 100000;
ALTER TABLE restaurant AUTO_INCREMENT = 100000;
ALTER TABLE menu AUTO_INCREMENT = 100000;
ALTER TABLE votes AUTO_INCREMENT = 1000;

INSERT INTO users (name, email, password, registered) VALUES
('Admin', 'admin@admin.com', '$2a$10$.ep1D0bvqWFKjfBGAMvGo.pO4deV.oC0y1fAM37PPWSaxy.dCQ4FK','2019-12-16'),
('User', 'user@user.com', '$2a$10$twPQhTPltGPZ4lka./SUsuBbkmobOeZBFJ2CepmN5kSgJFSC0tuke','2019-12-16');

INSERT INTO user_roles (role, user_id) VALUES
('ROLE_ADMIN', 100000),
('ROLE_USER', 100001);

INSERT INTO restaurant (name, description, address, added) VALUES
('Burger King', 'Быстрое питание', 'г. Минск. ул. Кирова, 10', '2019-12-16 01:00'),
('Renaissance', 'Итальянская кухня', 'г. Минск, ул. Сурганова, 5', '2019-12-16 02:00');

INSERT INTO menu (name, price, menu_date, restaurant_id) VALUES
       ('Гамбургер', 4.39, '2019-12-16', 100000),
       ('Кока Кола', 1.2, '2019-12-16', 100000),
       ('Хот дог', 2.0, '2019-12-17', 100000),
       ('Картофель фри', 3.45, '2019-12-17', 100000),
       ('Чизбурер', 5.21, '2019-12-18', 100000),
       ('Воппер', 2.67, '2019-12-18', 100000),
       ('Паста', 10.60, '2019-12-16',  100001),
       ('Сибас', 16.63, '2019-12-16', 100001),
       ('Суп-пюре', 7.35, '2019-12-17', 100001),
       ('Паста карбонара', 15.39, '2019-12-17', 100001),
       ('Пицца', 16.23, '2019-12-18', 100001),
       ('Брускетта с помидорами', 7.35, '2019-12-18', 100001),
       ('Фанта', 2.35, CURDATE(), 100000),
       ('Вино Сопрано', 14.35, CURDATE(), 100001);


INSERT INTO votes (id, user_id, restaurant_id, voting_date) VALUES
       (1000, 100000, 100000,'2019-12-16'),
       (1001, 100001, 100001,'2019-12-16'),
       (1002, 100000, 100000,'2019-12-17'),
       (1003, 100001, 100000,'2019-12-17');