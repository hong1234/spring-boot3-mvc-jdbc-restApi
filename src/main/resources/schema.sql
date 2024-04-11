-- MySQL --

-- DROP TABLE IF EXISTS reviews;
-- DROP TABLE IF EXISTS books;

-- CREATE TABLE `books` (
--   `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
--   `title` varchar(150) NOT NULL,
--   `content` text NOT NULL,
--   `created_on` timestamp DEFAULT NULL,
--   `updated_on` timestamp DEFAULT NULL
--   --   `created_on` datetime(6) DEFAULT NULL,
--   --   `updated_on` datetime(6) DEFAULT NULL,
--   --   PRIMARY KEY (`id`)
-- ); 

-- CREATE TABLE `reviews` (
--   `book_id` int NOT NULL,
--   `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
--   `name` varchar(150) NOT NULL,
--   `email` varchar(150) NOT NULL,
--   `content` text NOT NULL,
--   `like_status` enum ('Low', 'Medium', 'High') NOT NULL,
--   `created_on` timestamp DEFAULT NULL,
--   `updated_on` timestamp DEFAULT NULL,
--   --   `created_on` datetime(6) DEFAULT NULL,
--   --   `updated_on` datetime(6) DEFAULT NULL,
--   --   PRIMARY KEY (`id`),
--   FOREIGN KEY (`book_id`) REFERENCES `books` (`id`)
-- );





-- H2 --

DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS books;

CREATE TABLE books (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  title varchar(150) NOT NULL,
  content varchar(500) NOT NULL,
  created_on timestamp DEFAULT NULL,
  updated_on timestamp DEFAULT NULL
  -- created_on datetime DEFAULT NULL,
  -- updated_on datetime DEFAULT NULL
  -- PRIMARY KEY (id)
);

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
  FOREIGN KEY (book_id) REFERENCES books (id)
) ;