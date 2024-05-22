-- Drop existing database and user if they exist
DROP DATABASE IF EXISTS onlinebookstoredb;
DROP USER IF EXISTS onlinebookstore;

-- Create new user and database
CREATE USER onlinebookstore WITH PASSWORD 'password';
CREATE DATABASE onlinebookstoredb WITH OWNER onlinebookstore;

-- Connect to the newly created database
\connect onlinebookstoredb;

-- Ensure privileges are set correctly
ALTER DEFAULT PRIVILEGES FOR USER onlinebookstore GRANT ALL ON TABLES TO onlinebookstore;
ALTER DEFAULT PRIVILEGES FOR USER onlinebookstore GRANT ALL ON SEQUENCES TO onlinebookstore;

-- Drop existing tables if they exist
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS users;

-- Create tables
CREATE TABLE authors (
                         author_id SERIAL PRIMARY KEY,
                         name VARCHAR(20) NOT NULL
);

CREATE TABLE books (
                       book_id SERIAL PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       author_id INT REFERENCES authors(author_id),
                       description TEXT,
                       price DECIMAL(10, 2),
                       quantity INT
);

CREATE TABLE users (
                       user_id SERIAL PRIMARY KEY,
                       username VARCHAR(20) NOT NULL,
                       email VARCHAR(30) NOT NULL,
                       password TEXT NOT NULL
);

CREATE TABLE orders (
                        order_id SERIAL PRIMARY KEY,
                        user_id INT REFERENCES users(user_id),
                        order_date TIMESTAMP NOT NULL,
                        total_amount DECIMAL(10, 2)
);

CREATE TABLE order_items (
                             order_item_id SERIAL PRIMARY KEY,
                             order_id INT REFERENCES orders(order_id),
                             book_id INT REFERENCES books(book_id),
                             quantity INT
);

CREATE TABLE reviews (
                         review_id SERIAL PRIMARY KEY,
                         user_id INT REFERENCES users(user_id),
                         book_id INT REFERENCES books(book_id),
                         rating INT,
                         comment TEXT
);
