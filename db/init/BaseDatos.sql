-- ========================
-- SCHEMA: microservicios_db
-- ========================

-- Tabla Person
CREATE TABLE person (
    "id" SERIAL PRIMARY KEY,
    "name" VARCHAR(100) NOT NULL,
    "gender" VARCHAR(10),
    "age" INT,
    "identification" VARCHAR(20) UNIQUE NOT NULL,
    "address" VARCHAR(200),
    "phone" VARCHAR(20)
);

-- Tabla Customer (hereda de Person por composición)
CREATE TABLE customer (
    "id" INT not null,
	"customer_id" VARCHAR(100) PRIMARY KEY,
    "password" VARCHAR(100) NOT NULL,
    "state" BOOLEAN NOT NULL,
    FOREIGN KEY (id) REFERENCES person(id)
);

-- Tabla Account
CREATE TABLE account (
    "id" SERIAL PRIMARY KEY,
    "account_number" VARCHAR(20) UNIQUE NOT NULL,
    "account_type" VARCHAR(50) NOT NULL,
    "initial_balance" DECIMAL(12,2) NOT NULL,
    "state" BOOLEAN NOT NULL,
    "customer_id" VARCHAR(100) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

-- Tabla Movement
CREATE TABLE movement (
    "id" SERIAL PRIMARY KEY,
    "date" TIMESTAMP NOT NULL,
    "movement_type" VARCHAR(50) NOT NULL,
    "amount" DECIMAL(12,2) NOT NULL,
    "balance" DECIMAL(12,2) NOT NULL,
    "account_id" INT NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(id)
);
