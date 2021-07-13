
DROP TABLE IF EXISTS TRANSACTION;
DROP TABLE IF EXISTS CASHIER;

--
-- App tables
--

CREATE TABLE CASHIER (
	ID SERIAL PRIMARY KEY,
	NAME VARCHAR(200),
	BALANCE DECIMAL(12,2),
	STARTED DECIMAL(12,2)
);

CREATE TABLE TRANSACTION (
	ID SERIAL PRIMARY KEY,
	CASHIER_ID BIGINT,
	STATUS VARCHAR(20),
	ACTION VARCHAR(20),
	VALUE DECIMAL(12,2),
	CREATED_AT TIMESTAMP,
	UPDATED_AT TIMESTAMP,
	FOREIGN KEY (CASHIER_ID) REFERENCES CASHIER(ID)
);
