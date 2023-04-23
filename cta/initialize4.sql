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
  `track_color` VARCHAR(20) PRIMARY KEY,
  `is_24h` BOOLEAN DEFAULT 0
);

CREATE TABLE Stations(
  `track_color` VARCHAR(20),
  `station_name` VARCHAR(50) NOT NULL,
  `lat` DOUBLE(8, 6),
  `lng` DOUBLE(9, 6),
  `location` VARCHAR(200),
  `zip` VARCHAR(5) NOT NULL,
  `has_elevator` BOOLEAN NOT NULL DEFAULT 0,
  `has_parking` BOOLEAN NOT NULL DEFAULT 0,
  PRIMARY KEY(`track_color`, `station_name`),
  INDEX(`station_name`),
  CONSTRAINT Stations_track_color FOREIGN KEY(`track_color`) REFERENCES Tracks(`track_color`)
    ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE Conductors(
  `conductor_username` VARCHAR(20) PRIMARY KEY,
  `password` VARCHAR(64) DEFAULT '',
  `fullname` VARCHAR(50) NOT NULL,
  `joined` DATE NOT NULL,
  `birth` DATE NOT NULL,
  `age` INT NOT NULL,
  `phones` VARCHAR(50),
  INDEX(`fullname`),
  CHECK(`age` >= 21)
);

CREATE TABLE Alerts(
  `alert_id` INT AUTO_INCREMENT PRIMARY KEY,
  `title` VARCHAR(400) NOT NULL,
  `message` VARCHAR(400),
  `date_start` DATE NOT NULL,
  `date_end` DATE,
  `track_color` VARCHAR(20),
  `conductor_username` VARCHAR(20) NOT NULL,
  INDEX(`title`),
  CONSTRAINT Alerts_track_color FOREIGN KEY(`track_color`) REFERENCES Tracks(`track_color`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT Alerts_username FOREIGN KEY(`conductor_username`)
    REFERENCES Conductors(`conductor_username`) ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE Locomotives(
  `locomotive_serial` VARCHAR(4) PRIMARY KEY,
  `since` YEAR(4) NOT NULL,
  CHECK(LENGTH(`locomotive_serial`) = 4)
);

CREATE TABLE Wagons(
  `wagon_id` VARCHAR(4) PRIMARY KEY,
  `seats` INT NOT NULL,
  CHECK(LENGTH(`wagon_id`) = 4)
);

CREATE TABLE Trains(
  `train_id` INT AUTO_INCREMENT PRIMARY KEY,
  `track_color` VARCHAR(20) NOT NULL,
  `locomotive_serial` VARCHAR(4) NOT NULL,
  `conductor_username` VARCHAR(20) NOT NULL,
  CONSTRAINT Trains_track_color FOREIGN KEY(`track_color`) REFERENCES Tracks(`track_color`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT Trains_locomotive_serial FOREIGN KEY(`locomotive_serial`)
    REFERENCES Locomotives(`locomotive_serial`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT Trains_username FOREIGN KEY(`conductor_username`)
    REFERENCES Conductors(`conductor_username`) ON DELETE RESTRICT ON UPDATE RESTRICT
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
  `joined` DATE NOT NULL,
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
  `trip_timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `passenger_id` INT,
  `fare` DECIMAL(13, 2) NOT NULL,
  `pass_id` INT,
  `track_color` VARCHAR(20) NOT NULL,
  `station_name_in` VARCHAR(50) NOT NULL,
  `station_name_out` VARCHAR(50),
  PRIMARY KEY(`trip_timestamp`, `passenger_id`),
  CONSTRAINT Trips_passenger_id FOREIGN KEY(`passenger_id`) REFERENCES Passengers(`passenger_id`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT Trips_pass_id FOREIGN KEY(`pass_id`) REFERENCES Passes(`pass_id`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT Trips_track_color FOREIGN KEY(`track_color`) REFERENCES Stations(`track_color`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT Trips_station_name_in FOREIGN KEY(`station_name_in`) REFERENCES Stations(`station_name`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT Trips_station_name_out FOREIGN KEY(`station_name_out`) REFERENCES Stations(`station_name`)
    ON DELETE RESTRICT ON UPDATE RESTRICT
);
