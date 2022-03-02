INSERT INTO users (first_name, last_name, passport, email, address, birth_date)
VALUES ('vlad', 'kaliaha', '1111', 'email1', 'address1', '2005-06-06'),
 ('andrei', 'yurueu', '2222', 'email2', 'address2', '2001-06-06'),
  ('yaroslav', 'vasilevski', '3333', 'email3', 'address3', '1998-06-06'),
   ('anastasiya', 'yurkova', '4444', 'email4', 'address4', '1999-06-06'),
    ( 'alexander', 'kupriyanenko', '5555', 'email5', 'address5', '1996-06-06');

INSERT INTO roles (role_name)
VALUES ('admin'),
        ('user');

INSERT INTO user_role_links (user_id, role_id)
VALUES (1, 1),
        (2, 2),
         (3, 2),
          (4, 2),
           (5, 2);

INSERT INTO orders (order_status, start_date, price, end_date, user_id)
VALUES  ('NEW', '1998-06-06', '243', '1998-06-06','1'),
 ('NEW', '1998-06-06', '21', '1998-06-06', '1'),
  ('NEW', '1998-06-06', '253', '1998-06-06', '1'),
   ('NEW', '1998-06-06', '273', '1998-06-06', '3'),
    ('NEW', '1998-06-06', '238', '1998-06-06', '4');

INSERT INTO books (title, pages, image_path)
VALUES  ('War and peace', '1365', 'image path'),
 ('The Master and Margarita', '638', 'image path'),
  ('Idiot', '496', 'image path'),
   ('The old man and the sea', '153', 'image path'),
    ('Eugene Onegin', '462', 'image path');

INSERT INTO book_copies (book_copy_status, registration_date, price, price_per_day, book_id)
VALUES  ('AVAILABLE', '2019-03-01', '1365', '150', 1),
 ('AVAILABLE', '2020-06-01', '1638', '210', 2),
  ('AVAILABLE', '2021-08-04', '2496', '225', 2),
   ('AVAILABLE', '2017-10-10', '937', '128', 5),
    ('AVAILABLE', '2020-06-02', '1007', '311', 3);

INSERT INTO order_book_copy_links (order_id, book_copy_id)
VALUES  (1, 3),
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
VALUES  (1, 3),
         (2, 1),
          (3, 1),
           (4, 4),
            (5, 2);

INSERT INTO authors (first_name, last_name, birth_date, image_path)
VALUES  ('Lev', 'Tolstoy', '1879-04-04', 'image path'),
 ('Ernest', 'Hemingway', '1903-07-07', 'image path'),
  ('Mikhail', 'Bulgakov', '1885-10-10', 'image path'),
   ('Alexander', 'Pushkin', '1852-02-02', 'image path'),
    ('Fedor', 'Dostoevsky', '1845-01-01', 'image path');

INSERT INTO book_author_links (book_id, author_id)
VALUES  (1, 1),
         (2, 3),
          (3, 5),
           (4, 2),
            (5, 4);

INSERT INTO book_damage (image_path, damage_description, user_id, order_id, book_copy_id)
VALUES  ('image path', 'damage1', '1', '1', '3'),
 ('image path', 'damage2', '1', '1', '2'),
  ('image path', 'damage3', '3', '4', '1'),
   ('image path', 'damage4', '4', '5', '5');