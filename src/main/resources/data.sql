insert into books(title, content, created_on) values 
('Introducing SpringBoot', 'SpringBoot is awesome', CURRENT_TIMESTAMP),
('Securing Web applications', 'This post will show how to use SpringSecurity', CURRENT_TIMESTAMP),
('Introducing Spring Social', 'Developing social web applications using Spring Social', CURRENT_TIMESTAMP)
;

insert into reviews(book_id, name, email, content, like_status, created_on) values
(1, 'John', 'john@gmail.com', 'This is cool', 'Low', CURRENT_TIMESTAMP),
(2, 'Rambo', 'rambo@gmail.com', 'Thanks for awesome tips', 'High', CURRENT_TIMESTAMP),
(3, 'Paul', 'paul@gmail.com', 'Nice post buddy.', 'High', CURRENT_TIMESTAMP)
;


-- TRUNCATE TABLE reviews;
-- TRUNCATE TABLE books;