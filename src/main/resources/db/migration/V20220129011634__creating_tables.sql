CREATE TABLE IF NOT EXISTS users (
  id BIGINT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(64) NOT NULL,
  last_name VARCHAR(64) NOT NULL,
  passport VARCHAR(128) NOT NULL,
  email VARCHAR(128) NOT NULL,
  address VARCHAR(128) NOT NULL,
  birth_date DATETIME NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT login_unique UNIQUE (passport),
  CONSTRAINT email_unique UNIQUE (email)
 );

CREATE TABLE IF NOT EXISTS roles (
  id BIGINT NOT NULL AUTO_INCREMENT,
  role_name VARCHAR(64) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT role_name_unique UNIQUE (role_name)
);

CREATE TABLE IF NOT EXISTS user_role_links (
  user_id BIGINT NOT NULL,
  role_id BIGINT NOT NULL,
  CONSTRAINT user_role_link_user_fk
    FOREIGN KEY (user_id)
    REFERENCES users (id),
  CONSTRAINT user_role_link_role_fk
    FOREIGN KEY (role_id)
    REFERENCES roles (id)
);

CREATE TABLE IF NOT EXISTS orders (
  id BIGINT NOT NULL AUTO_INCREMENT,
  order_status ENUM ('NEW', 'ACCEPTED', 'COMPLETED') NOT NULL,
  start_date DATETIME NOT NULL,
  price INT NOT NULL,
  user_id BIGINT NOT NULL,
  end_date DATETIME NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT user_order_fk
    FOREIGN KEY (user_id)
    REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS books (
  id BIGINT NOT NULL AUTO_INCREMENT,
  title VARCHAR(128) NOT NULL,
  pages INT NOT NULL,
  image_path VARCHAR(512) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS book_copies (
  id BIGINT NOT NULL AUTO_INCREMENT,
  book_copy_status ENUM ('ORDERED', 'AVAILABLE') NOT NULL,
  registration_date DATE NOT NULL,
  price INT NOT NULL,
  price_per_day INT NOT NULL,
  book_id BIGINT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT book_copy_link_book
    FOREIGN KEY (book_id)
    REFERENCES books (id)
);

CREATE TABLE IF NOT EXISTS order_book_copy_links (
  order_id BIGINT NOT NULL,
  book_copy_id BIGINT NOT NULL,
  CONSTRAINT book_copy_link_order
    FOREIGN KEY (order_id)
    REFERENCES orders (id),
  CONSTRAINT order_link_book_copy
    FOREIGN KEY (book_copy_id)
    REFERENCES book_copies (id)
);

CREATE TABLE IF NOT EXISTS authors (
  id BIGINT NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(64) NOT NULL,
  last_name VARCHAR(64) NOT NULL,
  birth_date DATETIME NOT NULL,
  image_path VARCHAR(512) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX id_unique (id)
);

CREATE TABLE IF NOT EXISTS book_author_links (
  book_id BIGINT NOT NULL,
  author_id BIGINT NOT NULL,
  CONSTRAINT book_id_link_author
    FOREIGN KEY (book_id)
    REFERENCES books (id),
  CONSTRAINT author_id_link_book
    FOREIGN KEY (author_id)
    REFERENCES authors (id)
);

CREATE TABLE IF NOT EXISTS genres (
  id BIGINT NOT NULL AUTO_INCREMENT,
  genre_name VARCHAR(64) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE INDEX genre_name_unique (genre_name));

CREATE TABLE IF NOT EXISTS book_genre_links (
  book_id BIGINT NOT NULL,
  genre_id BIGINT NOT NULL,
  CONSTRAINT book_link_genre
    FOREIGN KEY (book_id)
    REFERENCES books (id),
  CONSTRAINT genre_link_book
    FOREIGN KEY (genre_id)
    REFERENCES genres (id)
);

CREATE TABLE IF NOT EXISTS book_damage (
  id BIGINT NOT NULL AUTO_INCREMENT,
  image_path VARCHAR(512) NOT NULL,
  damage_description VARCHAR(5000) NOT NULL,
  user_id BIGINT NOT NULL,
  order_id BIGINT NOT NULL,
  book_copy_id BIGINT NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT fk_table1_users1
    FOREIGN KEY (user_id)
    REFERENCES users (id),
  CONSTRAINT fk_table1_orders1
    FOREIGN KEY (order_id)
    REFERENCES orders (id),
  CONSTRAINT fk_table1_book_copies1
    FOREIGN KEY (book_copy_id)
    REFERENCES book_copies (id)
);
COMMIT;