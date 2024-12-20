-- H2 or MySQL --
DROP TABLE IF EXISTS images;
DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS books;

DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;

CREATE TABLE books (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  -- uuid  VARCHAR(255) NOT NULL,
  title varchar(150) NOT NULL,
  content varchar(500) NOT NULL,
  created_on timestamp DEFAULT NULL,
  updated_on timestamp DEFAULT NULL,
  -- created_on datetime DEFAULT NULL,
  UNIQUE(title)
  -- PRIMARY KEY (id)
);

-- ALTER TABLE books ADD INDEX (title);

CREATE TABLE reviews (
  book_id int NOT NULL,
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name varchar(150) NOT NULL,
  email varchar(150) NOT NULL,
  content varchar(500) NOT NULL,
  like_status enum ('Low', 'Medium', 'High') NOT NULL,
  created_on timestamp DEFAULT NULL,
  updated_on timestamp DEFAULT NULL,
  -- created_on datetime DEFAULT NULL,
  -- updated_on datetime DEFAULT NULL,
  -- PRIMARY KEY (id),
  constraint fk_book_id_reviews FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE
) ;

CREATE TABLE images
(
  book_id int NOT NULL,
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  uuid  VARCHAR(255) NOT NULL,
  title VARCHAR(255) NOT NULL,
  constraint fk_book_id_images FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE
);

create table users(
	username varchar(50) not null primary key,
	password varchar(500) not null,
	enabled boolean not null
);

create table authorities (
	username varchar(50) not null,
	authority varchar(50) not null,
	constraint fk_authorities_users foreign key(username) references users(username)
);

-- create index ix_book_title on books (title);

create unique index ix_auth_username on authorities (username,authority);

