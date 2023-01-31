# CS425: [Homework 1.3](https://github.com/hendraanggrian/IIT-CS425/raw/assets/Homework%201.3.docx)

Given SQL tables below, show the screenshot output of each command.

### *Sailors*

| Sname | SID | Rating | Age |
| --- | ---: | ---: | ---: |
| Marx | 23 | 8 | 52 |
| Martin | 25 | 9 | 51 |
| Adams | 27 | 8 | 36 |
| Carrey | 33 | 10 | 22 |

### *Captains*

Implied to be a carbon copy of *Sailors*, but I made slight changes in columns'
name.

| Cname | CID | Rating | Age |
| --- | ---: | ---: | ---: |
| Marx | 23 | 8 | 52 |
| Martin | 25 | 9 | 51 |
| Adams | 27 | 8 | 36 |
| Carrey | 33 | 10 | 22 |

### *Boats*

| Bname | BID | Fee | Location |
| --- | ---: | ---: | --- |
| Wayfarer | 109 | 120 | Hout Bay |
| SeaPride | 108 | 500 | Fish Hoek |
| Yupie | 101 | 400 | Hout Bay |
| Joy | 104 | 200 | Hout Bay |

### *Reserves*

| SID | BID | Day | Deposit |
| ---: | ---: | :---: | ---: |
| 23 | 109 | 2014-08-01 | 120 |
| 23 | 108 | 2014-08-08 | 120 |
| 25 | 101 | 2014-08-08 | 0 |
| 27 | 101 | 2014-08-09 | 100 |
| 27 | 109 | 2014-08-15 | 120 |
| 33 | 109 | 2014-09-04 | 0 |
| 33 | 104 | 2014-09-11 | 0 |

## 1. Get everything in the Sailors table.

```sql
SELECT * FROM Sailors
```

![Screenschot for question 1.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/SailingDB/screenshots/1.png)

## 2. Get sailor ID, rank & age of all sailors, ordered from highest to lowest rank. Rank is 10 times rating.

```sql
SELECT `SID`, `Rating` * 10, `Age` FROM Sailors ORDER BY `Rating` DESC
```

![Screenschot for question 2.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/SailingDB/screenshots/2.png)

## 3. Get alphabetical list of sailors with rating less than 10.

```sql
SELECT `Sname` FROM Sailors WHERE `Rating` <= 9 ORDER BY `Sname`
```

![Screenschot for question 3.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/SailingDB/screenshots/3.png)

## 4. Find how much deposit money there is in total and how many tuples are in the reserves table.

```sql
SELECT SUM(`Deposit`) AS `Total`, COUNT(`Deposit`) AS `Howmany` FROM Reserves
```

![Screenschot for question 4.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/SailingDB/screenshots/4.png)

## 5. Get all info on boats in Fishhoek but l'm not sure how you spell Fishoek.

```sql
SELECT * FROM Boats WHERE `Location` LIKE '_IS%K'
```

![Screenschot for question 5.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/SailingDB/screenshots/5.png)

## 6. In what locations are boats kept?

```sql
SELECT DISTINCT `Location` FROM Boats
```

![Screenschot for question 6.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/SailingDB/screenshots/6.png)

## 7. Get the names of all Boats that have a fee value recorded in the database.

```sql
SELECT `Bname` FROM Boats WHERE `Fee` IS NOT NULL
```

![Screenschot for question 7.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/SailingDB/screenshots/7.png)

## 8. Get ID of all boats that have not been reserved.

```sql
SELECT `BID` FROM Boats WHERE `BID` NOT IN(SELECT `BID` FROM Reserves)
```

![Screenschot for question 8.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/SailingDB/screenshots/8.png)

## 9. Get all reservation info, including all details on the boats being reserved.

```sql
SELECT * FROM Reserves, Boats WHERE Reserves.`BID` = Boats.`BID`
```

![Screenschot for question 9.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/SailingDB/screenshots/9.png)

## 10. For all reservations, get the name of the sailor, along with the day and name of the boat booked.

```sql
SELECT `Sname`, `Day`, `Bname`
  FROM Sailors AS S, Reserves AS R, Boats AS B
  WHERE S.`SID` = R.`SID` AND R.`BID` = B.`BID`
```

![Screenschot for question 10.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/SailingDB/screenshots/10.png)

## 11. Get the average deposit paid for each boat.

```sql
SELECT `BID`, AVG(`Deposit`) FROM Reserves GROUP BY `BID`
```

![Screenschot for question 11.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/SailingDB/screenshots/11.png)

## 12. Get the average deposit paid for each boat that has been booked by more than one person.

```sql
SELECT `BID`, AVG(`Deposit`) FROM Reserves GROUP BY `BID`
  HAVING COUNT(DISTINCT `SID`) > 1
```

![Screenschot for question 12.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/SailingDB/screenshots/12.png)

## 13. Get the average firm deposit paid for each boat that has been booked by more than one person, in increasing order of amount. A firm deposit is one which exceeds R10

```sql
SELECT `BID`, AVG(`Deposit`) AS `AverageDeposit` FROM Reserves
  WHERE `Deposit` > 10 GROUP BY `BID` HAVING COUNT(DISTINCT `SID`) > 1
  ORDER BY `AverageDeposit`
```

![Screenschot for question 13.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/SailingDB/screenshots/13.png)

## 14. Get name & rating of sailors with rating exceeding 7 who made any reservation with 0 deposit.

```sql
SELECT `Sname`, `Rating` FROM Sailors
  WHERE `Rating` > 7
    AND `SID` IN(SELECT `SID` FROM Reserves WHERE `Deposit` = 0)
```

![Screenschot for question 14.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/SailingDB/screenshots/14.png)

