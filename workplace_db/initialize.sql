CREATE SCHEMA IF NOT EXISTS WorkplaceDB;
USE WorkplaceDB;

DROP TABLE IF EXISTS Salaries;

-- Salaries

CREATE TABLE Salaries(
  `row_num` INT AUTO_INCREMENT PRIMARY KEY,
  `first_name` VARCHAR(20) NOT NULL,
  `last_name` VARCHAR(20) NOT NULL,
  `salary` DECIMAL NOT NULL
);

INSERT INTO Salaries VALUES
  (NULL, 'Karen', 'Colmenares', 2500.00),
  (NULL, 'Guy', 'Himuro', 2600.00),
  (NULL, 'Irene', 'Mikkilineni', 2700.00),
  (NULL, 'Sigal', 'Tobias', 2800.00),
  (NULL, 'Shelli', 'Baida', 2900.00),
  (NULL, 'Alexander', 'Khoo', 3100.00),
  (NULL, 'Britney', 'Everett', 3900.00),
  (NULL, 'Sarah', 'Bell', 4000.00),
  (NULL, 'Diana', 'Lorentz', 4200.00),
  (NULL, 'Jennifer', 'Whalen', 4400.00),
  (NULL, 'David', 'Austin', 4800.00),
  (NULL, 'Valli', 'Pataballa', 4800.00),
  (NULL, 'Bruce', 'Ernst', 6000.00),
  (NULL, 'Pat', 'Fay', 6000.00),
  (NULL, 'Charles', 'Johnson', 6200.00);
