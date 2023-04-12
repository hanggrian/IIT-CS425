# [Project Deliverable 3](https://github.com/hendraanggrian/IIT-CS425/blob/assets/assignments/proj.pdf): CTA

> Implement the logical model using a suitable relational DBMS. Create the
  database in the database system using general Data-definition language (DDL)
  statements. Load mock data (using free online data generator tools) into the
  database to test a variety of SQL commands.

## Problem 1

> Create the database in the database system using general Data-definition
  language (DDL) statements.

### SQL commands

```sql
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
  `message` VARCHAR(500) NOT NULL,
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
    ON DELETE RESTRICT ON UPDATE RESTRICT
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
```

[View full code](https://github.com/hendraanggrian/IIT-CS425/blob/main/cta/initialize3.sql)
/ [data](https://github.com/hendraanggrian/IIT-CS425/blob/main/cta/data3.sql)

## Problem 2

> Load mock data (using free online data generator tools) into the database to
  test a variety of SQL commands.

### SQL commands

```sql
INSERT INTO Conductors VALUES
  ('stan', DEFAULT, 'Stan Marsh', '1988-10-19', 2023 - 1988, '202-555-0148'),
  ('kyle', DEFAULT, 'Kyle Broflovski', '1988-05-26', 2023 - 1988, '202-555-0104,202-555-0168'),
  ('eric', DEFAULT, 'Eric Cartman', '1988-04-13', 2023 - 1988, '202-555-0171'),
  ('kenny', DEFAULT, 'Kenny McCormick', '1988-04-22', 2023 - 1988, '202-555-0174,202-555-0103');

INSERT INTO Alerts VALUES
  (NULL, 'New Schedules in Effect', 'Beginning Sun, March 26, updated schedules went into effect for all L lines. See transitchicago.com/schedules or schedules posted in stations.',
    '2023-03-26', NULL, 'eric'),
  (NULL, 'Berwyn Station Temporary Closure', 'Berwyn station is temporarily closed. Please use the adjacent stations at Bryn Mawr or Argyle. 92 Foster bus rerouted to Bryn Mawr station.',
    '2023-03-16', NULL, 'eric'),
  (NULL, 'Boarding Change at Belmont', 'At Belmont station, Kimball-bound Brown Line trains resume stopping on outer track; Linden-bound Purple Line Exp trains continue to board/exit on inner track.',
    '2023-02-19', '2023-02-28', 'eric'),
  (NULL, 'Service for 2023 Cubs Weekday Night Games and Wrigley Field Concerts', 'Loop-bound Purple Line Express trains will stop at Sheridan for all weekday Cubs night games and Wrigley Field concerts.',
    '2023-01-17', '2023-01-20', 'eric');

INSERT INTO Tracks VALUES
  ('Red'),
  ('Blue'),
  ('Brown'),
  ('Green'),
  ('Orange'),
  ('Pink'),
  ('Purple'),
  ('Yellow');

INSERT INTO Stations VALUES
  (42.0737, 87.6905, 'Purple', 'Linden', '349 Linden Avenue', '60091', 1, 1),
  (42.0188, 87.6725, 'Purple', 'Howard', '7519 North Paulina Street', '60626', 1, 1),
  (42.0403, 87.7523, 'Yellow', 'Dempster-Skokie', '5005 Dempster Street', '60077', 1, 1),
  (42.0188, 87.6725, 'Yellow', 'Howard', '7519 North Paulina Street', '60626', 1, 1),
  (41.9676, 87.7129, 'Brown', 'Kimball', '4755 North Kimball Avenue', '60625', 1, 1),
  (41.9395, 87.6533, 'Brown', 'Belmont', '945 West Belmont Avenue', '60657', 1, 0),
  (42.0188, 87.6725, 'Red', 'Howard', '7519 North Paulina Street', '60626', 1, 1),
  (41.9395, 87.6533, 'Red', 'Belmont', '945 West Belmont Avenue', '60657', 1, 0),
  (41.7866, 87.7378, 'Orange', 'Midway', '4612 West 59th Street', '60629', 1, 1),
  (41.8674, 87.6266, 'Orange', 'Roosevelt', '1167 South State Street', '60605', 1, 0),
  (41.8857, 87.6308, 'Green', 'Clark-Lake', '100-124 West Lake Street', '60601', 1, 0),
  (41.8674, 87.6266, 'Green', 'Roosevelt', '1167 South State Street', '60605', 1, 0),
  (41.8857, 87.6308, 'Blue', 'Clark-Lake', '100-124 West Lake Street', '60601', 1, 0),
  (41.8755, 87.6317, 'Blue', 'LaSalle', '150 West Ida B. Wells Drive', '60605', 0, 0),
  (41.8755, 87.6317, 'Pink', 'LaSalle', '150 West Ida B. Wells Drive', '60605', 0, 0),
  (41.7997, 87.7244, 'Pink', 'Pulaski', '5106 South Pulaski Road', '60632', 1, 0);

INSERT INTO Locomotives VALUES
  ('1000', 2004),
  ('2000', 1986),
  ('3000', 1998),
  ('4000', 1999);

INSERT INTO Wagons VALUES
  ('0001', 1988), ('0002', 1992), ('0003', 2008),
  ('0004', 1984), ('0005', 1989),
  ('0006', 2004), ('0007', 1992), ('0008', 1982),
  ('0009', 2002), ('0010', 1990);

INSERT INTO Trains VALUES
  (1, 'Purple', '1000', 'stan'),
  (2, 'Yellow', '2000', 'kyle'),
  (3, 'Brown', '3000', 'eric'),
  (4, 'Red', '4000', 'kenny');

INSERT INTO Railcars VALUES
  (1, '0001', '0002', '0003'),
  (2, '0004', '0005'),
  (3, '0006', '0007', '0008'),
  (4, '0009', '0010');

INSERT INTO Passengers VALUES
  (1, 'Randy Marsh'),
  (2, 'Gerald Broflovski'),
  (3, 'Liane Cartman'),
  (4, 'Stuart McCormick');

INSERT INTO Passes VALUES
  (1, '2023-01-01', '2024-01-01', 1),
  (2, '2023-02-03', '2023-03-03', 2),
  (3, '2023-03-02', '2023-03-09', 3),
  (4, '2023-02-01', '2023-04-01', 4);

INSERT INTO Trips VALUES
  (DEFAULT, 1, NULL, 1, 'Purple', 42.0737, 87.6905, 42.0188, 87.6725),
  (DEFAULT, 2, 3.0, NULL, 'Yellow', 42.0403, 87.7523, 42.0188, 87.6725),
  (DEFAULT, 3, 4.0, NULL, 'Brown', 41.9676, 87.7129, 41.9395, 87.6533),
  (DEFAULT, 4, 5.0, NULL, 'Red', 42.0188, 87.6725, 41.9395, 87.6533);
```

## Extra

## UML diagram 2

![The UML diagram stage 2.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/uml2.png)

[View diagram file](https://github.com/hendraanggrian/IIT-CS425/blob/main/cta/uml.drawio)

## Checklist

- Main objectives:
  - [ ] Create desktop application.
  - [x] Create full database diagram.
- SQL commands improvement:
  - [x] Add restriction/cascading when deleting/updating.
  - [x] Add `CHECK` contraints to restrict bad input.
  - [x] Add nullability check.
  - [x] Add default values.
- Possible schema imporovement:
  - [ ] Support traveling by bus, doesn't add many tables but massively change
    the structure of existing tables.
  - [x] Support membership with weekly and/or monthly payment, potentially adding
    2-3 more tables.
