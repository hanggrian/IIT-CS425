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

CREATE TABLE Tracks(
  `color` VARCHAR(10) PRIMARY KEY
);

CREATE TABLE Stations(
  `lat` DECIMAL(16, 12),
  `lng` DECIMAL(16, 12),
  `track_color` VARCHAR(10),
  `name` VARCHAR(50),
  `has_elevator` BOOLEAN,
  PRIMARY KEY(`lat`, `lng`, `track_color`),
  CONSTRAINT station_track FOREIGN KEY(`track_color`) REFERENCES Tracks(`color`)
);

CREATE TABLE Conductors(
  `name` VARCHAR(50) PRIMARY KEY,
  `birth` DATE
);

CREATE TABLE Locomotives(
  `serial_no` VARCHAR(20) PRIMARY KEY,
  `year_made` YEAR
);

CREATE TABLE Trains(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `track_color` VARCHAR(10),
  `locomotive_no` VARCHAR(20),
  `conductor_name` VARCHAR(50),
  CONSTRAINT train_track FOREIGN KEY(`track_color`) REFERENCES Tracks(`color`),
  CONSTRAINT train_locomotive FOREIGN KEY(`locomotive_no`) REFERENCES Locomotives(`serial_no`),
  CONSTRAINT train_conductor FOREIGN KEY(`conductor_name`) REFERENCES Conductors(`name`)
);

CREATE TABLE Wagons(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `seats` INT
);

CREATE TABLE WagonRegistrations(
  `train_id` INT,
  `wagon_id` INT,
  PRIMARY KEY(`train_id`, `wagon_id`),
  CONSTRAINT wagonreg_train FOREIGN KEY(`train_id`) REFERENCES Trains(`id`),
  CONSTRAINT wagonreg_wagon FOREIGN KEY(`wagon_id`) REFERENCES Wagons(`id`)
);

CREATE TABLE Passengers(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `fare` DECIMAL(13, 2),
  `train_id` INT,
  CONSTRAINT passengers_train FOREIGN KEY(`train_id`) REFERENCES Trains(`id`)
);
