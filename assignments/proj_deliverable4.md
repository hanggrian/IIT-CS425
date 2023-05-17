# [Project deliverable 4](https://github.com/hendraanggrian/IIT-CS425/blob/assets/assignments/proj.pdf): *Chicago Transit Authority (CTA)*

> **Application**: The last deliverable requires to write a program in any
  programming language SQL supports and implement a variety of SQL commands.
  Extra points will be awarded to a group that tests a variety of challenging
  SQL usage (such as set operations, aggregate functions, set membership, set
  comparison, subqueries using WITH clause, etc.). Further, interesting use
  cases that can be evaluated with sample queries will earn extra points. The
  application can either be web or desktop application and must be demonstrated
  on the due date.
>
> | Rubric | Bad | Good | Great | Total |
> | --- | ---: | ---: | ---: | ---: |
> | Students have used a programming language to demonstrate the 10 different types of queries tested in the 3rd deliverable. | 0-4 | 5-6 | 7-8 | 9-10 |
> | Students have demonstrated at least 2 or 3 complex, but interesting queries according to the selected application. | 0-1 | 2-3 | 4-5 | 5 |
> | The application has a user friendly interface/display with features such as menus, buttons, etc. | 0-1 | 2-3 | 4-5 | 5 |
> | Students have presented their project in class on the due date and have uploaded all the working files and folders of the project. | 0-4 | 5-6 | 7-8 | 9-10 |
> | Personal contribution, a group must give a percent contribution of each member across all deliverables. | 0-1 | 2-3 | 4-5 | 5 |

![App preview.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/preview.png)

A 3-columns windowed multi-platform desktop app.

