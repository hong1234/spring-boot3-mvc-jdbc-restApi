# spring boot 3 basic authen secured restful API and Jdbc

## install restful API project java

git clone https://github.com/hong1234/spring-boot3-mvc-jdbc-restApi.git

cd spring-boot3-mvc-jdbc-restApi

## run API server

mvn spring-boot:run

## requests using Postman

authen mehtod: Basic Auth

    username: autor

    password: autor

GET http://localhost:8000/api/books

GET http://localhost:8000/api/books/1

## add new book

POST http://localhost:8000/api/books

Body /Raw/Json

{
    "title":"test",
    "content":"test"
}

## add review to the book with id = 1

POST http://localhost:8000/api/reviews/1

    username: user

    password: user

Body /Raw/Json

{
    "name":"hong", 
    "email": "hongle@gmail.de", 
    "content":"i like this book",
    "likeStatus":"High"
}

# UI SERVER

## install UI app javascript

git clone https://github.com/hong1234/BookReviewReactUI_v2J.git

cd BookReviewReactUI_v2J

npm install

## run dev UI-server

npm start


## building asserts

npm run build

## run prod UI-server

php -S localhost:3000 -t build/

## url in browser

http://localhost:3000/


