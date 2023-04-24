# [Project Deliverable 3](https://github.com/hendraanggrian/IIT-CS425/blob/assets/assignments/proj.pdf): *Chicago Transit Authority (CTA)*

> **Physical model**: Implement the logical model using a suitable relational
  DBMS. Create the database in the database system using general Data-definition
  language (DDL) statements. Load mock data (using free online data generator
  tools) into the database to test a variety of SQL commands.
>
> | Rubric | Bad | Good | Great | Total |
> | --- | ---: | ---: | ---: | ---: |
> | A student has created the database and the necessary relations using SQL DDL commands. | 0-1 | 2-3 | 4-5 | 5 |
> | The exact Logical model (relation schemas) submitted in 2nd deliverable has been fully implemented in MySQL or PostgreSQL. The student needs to clearly state if the logical model implemented differs from that of the 2nd deliverable. | 0-2 | 4-6 | 7-10 | 10 |
> | At least 25 records (25 or more) have been loaded into each relation of the database. | 0-2 | 4-6 | 7-10 | 10 |
> | A student has tested at least 10 different types of queries and has provided: 1) the query statement, 2) the SQL commands, and 3) the query output per query. | 0-1 | 2-3 | 4-5 | 5 |

> ### Old ER diagram
>
> <img width="480" src="https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/er2.png"/>

## New ER diagram

![The ER diagram stage 3.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/er3.png)

