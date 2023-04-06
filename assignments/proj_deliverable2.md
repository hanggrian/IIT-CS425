# [Project Deliverable 2](https://github.com/hendraanggrian/IIT-CS425/blob/assets/assignments/proj.pdf): CTA

> Develop a detailed ER-model for the application. Translate the conceptual
  model into a detailed logical model showing relational schema with appropriate
  data type, primary keys, foreign keys, and any constraints.

## Problem 1

> You are required to present a detailed ERD.

### ER diagram

![The ER diagram stage 2.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/er2.png)

[View diagram file](https://github.com/hendraanggrian/IIT-CS425/blob/main/cta/er.drawio)

### Rules

- A wagon lines up **0 or many** railcars, a railcar is created by **1 and 1**
  wagon.
- A train is made of **1 and 1** railcar, a railcar is tied to **1 and 1**
  train.
- A locomotive powers **0 or many** trains, a train is powered by
  **1 and 1** locomotive.
- A conductor controls **0 or many** trains, a train is supervised by
  **1 and 1** conductor.
- A conductor issues **0 or many** alerts, an alert is issued by **1 and 1**
  conductor.
- A track is travelled **0 or many** trains, a train is only designated to
  **1 and 1** railtrack.
- A track can have **0 or many** stations, a station is constructor specifically
  for **1 and 1** railtrack.
- A station is known to have **0 or many** trips, a trip is only good for
  **1 and 1** station.
- A passenger rides **0 or many** trips, a trip is paid under **1 and 1** name.
- A passenger subscribes to **0 or many** pass, a pass is tied to **1 and 1**
  passenger.
- A pass can optionally be used **1 and 1** trip.

## Problem 2

> You are required to present a logical structure/schema of the database.

### UML diagram

![The UML diagram stage 1.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/uml1.png)

[View diagram file](https://github.com/hendraanggrian/IIT-CS425/blob/main/cta/uml.drawio)

### *Conductors* & *Alerts*

A conductor is a CTA employee with 2 jobs: operating a train (mostly in
locomotive) and announcing service alerts.

| <small>Conductors</small><br>**social_sec** | <br>name | <br>birth |
| --- | --- | --- |
| **1234567890** | Jane Doe | 1990-01-01 |

Alerts are to be displayed in every station.

| <small>Alerts</small><br>**id** | <br>message | <br>date_start | <br>date_end | <br>*conductor_sec* |
| --- | --- | --- | --- | --- |
| **1** | Elevator maintenance at Damen. | 2023-03-28 | 2023-04-04 | *1234567890* |

### *Stations* & *Tracks*

A station is a train stop, transferrable station requires multiple entries.

| <small>Stations</small><br>**lat** | <br>**lng** | <br>name | <br>zip | <br>has_parking | <br>has_elevator | <br>*track_color* |
| --- | --- | --- | --- | --- | --- | --- |
| **41.9100** | **87.6780** | Damen | 60622 | FALSE | FALSE | *Blue* |

A track is a collection of stations.

| <small>Tracks</small><br>**color** | <br>is_24h |
| --- | --- |
| **Blue** | FALSE |

### *Locomotives*, *Wagons*, *Trains* & *Railcars*

Every entry into locomotives and wagons are tied into a real-world object. They
are individual parts of a train line.

| <small>Locomotives</small><br>**serial_no** | <br>since |
| --- | --- |
| **X101** | 1985 |

| <small>Wagons</small><br>**id** | <br>seats |
| --- | --- |
| **7501** | 40 |
| **7502** | 50 |

An entry of trains is a combination of a locomotive and wagons. railcars is a
bridge table that represent that relationship.

| <small>Trains</small><br>**id** | <br>*track_color* | <br>*locomotive_no* | <br>*conductor_sec* |
| --- | --- | --- | --- |
| **1** | *Blue* | *X101* | *1234567890* |

| <small>Railcars</small><br>**train_id** | <br>**wagon_id** |
| --- | --- |
| **1** | **7501** |
| **1** | **7502** |

### *Passengers*, *Trips* & *Passes*

The last section of the scheme is related to customers and payments. An entry
into passengers equalize a new *Ventra* account.

| <small>Passengers</small><br>**id** | <br>name |
| --- | --- |
| **1** | Hendra Anggrian |

A trip is a history of single travel. Each pass is potentially paid with a pass,
which is an equivalent of *Ventra UPass*.

| <small>Trips</small><br>**timestamp** | <br>**passenger_id** | <br>fare | <br>*station1_lat* | <br>*station1_lng* | <br>*station2_lat* | <br>*station2_lng* | <br>*pass_id* |
| --- | --- | --- | --- | --- | --- | --- | --- |
| **2023-03-28 12:00:00** | **1** | 5 | *41.9100* | *87.6780* | *41.9100* | *87.6780* | *1* |
| **2023-03-28 13:00:00** | **1** | 5 | *41.9100* | *87.6780* | *41.9100* | *87.6780* | NULL |

| <small>Passes</small><br>**id** | <br>date_start | <br>date_end | <br>*passenger_id* |
| --- | --- | --- | --- |
| **1** | 2023-03-01 | 2023-04-01 | *1* |

## Extra

### SQL commands

```sql
CREATE TABLE Conductors(
  `social_sec` VARCHAR(10) PRIMARY KEY,
  `name` VARCHAR(50) NOT NULL,
  `birth` DATE NOT NULL,
  `age` INT NOT NULL,
  `phones` VARCHAR(50),
  CHECK(`age` >= 21)
);

CREATE TABLE Alerts(
  `alert_id` INT AUTO_INCREMENT PRIMARY KEY,
  `message` VARCHAR(280) NOT NULL,
  `date_start` DATE NOT NULL,
  `date_end` DATE NOT NULL,
  `social_sec` VARCHAR(10) NOT NULL,
  FOREIGN KEY(`social_sec`) REFERENCES Conductors(`social_sec`),
  CHECK(`date_start` < `date_end`)
);

CREATE TABLE Tracks(
  `track_color` VARCHAR(10) PRIMARY KEY
);

CREATE TABLE Stations(
  `station_lat` DECIMAL(8, 6),
  `station_lng` DECIMAL(9, 6),
  `station_color` VARCHAR(10),
  `name` VARCHAR(50) NOT NULL,
  `zip` VARCHAR(5) NOT NULL,
  `note` VARCHAR(280),
  `has_elevator` BOOLEAN NOT NULL DEFAULT 0,
  `has_parking` BOOLEAN NOT NULL DEFAULT 0,
  PRIMARY KEY(`station_lat`, `station_lng`, `station_color`),
  INDEX(`station_lat`),
  INDEX(`station_lng`),
  FOREIGN KEY(`station_color`) REFERENCES Tracks(`track_color`)
);

CREATE TABLE Locomotives(
  `serial_no` VARCHAR(4) PRIMARY KEY,
  `since` YEAR NOT NULL,
  CHECK(LENGTH(`serial_no`) = 4)
);

CREATE TABLE Wagons(
  `wagon_id` VARCHAR(4) PRIMARY KEY,
  `seats` INT NOT NULL,
  CHECK(LENGTH(`serial_no`) = 4)
);

CREATE TABLE Trains(
  `train_id` INT AUTO_INCREMENT PRIMARY KEY,
  `track_color` VARCHAR(10) NOT NULL,
  `serial_no` VARCHAR(4) NOT NULL,
  `social_sec` VARCHAR(10) NOT NULL,
  FOREIGN KEY(`track_color`) REFERENCES Tracks(`track_color`),
  FOREIGN KEY(`serial_no`) REFERENCES Locomotives(`serial_no`),
  FOREIGN KEY(`social_sec`) REFERENCES Conductors(`social_sec`)
);

CREATE TABLE Railcars(
  `train_id` INT,
  `wagon_id` VARCHAR(4),
  PRIMARY KEY(`train_id`, `wagon_id`),
  FOREIGN KEY(`train_id`) REFERENCES Trains(`train_id`),
  FOREIGN KEY(`wagon_id`) REFERENCES Wagons(`wagon_id`)
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
  FOREIGN KEY(`passenger_id`) REFERENCES Passengers(`passenger_id`),
  CHECK(`date_start` < `date_end`)
);

CREATE TABLE Trips(
  `timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `passenger_id` INT,
  `fare` DECIMAL(13, 2),
  `pass_id` INT,
  `station_lat1` DECIMAL(8, 6) NOT NULL,
  `station_lng1` DECIMAL(9, 6) NOT NULL,
  `station_color1` VARCHAR(10) NOT NULL,
  `station_lat2` DECIMAL(8, 6),
  `station_lng2` DECIMAL(9, 6),
  `station_color2` VARCHAR(10),
  PRIMARY KEY(`timestamp`, `passenger_id`),
  FOREIGN KEY(`passenger_id`) REFERENCES Passengers(`passenger_id`),
  FOREIGN KEY(`pass_id`) REFERENCES Passes(`pass_id`),
  FOREIGN KEY(`station_lat1`) REFERENCES Stations(`station_lat`),
  FOREIGN KEY(`station_lng1`) REFERENCES Stations(`station_lng`),
  FOREIGN KEY(`station_color1`) REFERENCES Stations(`station_color`),
  FOREIGN KEY(`station_lat2`) REFERENCES Stations(`station_lat`),
  FOREIGN KEY(`station_lng2`) REFERENCES Stations(`station_lng`),
  FOREIGN KEY(`station_color2`) REFERENCES Stations(`station_color`)
);
```

[View full code](https://github.com/hendraanggrian/IIT-CS425/blob/main/cta/initialize2.sql)
/ [data](https://github.com/hendraanggrian/IIT-CS425/blob/main/cta/data2.sql)

## Checklist

- Main objectives:
  - [ ] Create desktop application.
  - [x] Create full database diagram.
- SQL commands improvement:
  - [ ] Add restriction/cascading when deleting/updating.
  - [x] Add `CHECK` contraints to restrict bad input.
  - [x] Add nullability check.
  - [x] Add default values.
- Possible schema imporovement:
  - [ ] Support traveling by bus, doesn't add many tables but massively change
    the structure of existing tables.
  - [x] Support membership with weekly and/or monthly payment, potentially adding
    2-3 more tables.
