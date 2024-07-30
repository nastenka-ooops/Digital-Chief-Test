CREATE SCHEMA products_db;

create table products
(
    id          bigint generated always as identity
        primary key,
    name        varchar(255)                        not null,
    description text,
    price       numeric(10, 2),
    active      boolean   default true,
    start_date  date                                not null,
    created_at  timestamp default CURRENT_TIMESTAMP not null,
    updated_at  timestamp default CURRENT_TIMESTAMP not null
);

create table skus
(
    id           bigint generated always as identity
        primary key,
    product_id   bigserial
        references products,
    color        varchar(50),
    availability boolean,
    size         varchar(10),
    created_at   timestamp default CURRENT_TIMESTAMP not null,
    updated_at   timestamp default CURRENT_TIMESTAMP not null
);

-- Вставляем данные в таблицу products
INSERT INTO products (name, description, price, active, start_date) VALUES
                                                                        ('Product 1', 'Description for product 1', 19.99, TRUE, '2023-01-01'),
                                                                        ('Product 2', 'Description for product 2', 29.99, TRUE, '2023-02-01'),
                                                                        ('Product 3', 'Description for product 3', 39.99, FALSE, '2023-03-01'),
                                                                        ('Product 4', 'Description for product 4', 49.99, TRUE, '2023-04-01'),
                                                                        ('Product 5', 'Description for product 5', 59.99, TRUE, '2023-05-01'),
                                                                        ('Product 6', 'Description for product 6', 69.99, FALSE, '2023-06-01'),
                                                                        ('Product 7', 'Description for product 7', 79.99, TRUE, '2023-07-01'),
                                                                        ('Product 8', 'Description for product 8', 89.99, TRUE, '2023-08-01'),
                                                                        ('Product 9', 'Description for product 9', 99.99, TRUE, '2023-09-01'),
                                                                        ('Product 10', 'Description for product 10', 109.99, TRUE, '2023-10-01'),
                                                                        ('Product 11', 'Description for product 11', 119.99, FALSE, '2023-11-01'),
                                                                        ('Product 12', 'Description for product 12', 129.99, TRUE, '2023-12-01'),
                                                                        ('Product 13', 'Description for product 13', 139.99, TRUE, '2023-01-15'),
                                                                        ('Product 14', 'Description for product 14', 149.99, TRUE, '2023-02-15'),
                                                                        ('Product 15', 'Description for product 15', 159.99, FALSE, '2023-03-15'),
                                                                        ('Product 16', 'Description for product 16', 169.99, TRUE, '2023-04-15'),
                                                                        ('Product 17', 'Description for product 17', 179.99, TRUE, '2023-05-15'),
                                                                        ('Product 18', 'Description for product 18', 189.99, FALSE, '2023-06-15'),
                                                                        ('Product 19', 'Description for product 19', 199.99, TRUE, '2023-07-15'),
                                                                        ('Product 20', 'Description for product 20', 209.99, TRUE, '2023-08-15');

-- Вставляем данные в таблицу skus
INSERT INTO skus (product_id, color, availability, size) VALUES
                                                             ((SELECT id FROM products WHERE name = 'Product 1'), 'Red', TRUE, 'M'),
                                                             ((SELECT id FROM products WHERE name = 'Product 1'), 'Blue', FALSE, 'L'),
                                                             ((SELECT id FROM products WHERE name = 'Product 2'), 'Green', TRUE, 'S'),
                                                             ((SELECT id FROM products WHERE name = 'Product 2'), 'Yellow', TRUE, 'M'),
                                                             ((SELECT id FROM products WHERE name = 'Product 3'), 'Black', FALSE, 'L'),
                                                             ((SELECT id FROM products WHERE name = 'Product 3'), 'White', TRUE, 'XL'),
                                                             ((SELECT id FROM products WHERE name = 'Product 4'), 'Red', TRUE, 'M'),
                                                             ((SELECT id FROM products WHERE name = 'Product 4'), 'Blue', FALSE, 'L'),
                                                             ((SELECT id FROM products WHERE name = 'Product 5'), 'Green', TRUE, 'S'),
                                                             ((SELECT id FROM products WHERE name = 'Product 5'), 'Yellow', TRUE, 'M'),
                                                             ((SELECT id FROM products WHERE name = 'Product 6'), 'Black', FALSE, 'L'),
                                                             ((SELECT id FROM products WHERE name = 'Product 6'), 'White', TRUE, 'XL'),
                                                             ((SELECT id FROM products WHERE name = 'Product 7'), 'Red', TRUE, 'M'),
                                                             ((SELECT id FROM products WHERE name = 'Product 7'), 'Blue', FALSE, 'L'),
                                                             ((SELECT id FROM products WHERE name = 'Product 8'), 'Green', TRUE, 'S'),
                                                             ((SELECT id FROM products WHERE name = 'Product 8'), 'Yellow', TRUE, 'M'),
                                                             ((SELECT id FROM products WHERE name = 'Product 9'), 'Black', FALSE, 'L'),
                                                             ((SELECT id FROM products WHERE name = 'Product 9'), 'White', TRUE, 'XL'),
                                                             ((SELECT id FROM products WHERE name = 'Product 10'), 'Red', TRUE, 'M'),
                                                             ((SELECT id FROM products WHERE name = 'Product 10'), 'Blue', FALSE, 'L'),
                                                             ((SELECT id FROM products WHERE name = 'Product 11'), 'Green', TRUE, 'S'),
                                                             ((SELECT id FROM products WHERE name = 'Product 11'), 'Yellow', TRUE, 'M'),
                                                             ((SELECT id FROM products WHERE name = 'Product 12'), 'Black', FALSE, 'L'),
                                                             ((SELECT id FROM products WHERE name = 'Product 12'), 'White', TRUE, 'XL'),
                                                             ((SELECT id FROM products WHERE name = 'Product 13'), 'Red', TRUE, 'M'),
                                                             ((SELECT id FROM products WHERE name = 'Product 13'), 'Blue', FALSE, 'L'),
                                                             ((SELECT id FROM products WHERE name = 'Product 14'), 'Green', TRUE, 'S'),
                                                             ((SELECT id FROM products WHERE name = 'Product 14'), 'Yellow', TRUE, 'M'),
                                                             ((SELECT id FROM products WHERE name = 'Product 15'), 'Black', FALSE, 'L'),
                                                             ((SELECT id FROM products WHERE name = 'Product 15'), 'White', TRUE, 'XL'),
                                                             ((SELECT id FROM products WHERE name = 'Product 16'), 'Red', TRUE, 'M'),
                                                             ((SELECT id FROM products WHERE name = 'Product 16'), 'Blue', FALSE, 'L'),
                                                             ((SELECT id FROM products WHERE name = 'Product 17'), 'Green', TRUE, 'S'),
                                                             ((SELECT id FROM products WHERE name = 'Product 17'), 'Yellow', TRUE, 'M'),
                                                             ((SELECT id FROM products WHERE name = 'Product 18'), 'Black', FALSE, 'L'),
                                                             ((SELECT id FROM products WHERE name = 'Product 18'), 'White', TRUE, 'XL'),
                                                             ((SELECT id FROM products WHERE name = 'Product 19'), 'Red', TRUE, 'M'),
                                                             ((SELECT id FROM products WHERE name = 'Product 19'), 'Blue', FALSE, 'L'),
                                                             ((SELECT id FROM products WHERE name = 'Product 20'), 'Green', TRUE, 'S'),
                                                             ((SELECT id FROM products WHERE name = 'Product 20'), 'Yellow', TRUE, 'M');


