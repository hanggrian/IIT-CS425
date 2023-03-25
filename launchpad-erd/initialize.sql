CREATE SCHEMA IF NOT EXISTS LaunchpadERD;
USE LaunchpadERD;

DROP TABLE IF EXISTS Missions;
DROP TABLE IF EXISTS Crews;
DROP TABLE IF EXISTS Launches;
DROP TABLE IF EXISTS Rockets;
DROP TABLE IF EXISTS Payloads;
DROP TABLE IF EXISTS Manufacturers;
DROP TABLE IF EXISTS Launchpads;

-- Launchpads

CREATE TABLE Launchpads(
  `name` VARCHAR(50) PRIMARY KEY,
  `location` VARCHAR(50)
);

-- Manufacturers

CREATE TABLE Manufacturers(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(50),
  `phone` VARCHAR(50),
  `address` VARCHAR(50)
);

-- Payloads

CREATE TABLE Payloads(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `weight` INT,

  `manufacturer_id` INT,
  CONSTRAINT payload_manufacturer FOREIGN KEY(`manufacturer_id`) REFERENCES Manufacturers(`id`)
);

-- Rockets

CREATE TABLE Rockets(
  `serial_no` INT PRIMARY KEY,
  `type` VARCHAR(10),
  `name` VARCHAR(50),
  `max_thrust` INT,
  `is_reusable` BOOLEAN,

  `manufacturer_id` INT,
  CONSTRAINT rocket_manufacturer FOREIGN KEY(`manufacturer_id`) REFERENCES Manufacturers(`id`)
);

-- Launches

CREATE TABLE Launches(
  `datetime` DATETIME PRIMARY KEY,
  `name` VARCHAR(50),

  `launchpad_name` VARCHAR(50),
  `payload_id` INT,
  `rocket_no` INT,
  CONSTRAINT host FOREIGN KEY(`launchpad_name`) REFERENCES Launchpads(`name`),
  CONSTRAINT carry FOREIGN KEY(`payload_id`) REFERENCES Payloads(`id`),
  CONSTRAINT powered FOREIGN KEY(`rocket_no`) REFERENCES Rockets(`serial_no`)
);

-- Crews

CREATE TABLE Crews(
  `name` VARCHAR(50) PRIMARY KEY,
  `surname` VARCHAR(50),
  `nationality` VARCHAR(50)
);

-- Missions

CREATE TABLE Missions(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `crew_name` VARCHAR(50),
  `launch_datetime` DATETIME,

  CONSTRAINT registers FOREIGN KEY(`crew_name`) REFERENCES Crews(`name`),
  CONSTRAINT registered FOREIGN KEY(`launch_datetime`) REFERENCES Launches(`datetime`)
);
