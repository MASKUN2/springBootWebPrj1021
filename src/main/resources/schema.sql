drop table if exists spring.users;
drop table if exists spring.authorities;
drop table IF EXISTS spring.product;
CREATE TABLE IF NOT EXISTS spring.users
(
    id          INT         NOT NULL AUTO_INCREMENT,
    username    VARCHAR(45) NOT NULL,
    password    TEXT        NOT NULL,
    algorithm varchar(45) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS spring.authorities
(
    id        INT         NOT NULL AUTO_INCREMENT,
    name  VARCHAR(45) NOT NULL,
    user INT NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS spring.product
(
    id      INT         NOT NULL AUTO_INCREMENT,
    name  VARCHAR(45) NOT NULL,
    price VARCHAR(45) NOT NULL,
    currency VARCHAR(45) NOT NULL,
    PRIMARY KEY (id)
);