[View diagram file](https://github.com/hendraanggrian/IIT-CS425/blob/main/cta/er.drawio)

> ### Old UML diagram
>
> <img width="480" src="https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/uml2.png"/>

## New UML diagram

![The UML diagram stage 3.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/uml3.png)

[View diagram file](https://github.com/hendraanggrian/IIT-CS425/blob/main/cta/uml.drawio)

## SQL initialization

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
```

[View full code](https://github.com/hendraanggrian/IIT-CS425/blob/main/cta/initialize3.sql)
/ [data](https://github.com/hendraanggrian/IIT-CS425/blob/main/cta/data3.sql)

## SQL commands

### Command 1

Tracks are based on actual tracks.

```sql
INSERT INTO Tracks VALUES
  ('Red', 0),
  ('Blue', 1),
  ('Brown', 0),
  ('Green', 0),
  ('Orange', 0),
  ('Pink', 0),
  ('Purple', 0),
  ('Yellow', 0),
  ('Garnet', 0),
  ('Amethyst', 0),
  ('Bloodstone', 0),
  ('Sapphire', 0),
  ('Agate', 0),
  ('Emerald', 0),
  ('Onyx', 0),
  ('Carnelian', 0),
  ('Peridot', 0),
  ('Beryl', 0),
  ('Topaz', 0),
  ('Ruby', 0),
  ('Opal', 0),
  ('Aquamarine', 0),
  ('Jade', 0),
  ('Obsidian', 0),
  ('Amber', 0);
```

![Screenschot for answer 1.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/data1.png)

### Command 2

Stations are based on [L map](https://www.transitchicago.com/assets/1/6/ctamap_Lsystem.png).

```sql
INSERT INTO Stations VALUES
  ('Purple', 'Linden', 42.0737, 87.6905, '349 Linden Avenue', '60091', 1, 1),
  ('Purple', 'Foster', 42.0541, 87.6836, '900 Foster Street', '60201', 0, 0),
  ('Purple', 'Howard', 42.0188, 87.6725, '7519 North Paulina Street', '60626', 1, 1),
  ('Yellow', 'Dempster-Skokie', 42.0403, 87.7523, '5005 Dempster Street', '60077', 1, 1),
  ('Yellow', 'Howard', 42.0188, 87.6725, '7519 North Paulina Street', '60626', 1, 1),
  ('Brown', 'Kimball', 41.9676, 87.7129, '4755 North Kimball Avenue', '60625', 1, 1),
  ('Brown', 'Paulina', 41.9437, 87.6708, '3410 North Lincoln Avenue', '60657', 1, 0),
  ('Brown', 'Belmont', 41.9395, 87.6533, '945 West Belmont Avenue', '60657', 1, 0),
  ('Red', 'Howard', 42.0188, 87.6725, '7519 North Paulina Street', '60626', 1, 1),
  ('Red', 'Sheridan', 41.9538, 87.6552, '3940 North Sheridan Road', '60613', 0, 0),
  ('Red', 'Belmont', 41.9395, 87.6533, '945 West Belmont Avenue', '60657', 1, 0),
  ('Red', 'Garfield', 41.7954, 87.6311, '220 West Garfield Boulevard', '60609', 1, 0),
  ('Orange', 'Midway', 41.7866, 87.7378, '4612 West 59th Street', '60629', 1, 1),
  ('Orange', 'Halsted', 41.8467, 87.6480, '2520 South Archer Avenue', '60608', 1, 1),
  ('Orange', 'Roosevelt', 41.8674, 87.6266, '1167 South State Street', '60605', 1, 0),
  ('Green', 'Clark-Lake', 41.8857, 87.6308, '100-124 West Lake Street', '60601', 1, 0),
  ('Green', 'Roosevelt', 41.8674, 87.6266, '1167 South State Street', '60605', 1, 0),
  ('Green', 'Ashland/63rd', 41.7794, 87.6639, '1167 South State Street', '60636', 1, 1),
  ('Blue', "O'Hare", 41.9811, 87.9008, "1000 O'Hare Drive", '60666', 1, 1),
  ('Blue', 'Irving Park', 41.9529, 87.7292, '	4131 West Irving Park Road', '60641', 0, 0),
  ('Blue', 'Clark-Lake', 41.8857, 87.6308, '100-124 West Lake Street', '60601', 1, 0),
  ('Blue', 'LaSalle', 41.8755, 87.6317, '150 West Ida B. Wells Drive', '60605', 0, 0),
  ('Pink', 'LaSalle', 41.8755, 87.6317, '150 West Ida B. Wells Drive', '60605', 0, 0),
  ('Pink', 'Polk', 41.8715, 87.6695, '1713 West Polk Street', '60612', 1, 0),
  ('Pink', 'Pulaski', 41.7997, 87.7244, '5106 South Pulaski Road', '60632', 1, 0);
```

![Screenschot for answer 2.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/data2.png)

### Command 3

```sql
INSERT INTO Conductors VALUES
  ('marsh1', DEFAULT, 'Stan Marsh', '1992-10-19', 2023 - 1992, '202-555-0148'),
  ('broflovski1', DEFAULT, 'Kyle Broflovski', '1992-05-26', 2023 - 1992, '202-555-0104,202-555-0168'),
  ('cartman1', DEFAULT, 'Eric Cartman', '1992-04-13', 2023 - 1992, '202-555-0171'),
  ('mccormick1', DEFAULT, 'Kenny McCormick', '1992-04-22', 2023 - 1992, '202-555-0174,202-555-0103'),
  ('stotch1', DEFAULT, 'Butters Stotch', '1997-08-13', 2023 - 1997, '202-555-0178'),
  ('marsh2', DEFAULT, 'Randy Marsh', '1997-01-01', 2023 - 1997, '202-555-0199,202-555-0166'),
  ('garrison1', DEFAULT, 'Herbert Garrison', '1997-01-01', 2023 - 1997, '202-555-0178'),
  ('mackey1', DEFAULT, 'Mr. Mackey', '1997-01-01', 2023 - 1997, '202-555-0166'),
  ('broflovski2', DEFAULT, 'Gerald Broflovski', '1997-01-01', 2023 - 1997, '202-555-0148'),
  ('broflovski3', DEFAULT, 'Sheila Broflovski', '1997-01-01', 2023 - 1997, '202-555-0185'),
  ('cartman2', DEFAULT, 'Liane Cartman', '1997-01-01', 2023 - 1997, '202-555-0178,202-555-0166'),
  ('valmer1', DEFAULT, 'Jimmy Valmer', '2001-01-01', 2023 - 2001, '202-555-0166'),
  ('black1', DEFAULT, 'Tolkien Black', '1997-01-01', 2023 - 1997, '202-555-0199'),
  ('testaburger1', DEFAULT, 'Wendy Testaburger', '1995-01-01', 2023 - 1995, '202-555-0166'),
  ('donovan1', DEFAULT, 'Clyde Donovan', '1997-01-01', 2023 - 1997, '202-555-0185,202-555-0178'),
  ('tucker1', DEFAULT, 'Craig Tucker', '1997-01-01', 2023 - 1997, '202-555-0114'),
  ('stevens1', DEFAULT, 'Bebe Stevens', '1994-01-01', 2023 - 1994, '202-555-0163'),
  ('turner1', DEFAULT, 'Heidi Turner', '1994-01-01', 2023 - 1994, '202-555-0178'),
  ('malkinson1', DEFAULT, 'Scott Malkinson', '1994-01-01', 2023 - 1994, '202-555-0161'),
  ('burch1', DEFAULT, 'Timmy Burch', '1994-01-01', 2023 - 1994, '202-555-0192'),
  ('tweak1', DEFAULT, 'Tweek Tweak', '1994-01-01', 2023 - 1994, '202-555-0199'),
  ('principal1', DEFAULT, 'PC Principal', '1994-01-01', 2023 - 1994, ''),
  ('woman1', DEFAULT, 'Strong Woman', '1994-01-01', 2023 - 1994, '202-555-0185'),
  ('marsh3', DEFAULT, 'Sharon Marsh', '1994-01-01', 2023 - 1994, '202-555-0114'),
  ('marsh4', DEFAULT, 'Shelley Marsh', '1994-01-01', 2023 - 1994, '202-555-0166');
```

![Screenschot for answer 3.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/data3.png)

### Command 4

Alerts are based on [rail status](https://www.transitchicago.com/travel-information/railstatus/) & [elevator alerts](https://www.transitchicago.com/alerts/elevators/).

```sql
INSERT INTO Alerts VALUES
  (NULL, 'Berwyn Station Temporary Closure', 'Berwyn station is temporarily closed. Please use the adjacent stations at Bryn Mawr or Argyle. 92 Foster bus rerouted to Bryn Mawr station.',
    '2021-05-16', NULL, 'Red', 'marsh1'),
  (NULL, 'Lawrence Station Temporary Closure', 'Lawrence station is temporarily closed. Please use the adjacent stations at Argyle or Wilson. 81 Lawrence bus rerouted to connect to Wilson station.',
    '2021-05-16', NULL, 'Red', 'broflovski1'),
  (NULL, 'Red and Purple Line Trains Share Track between Thorndale and Belmont (Updated)', 'Red and Purple line trains share tracks btwn Thorndale and Belmont. Purple Line Express trains continue to make only express stops between Howard and Belmont.',
    '2021-05-16', NULL, 'Red', 'cartman1'),
  (NULL, 'Argyle Temporary Station and Entrance Opens', 'The current Argyle station entrance is closed. The new, temporary Argyle station entrance is next to the closed one, 50 feet west.',
    '2021-05-16', NULL, 'Red', 'mccormick1'),
  (NULL, 'Bryn Mawr Temporary Station Opens', "New, temporary Bryn Mawr station is open. Separate entry for each direction: Howard-bnd enter thru old entrance; 95th-bnd enter on B'way north of Bryn Mawr.",
    '2021-05-16', NULL, 'Red', 'stotch1'),
  (NULL, 'Boarding Change at Belmont', 'At Belmont station, Kimball-bound Brown Line trains resume stopping on outer track; Linden-bound Purple Line Exp trains continue to board/exit on inner track.',
    '2021-11-19', NULL, 'Brown', 'marsh2'),
  (NULL, 'Service for 2023 Cubs Weekday Night Games and Wrigley Field Concerts', 'Loop-bound Purple Line Express trains will stop at Sheridan for all weekday Cubs night games and Wrigley Field concerts.',
    '2023-04-10', '2023-09-21', 'Purple', 'garrison1'),
  (NULL, 'Boarding Change at Belmont', 'Board/exit Loop-bound Purple Line Express trains on the Red Line side of the 95th- and Loop-bound platform at the Belmont station.',
    '2022-01-17', NULL, 'Purple', 'mackey1'),
  (NULL, 'Boarding Change at Belmont', 'At Belmont station, Kimball-bound Brown Line trains resume stopping on outer track; Linden-bound Purple Line Exp trains continue to board/exit on inner track.',
    '2021-11-19', NULL, 'Purple', 'broflovski2'),
  (NULL, 'Red and Purple Line Trains Share Track between Thorndale and Belmont (Updated)', 'Red and Purple line trains share tracks btwn Thorndale and Belmont. Purple Line Express trains continue to make only express stops between Howard and Belmont.',
    '2021-05-16', NULL, 'Purple', 'broflovski3'),
  (NULL, 'Service for 2023 Cubs Night Games and Wrigley Field Concerts', 'Later Yellow Line service from Howard runs for Cubs night games/Wrigley Field concerts. Skokie-bound trains run will until 12am on game and concert nights.',
    '2023-04-10', '2023-09-21', 'Yellow', 'cartman2'),
  (NULL, 'Service for 2023 Cubs Weekday Night Games and Wrigley Field Concerts', 'Loop-bound Purple Line Express trains will stop at Sheridan for all weekday Cubs night games and Wrigley Field concerts.',
    '2023-04-10', '2023-09-21', 'Purple', 'valmer1'),
  (NULL, 'Boarding Change at Belmont', 'Board/exit Loop-bound Purple Line Express trains on the Red Line side of the 95th- and Loop-bound platform at the Belmont station.',
    '2022-01-17', NULL, 'Purple', 'black1'),
  (NULL, 'Boarding Change at Belmont', 'At Belmont station, Kimball-bound Brown Line trains resume stopping on outer track; Linden-bound Purple Line Exp trains continue to board/exit on inner track.',
    '2022-11-19', NULL, 'Purple', 'testaburger1'),
  (NULL, 'Red and Purple Line Trains Share Track between Thorndale and Belmont (Updated)', 'Red and Purple line trains share tracks btwn Thorndale and Belmont. Purple Line Express trains continue to make only express stops between Howard and Belmont.',
    '2022-05-16', NULL, 'Purple', 'donovan1'),
  (NULL, 'Roosevelt Station Pedway-to-Street Elevator Temporarily Out of Service', 'Pedway-to-street elevator in the Roosevelt stn transfer tunnel between the Green/Orange elevated lines and the Red Line subway is temporarily out of service.',
    '2023-01-12', NULL, 'Red', 'tucker1'),
  (NULL, 'Elevator at 63rd (Red Line) Temporarily Out-of-Service', 'The elevator at 63rd (Red Line) is temporarily out-of-service due to an electrical problem.',
    '2023-04-10', NULL, 'Red', 'stevens1'),
  (NULL, 'Elevator at 95th/Dan Ryan Temporarily Out-of-Service', 'The North Terminal elevator to/from platform at 95th/Dan Ryan (Red Line) is temporarily out-of-service because of vandalism or abuse of the elevator.',
    '2023-07-23', NULL, 'Red', 'turner1'),
  (NULL, 'Elevator at Paulina Back in Service', 'The Loop-bound platform elevator at Paulina (Brown Line) is back in service.',
    '2023-04-11', NULL, 'Brown', 'malkinson1'),
  (NULL, 'Elevator at Irving Park Temporarily Out-of-Service', 'The Loop-bound platform elevator at Irving Park (Brown Line) is temporarily out-of-service.',
    '2023-04-11', NULL, 'Brown', 'burch1'),
  (NULL, 'Roosevelt Station Pedway-to-Street Elevator Temporarily Out of Service', 'Pedway-to-street elevator in the Roosevelt stn transfer tunnel between the Green/Orange elevated lines and the Red Line subway is temporarily out of service.',
    '2023-01-12', NULL, 'Green', 'tweak1'),
  (NULL, 'Elevator at Pulaski Temporarily Out-of-Service', 'The 63rd-bound platform elevator at Pulaski (Green Line) is temporarily out-of-service due to its doors needing repair & vandalism.',
    '2023-04-09', NULL, 'Green', 'principal1'),
  (NULL, 'Elevator at Conservatory-Central Park Drive Temporarily Out-of-Service', 'The Harlem-bound platform elevator at Conservatory (Green Line) is temporarily out-of-service.',
    '2023-04-11', NULL, 'Green', 'woman1'),
  (NULL, 'Elevator at California Temporarily Out-of-Service', 'The elevator to/from street at California (Green Line) is temporarily out-of-service due to an electrical problem.',
    '2023-04-13', '2023-04-15', 'Green', 'marsh3'),
  (NULL, 'Roosevelt Station Pedway-to-Street Elevator Temporarily Out of Service', 'Pedway-to-street elevator in the Roosevelt stn transfer tunnel between the Green/Orange elevated lines and the Red Line subway is temporarily out of service.',
    '2023-01-12', NULL, 'Orange', 'marsh4');
```

![Screenschot for answer 4.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/data4.png)

### Command 5

```sql
INSERT INTO Locomotives VALUES
  ('0100', 1982),
  ('0200', 2001),
  ('0300', 1992),
  ('0400', 1964),
  ('0500', 1996),
  ('0600', 2000),
  ('0700', 1998),
  ('0800', 1993),
  ('0900', 1982),
  ('1000', 2001),
  ('1100', 1992),
  ('1200', 1964),
  ('1300', 1996),
  ('1400', 2000),
  ('1500', 1998),
  ('1600', 2004),
  ('1700', 1964),
  ('1800', 1973),
  ('1900', 1982),
  ('2000', 1979),
  ('2100', 1966),
  ('2200', 1946),
  ('2300', 1972),
  ('2400', 1903),
  ('2500', 2003);
```

![Screenschot for answer 5.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/data5.png)

### Command 6

```sql
INSERT INTO Wagons VALUES
  ('0101', 50), ('0102', 50),
  ('0201', 60),
  ('0301', 65), ('0302', 50),
  ('0401', 45), ('0402', 60), ('0403', 40),
  ('0501', 45), ('0502', 40),
  ('0601', 30), ('0602', 35), ('0603', 35),
  ('0701', 50), ('0702', 45),
  ('0801', 30), ('0802', 45),
  ('0901', 25), ('0902', 20), ('0903', 35), ('0904', 25),
  ('1001', 45), ('1002', 50), ('1003', 65),
  ('1101', 35), ('1102', 60),
  ('1201', 45), ('1202', 45), ('1203', 55),
  ('1301', 50), ('1302', 50),
  ('1401', 35), ('1402', 50),
  ('1501', 40),
  ('1601', 50), ('1602', 60),
  ('1701', 35), ('1702', 50), ('1703', 50),
  ('1801', 45), ('1802', 40),
  ('1901', 50), ('1902', 60),
  ('2001', 55),
  ('2101', 35),
  ('2201', 50), ('2202', 45),
  ('2301', 35), ('2302', 50),
  ('2401', 65), ('2402', 40), ('2403', 40),
  ('2501', 40), ('2502', 50);
```

![Screenschot for answer 6.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/data6.png)

### Command 7

```sql
INSERT INTO Trains VALUES
  (1, 'Red', '0100', 'marsh1'),
  (2, 'Red', '0200', 'broflovski1'),
  (3, 'Red', '0300', 'cartman1'),
  (4, 'Red', '0400', 'mccormick1'),
  (5, 'Blue', '0500', 'stotch1'),
  (6, 'Blue', '0600', 'marsh2'),
  (7, 'Blue', '0700', 'garrison1'),
  (8, 'Blue', '0800', 'mackey1'),
  (9, 'Brown', '0900', 'broflovski2'),
  (10, 'Brown', '1000', 'broflovski3'),
  (11, 'Brown', '1100', 'cartman2'),
  (12, 'Green', '1200', 'valmer1'),
  (13, 'Green', '1300', 'black1'),
  (14, 'Green', '1400', 'testaburger1'),
  (15, 'Orange', '1500', 'donovan1'),
  (16, 'Orange', '1600', 'tucker1'),
  (17, 'Orange', '1700', 'stevens1'),
  (18, 'Pink', '1800', 'turner1'),
  (19, 'Pink', '1900', 'malkinson1'),
  (20, 'Pink', '2000', 'burch1'),
  (21, 'Purple', '2100', 'tweak1'),
  (22, 'Purple', '2200', 'principal1'),
  (23, 'Purple', '2300', 'woman1'),
  (24, 'Yellow', '2400', 'marsh3'),
  (25, 'Yellow', '2500', 'marsh4');
```

![Screenschot for answer 7.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/data7.png)

### Command 8

```sql
INSERT INTO Railcars VALUES
  (1, '0101'), (1, '0102'),
  (2, '0201'),
  (3, '0301'), (3, '0302'),
  (4, '0401'), (4, '0402'), (4, '0403'),
  (5, '0501'), (5, '0502'),
  (6, '0601'), (6, '0602'), (6, '0603'),
  (7, '0701'), (7, '0702'),
  (8, '0801'), (8, '0802'),
  (9, '0901'), (9, '0902'), (9, '0903'), (9, '0904'),
  (10, '1001'), (10, '1002'), (10, '1003'),
  (11, '1101'), (11, '1102'),
  (12, '1201'), (12, '1202'), (12, '1203'),
  (13, '1301'), (13, '1302'),
  (14, '1401'), (14, '1402'),
  (15, '1501'),
  (16, '1601'), (16, '1602'),
  (17, '1701'), (17, '1702'), (17, '1703'),
  (18, '1801'), (18, '1802'),
  (19, '1901'), (19, '1902'),
  (20, '2001'),
  (21, '2101'),
  (22, '2201'), (22, '2202'),
  (23, '2301'), (23, '2302'),
  (24, '2401'), (24, '2402'), (24, '2403'),
  (25, '2501'), (25, '2502');
```

![Screenschot for answer 8.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/data8.png)

### Command 9

```sql
INSERT INTO Passengers VALUES
  (1, 'Philip J. Fry'),
  (2, 'Turanga Leela'),
  (3, 'Bender Bending Rodriguez'),
  (4, 'Hubert J. Farnsworth'),
  (5, 'John Zoidberg'),
  (6, 'Amy Wong'),
  (7, 'Hermes Conrad'),
  (8, 'Nibbler'),
  (9, 'Zapp Brannigan'),
  (10, 'Kif Kroker'),
  (11, 'Mom'),
  (12, 'Headless Body of Agnew'),
  (13, 'Boxy'),
  (14, 'Brain Slugs'),
  (15, 'Brain Spawn'),
  (16, 'Calculon'),
  (17, 'Antonio Calculon'),
  (18, 'The Crushinator'),
  (19, 'Father Changstein-El-Gamal'),
  (20, 'Chanukah Zombie'),
  (21, 'Clamps'),
  (22, 'Dwight Conrad'),
  (23, 'LaBarbara Conrad'),
  (24, 'Donbot'),
  (25, 'Abner Doubledeal');
```

![Screenschot for answer 9.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/data9.png)

### Command 10

```sql
INSERT INTO Passes VALUES
  (1, '2020-01-03', '2020-06-03', 1),
  (2, '2020-01-06', '2020-09-06', 2),
  (3, '2020-01-10', '2020-05-10', 3),
  (4, '2020-02-28', '2020-05-28', 4),
  (5, '2020-03-09', '2021-06-09', 5),
  (6, '2021-03-13', '2021-07-13', 6),
  (7, '2021-04-25', '2021-06-25', 7),
  (8, '2021-05-10', '2021-07-10', 8),
  (9, '2021-05-23', '2021-06-23', 9),
  (10, '2021-06-29', '2021-08-29', 10),
  (11, '2021-07-06', '2022-08-06', 11),
  (12, '2022-07-14', '2022-09-14', 12),
  (13, '2022-07-31', '2022-10-31', 13),
  (14, '2022-08-25', '2022-09-25', 14),
  (15, '2022-09-05', '2022-10-05', 15),
  (16, '2022-09-21', '2022-11-21', 16),
  (17, '2022-10-09', '2022-11-09', 17),
  (18, '2022-10-11', '2022-12-11', 18),
  (19, '2022-10-24', '2022-11-24', 19),
  (20, '2022-11-03', '2023-12-03', 20),
  (21, '2023-11-16', '2024-01-16', 21),
  (22, '2023-11-29', '2024-02-29', 22),
  (23, '2023-12-13', '2024-01-13', 23),
  (24, '2023-12-21', '2024-02-21', 24),
  (25, '2023-12-27', '2024-03-27', 25);
```

![Screenschot for answer 10.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/data10.png)

### Command 11

```sql
INSERT INTO Trips VALUES
  ('2020-01-04', 1, NULL, 1, 'Red', 'Howard', 'Belmont'),
  ('2020-02-12', 2, NULL, 2, 'Brown', 'Belmont', 'Paulina'),
  ('2021-01-23', 3, 3, NULL, 'Purple', 'Linden', 'Foster'),
  ('2021-02-12', 4, NULL, 4, 'Brown', 'Kimball', 'Belmont'),
  ('2021-04-17', 5, NULL, 5, 'Blue', "O'Hare", 'Irving Park'),
  ('2021-04-29', 6, 2, NULL, 'Blue', 'LaSalle', 'Clark-Lake'),
  ('2021-06-27', 7, NULL, 7, 'Blue', 'LaSalle', 'Irving Park'),
  ('2021-08-27', 8, NULL, 8, 'Green', 'Clark-Lake', 'Roosevelt'),
  ('2021-09-21', 9, NULL, 9, 'Green', 'Ashland/63rd', 'Roosevelt'),
  ('2021-11-28', 10, NULL, 10, 'Orange', 'Midway', 'Halsted'),
  ('2021-11-02', 11, 3, NULL, 'Orange', 'Roosevelt', 'Halsted'),
  ('2022-02-06', 12, NULL, 12, 'Red', 'Belmont', 'Garfield'),
  ('2022-04-27', 13, NULL, 13, 'Pink', 'Polk', 'Pulaski'),
  ('2022-05-08', 14, NULL, 14, 'Pink', 'LaSalle', 'Pulaski'),
  ('2022-05-19', 15, 2, NULL, 'Pink', 'Pulaski', 'Polk'),
  ('2022-06-21', 16, NULL, 16, 'Yellow', 'Dempster-Skokie', 'Howard'),
  ('2022-09-05', 17, NULL, 17, 'Yellow', 'Howard', 'Dempster-Skokie'),
  ('2022-10-01', 18, NULL, 18, 'Brown', 'Paulina', 'Kimball'),
  ('2022-10-23', 19, NULL, 19, 'Red', 'Sheridan', 'Garfield'),
  ('2022-12-11', 20, 2, NULL, 'Green', 'Roosevelt', 'Clark-Lake'),
  (DEFAULT, 21, 2, NULL, 'Blue', "O'Hare", 'Clark-Lake'),
  (DEFAULT, 22, NULL, 22, 'Blue', 'Irving Park', 'Clark-Lake'),
  (DEFAULT, 23, NULL, 23, 'Green', 'Roosevelt', 'Ashland/63rd'),
  (DEFAULT, 24, 3, NULL, 'Purple', 'Linden', 'Howard'),
  (DEFAULT, 25, NULL, 25, 'Purple', 'Howard', 'Foster');
```

![Screenschot for answer 11.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/data11.png)

## SQL statements

### Statement 1

**Operable tracks**: List how many tracks are actively in use.

```sql
SELECT CONCAT(`track`, IF(`is_24h`, ' (24h)', '')) AS `Active stations`
 FROM Tracks WHERE `track` IN(SELECT `track` FROM Stations);
```

![Screenschot for answer 1.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/statement1.png)

### Statement 2

**Station's route**: Blue line is the most used track of CTA, list its route and
intersections with other lines.

```sql
SELECT `track`, `station`, `location`, `zip` FROM Stations
  WHERE `station` IN(SELECT `station` FROM Stations WHERE `track` = 'Blue');
```

![Screenschot for answer 2.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/statement2.png)

### Statement 3

**Station's infrastructure**: CTA strives for building quality, here's a
statistics of how many infrastructure are in place.

```sql
SELECT CONCAT('Elevator ', AVG(`has_elevator`) * 100, '%')
  AS `Infrastructure rating` FROM Stations UNION
SELECT CONCAT('Parking ', AVG(`has_parking`) * 100, '%')
  AS `Infrastructure rating` FROM Stations;
```

![Screenschot for answer 3.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/statement3.png)

### Statement 4

**Old alerts**: Service alerts can have indefinite running time. List really old
alerts.

```sql
SELECT `track`, `title`, `date_start` FROM Alerts
  WHERE `date_start` < '2022-01-01' AND `date_end` IS NULL;
```

![Screenschot for answer 4.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/statement4.png)

### Statement 5

**Punish lazy conductors**: Maintaining service alert is the responsibility of
the employee who create it. Notify those employees.

```sql
SELECT CONCAT(`name`, ' (', `username`, ')') AS `Lazy employees` FROM Conductors
  WHERE `username` IN(SELECT `username` FROM Alerts
    WHERE `date_start` < '2022-01-01' AND `date_end` IS NULL);
```

![Screenschot for answer 5.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/statement5.png)

### Statement 6

**Old trains**: Old trains are prone to accidents and need to be decommisioned.

```sql
SELECT * FROM Trains AS T LEFT JOIN Locomotives AS L
  ON T.`serial_no` = L.`serial_no`
  WHERE `since` < 1980;
```

![Screenschot for answer 6.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/statement6.png)

### Statement 7

**Train status**: Common attributes of a train: track, conductor, and how many
wagons it carries.

```sql
SELECT R.`train_id`, `track`, `username`, COUNT(R.`train_id`) AS `Wagon count`
  FROM Railcars AS R LEFT JOIN Trains AS T
  ON R.`train_id` = T.`train_id` GROUP BY `train_id`;
```

![Screenschot for answer 7.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/statement7.png)

### Statement 8

**Heaviest trains**: Each wagon contain different number of seats, calculate the
total.

```sql
SELECT R.`train_id`, SUM(W.`seats`) AS `Seat capacity`
  FROM Railcars AS R LEFT JOIN Wagons AS W
  ON R.`wagon_id` = W.`wagon_id` GROUP BY `train_id`;
```

![Screenschot for answer 8.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/statement8.png)

### Statement 9

**Pass popularity**: Discover how many of CTA users have ongoing passes.

```sql
SELECT `pass_id`, `date_start`, P1.`passenger_id`, `name` FROM Passes AS P1
  LEFT JOIN Passengers AS P2
  ON P1.`passenger_id` = P2.`passenger_id`
  WHERE `date_end` >= '2023-01-01';
```

![Screenschot for answer 9.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/statement9.png)

### Statement 10

**Passenger engagement**: Discover how many CTA customers have used the service
within this year.

```sql
SELECT CONCAT(`passenger_id`, '. ', `name`) AS `Recent customers`
  FROM Passengers WHERE `passenger_id`
    IN(SELECT `passenger_id` FROM Trips WHERE `timestamp` >= '2023-01-01');
```

![Screenschot for answer 10.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/statement10.png)

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
  - [x] Support membership with weekly and/or monthly payment, potentially
    adding 2-3 more tables.
