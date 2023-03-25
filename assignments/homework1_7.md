# [Homework 1.7](https://github.com/hendraanggrian/IIT-CS425/raw/assets/assignments/homework1_7.pdf): Launchpad ERD

Launch Pad business rules:

1. A launchpad hosts multiple launches over time, and each launch is hosted by
  exactly one launchpad. Therefore, Launchpads will be created in the database
  even before the first launch is hosted there.
2. A launchpad is located in a country, and a country, in turn, is located on a
  continent.
3. Each launchpad has a name and a location.
4. A launch requires a launchpad that hosts it, a launch vehicle (the rocket),
  and the payload.
5. For a launch, the date, time and name should be recorded as well as whether
  it is a crewed launch or not.
6. Each launch is performed by a launch vehicle. The serial number for the
  launch vehicle should be recorded. A launch vehicle can perform multiple
  launches over time.
7. Each launch vehicle is of a specific launch vehicle type. The name, maximum
  thrust and whether it is reusable need to be recorded for the launch vehicle
  type.
8. A specific manufacturer makes each launch vehicle type, and a manufacturer
  can make many different launch vehicle types.
9. Each launch carries one payload, and a payload is only carried by one launch.
10. A manufacturer also makes each payload, and a manufacturer can make many
  different payloads over time.
11. Multiple crew members can be carried on a crewed launch, and each crew
  member can be carried on multiple launches over time.
12. Each crew member has a nationality (the country they come from), and their
  name and surname also need to be recorded.
13. Crew members can appear in the database before they launch for the first
  time.

## Schema

Several notes about this solution:

- `Missions` is a bridge table for many-to-many relationship between `Crews`
  and `Launches`.

![The ER model.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/launchpad-erd/erd.png)

```sql
CREATE SCHEMA IF NOT EXISTS LaunchpadERD;
USE LaunchpadERD;

DROP TABLE IF EXISTS Missions;
DROP TABLE IF EXISTS Crews;
DROP TABLE IF EXISTS Launches;
DROP TABLE IF EXISTS Rockets;
DROP TABLE IF EXISTS Payloads;
DROP TABLE IF EXISTS Manufacturers;
DROP TABLE IF EXISTS Launchpads;

CREATE TABLE Launchpads(
  `name` VARCHAR(50) PRIMARY KEY,
  `location` VARCHAR(50)
);

CREATE TABLE Manufacturers(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(50),
  `phone` VARCHAR(50),
  `address` VARCHAR(50)
);

CREATE TABLE Payloads(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `weight` INT,

  `manufacturer_id` INT,
  CONSTRAINT payload_manufacturer FOREIGN KEY(`manufacturer_id`) REFERENCES Manufacturers(`id`)
);

CREATE TABLE Rockets(
  `serial_no` INT PRIMARY KEY,
  `type` VARCHAR(10),
  `name` VARCHAR(50),
  `max_thrust` INT,
  `is_reusable` BOOLEAN,

  `manufacturer_id` INT,
  CONSTRAINT rocket_manufacturer FOREIGN KEY(`manufacturer_id`) REFERENCES Manufacturers(`id`)
);

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

CREATE TABLE Crews(
  `name` VARCHAR(50) PRIMARY KEY,
  `surname` VARCHAR(50),
  `nationality` VARCHAR(50)
);

CREATE TABLE Missions(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `crew_name` VARCHAR(50),
  `launch_datetime` DATETIME,

  CONSTRAINT registers FOREIGN KEY(`crew_name`) REFERENCES Crews(`name`),
  CONSTRAINT registered FOREIGN KEY(`launch_datetime`) REFERENCES Launches(`datetime`)
);
```

[View full code](https://github.com/hendraanggrian/IIT-CS425/blob/main/launchpad-erd/initialize.sql)
