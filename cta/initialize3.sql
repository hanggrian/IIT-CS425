CREATE SCHEMA IF NOT EXISTS CTA;
USE CTA;

DROP TABLE IF EXISTS Trips;
DROP TABLE IF EXISTS Passes;
DROP TABLE IF EXISTS Passengers;
DROP TABLE IF EXISTS Railcars;
DROP TABLE IF EXISTS Trains;
DROP TABLE IF EXISTS Wagons;
DROP TABLE IF EXISTS Locomotives;
DROP TABLE IF EXISTS Alerts;
DROP TABLE IF EXISTS Conductors;
DROP TABLE IF EXISTS Stations;
DROP TABLE IF EXISTS Tracks;

CREATE TABLE Tracks(
  `track` VARCHAR(20) PRIMARY KEY,
  `is_24h` BOOLEAN DEFAULT 0
);

CREATE TABLE Stations(
  `track` VARCHAR(20),
  `station` VARCHAR(50) NOT NULL,
  `lat` DECIMAL(8, 6),
  `lng` DECIMAL(9, 6),
  `location` VARCHAR(200),
  `zip` VARCHAR(5) NOT NULL,
  `has_elevator` BOOLEAN NOT NULL DEFAULT 0,
  `has_parking` BOOLEAN NOT NULL DEFAULT 0,
  PRIMARY KEY(`track`, `station`),
  INDEX(`station`),
  CONSTRAINT Stations_track FOREIGN KEY(`track`) REFERENCES Tracks(`track`)
    ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE Conductors(
  `username` VARCHAR(20) PRIMARY KEY,
  `password` VARCHAR(20) DEFAULT '1234',
  `fullname` VARCHAR(50) NOT NULL,
  `birth` DATE NOT NULL,
  `age` INT NOT NULL,
  `phones` VARCHAR(50),
  INDEX(`fullname`)
  CHECK(`age` >= 21)
);

CREATE TABLE Alerts(
  `alert_id` INT AUTO_INCREMENT PRIMARY KEY,
  `title` VARCHAR(400) NOT NULL,
  `message` VARCHAR(400),
  `date_start` DATE NOT NULL,
  `date_end` DATE,
  `track` VARCHAR(20),
  `username` VARCHAR(20) NOT NULL,
  INDEX(`title`)
  CONSTRAINT Alerts_track FOREIGN KEY(`track`) REFERENCES Tracks(`track`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT Alerts_username FOREIGN KEY(`username`) REFERENCES Conductors(`username`)
    ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE Locomotives(
  `serial_no` VARCHAR(4) PRIMARY KEY,
  `since` YEAR(4) NOT NULL,
  CHECK(LENGTH(`serial_no`) = 4)
);

CREATE TABLE Wagons(
  `wagon_id` VARCHAR(4) PRIMARY KEY,
  `seats` INT NOT NULL,
  CHECK(LENGTH(`wagon_id`) = 4)
);

CREATE TABLE Trains(
  `train_id` INT AUTO_INCREMENT PRIMARY KEY,
  `track` VARCHAR(20) NOT NULL,
  `serial_no` VARCHAR(4) NOT NULL,
  `username` VARCHAR(20) NOT NULL,
  CONSTRAINT Trains_track FOREIGN KEY(`track`) REFERENCES Tracks(`track`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT Trains_serial_no FOREIGN KEY(`serial_no`) REFERENCES Locomotives(`serial_no`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT Trains_username FOREIGN KEY(`username`) REFERENCES Conductors(`username`)
    ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE Railcars(
  `train_id` INT,
  `wagon_id` VARCHAR(4),
  PRIMARY KEY(`train_id`, `wagon_id`),
  CONSTRAINT Railcars_train_id FOREIGN KEY(`train_id`) REFERENCES Trains(`train_id`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT Railcars_wagon_id FOREIGN KEY(`wagon_id`) REFERENCES Wagons(`wagon_id`)
    ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE Passengers(
  `passenger_id` INT AUTO_INCREMENT PRIMARY KEY,
  `fullname` VARCHAR(50) NOT NULL,
  INDEX(`fullname`)
);

CREATE TABLE Passes(
  `pass_id` INT AUTO_INCREMENT PRIMARY KEY,
  `date_start` DATE NOT NULL,
  `date_end` DATE NOT NULL,
  `passenger_id` INT NOT NULL,
  CONSTRAINT Passes_passenger_id FOREIGN KEY(`passenger_id`) REFERENCES Passengers(`passenger_id`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CHECK(`date_start` < `date_end`)
);

CREATE TABLE Trips(
  `timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `passenger_id` INT,
  `fare` DECIMAL(13, 2),
  `pass_id` INT,
  `track` VARCHAR(20) NOT NULL,
  `station_in` VARCHAR(50) NOT NULL,
  `station_out` VARCHAR(50) NOT NULL,
  PRIMARY KEY(`timestamp`, `passenger_id`),
  CONSTRAINT Trips_passenger_id FOREIGN KEY(`passenger_id`) REFERENCES Passengers(`passenger_id`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT Trips_pass_id FOREIGN KEY(`pass_id`) REFERENCES Passes(`pass_id`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT Trips_track FOREIGN KEY(`track`) REFERENCES Stations(`track`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT Trips_station_in FOREIGN KEY(`station_in`) REFERENCES Stations(`station`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT Trips_station_out FOREIGN KEY(`station_out`) REFERENCES Stations(`station`)
    ON DELETE RESTRICT ON UPDATE RESTRICT
);
