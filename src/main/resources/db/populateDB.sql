DELETE
FROM meals;

DELETE
FROM user_roles;

DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001);

INSERT INTO meals (datetime, description, calories, user_id)
VALUES ('2020-01-10T10:00:00', 'Завтрак', 400, 100000);

INSERT INTO meals (datetime, description, calories, user_id)
VALUES ('2020-01-10T13:00:00', 'Обед', 500, 100000);

INSERT INTO meals (datetime, description, calories, user_id)
VALUES ('2020-01-12T19:00:00', 'Ужин', 600, 100000);

INSERT INTO meals (datetime, description, calories, user_id)
VALUES ('2020-01-13T00:00:00', 'Поздний перекус', 300, 100000);

INSERT INTO meals (datetime, description, calories, user_id)
VALUES ('2020-01-11T11:00:00', 'Завтрак', 450, 100001);

INSERT INTO meals (datetime, description, calories, user_id)
VALUES ('2020-01-11T14:00:00', 'Обед', 550, 100001);

INSERT INTO meals (datetime, description, calories, user_id)
VALUES ('2020-01-12T19:00:00', 'Ужин', 650, 100001);