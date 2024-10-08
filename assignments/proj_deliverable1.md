# [Project deliverable 1](https://github.com/hanggrian/IIT-CS425/blob/assets/assignments/proj.pdf): *Chicago Transit Authority (CTA)*

> **Conceptual model**: Create a conceptual model consisting of the most
  essential information required for the selected application. Explore the
  website of the selected application to identify the relevant data,
  relationships, semantics, constraints, users, and users’ needs, and
  interesting use cases. Represent the conceptual model using the Entity
  relationship - Diagram (ERD) showing the most important entities and
  relationships.

## ER diagram

![The ER diagram stage 1.](https://github.com/hanggrian/IIT-CS425/raw/assets/cta/er1.svg)

[View diagram file](https://github.com/hanggrian/IIT-CS425/blob/main/cta/er.drawio)

### Rules

- A train need **one** locomotive (engine) to run, the locomotives can be used
  in **many** trains.
- A train can carry **many** wagons (railcars), the wagons can be used in
  **many** trains.
- A train is controlled by **one** conductor, the conductor control **many**
  trains.
- A passenger enter **one** train, the train can be boarded by **many**
  passengers.
- A track consist of **many** stations, a station can only be registered to
  **one** track.
- A track can be passed by **many** trains, a train is only registered to
  **one** track.

## Notes

- **WagonRegistrations** is a bridge table for many-to-many relationship
  between **Wagons** and **Trains**.
- New entry is added to **Passengers** whenever a train is boarded, transferring
  between lines cause multiple entries.
- Every entry of **Locomotives** and **Wagons** are tied to a real-world object.
  While **Trains** only represent a combination of them, new combinations are
  created every day.
- Transferrable stations require multiple entries into **Stations**.

## SQL initialization

```sql
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
```

[View full code](https://github.com/hanggrian/IIT-CS425/blob/main/cta/initialize1.sql)
/ [data](https://github.com/hanggrian/IIT-CS425/blob/main/cta/data1.sql)

## Checklist

- Main objectives:
  - [ ] Create full database diagram.
- SQL commands improvement:
  - [ ] Add `CHECK` contraints to restrict bad input.
  - [ ] Add nullability check.
  - [ ] Add default values.
- Possible schema imporovement:
  - [ ] Support membership with weekly and/or monthly payment, potentially
        adding 2-3 more tables.
  - [ ] Support traveling by bus, doesn't add many tables but massively change
        the structure of existing tables.
