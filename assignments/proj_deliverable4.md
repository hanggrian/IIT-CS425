# [Project Deliverable 4](https://github.com/hendraanggrian/IIT-CS425/blob/assets/assignments/proj.pdf): *Chicago Transit Authority (CTA)*

> The last deliverable requires to write a program in any programming language
  SQL supports and implement a variety of SQL commands.

## Problem 1

> The application can either be web or desktop application and must be
  demonstrated on the due date.

![App preview.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/preview.png)

A 3-columns windowed multi-platform desktop app.

- [DMG](https://github.com/hendraanggrian/IIT-CS425/releases/download/proj-deliverable4/cta-1.0-x64.dmg)
  &ndash; macOS moutable with one-file application inside. For permission error,
  run `sudo xattr -cr /Applications/CTA.app`.
- [EXE](https://github.com/hendraanggrian/IIT-CS425/releases/download/proj-deliverable4/cta-1.0-x64.exe)
  &ndash; Windows installer to default directory.
- [JAR](https://github.com/hendraanggrian/IIT-CS425/releases/download/proj-deliverable4/cta-1.0.jar)
  &ndash; JRE executable that requires version 17+.

### Tech stack

- *JavaFX* GUI:
  - Reactive interface, texts are updated real-time with `Observable`.
  - Handles file import/export with native picker.
- *Kotlin* language and buildscript.
- [Ktorm](https://www.ktorm.org/), use SQL with Kotlin DSL.

## Problem 2

> Extra points will be awarded to a group that tests a variety of challenging
  SQL usage (such as set operations, aggregate functions, set membership, set
  comparison, subqueries using WITH clause, etc.).

### Sample 1

At the start of use, there is a MySQL login prompt. This dialog remembers user
input by saving it to local preferences. Admittedly not the best choice to store
a password, but security has never been a strong focus of this assignment.

![Login dialog.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/sample1.png)

### Sample 2

Password are obfuscated with `SHA-256` hash function, the string will be
generated up to 64 length.

![Conductor table dialog.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/sample2_1.png)

![Reset password dialog.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/sample2_2.png)

![Reset password result.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/sample2_3.png)

### Sample 3

Adding an alert requires conductor's credential. The password entered here will
also be hashed and compared to `db.conductors[i].password`, which is already
hashed.

![Add alert dialog.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/sample3_1.png)

![View alert dialog.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/sample3_2.png)

## Problem 3

> Further, interesting use cases that can be evaluated with sample queries will
  earn extra points.

## Extra

> #### Old ER diagram
>
> <img width="480" src="https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/er3.png"/>

### New ER diagram

![The ER diagram stage 4.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/er4.png)

[View diagram file](https://github.com/hendraanggrian/IIT-CS425/blob/main/cta/er.drawio)

> #### Old UML diagram
>
> <img width="480" src="https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/uml3.png"/>

### New UML diagram

![The ER diagram stage 4.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/uml4.png)

[View diagram file](https://github.com/hendraanggrian/IIT-CS425/blob/main/cta/uml.drawio)

### SQL initialization

```sql
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
```

[View full code](https://github.com/hendraanggrian/IIT-CS425/blob/main/cta/initialize4.sql)
/ [data](https://github.com/hendraanggrian/IIT-CS425/blob/main/cta/data4.sql)

## Checklist

- Main objectives:
  - [x] Create desktop application.
  - [x] Create full database diagram.
- SQL commands improvement:
  - [x] Add restriction/cascading when deleting/updating.
  - [x] Add `CHECK` contraints to restrict bad input.
  - [x] Add nullability check.
  - [x] Add default values.
- Possible schema imporovement:
  - [ ] ~~Support traveling by bus, doesn't add many tables but massively change
    the structure of existing tables~~.
  - [x] Support membership with weekly and/or monthly payment, potentially
    adding 2-3 more tables.
