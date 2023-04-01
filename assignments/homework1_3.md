# [Homework 1.3](https://github.com/hendraanggrian/IIT-CS425/raw/assets/assignments/homework1_3.docx): Sailing DB

> Implement the database schema (SailingDB) found in the attachment pdf below
  and insert the data as shown in each table. You are provided with the query
  statements and the SQL commands labelled from no.1 to 29, respectively. You
  are required to screenshot the resultant table for each SQL command.

**Note**: *Captains* are implied to be a carbon copy of *Sailors*, but I made
slight changes in columns' name.

| <small>Sailors</small><br>Sname | <br>SID | <br>Rating | <br>Age |
| --- | ---: | ---: | ---: |
| Marx | 23 | 8 | 52 |
| Martin | 25 | 9 | 51 |
| Adams | 27 | 8 | 36 |
| Carrey | 33 | 10 | 22 |

| <small>Captains</small><br>Cname | <br>CID | <br>Rating | <br>Age |
| --- | ---: | ---: | ---: |
| Marx | 23 | 8 | 52 |
| Martin | 25 | 9 | 51 |
| Adams | 27 | 8 | 36 |
| Carrey | 33 | 10 | 22 |

| <small>Boats</small><br>Bname | <br>BID | <br>Fee | <br>Location |
| --- | ---: | ---: | --- |
| Wayfarer | 109 | 120 | Hout Bay |
| SeaPride | 108 | 500 | Fish Hoek |
| Yupie | 101 | 400 | Hout Bay |
| Joy | 104 | 200 | Hout Bay |

| <small>Reserves</small><br>SID | <br>BID | <br>Day | <br>Deposit |
| ---: | ---: | :---: | ---: |
| 23 | 109 | 2014-08-01 | 120 |
| 23 | 108 | 2014-08-08 | 120 |
| 25 | 101 | 2014-08-08 | 0 |
| 27 | 101 | 2014-08-09 | 100 |
| 27 | 109 | 2014-08-15 | 120 |
| 33 | 109 | 2014-09-04 | 0 |
| 33 | 104 | 2014-09-11 | 0 |

### SQL Commands

```sql
CREATE TABLE Sailors(
  `Sname` VARCHAR(20) NOT NULL,
  `SID` INT AUTO_INCREMENT PRIMARY KEY,
  `Rating` INT,
  `Age` INT NOT NULL
);

CREATE TABLE Captains(
  `Cname` VARCHAR(20) NOT NULL,
  `CID` INT AUTO_INCREMENT PRIMARY KEY,
  `Rating` INT,
  `Age` INT NOT NULL
);

CREATE TABLE Boats(
  `Bname` VARCHAR(20) NOT NULL,
  `BID` INT AUTO_INCREMENT PRIMARY KEY,
  `Fee` INT NOT NULL,
  `Location` VARCHAR(20) NOT NULL
);

CREATE TABLE Reserves(
  `SID` INT NOT NULL,
  `BID` INT NOT NULL,
  `Day` DATE NOT NULL,
  `Deposit` INT NOT NULL,
  PRIMARY KEY(`SID`, `BID`),
  CONSTRAINT Reserves_SID FOREIGN KEY(`SID`) REFERENCES Sailors(`SID`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT Reserves_BID FOREIGN KEY(`BID`) REFERENCES Boats(`BID`)
    ON DELETE RESTRICT ON UPDATE RESTRICT
);
```