## 15. Get names of boats located in a place other than Hout Bay or Fish Hoek.

```sql
SELECT `Bname` FROM Boats WHERE `Location` NOT IN('Hout Bay', 'Fish Hoek')
```

![Screenschot for question 15.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/SailingDB/screenshots/15.png)

## 16. Get names of boats having a fee larger than any boat located in Hout Bay.

```sql
SELECT DISTINCT `Bname` FROM Boats
  WHERE `Fee` > SOME(SELECT `Fee` FROM Boats WHERE `Location` = 'Hout Bay')
```

![Screenschot for question 16.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/SailingDB/screenshots/16.png)

## 17. Get names that are in both the sailors and the captains relations.

```sql
SELECT `Sname` FROM Sailors
  WHERE EXISTS(SELECT * FROM Captains WHERE Captains.`CID` = Sailors.`SID`)
```

![Screenschot for question 17.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/SailingDB/screenshots/17.png)

## 18. Get names of boats that have exactly 1 reservation.

> There seem to be a SQL syntax error on this statement.

```sql
SELECT `Bname` FROM Boats AS B
  WHERE UNIQUE(SELECT `BID` FROM Reserves WHERE Reserves.`BID` = B.`BID`)
```

![Screenschot for question 18.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/SailingDB/screenshots/18.png)

## 19. Get sailor ID and total deposit paid for sailors who have booked more than 1 boat.

```sql
SELECT `SID`, `TotalDeposit` FROM(
  SELECT `SID`,
  COUNT(`BID`),
  SUM(`Deposit`) FROM Reserves
    WHERE `Deposit` IS NOT NULL AND `Deposit` > 0 GROUP BY `SID`
) AS RESULT(`SID`, `NumBoats`, `TotalDeposit`) WHERE `NumBoats` > 1
```

![Screenschot for question 19.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/SailingDB/screenshots/19.png)

## 20. Get all reservation info including details of the boat booked.

```sql
SELECT * FROM Boats INNER JOIN Reserves ON Boats.`BID` = Reserves.`BID`
```

![Screenschot for question 20.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/SailingDB/screenshots/20.png)

## 21. Get all information on every boat. If a boat has reservations, including all its reservations info.

```sql
SELECT * FROM Boats LEFT OUTER JOIN Reserves ON Boats.`BID` = Reserves.`BID
```

![Screenschot for question 21.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/SailingDB/screenshots/21.png)

## 22. Create a new tuple for the boat named "Nino" which has fee R150, BID 110, and is in Fish Hoek.

```sql
INSERT INTO Boats VALUES('Nino', 110, 150, 'Fish Hoek')
```

### Added *Boats*

| Bname | BID | Fee | Location |
| --- | ---: | ---: | --- |
| Nino | 110 | 150 | Fish Hoek |

## 23. Remove all bookings from Reserves where there is no deposit.

```sql
DELETE FROM Reserves WHERE `Deposit` IS NULL OR `Deposit` = 0
```

### Removed *Reserves*

| SID | BID | Day | Deposit |
| ---: | ---: | :---: | ---: |
| 25 | 101 | 2014-08-08 | 0 |
| 33 | 109 | 2014-09-04 | 0 |
| 33 | 104 | 2014-09-11 | 0 |

## 24. Increase the fee of every boat by 12%.

```sql
UPDATE Boats SET `Fee` = `Fee` * 1.12
```

### Updated **Boats**

| Bname | BID | Fee | Location |
| --- | ---: | ---: | --- |
| Wayfarer | 109 | **134** | Hout Bay |
| SeaPride | 108 | **560** | Fish Hoek |
| Yupie | 101 | **448** | Hout Bay |
| Joy | 104 | **224** | Hout Bay |
| Nino | 110 | **168** | Fish Hoek |

## 25. Make a view called Bookings which hides the Deposit value i.e. only has the other 3 attributes.

```sql
CREATE VIEW Bookings AS SELECT `SID`, `BID`, `Day` FROM Reserves
```
#### Created *Bookings* view

| SID | BID | Day |
| ---: | ---: | :---: |
| 23 | 108 | 2014-08-08 |
| 23 | 109 | 2014-08-01 |
| 27 | 101 | 2014-08-09 |
| 27 | 109 | 2014-08-15 |

## 26. Create a table called Reserves with 3 integer attributes BID, SID & deposit, and a date attribute Day. Allow only deposit to be omitted, and ensure SID and BID values exist in the database. When someone books a boat it is for the whole day.

```sql
```

## 27. Add a new attribute NEEDSREPAIR to the Boats table. It is usually "N".

```sql
ALTER TABLE Boats ADD `NeedsRepair` CHAR(1) DEFAULT 'N'
```

### Updated *Boats*

| Bname | BID | Fee | Location | NeedsRepair |
| --- | ---: | ---: | --- | --- |
| Wayfarer | 109 | 134 | Hout Bay | **N** |
| SeaPride | 108 | 560 | Fish Hoek | **N** |
| Yupie | 101 | 448 | Hout Bay | **N** |
| Joy | 104 | 224 | Hout Bay | **N** |
| Nino | 110 | 158 | Fish Hoek | **N** |

## 28. We should not be ageist. Remove the Age attribute.

```sql
ALTER TABLE Sailors DROP `Age`
```

### Updated *Sailors*

| Sname | SID | Rating |
| --- | ---: | ---: |
| Marx | 23 | 8 |
| Martin | 25 | 9 |
| Adams | 27 | 8 |
| Carrey | 33 | 10 |

## 29. Remove the Captains relation altogether so that nobody can try insert or use Captains in future.

```sql
DROP TABLE Captains
```