- [DMG](https://github.com/hendraanggrian/IIT-CS425/releases/download/proj-deliverable4/cta-1.0-x64.dmg)
  &ndash; macOS moutable with one-file application inside.
  - For permission error: run `sudo xattr -cr /Applications/CTA.app`, open app,
    then click allow on *System Settings > Privacy & Security*.
- [EXE](https://github.com/hendraanggrian/IIT-CS425/releases/download/proj-deliverable4/cta-1.0-x64.exe)
  &ndash; Windows installer to default directory.
- [JAR](https://github.com/hendraanggrian/IIT-CS425/releases/download/proj-deliverable4/cta-1.0.jar)
  &ndash; JRE executable that requires version 17+.

### Tech stack

- *JavaFX* GUI.
- *Kotlin* language and buildscript.
- [Ktorm](https://www.ktorm.org/), use SQL result rows as Kotlin objects.

## Use case

### Case 1

**Login dialog**: At the start of use, there is a MySQL login prompt. This
dialog remembers user input by saving it to local preferences. This MySQL user'
credential is separate from `db.conductors[i].password`.

This `db` instance will be carried around everywhere. There is no global
instance to database, as it is a bad practice.

```kotlin
val prefs = Preferences.userNodeForPackage(App::class.java)
prefs.put("host_ip", hostIp)
prefs.put("host_port", hostPort)
prefs.put("schema", schema)
prefs.put("user", user)
prefs.put("password", password)
prefs.putBoolean("keep_sign", keepSignCheck.isSelected)
prefs.sync()
prefs.flush()

val db = Database.connect(
    "jdbc:mysql://$hostIp:$hostPort/$schema",
    driver = "com.mysql.cj.jdbc.Driver",
    user = user,
    password = password,
    logger = Slf4jLoggerAdapter("CTA")
)
```

![Login dialog.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/case1.png)

### Case 2

**List items**: Once logged in, the main screen consist of trips, alerts, and
stations. Table items are populated manually by clicking refresh menu.

```kotlin
val tripTable = tableView<Trip>()
val alertTable = tableView<Alert>()
val stationTable = tableView<Station>()

"File" {
    "Refresh" {
        accelerator = SHORTCUT_DOWN + R
        onAction {
            tripTable.items = db.sequenceOf(Trips)
            alertTable.items = db.sequenceOf(Alerts)
            stationTable.items = db.sequenceOf(Stations)
        }
    }
}
```

![Refresh menu.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/case2_1.png)

![3 tables in main stage.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/case2_2.png)

### Case 3

**Randomize password**: Password are obfuscated with `SHA-256` hash function,
the string will be generated up to 64 length.

```kotlin
val digest = java.security.MessageDigest.getInstance("SHA-256")
digest.update(toByteArray())
val newPassword = String(digest.digest())

db.update(Conductors) {
    set(it.password, newPassword)
    where { it.conductorUsername eq selectedConductor.conductorUsername }
}
```

![Conductor table dialog.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/case3_1.png)

![Reset password dialog.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/case3_2.png)

![Reset password result.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/case3_3.png)

### Case 4

**Add alert**: Adding an alert requires conductor's credential. The password
entered here will also be hashed and compared to `db.conductors[i].password`,
which is already hashed.

```kotlin
val alert = Alert {
    title = titleField.text
    message = messageArea.text
    dateStart = dateStartPicker.value
    dateEnd = dateEndPicker.value
    track = trackChoice.value
    conductor = conductorUserChoice.value
}
db.alerts.add(alert)
```

![Add alert dialog.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/case4_1.png)

![View alert dialog.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/case4_2.png)

### Case 5

**Add pass**: Add pass to a passenger specifying date range. If today is in
range of their passes, the column value will say **Yes**.

```kotlin
val pass = Pass {
    passenger = selectedPassenger
    dateStart = dateStartPicker.value
    dateEnd = dateEndPicker.value
}
db.passes.add(alert)
```

![Add alert dialog.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/case5_1.png)

![View alert dialog.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/case5_2.png)

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

> ## Old SQL statements
>
> SQL statements from deliverable 3 still stands.
>
> ### Statement 1
>
> **Operable tracks**: List how many tracks are actively in use.
>
> ```sql
> SELECT CONCAT(`track`, IF(`is_24h`, ' (24h)', '')) AS `Active stations`
>   FROM Tracks WHERE `track` IN(SELECT `track` FROM Stations);
> ```
>
> ![Screenschot for answer 1.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/statement1.png)
>
> ### Statement 2
>
> **Station's route**: Blue line is the most used track of CTA, list its route
  and intersections with other lines.
>
> ```sql
> SELECT `track`, `station`, `location`, `zip` FROM Stations
>   WHERE `station` IN(SELECT `station` FROM Stations WHERE `track` = 'Blue');
> ```
>
> ![Screenschot for answer 2.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/statement2.png)
>
> ### Statement 3
>
> **Station's infrastructure**: CTA strives for building quality, here's a
  statistics of how many infrastructure are in place.
>
> ```sql
> SELECT CONCAT('Elevator ', AVG(`has_elevator`) * 100, '%')
>   AS `Infrastructure rating` FROM Stations UNION
> SELECT CONCAT('Parking ', AVG(`has_parking`) * 100, '%')
>   AS `Infrastructure rating` FROM Stations;
> ```
>
> ![Screenschot for answer 3.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/statement3.png)
>
> ### Statement 4
>
> **Old alerts**: Service alerts can have indefinite running time. List really
  old alerts.
>
> ```sql
> SELECT `track`, `title`, `date_start` FROM Alerts
>   WHERE `date_start` < '2022-01-01' AND `date_end` IS NULL;
> ```
>
> ![Screenschot for answer 4.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/statement4.png)
>
> ### Statement 5
>
> **Punish lazy conductors**: Maintaining service alert is the responsibility of
  the employee who create it. Notify those employees.
>
> ```sql
> SELECT CONCAT(`name`, ' (', `username`, ')') AS `Lazy employees` FROM Conductors
>   WHERE `username` IN(SELECT `username` FROM Alerts
>     WHERE `date_start` < '2022-01-01' AND `date_end` IS NULL);
> ```
>
> ![Screenschot for answer 5.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/statement5.png)
>
> ### Statement 6
>
> **Old trains**: Old trains are prone to accidents and need to be decommisioned.
>
> ```sql
> SELECT * FROM Trains AS T LEFT JOIN Locomotives AS L
>   ON T.`serial_no` = L.`serial_no`
>   WHERE `since` < 1980;
> ```
>
> ![Screenschot for answer 6.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/statement6.png)
>
> ### Statement 7
>
> **Train status**: Common attributes of a train: track, conductor, and how many
  wagons it carries.
>
> ```sql
> SELECT R.`train_id`, `track`, `username`, COUNT(R.`train_id`) AS `Wagon count`
>   FROM Railcars AS R LEFT JOIN Trains AS T
>   ON R.`train_id` = T.`train_id` GROUP BY `train_id`;
> ```
>
> ![Screenschot for answer 7.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/statement7.png)
>
> ### Statement 8
>
> **Heaviest trains**: Each wagon contain different number of seats, calculate
  the total.
>
> ```sql
> SELECT R.`train_id`, SUM(W.`seats`) AS `Seat capacity`
>   FROM Railcars AS R LEFT JOIN Wagons AS W
>   ON R.`wagon_id` = W.`wagon_id` GROUP BY `train_id`;
> ```
>
> ![Screenschot for answer 8.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/statement8.png)
>
> ### Statement 9
>
> **Pass popularity**: Discover how many of CTA users have ongoing passes.
>
> ```sql
> SELECT `pass_id`, `date_start`, P1.`passenger_id`, `name` FROM Passes AS P1
>   LEFT JOIN Passengers AS P2
>   ON P1.`passenger_id` = P2.`passenger_id`
>   WHERE `date_end` >= '2023-01-01';
> ```
>
> ![Screenschot for answer 9.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/statement9.png)
>
> ### Statement 10
>
> **Passenger engagement**: Discover how many CTA customers have used the
  service within this year.
>
> ```sql
> SELECT CONCAT(`passenger_id`, '. ', `name`) AS `Recent customers`
>   FROM Passengers WHERE `passenger_id`
>     IN(SELECT `passenger_id` FROM Trips WHERE `timestamp` >= '2023-01-01');
> ```
>
> ![Screenschot for answer 10.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/statement10.png)

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
