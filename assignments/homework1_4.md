[View questions](https://github.com/hendraanggrian/IIT-CS425/raw/assets/assignments/homework1_4.pdf)
/ [homepage](https://github.com/hendraanggrian/IIT-CS425/)

# CS425: Homework 1.4

Create FOUR (4) relations called Coach, Person, Player, and Team, each
containing the same column names as those found in the corresponding CSV data
files (see attachment on BB). Load the data into the MySQL database using the
CSV files (Hint: Import the data using import wizard).

## Schema

```sql
CREATE SCHEMA IF NOT EXISTS BasketballDB;
USE BasketballDB;

CREATE TABLE Teams(
  `TmID` VARCHAR(3) PRIMARY KEY,
  `ConfID` VARCHAR(2) NOT NULL,
  `Ranking` INT NOT NULL,
  `Playoff` VARCHAR(2),
  `Name` VARCHAR(40) NOT NULL,
  `Won` INT NOT NULL,
  `Lost` INT NOT NULL,
  `Games` INT NOT NULL
);

CREATE TABLE Persons(
  `BioID` VARCHAR(10) PRIMARY KEY,
  `FirstName` VARCHAR(20),
  `LastName` VARCHAR(20) NOT NULL,
  `BirthDate` VARCHAR(10),
  `BirthCity` VARCHAR(40),
  `BirthCountry` VARCHAR(20)
);

CREATE TABLE Players(
  `BioID` VARCHAR(10) PRIMARY KEY,
  `TmID` VARCHAR(3) NOT NULL,
  `Played` INT NOT NULL,
  `Started` INT NOT NULL,
  `Minutes` INT NOT NULL,
  `Points` INT NOT NULL,
  `Attempts` INT NOT NULL,
  `Made` INT NOT NULL,
  CONSTRAINT Players_BioID FOREIGN KEY(`BioID`) REFERENCES Persons(`BioID`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT Players_TmID FOREIGN KEY(`TmID`) REFERENCES Teams(`TmID`)
    ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE Coaches(
  `BioID` VARCHAR(10) PRIMARY KEY,
  `TmID` VARCHAR(3) NOT NULL,
  `Won` INT NOT NULL,
  `Lost` INT NOT NULL,
  `Games` INT NOT NULL,
  `Stint` INT NOT NULL,
  CONSTRAINT Coaches_BioID FOREIGN KEY(`BioID`) REFERENCES Persons(`BioID`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT Coaches_TmID FOREIGN KEY(`TmID`) REFERENCES Teams(`TmID`)
    ON DELETE RESTRICT ON UPDATE RESTRICT
);
```

[View full code](https://github.com/hendraanggrian/IIT-CS425/blob/main/basketball_db/initialize.sql)
/ [table data](https://github.com/hendraanggrian/IIT-CS425/blob/main/basketball_db/)

## 1. Output the number of rows in each of your 4 relations (using 4 SELECT statements) in this order: Coach, Person, Player, Team. Call the result column `LOADED` each time.

```sql
SELECT COUNT(*) AS `LOADED` FROM Coaches
  UNION SELECT COUNT(*) AS `LOADED` FROM Persons
  UNION SELECT COUNT(*) AS `LOADED` FROM Players
  UNION SELECT COUNT(*) AS `LOADED` FROM Teams;
```

![Screenschot for answer 1.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/basketball_db/1.png)

## 2. Output everything in the Team relation, in ascending (increasing) order of `TmID`.

```sql
SELECT * FROM Teams ORDER BY `TmID`;
```

![Screenschot for answer 2.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/basketball_db/2.png)

## 3. Show the `TmID` of teams with Milwaukee in their name, but your query must cater for the fact that people spell this incorrectly - everyone starts with "MIL", then somewhere later they have a "W" and even later a "K". Example misspellings are Millwaukee, Milwakee, Milwuakee, Milwaukey, etc.

```sql
SELECT `TmId` FROM Teams WHERE `Name` LIKE 'mil%w%k%';
```

![Screenschot for answer 3.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/basketball_db/3.png)

## 4. What are the lowest and the highest number of `Games` (played) in the coach data? Call the first result column `LOWEST` and the second result column `HIGHEST`.

```sql
SELECT MIN(`Games`) AS `LOWEST`, MAX(`Games`) AS `HIGHEST` FROM Coaches;
```

![Screenschot for answer 4.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/basketball_db/4.png)

## 5. Show the `BioID`, `TmID`, `Points` and `Attempts` for each player that scored more than 2000 points, in decreasing order of `Points`. Players with the same number of `Points` should be shown in alphabetical order of `BioID`.

```sql
SELECT `BioID`, `TmID`, `Points`, `Attempts` FROM Players
  WHERE `Points` > 2000
  ORDER BY `Points` DESC, `BioID` ASC;
```

![Screenschot for answer 5.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/basketball_db/5.png)

## 6. Show `BioID` of persons born in Gabon (GAB) and BioID of persons born in Egypt (EGY), if any.

```sql
SELECT `BioID` FROM Persons WHERE `BirthCountry` IN('GAB', 'EGY');
```

![Screenschot for answer 6.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/basketball_db/6.png)

## 7. Show the following for *Jamesle01*: his Points, the full Name of his team, & the BioID of his team's coach.

```sql
SELECT p.`Points` AS `PlayerPoints`, t.`TmID` AS `TeamName`,
    c.`BioID` AS `CoachBio`
  FROM Players AS p, Teams AS t, Coaches AS c
  WHERE p.`BioID` = 'jamesle01'
    AND p.`TmID` = t.`TmID`
    AND p.`TmID` = t.`TmID`;
```

![Screenschot for answer 7.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/basketball_db/7.png)

## 8. Show the `BoID` of players whose `BirthCountry` is in the data, but their `BirthCity` is not in the data.

```sql
SELECT pe.`BioID` FROM Persons AS pe, Players AS pl
  WHERE pe.`BioID` = pl.`BioID`
    AND(`BirthCountry` IS NOT NULL AND `BirthCountry` <> '')
    AND(`BirthCity` IS NULL OR `BirthCity` = '');
```

![Screenschot for answer 8.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/basketball_db/8a.png)

### Alternative answer

There are no players that satisfies the predicate. What I can find, however, are **persons** with such conditions.

```sql
SELECT `BioID` FROM Persons
  WHERE(`BirthCountry` IS NOT NULL AND `BirthCountry` <> '')
    AND(`BirthCity` IS NULL OR `BirthCity` = '');
```

![Screenschot for alternative answer 8.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/basketball_db/8b.png)

## 9. Which coach(es) `Won` the most games? Give `BioID` and the number `Won`.

```sql
SELECT `BioID`, `Won` FROM Coaches
  WHERE `Won` = (SELECT MAX(`Won`) FROM Coaches);
```

![Screenschot for answer 9.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/basketball_db/9.png)

## 10. What percentage of players have `Points` scored as zero? Call the result column `NONSCORERS`.

```sql
SELECT SUM(`Points` = 0) / COUNT(*) * 100 AS `NONSCORERS` FROM Players;
```

![Screenschot for answer 10.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/basketball_db/10.png)

## 11. How many teams have `Lost` more games than they have `Won`? Call the result column `LOSERS`.

```sql
SELECT SUM(`Lost` > `Won`) AS `LOSERS` FROM Teams;
```

![Screenschot for answer 11.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/basketball_db/11.png)

## 12. How many teams belong to each `ConfID`? Call the 2nd column `CONFSIZE`.

```sql
SELECT `ConfID`, COUNT(*) AS `CONFSIZE` FROM Teams GROUP BY `ConfID`;
```

![Screenschot for answer 12.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/basketball_db/12.png)

## 13. How many countries do the persons in this data come from? Call the result column `NUMLANDS`.

```sql
SELECT COUNT(DISTINCT `BirthCountry`) AS `NUMLANDS` FROM Persons;
```

![Screenschot for answer 13.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/basketball_db/13.png)

## 14. Which pairs of teams have the exact same record (meaning the same values for `Won` and the same values for `Lost`)? Show their `Won` value and then their `Lost` value and then the 2 Names, making sure that the 3rd column `Name` is alphabetically before the 4th column `Name` so information is not repeated. Call the 3rd column `TEAM1` and the 4th column `TEAM2`.

```sql
SELECT a.`Won`, a.`Lost`, a.`Name` AS `TEAM1`, a.`Name` AS `TEAM2`
  FROM Teams AS a, Teams AS b
  WHERE a.`Won` = b.`Won`
    AND a.`Lost` = b.`Lost`
    AND a.`Name` < b.`Name`;
```

![Screenschot for answer 14.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/basketball_db/14.png)

## 15. For each of the 5 Rankings in Confld "EC", show the average number of games `Lost` by those teams. Call the 2nd column `AVLOSSES`.

```sql
SELECT `Ranking`, AVG(`Lost`) AS `AVLOSSES` FROM Teams
  WHERE `ConfID` = 'EC'
  GROUP BY `Ranking` ORDER BY `Ranking`;
```

![Screenschot for answer 15.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/basketball_db/15.png)

## 16. Give a SQL statement to output "INVALID" if any information in any row (tuple) of any relation has invalid data (e.g., the Games value is not equal to `Won` value plus `Lost` value). If all the data is valid then it should output an empty table. Call the result column `ANYPROBLEMS`.

```sql
SELECT 'INVALID' AS `ANYPROBLEMS` FROM Teams
  WHERE `Games` <> `Won` + `Lost`;
```

![Screenschot for answer 16.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/basketball_db/16.png)
