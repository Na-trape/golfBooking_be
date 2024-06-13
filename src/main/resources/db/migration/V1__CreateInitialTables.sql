CREATE TABLE country
(
    id   SERIAL PRIMARY KEY,
    code CHAR(2) NOT NULL,
    name VARCHAR(50),
    UNIQUE (code),
    UNIQUE (name)
);

CREATE TABLE player
(
    id         SERIAL PRIMARY KEY,
    license        INT NOT NULL,
    name       VARCHAR(50),
    country_id INT,
    UNIQUE (license),
    FOREIGN KEY (country_id) REFERENCES country (id)
);

CREATE TABLE trainer
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL,
    hcp         VARCHAR(20) NOT NULL,
    experience  INT NOT NULL,
    description VARCHAR(500) NOT NULL,
    country_id  INT,
    FOREIGN KEY (country_id) REFERENCES country (id)
);

CREATE TABLE "user"
(
    id         SERIAL PRIMARY KEY,
    username   VARCHAR(20) NOT NULL,
    password   VARCHAR(100) NOT NULL,
    player_id INT,
    trainer_id INT,
    UNIQUE (username),
    FOREIGN KEY (player_id) REFERENCES player (id),
    FOREIGN KEY (trainer_id) REFERENCES trainer (id)
);

CREATE TABLE user_role
(
    id        SERIAL PRIMARY KEY,
    user_id   INT NOT NULL,
    role_name VARCHAR(50) NOT NULL,
    UNIQUE (user_id, role_name),
    FOREIGN KEY (user_id) REFERENCES "user" (id)
);

CREATE TABLE product
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL,
    price       DECIMAL(10, 2) NOT NULL,
    category    VARCHAR(50) NOT NULL,
    description VARCHAR(500)
);