
INSERT INTO CASHIER(ID, NAME, BALANCE, STARTED) VALUES
					(1, 'Energy & bin', 32.54, 0.00),
					(2, 'Geral', 120.00, 23.00),
					(3, 'Rent & Clean', 3.11, 12.45);

INSERT INTO TRANSACTION(ID, CASHIER_ID, STATUS, ACTION, VALUE, CREATED_AT, UPDATED_AT) VALUES
					(1, 1, 'SENDED',   'DEPOSIT',  12.32, {ts '2021-07-13 06:00:37.12'}, null),
					(2, 1, 'FINISHED', 'WITHDRAW', 65.10, {ts '2021-07-13 07:06:37.12'}, {ts '2021-07-14 12:49:05.572'}),
					(3, 2, 'SENDED',   'WITHDRAW', 90.08, {ts '2021-07-10 01:31:37.12'}, null);
						