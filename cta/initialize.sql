CREATE SCHEMA IF NOT EXISTS CTA;
USE CTA;

DROP TABLE IF EXISTS Passengers;
DROP TABLE IF EXISTS WagonRegistrations;
DROP TABLE IF EXISTS Wagons;
DROP TABLE IF EXISTS Trains;
DROP TABLE IF EXISTS Locomotives;
DROP TABLE IF EXISTS Conductors;
DROP TABLE IF EXISTS Stations;
DROP TABLE IF EXISTS Tracks;

-- Tracks

CREATE TABLE Tracks(
  `color` VARCHAR(10) PRIMARY KEY
);

INSERT INTO Tracks VALUES
  ('Blue'),
  ('Green');

-- Stations

CREATE TABLE Stations(
  `lat` DECIMAL(16, 12),
  `lng` DECIMAL(16, 12),
  `track_color` VARCHAR(10),
  `name` VARCHAR(50),
  `has_elevator` BOOLEAN,
  PRIMARY KEY(`lat`, `lng`, `track_color`),
  CONSTRAINT station_track FOREIGN KEY(`track_color`) REFERENCES Tracks(`color`)
);

INSERT INTO Stations VALUES
  ('0.1', '0.1', 'Blue', 'Damen', 0),
  ('0.1', '0.2', 'Blue', 'Clark-Lake', 1),
  ('0.1', '0.2', 'Green', 'Clark-Lake', 1),
  ('0.2', '0.2', 'Green', 'Roosevelt', 0);

-- Conductors

CREATE TABLE Conductors(
  `name` VARCHAR(50) PRIMARY KEY,
  `birth` DATE
);

INSERT INTO Conductors VALUES
  ('Jane', '1991-01-01'),
  ('Michael', '1992-02-02');

-- Locomotives

CREATE TABLE Locomotives(
  `serial_no` VARCHAR(20) PRIMARY KEY,
  `year_made` YEAR
);

INSERT INTO Locomotives VALUES
  ('X1', 1998),
  ('A9', 1980);

-- Trains

CREATE TABLE Trains(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `track_color` VARCHAR(10),
  `locomotive_no` VARCHAR(20),
  `conductor_name` VARCHAR(50),
  CONSTRAINT train_track FOREIGN KEY(`track_color`) REFERENCES Tracks(`color`),
  CONSTRAINT train_locomotive FOREIGN KEY(`locomotive_no`) REFERENCES Locomotives(`serial_no`),
  CONSTRAINT train_conductor FOREIGN KEY(`conductor_name`) REFERENCES Conductors(`name`)
);

INSERT INTO Trains VALUES
  (1, 'Blue', 'X1', 'Jane'),
  (2, 'Green', 'A9', 'Michael');

-- Wagons

CREATE TABLE Wagons(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `seats` INT
);

INSERT INTO Wagons VALUES
  (1, 50),
  (2, 50),
  (3, 50),
  (4, 50);

-- WagonRegistrations

CREATE TABLE WagonRegistrations(
  `train_id` INT,
  `wagon_id` INT,
  PRIMARY KEY(`train_id`, `wagon_id`),
  CONSTRAINT wagonreg_train FOREIGN KEY(`train_id`) REFERENCES Trains(`id`),
  CONSTRAINT wagonreg_wagon FOREIGN KEY(`wagon_id`) REFERENCES Wagons(`id`)
);

INSERT INTO WagonRegistrations VALUES
  (1, 1),
  (1, 2),
  (2, 3),
  (2, 4);

-- Passengers

CREATE TABLE Passengers(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `fare` DECIMAL(13, 2),
  `train_id` INT,
  CONSTRAINT passengers_train FOREIGN KEY(`train_id`) REFERENCES Trains(`id`)
);

INSERT INTO Passengers VALUES
  (NULL, 3.0, 1),
  (NULL, 5.0, 2);
