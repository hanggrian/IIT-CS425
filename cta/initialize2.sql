CREATE SCHEMA IF NOT EXISTS CTA;
USE CTA;

DROP TABLE IF EXISTS Trips;
DROP TABLE IF EXISTS Passes;
DROP TABLE IF EXISTS Passengers;
DROP TABLE IF EXISTS Railcars;
DROP TABLE IF EXISTS Trains;
DROP TABLE IF EXISTS Wagons;
DROP TABLE IF EXISTS Locomotives;
DROP TABLE IF EXISTS Stations;
DROP TABLE IF EXISTS Tracks;
DROP TABLE IF EXISTS Alerts;
DROP TABLE IF EXISTS Conductors;

-- Conductors

CREATE TABLE Conductors(
  `social_sec` VARCHAR(10) PRIMARY KEY,
  `name` VARCHAR(50) NOT NULL,
  `birth` DATE NOT NULL
);

INSERT INTO Conductors VALUES
  ('1234567890', 'Jane', '1991-01-01'),
  ('1122334455', 'John', '1992-02-02');

-- Alerts

CREATE TABLE Alerts(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `message` VARCHAR(280) NOT NULL,
  `date_start` DATE NOT NULL,
  `date_end` DATE NOT NULL,
  `conductor_soc` VARCHAR(50) NOT NULL,
  FOREIGN KEY(`conductor_soc`) REFERENCES Conductors(`social_sec`)
);

INSERT INTO Alerts VALUES
  (NULL, 'Elevator maintenance at Damen.', '2023-03-27', '2023-04-27', '1234567890'),
  (NULL, 'Closed today because of tornado.', '2023-03-28', '2023-03-28', '1122334455');

-- Tracks

CREATE TABLE Tracks(
  `color` VARCHAR(10) PRIMARY KEY
);

INSERT INTO Tracks VALUES
  ('Blue'),
  ('Green');

-- Stations

CREATE TABLE Stations(
  `lat` DECIMAL(8, 6),
  `lng` DECIMAL(9, 6),
  `track_color` VARCHAR(10),
  `name` VARCHAR(50) NOT NULL,
  `zip` VARCHAR(5) NOT NULL,
  `has_elevator` BOOLEAN NOT NULL DEFAULT 0,
  `has_parking` BOOLEAN NOT NULL DEFAULT 0,
  PRIMARY KEY(`lat`, `lng`, `track_color`),
  INDEX(`lat`),
  INDEX(`lng`),
  FOREIGN KEY(`track_color`) REFERENCES Tracks(`color`)
);

INSERT INTO Stations VALUES
  (41.9100, 87.6780, 'Blue', 'Damen', '60622', DEFAULT, DEFAULT),
  (41.8858, 87.6316, 'Blue', 'Clark-Lake', '60601', DEFAULT, DEFAULT),
  (41.8858, 87.6316, 'Green', 'Clark-Lake', '60601', DEFAULT, DEFAULT),
  (41.8674, 87.6266, 'Green', 'Roosevelt', '60605', DEFAULT, DEFAULT);

-- Locomotives

CREATE TABLE Locomotives(
  `serial_no` VARCHAR(20) PRIMARY KEY,
  `since` YEAR NOT NULL
);

INSERT INTO Locomotives VALUES
  ('X111', 1998),
  ('A999', 1980);

-- Wagons

CREATE TABLE Wagons(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `seats` INT NOT NULL
);

INSERT INTO Wagons VALUES
  (1, 40),
  (2, 50),
  (3, 45),
  (4, 55);

-- Trains

CREATE TABLE Trains(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `track_color` VARCHAR(10),
  `locomotive_no` VARCHAR(20),
  `conductor_sec` VARCHAR(10),
  FOREIGN KEY(`track_color`) REFERENCES Tracks(`color`),
  FOREIGN KEY(`locomotive_no`) REFERENCES Locomotives(`serial_no`),
  FOREIGN KEY(`conductor_sec`) REFERENCES Conductors(`social_sec`)
);

INSERT INTO Trains VALUES
  (1, 'Blue', 'X111', '1234567890'),
  (2, 'Green', 'A999', '1122334455');

-- Railcars

CREATE TABLE Railcars(
  `train_id` INT,
  `wagon_id` INT,
  PRIMARY KEY(`train_id`, `wagon_id`),
  FOREIGN KEY(`train_id`) REFERENCES Trains(`id`),
  FOREIGN KEY(`wagon_id`) REFERENCES Wagons(`id`)
);

INSERT INTO Railcars VALUES
  (1, 1),
  (1, 2),
  (2, 3),
  (2, 4);

-- Passengers

CREATE TABLE Passengers(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(50) NOT NULL
);

INSERT INTO Passengers VALUES
  (1, 'Michael'),
  (2, 'Mike');

-- Passes

CREATE TABLE Passes(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `date_start` DATE NOT NULL,
  `date_end` DATE NOT NULL,
  `passenger_id` INT NOT NULL,
  FOREIGN KEY(`passenger_id`) REFERENCES Passengers(`id`)
);

INSERT INTO Passes VALUES
  (1, '2023-03-01', '2023-04-01', 1),
  (2, '2023-03-01', '2023-04-01', 2);

-- Trips

CREATE TABLE Trips(
  `passenger_id` INT NOT NULL,
  `fare` DECIMAL(13, 2),
  `pass_id` INT,
  `station1_lat` DECIMAL(8, 6) NOT NULL,
  `station1_lng` DECIMAL(9, 6) NOT NULL,
  `station1_color` VARCHAR(10) NOT NULL,
  `station2_lat` DECIMAL(8, 6),
  `station2_lng` DECIMAL(9, 6),
  `station2_color` VARCHAR(10),
  `timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(`timestamp`, `passenger_id`),
  FOREIGN KEY(`passenger_id`) REFERENCES Passengers(`id`),
  FOREIGN KEY(`pass_id`) REFERENCES Passes(`id`),
  FOREIGN KEY(`station1_lat`) REFERENCES Stations(`lat`),
  FOREIGN KEY(`station1_lng`) REFERENCES Stations(`lng`),
  FOREIGN KEY(`station1_color`) REFERENCES Stations(`track_color`),
  FOREIGN KEY(`station2_lat`) REFERENCES Stations(`lat`),
  FOREIGN KEY(`station2_lng`) REFERENCES Stations(`lng`),
  FOREIGN KEY(`station2_color`) REFERENCES Stations(`track_color`)
);

INSERT INTO Trips VALUES
  (1, 3.0, 1, 41.9100, 87.6780, 'Blue', 41.8858, 87.6316, 'Green', DEFAULT),
  (2, 2.5, 2, 41.9100, 87.6780, 'Blue', 41.8674, 87.6266, 'Green', DEFAULT);
