# [Project Deliverable 2](https://github.com/hendraanggrian/IIT-CS425/raw/assets/assignments/project.pdf): CTA

## ERD

![The ER model stage 2.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/erd2.png)

### Rules

- A train need **one** locomotive (engine) to run, the locomotives can be used
  in **many** trains.
- A train can carry **many** wagons (railcars), the wagons can be used in
  **many** trains.
- A train is controlled by **one** conductor, the conductor control **many**
  trains.
- A track consist of **many** stations, a station can only be registered to
  **one** track.
- A track can be passed by **many** trains, a train is only registered to
  **one** track.
- A passenger takes **many** trips, a trip is registered to **one** passenger.
- A passenger pays **many** fares, a fare is only good for **one** passenger.
- A passenger have **many** passes, a pass can be used to **one** fare.

## Schema

![The database schema stage 1.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/schema1.png)

### Conductors & Alerts

A conductor is a CTA employee with 2 jobs: operating a train (mostly in
locomotive) and announcing service alerts.

| <small>Conductors</small><br>**social_sec** | <br>name | <br>birth |
| --- | --- | --- |
| **1234567890** | Jane Doe | 1990-01-01 |

Alerts are to be displayed in every station.

| <small>Alerts</small><br>**id** | <br>message | <br>date_start | <br>date_end | <br>*conductor_sec* |
| --- | --- | --- | --- | --- |
| **1** | Elevator maintenance at Damen. | 2023-03-28 | 2023-04-04 | *1234567890* |

### Stations & Tracks

A station is a train stop, transferrable station requires multiple entries.

| <small>Stations</small><br>**lat** | <br>**lng** | <br>name | <br>zip | <br>has_parking | <br>has_elevator | <br>*track_color* |
| --- | --- | --- | --- | --- | --- | --- |
| **41.9100** | **87.6780** | Damen | 60622 | FALSE | FALSE | *Blue* |

A track is a collection of stations.

| <small>Tracks</small><br>**color** | <br>is_24h |
| --- | --- |
| **Blue** | FALSE |

### Locomotives, Wagons, Trains & Railcars

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

### Passengers, Trips & Passes

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

## SQL Commands

```sql
CREATE TABLE Conductors(
  `social_sec` VARCHAR(10) PRIMARY KEY,
  `name` VARCHAR(50) NOT NULL,
  `birth` DATE NOT NULL
);

CREATE TABLE Alerts(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `message` VARCHAR(280) NOT NULL,
  `date_start` DATE NOT NULL,
  `date_end` DATE NOT NULL,
  `conductor_soc` VARCHAR(50) NOT NULL,
  FOREIGN KEY(`conductor_soc`) REFERENCES Conductors(`social_sec`)
);

CREATE TABLE Tracks(
  `color` VARCHAR(10) PRIMARY KEY
);

CREATE TABLE Stations(
  `lat` DECIMAL(8, 6),
  `lng` DECIMAL(9, 6),
  `track_color` VARCHAR(10),
  `name` VARCHAR(50) NOT NULL,
  `zip` VARCHAR(5) NOT NULL,
  `has_elevator` BOOLEAN NOT NULL DEFAULT 0,
  `has_parking` BOOLEAN NOT NULL DEFAULT 0,
  PRIMARY KEY(`lat`, `lng`, `track_color`),
  INDEX(`lat`),
  INDEX(`lng`),
  FOREIGN KEY(`track_color`) REFERENCES Tracks(`color`)
);

CREATE TABLE Locomotives(
  `serial_no` VARCHAR(20) PRIMARY KEY,
  `since` YEAR NOT NULL
);

CREATE TABLE Wagons(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `seats` INT NOT NULL
);

CREATE TABLE Trains(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `track_color` VARCHAR(10),
  `locomotive_no` VARCHAR(20),
  `conductor_sec` VARCHAR(10),
  FOREIGN KEY(`track_color`) REFERENCES Tracks(`color`),
  FOREIGN KEY(`locomotive_no`) REFERENCES Locomotives(`serial_no`),
  FOREIGN KEY(`conductor_sec`) REFERENCES Conductors(`social_sec`)
);

CREATE TABLE Railcars(
  `train_id` INT,
  `wagon_id` INT,
  PRIMARY KEY(`train_id`, `wagon_id`),
  FOREIGN KEY(`train_id`) REFERENCES Trains(`id`),
  FOREIGN KEY(`wagon_id`) REFERENCES Wagons(`id`)
);

CREATE TABLE Passengers(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(50) NOT NULL
);

CREATE TABLE Passes(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `date_start` DATE NOT NULL,
  `date_end` DATE NOT NULL,
  `passenger_id` INT NOT NULL,
  FOREIGN KEY(`passenger_id`) REFERENCES Passengers(`id`)
);

CREATE TABLE Trips(
  `passenger_id` INT NOT NULL,
  `fare` DECIMAL(13, 2),
  `pass_id` INT,
  `station1_lat` DECIMAL(8, 6) NOT NULL,
  `station1_lng` DECIMAL(9, 6) NOT NULL,
  `station1_color` VARCHAR(10) NOT NULL,
  `station2_lat` DECIMAL(8, 6),
  `station2_lng` DECIMAL(9, 6),
  `station2_color` VARCHAR(10),
  `timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY(`timestamp`, `passenger_id`),
  FOREIGN KEY(`passenger_id`) REFERENCES Passengers(`id`),
  FOREIGN KEY(`pass_id`) REFERENCES Passes(`id`),
  FOREIGN KEY(`station1_lat`) REFERENCES Stations(`lat`),
  FOREIGN KEY(`station1_lng`) REFERENCES Stations(`lng`),
  FOREIGN KEY(`station1_color`) REFERENCES Stations(`track_color`),
  FOREIGN KEY(`station2_lat`) REFERENCES Stations(`lat`),
  FOREIGN KEY(`station2_lng`) REFERENCES Stations(`lng`),
  FOREIGN KEY(`station2_color`) REFERENCES Stations(`track_color`)
);
```

[View full code](https://github.com/hendraanggrian/IIT-CS425/blob/main/cta/initialize2.sql)

## What's next at Deliverable 3

- Early form of desktop application.
- SQL commands improvement:
  - Add restriction/cascading when deleting/updating.
- Possible schema imporovement:
  - Support traveling by bus.
