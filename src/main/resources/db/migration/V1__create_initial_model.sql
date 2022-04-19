CREATE TABLE Brand
(
    id   BIGINT auto_increment,
    name VARCHAR(255)
);

CREATE TABLE Product
(
    id   BIGINT auto_increment,
    name VARCHAR(255)
);

CREATE TABLE Prices
(
    id         BIGINT auto_increment,
    brand_id   BIGINT         NOT NULL,
    product_id BIGINT         NOT NULL,
    start_date timestamp      NOT NULL,
    end_date   timestamp      NOT NULL,
    price_list int            NOT NULL,
    priority   int            NOT NULL,
    price      NUMERIC(10, 2) NOT NULL,
    currency   CHAR(3)        NOT NULL,
    foreign key (brand_id) references Brand (id),
    foreign key (product_id) references Product (id)
);