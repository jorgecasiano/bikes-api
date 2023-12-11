-- Fabricantes
INSERT INTO MANUFACTURERS (NAME) VALUES ('Specialized');
INSERT INTO MANUFACTURERS (NAME) VALUES ('Scott');
INSERT INTO MANUFACTURERS (NAME) VALUES ('Trek');
INSERT INTO MANUFACTURERS (NAME) VALUES ('Giant');
INSERT INTO MANUFACTURERS (NAME) VALUES ('Canyon');
INSERT INTO MANUFACTURERS (NAME) VALUES ('Cannondale');
INSERT INTO MANUFACTURERS (NAME) VALUES ('Orbea');
INSERT INTO MANUFACTURERS (NAME) VALUES ('BMC');


-- Tipos de artículos
INSERT INTO ITEM_TYPES (NAME) VALUES ('Cuadro');
INSERT INTO ITEM_TYPES (NAME) VALUES ('Horquilla');
INSERT INTO ITEM_TYPES (NAME) VALUES ('Freno');
INSERT INTO ITEM_TYPES (NAME) VALUES ('Transmisión');
INSERT INTO ITEM_TYPES (NAME) VALUES ('Pedal');
INSERT INTO ITEM_TYPES (NAME) VALUES ('Rueda');

-- Cuadros
INSERT INTO ITEMS (MODEL, TYPE_ID, DESCRIPTION) VALUES ('Trek Madone', 1, 'Trek Madone');
INSERT INTO ITEMS (MODEL, TYPE_ID, DESCRIPTION) VALUES ('Trek FX', 1, 'Trek FX');
INSERT INTO ITEMS (MODEL, TYPE_ID, DESCRIPTION) VALUES ('Sunday Soundwave', 1, 'Sunday Soundwave');

-- Pedales
INSERT INTO ITEMS (MODEL, TYPE_ID, DESCRIPTION) VALUES ('Shimano SPD-SL', 5, 'Shimano SPD-SL');

-- Trek Top Fuel
INSERT INTO BIKES (NAME, DESCRIPTION, PRICE, CURRENCY, MANUFACTURER_ID) VALUES ('Trek Top Fuel', 'Trek Top Fue', 500.00, 'EUR', 3);
-- Cuadro Trek Madone y pedales Shimano
INSERT INTO REL_BIKES_ITEMS (BIKE_ID, ITEM_ID) VALUES (1, 1);
INSERT INTO REL_BIKES_ITEMS (BIKE_ID, ITEM_ID) VALUES (1, 4);

-- Insertar otra bicicleta
INSERT INTO BIKES (NAME, DESCRIPTION, PRICE, CURRENCY, MANUFACTURER_ID) VALUES ('Trek Domane', 'Trek Domane', 600.00, 'EUR', 3);
-- Cuadro Trek FX y pedales Shimano
INSERT INTO REL_BIKES_ITEMS (BIKE_ID, ITEM_ID) VALUES (2, 2);
INSERT INTO REL_BIKES_ITEMS (BIKE_ID, ITEM_ID) VALUES (2, 4);