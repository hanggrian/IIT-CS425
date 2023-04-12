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

CREATE TABLE Conductors(
  `username` VARCHAR(20) PRIMARY KEY,
  `password` VARCHAR(20) DEFAULT '1234',
  `name` VARCHAR(50) NOT NULL,
  `birth` DATE NOT NULL,
  `age` INT NOT NULL,
  `phones` VARCHAR(50),
  CHECK(`age` >= 21)
);

CREATE TABLE Alerts(
  `alert_id` INT AUTO_INCREMENT PRIMARY KEY,
  `title` VARCHAR(250) NOT NULL,
  `message` VARCHAR(500),
  `date_start` DATE NOT NULL,
  `date_end` DATE,
  `username` VARCHAR(10) NOT NULL,
  CONSTRAINT Alerts_username FOREIGN KEY(`username`) REFERENCES Conductors(`username`)
    ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE Tracks(
  `track_color` VARCHAR(10) PRIMARY KEY
);

CREATE TABLE Stations(
  `station_lat` DECIMAL(8, 6),
  `station_lng` DECIMAL(9, 6),
  `station_color` VARCHAR(10),
  `name` VARCHAR(50) NOT NULL,
  `location` VARCHAR(250),
  `zip` VARCHAR(5) NOT NULL,
  `has_elevator` BOOLEAN NOT NULL DEFAULT 0,
  `has_parking` BOOLEAN NOT NULL DEFAULT 0,
  PRIMARY KEY(`station_lat`, `station_lng`, `station_color`),
  INDEX(`station_lat`),
  INDEX(`station_lng`),
  CONSTRAINT Stations_station_color FOREIGN KEY(`station_color`) REFERENCES Tracks(`track_color`)
    ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE Locomotives(
  `serial_no` VARCHAR(4) PRIMARY KEY,
  `since` YEAR NOT NULL,
  CHECK(LENGTH(`serial_no`) = 4)
);

CREATE TABLE Wagons(
  `wagon_id` VARCHAR(4) PRIMARY KEY,
  `seats` INT NOT NULL,
  CHECK(LENGTH(`wagon_id`) = 4)
);

CREATE TABLE Trains(
  `train_id` INT AUTO_INCREMENT PRIMARY KEY,
  `track_color` VARCHAR(10) NOT NULL,
  `serial_no` VARCHAR(4) NOT NULL,
  `username` VARCHAR(10) NOT NULL,
  CONSTRAINT Trains_track_color FOREIGN KEY(`track_color`) REFERENCES Tracks(`track_color`)
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
  `name` VARCHAR(50) NOT NULL
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
  `station_color` VARCHAR(10) NOT NULL,
  `station_lat1` DECIMAL(8, 6) NOT NULL,
  `station_lng1` DECIMAL(9, 6) NOT NULL,
  `station_lat2` DECIMAL(8, 6),
  `station_lng2` DECIMAL(9, 6),
  PRIMARY KEY(`timestamp`, `passenger_id`),
  CONSTRAINT Trips_passenger_id FOREIGN KEY(`passenger_id`) REFERENCES Passengers(`passenger_id`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT Trips_pass_id FOREIGN KEY(`pass_id`) REFERENCES Passes(`pass_id`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT Trips_station_color FOREIGN KEY(`station_color`) REFERENCES Stations(`station_color`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT Trips_station_lat1 FOREIGN KEY(`station_lat1`) REFERENCES Stations(`station_lat`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT Trips_station_lng1 FOREIGN KEY(`station_lng1`) REFERENCES Stations(`station_lng`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT Trips_station_lat2 FOREIGN KEY(`station_lat2`) REFERENCES Stations(`station_lat`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT Trips_station_lng2 FOREIGN KEY(`station_lng2`) REFERENCES Stations(`station_lng`)
    ON DELETE RESTRICT ON UPDATE RESTRICT
);