[View full code](https://github.com/hendraanggrian/IIT-CS425/blob/main/sailing-db/initialize.sql)

## Problem 1

> Get everything in *Sailors*.

```sql
SELECT * FROM Sailors;
```

![Screenschot for answer 1.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/1.png)

## Problem 2

> Get `SID`, `Rating` & `Age` of all sailors, ordered from highest to lowest
  rank. `Rating` is 10 times rating.

```sql
SELECT `SID`, `Rating` * 10, `Age` FROM Sailors ORDER BY `Rating` DESC;
```

![Screenschot for answer 2.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/2.png)

## Problem 3

> Get alphabetical list of sailors with rating less than 10.

```sql
SELECT `Sname` FROM Sailors WHERE `Rating` <= 9 ORDER BY `Sname`;
```

![Screenschot for answer 3.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/3.png)

## Problem 4

> Find how much deposit money there is in total and how many tuples are in
  *Reserves*.

```sql
SELECT SUM(`Deposit`) AS `TOTAL`, COUNT(`Deposit`) AS `HOWMANY` FROM Reserves;
```

![Screenschot for answer 4.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/4.png)

## Problem 5

> Get all info on boats in Fishhoek.

```sql
SELECT * FROM Boats WHERE `Location` LIKE '_is%k';
```

![Screenschot for answer 5.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/5.png)

## Problem 6

> In what locations are boats kept?

```sql
SELECT DISTINCT `Location` FROM Boats;
```

![Screenschot for answer 6.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/6.png)

## Problem 7

> Get the names of all boats that have a fee value recorded in the database.

```sql
SELECT `Bname` FROM Boats WHERE `Fee` IS NOT NULL;
```

![Screenschot for answer 7.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/7.png)

## Problem 8

> Get ID of all boats that have not been reserved.

```sql
SELECT `BID` FROM Boats WHERE `BID` NOT IN(SELECT `BID` FROM Reserves);
```

![Screenschot for answer 8.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/8.png)

## Problem 9

> Get all reservation info, including all details on the boats being reserved.

```sql
SELECT * FROM Reserves, Boats WHERE Reserves.`BID` = Boats.`BID`;
```

![Screenschot for answer 9.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/9.png)

## Problem 10

> For all reservations, get the name of the sailor, along with the day and name
  of the boat booked.

```sql
SELECT `Sname`, `Day`, `Bname`
  FROM Sailors AS s, Reserves AS r, Boats AS b
  WHERE s.`SID` = r.`SID` AND r.`BID` = b.`BID`;
```

![Screenschot for answer 10.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/10.png)

## Problem 11

> Get the average deposit paid for each boat.

```sql
SELECT `BID`, AVG(`Deposit`) FROM Reserves GROUP BY `BID`;
```

![Screenschot for answer 11.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/11.png)

## Problem 12

> Get the average deposit paid for each boat that has been booked by more than
  one person.

```sql
SELECT `BID`, AVG(`Deposit`) FROM Reserves GROUP BY `BID`
  HAVING COUNT(DISTINCT `SID`) > 1;
```

![Screenschot for answer 12.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/12.png)

## Problem 13

> Get the average firm deposit paid for each boat that has been booked by more
  than one person, in increasing order of amount. A firm deposit is one which
  exceeds 10.

```sql
SELECT `BID`, AVG(`Deposit`) AS `AVERAGEDEPOSIT` FROM Reserves
  WHERE `Deposit` > 10 GROUP BY `BID` HAVING COUNT(DISTINCT `SID`) > 1
  ORDER BY `AVERAGEDEPOSIT`;
```

![Screenschot for answer 13.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/13.png)

## Problem 14

> Get name & rating of sailors with rating exceeding 7 who made any reservation
  with 0 deposit.

```sql
SELECT `Sname`, `Rating` FROM Sailors
  WHERE `Rating` > 7
    AND `SID` IN(SELECT `SID` FROM Reserves WHERE `Deposit` = 0);
```

![Screenschot for answer 14.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/14.png)

## Problem 15

> Get names of boats located in a place other than Hout Bay or Fish Hoek.

```sql
SELECT `Bname` FROM Boats WHERE `Location` NOT IN('Hout Bay', 'Fish Hoek');
```

![Screenschot for answer 15.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/15.png)

## Problem 16

> Get names of boats having a fee larger than any boat located in Hout Bay.

```sql
SELECT DISTINCT `Bname` FROM Boats
  WHERE `Fee` > SOME(SELECT `Fee` FROM Boats WHERE `Location` = 'Hout Bay');
```

![Screenschot for answer 16.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/16.png)

## Problem 17

> Get names that are in both the sailors and the captains relations.

```sql
SELECT `Sname` FROM Sailors
  WHERE EXISTS(SELECT * FROM Captains WHERE Captains.`CID` = Sailors.`SID`);
```

![Screenschot for answer 17.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/17.png)

## Problem 18

> Get names of boats that have exactly 1 reservation.

```sql
SELECT `Bname` FROM Boats AS b
  WHERE EXISTS(SELECT `BID` FROM Reserves WHERE Reserves.`BID` = b.`BID`);
```

![Screenschot for answer 18.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/18.png)

## Problem 19

> Get sailor ID and total deposit paid for sailors who have booked more than 1
  boat.

```sql
SELECT `SID`, `TotalDeposit`
  FROM(SELECT `SID`, COUNT(`BID`), SUM(`Deposit`) FROM Reserves
    WHERE `Deposit` IS NOT NULL AND `Deposit` > 0 GROUP BY `SID`)
  AS RESULT(`SID`, `NumBoats`, `TotalDeposit`) WHERE `NumBoats` > 1;
```

![Screenschot for answer 19.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/19.png)

## Problem 20

> Get all reservation info including details of the boat booked.

```sql
SELECT * FROM Boats INNER JOIN Reserves ON Boats.`BID` = Reserves.`BID`;
```

![Screenschot for answer 20.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/20.png)

## Problem 21

> Get all information on every boat. If a boat has reservations, including all
  its reservations info.

```sql
SELECT * FROM Boats LEFT OUTER JOIN Reserves ON Boats.`BID` = Reserves.`BID`;
```

![Screenschot for answer 21.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/21.png)

## Problem 22

> Create a new tuple for the boat named "Nino" which has fee 150, BID 110, and
  is in Fish Hoek.

```sql
INSERT INTO Boats VALUES('Nino', 110, 150, 'Fish Hoek');
```

![Screenschot for answer 22.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/22.png)

### Added *Boats*

| Bname | BID | Fee | Location |
| --- | ---: | ---: | --- |
| Nino | 110 | 150 | Fish Hoek |

## Problem 23

> Remove all bookings from *Reserves* where there is no deposit.

```sql
DELETE FROM Reserves WHERE `Deposit` IS NULL OR `Deposit` = 0;
```

![Screenschot for answer 23.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/23.png)

### Removed *Reserves*

| SID | BID | Day | Deposit |
| ---: | ---: | :---: | ---: |
| 25 | 101 | 2014-08-08 | 0 |
| 33 | 109 | 2014-09-04 | 0 |
| 33 | 104 | 2014-09-11 | 0 |

## Problem 24

> Increase the fee of every boat by 12%.

```sql
UPDATE Boats SET `Fee` = `Fee` * 1.12;
```

![Screenschot for answer 24.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/24.png)

### Updated **Boats**

| Bname | BID | Fee | Location |
| --- | ---: | ---: | --- |
| Wayfarer | 109 | **134** | Hout Bay |
| SeaPride | 108 | **560** | Fish Hoek |
| Yupie | 101 | **448** | Hout Bay |
| Joy | 104 | **224** | Hout Bay |
| Nino | 110 | **168** | Fish Hoek |

## Problem 25

> Make a view called 'Bookings' which hides the `Deposit` value (i.e. only has
  the other 3 attributes).

```sql
CREATE VIEW Bookings AS SELECT `SID`, `BID`, `Day` FROM Reserves;
```

![Screenschot for answer 25.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/25.png)

#### Created *Bookings* view

| SID | BID | Day |
| ---: | ---: | :---: |
| 23 | 108 | 2014-08-08 |
| 23 | 109 | 2014-08-01 |
| 27 | 101 | 2014-08-09 |
| 27 | 109 | 2014-08-15 |

## Problem 26

> Create a table called *Reserves* with 3 integer attributes `BID`, `SID`
  & `Deposit`, and a date attribute `Day`. Allow only deposit to be omitted, and
  ensure `SID` and `BID` values exist in the database. When someone books a boat
  it is for the whole day.

```sql
CREATE TABLE Reserves(
  `SID` INT NOT NULL,
  `BID` INT NOT NULL,
  `Day` DATE NOT NULL,
  `Deposit` INT NOT NULL,
  PRIMARY KEY(`SID`, `BID`),
  CHECK(`BID` IN(SELECT `BID` FROM Boats)),
  CHECK(`SID` IN(SELECT `SID` FROM Sailors))
);
```

![Screenschot for answer 26.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/26.png)

> There seem to be a `reserves_chk_1` error.

## Problem 27

> Add a new attribute `NEEDSREPAIR` to *Boats*, it is usually "N".

```sql
ALTER TABLE Boats ADD `NEEDSREPAIR` CHAR(1) DEFAULT 'N';
```

![Screenschot for answer 27.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/27.png)

### Updated *Boats*

| Bname | BID | Fee | Location | NEEDSREPAIR |
| --- | ---: | ---: | --- | --- |
| Wayfarer | 109 | 134 | Hout Bay | **N** |
| SeaPride | 108 | 560 | Fish Hoek | **N** |
| Yupie | 101 | 448 | Hout Bay | **N** |
| Joy | 104 | 224 | Hout Bay | **N** |
| Nino | 110 | 158 | Fish Hoek | **N** |

## Problem 28

> Remove the `Age` attribute.

```sql
ALTER TABLE Sailors DROP `Age`;
```

![Screenschot for answer 28.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/28.png)

### Updated *Sailors*

| Sname | SID | Rating |
| --- | ---: | ---: |
| Marx | 23 | 8 |
| Martin | 25 | 9 |
| Adams | 27 | 8 |
| Carrey | 33 | 10 |

## Problem 29

> Remove the *Captains* relation altogether so that nobody can try insert or use
  *Captains* in future.

```sql
DROP TABLE Captains;
```

![Screenschot for answer 29.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/sailing-db/29.png)
