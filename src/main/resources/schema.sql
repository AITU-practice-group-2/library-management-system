CREATE TABLE IF NOT EXISTS authors (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    bio VARCHAR(2000) NOT NULL
);

CREATE TABLE IF NOT EXISTS categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(200) NOT NULL
);

CREATE TABLE IF NOT EXISTS books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description VARCHAR(2000) NOT NULL,
    publication_year INT NOT NULL,
    pages INT NOT NULL,
    available INT NOT NULL,
    author_id INT REFERENCES authors NOT NULL,
    category_id INT REFERENCES categories NOT NULL,
    image_src VARCHAR(1000),
    popularity INT NOT NULL,
    views INT NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    login VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    role VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS reservations (
    id SERIAL PRIMARY KEY,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    start_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    due_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    return_date TIMESTAMP WITHOUT TIME ZONE,
    status VARCHAR(20) NOT NULL,
    reserver_id INT REFERENCES users NOT NULL,
    book_id INT REFERENCES books NOT NULL
);

CREATE TABLE IF NOT EXISTS reviews (
    id SERIAL PRIMARY KEY,
    comment VARCHAR(3000) NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    reviewer_id INT REFERENCES users NOT NULL,
    book_id INT REFERENCES books NOT NULL,
    UNIQUE (reviewer_id, book_id)
);

CREATE TABLE IF NOT EXISTS users_authors (
    user_id int references users(id),
    author_id int references authors(id),
    primary key (user_id, author_id),
    popularity int
);

CREATE TABLE IF NOT EXISTS users_categories (
    user_id int references users(id),
    category_id int references categories(id),
    primary key (user_id, category_id),
    popularity int
);