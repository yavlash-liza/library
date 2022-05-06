INSERT INTO users (first_name, last_name, passport, email, address, password, birth_date)
VALUES ('vlad', 'kaliaha', '1111', 'email1@mail.com', 'address1', '$2a$12$puiOOWl2E7oexBqiw3WnL.dbvpef2.tmrhBtw6116hS.uP61poDk2', '2005-06-06'),
       ('andrei', 'yurueu', '2222', 'email2@mail.com', 'address2', '$2a$12$puiOOWl2E7oexBqiw3WnL.dbvpef2.tmrhBtw6116hS.uP61poDk2', '2001-06-06'),
       ('yaroslav', 'vasilevski', '3333', 'email3@mail.com', 'address3', '$2a$12$puiOOWl2E7oexBqiw3WnL.dbvpef2.tmrhBtw6116hS.uP61poDk2', '1998-06-06'),
       ('anastasiya', 'yurkova', '4444', 'email4@mail.com', 'address4', '$2a$12$puiOOWl2E7oexBqiw3WnL.dbvpef2.tmrhBtw6116hS.uP61poDk2', '1999-06-06'),
       ('alexander', 'kupriyanenko', '5555', 'email5@mail.com', 'address5', '$2a$12$puiOOWl2E7oexBqiw3WnL.dbvpef2.tmrhBtw6116hS.uP61poDk2', '1996-06-06');

INSERT INTO roles (role_name)
VALUES ('admin'),
       ('user');

INSERT INTO role_authority (id, authority_name)
VALUES (1, 'ROLE_READ'),

       (2, 'USER_READ'),
       (3, 'USER_DELETE'),
       (4, 'USER_WRITE'),

       (5, 'ORDER_READ'),
       (6, 'ORDER_DELETE'),
       (7, 'ORDER_WRITE'),

       (8, 'GENRE_READ'),
       (9, 'GENRE_DELETE'),
       (10, 'GENRE_WRITE'),

       (11, 'BOOK_DAMAGE_READ'),
       (12, 'BOOK_DAMAGE_DELETE'),
       (13, 'BOOK_DAMAGE_WRITE'),

       (14, 'BOOK_READ'),
       (15, 'BOOK_DELETE'),
       (16, 'BOOK_WRITE'),

       (17, 'AUTHOR_READ'),
       (18, 'AUTHOR_DELETE'),
       (19, 'AUTHOR_WRITE');

INSERT INTO role_authority_links (authority_id, role_id)
VALUES (1, 1),

       (2, 1),
       (2, 2),
       (3, 1),
       (4, 1),
       (4, 2),

       (5, 1),
       (5, 2),
       (6, 1),
       (6, 2),
       (7, 1),
       (7, 2),

       (8, 1),
       (8, 2),
       (9, 1),
       (10, 1),

       (11, 1),
       (11, 2),
       (12, 1),
       (13, 1),

       (14, 1),
       (14, 2),
       (15, 1),
       (16, 1),

       (17, 1),
       (17, 2),
       (18, 1),
       (19, 1);

INSERT INTO user_role_links (user_id, role_id)
VALUES (1, 1),
       (2, 2),
       (3, 2),
       (4, 2),
       (5, 2);

INSERT INTO orders (order_status, start_date, price, end_date, user_id)
VALUES ('NEW', '1998-06-06', '243', '1998-06-06', '1'),
       ('NEW', '1998-06-06', '21', '1998-06-06', '1'),
       ('NEW', '1998-06-06', '253', '1998-06-06', '1'),
       ('NEW', '1998-06-06', '273', '1998-06-06', '3'),
       ('NEW', '1998-06-06', '238', '1998-06-06', '4');

INSERT INTO books (title, pages, image_path)
VALUES ('War and peace', '1365', 'image path'),
       ('The Master and Margarita', '638', 'image path'),
       ('Idiot', '496', 'image path'),
       ('The old man and the sea', '153', 'image path'),
       ('Eugene Onegin', '462', 'image path');

INSERT INTO book_copies (book_copy_status, registration_date, image_path, price_per_day, book_id)
VALUES ('AVAILABLE', '2019-03-01', 'image path', '150', 1),
       ('AVAILABLE', '2020-06-01', 'image path', '210', 2),
       ('AVAILABLE', '2021-08-04', 'image path', '225', 2),
       ('AVAILABLE', '2017-10-10', 'image path', '128', 5),
       ('AVAILABLE', '2020-06-02', 'image path', '311', 3);

INSERT INTO order_book_copy_links (order_id, book_copy_id)
VALUES (1, 3),
       (1, 2),
       (3, 4),
       (4, 1),
       (5, 5);

INSERT INTO genres (genre_name)
VALUES ('NOVEL'),
       ('ADVENTURE'),
       ('COMEDY'),
       ('CRIME'),
       ('HORROR'),
       ('SCIENCE FICTION'),
       ('ROMANCE');

INSERT INTO book_genre_links (book_id, genre_id)
VALUES (1, 3),
       (2, 1),
       (3, 1),
       (4, 4),
       (5, 2);

INSERT INTO authors (first_name, last_name, birth_date, image_path)
VALUES ('Lev', 'Tolstoy', '1879-04-04', 'image path'),
       ('Ernest', 'Hemingway', '1903-07-07', 'image path'),
       ('Mikhail', 'Bulgakov', '1885-10-10', 'image path'),
       ('Alexander', 'Pushkin', '1852-02-02', 'image path'),
       ('Fedor', 'Dostoevsky', '1845-01-01', 'image path');

INSERT INTO book_author_links (book_id, author_id)
VALUES (1, 1),
       (2, 3),
       (3, 5),
       (4, 2),
       (5, 4);

INSERT INTO book_damage (image_path, damage_description, user_id, order_id, book_copy_id)
VALUES ('image path', 'damage1', '1', '1', '3'),
       ('image path', 'damage2', '1', '1', '2'),
       ('image path', 'damage3', '3', '4', '1'),
       ('image path', 'damage4', '4', '5', '5');