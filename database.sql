-- Drop old tables
DROP TABLE IF EXISTS article_join_commande;
DROP TABLE IF EXISTS article_join_fournisseur;
DROP TABLE IF EXISTS hist_join_com;
DROP TABLE IF EXISTS client;
DROP TABLE IF EXISTS salarie;
DROP TABLE IF EXISTS fournisseur;
DROP TABLE IF EXISTS commande;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS article;
DROP TABLE IF EXISTS adresse;

-- Drop conflicting tables
DROP TABLE IF EXISTS product_join_order;
DROP TABLE IF EXISTS product_join_supplier;
DROP TABLE IF EXISTS orders_join_client;
DROP TABLE IF EXISTS client;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS supplier;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS address;

-- Addresses
CREATE TABLE address (
    id SERIAL PRIMARY KEY,
    postcode VARCHAR(10) NOT NULL,
    street VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL
);

-- Products
CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    stock INT NOT NULL,
    description text,
    producttype VARCHAR(255) NOT NULL,
    origin VARCHAR(255) NOT NULL,
    medal VARCHAR(255),
    birthdate DATE NOT NULL,
    productorname VARCHAR(255) NOT NULL
);

-- Users
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    lastname VARCHAR(255) NOT NULL,
    firstname VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Orders
CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    orderdate DATE NOT NULL,
    deliverydate DATE,
    price NUMERIC NOT NULL
);

-- Suppliers
CREATE TABLE supplier (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address INT NOT NULL UNIQUE,
    phone VARCHAR(20) UNIQUE,
    FOREIGN KEY (address) REFERENCES address (id)
);

-- Salaries
CREATE TABLE employee (
    users INT NOT NULL UNIQUE,
    job VARCHAR(255) NOT NULL,
	FOREIGN KEY (users) REFERENCES users (id)
);

-- Clients
CREATE TABLE client (
    users INT NOT NULL UNIQUE,
    address INT NOT NULL,
    mail VARCHAR(255) NOT NULL UNIQUE,
	FOREIGN KEY (users) REFERENCES users (id),
	FOREIGN KEY (address) REFERENCES address (id)
);

-- With that we can know the products in an order
CREATE TABLE product_join_order (
    product INT NOT NULL,
    orders INT NOT NULL,
	FOREIGN KEY (product) REFERENCES product (id),
	FOREIGN KEY (orders) REFERENCES orders (id)
);

-- With this one we know which supplier have witch product
CREATE TABLE product_join_supplier (
    product INT NOT NULL,
    supplier INT NOT NULL,
	FOREIGN KEY (product) REFERENCES product (id),
	FOREIGN KEY (supplier) REFERENCES supplier (id)
);

-- This is the historic of order from the users
CREATE TABLE hist_join_com (
    users INT NOT NULL,
    orders INT NOT NULL,
    quantity INT NOT NULL,
	FOREIGN KEY (users) REFERENCES users (id),
	FOREIGN KEY (orders) REFERENCES orders (id)
);
