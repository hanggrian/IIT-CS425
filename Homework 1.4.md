# CS425: [Homework 1.4](https://github.com/hendraanggrian/IIT-CS425/raw/assets/Homework%201.4.pdf)

## 1. Output the number of rows in each of your 4 relations (using 4 SELECT statements) in this order: Coach, Person, Player, Team. Call the result column LOADED each time.

```sql
SELECT COUNT(*) AS 'Loaded' FROM Coaches
  UNION SELECT COUNT(*) AS 'Loaded' FROM Persons
  UNION SELECT COUNT(*) AS 'Loaded' FROM Players
  UNION SELECT COUNT(*) AS 'Loaded' FROM Teams;
```

![Screenschot for answer 1.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/BasketballDB/screenshots/1.png)

## 2. Output everything in the Team relation, in ascending (increasing) order of TmID.

```sql
SELECT * FROM Teams ORDER BY `TmID`;
```

![Screenschot for answer 2.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/BasketballDB/screenshots/2.png)

## 3. Show the TmID of teams with Milwaukee in their name, but your query must cater for the fact that people spell this incorrectly - everyone starts with "MIL", then somewhere later they have a "W" and even later a "K". Example misspellings are Millwaukee, Milwakee, Milwuakee, Milwaukey, etc.

```sql
SELECT `TmId` FROM Teams WHERE `Name` LIKE 'mil%w%k%';
```

![Screenschot for answer 3.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/BasketballDB/screenshots/3.png)

## 4. What are the lowest and the highest number of Games (played) in the coach data? Call the first result column LOWEST and the second result column HIGHEST.

```sql
SELECT MIN(`Games`) AS `LOWEST`, MAX(`Games`) AS `HIGHEST` FROM Coaches;
```

![Screenschot for answer 4.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/BasketballDB/screenshots/4.png)

## 5. Show the BiolD, TmID, Points and Attempts for each player that scored more than 2000 points, in decreasing order of Points. Players with the same number of Points should be shown in alphabetical order of BiolD.

```sql
SELECT `BioID`, `TmID`, `Points`, `Attempts` FROM Players
  WHERE `Points` > 2000 ORDER BY `Points` DESC;
```

![Screenschot for answer 5.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/BasketballDB/screenshots/5.png)

## 6. Show BiolD of persons born in Gabon (GAB) and BiolD of persons born in Egypt (EGY), if any.

```sql
SELECT `BioID` FROM Persons
  WHERE `BirthCountry` = 'GAB' OR `BirthCountry` = 'EGY';
```

![Screenschot for answer 6.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/BasketballDB/screenshots/6.png)

## 7. Show the following for Jamesle01: his Points, the full Name of his team, & the BiolD of his team's coach.

```sql
SELECT P.`Points` AS `PlayerPoints`, T.`TmID` AS `TeamName`, C.`BioID` AS `CoachBio`
  FROM Players AS P, Teams AS T, Coaches AS C
  WHERE P.`BioID` = 'jamesle01'
    AND P.`TmID` = T.`TmID`
    AND P.`TmID` = C.`TmID`;
```

![Screenschot for answer 7.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/BasketballDB/screenshots/7.png)

## 8. Show the BolD of players whose BirthCountry is in the data, but their BirthCity is not in the data.

```sql
SELECT `BioID`
  FROM(SELECT P.`BioID`, P.`BirthCountry`, P.`BirthCity`
    FROM Persons AS P, Players
      WHERE Players.`BioID` = P.`BioID`)
  AS RESULT(`BioID`, `BirthCountry`, `BirthCity`)
  WHERE(`BirthCountry` IS NOT NULL AND `BirthCountry` != '')
    AND(`BirthCity` IS NULL OR `BirthCity` = '');
```

![Screenschot for answer 8.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/BasketballDB/screenshots/8a.png)

### Alternative answer

There are no players that satisfies the predicate. What I can find, however, are **persons** with such conditions.

```sql
SELECT `BioID`
  FROM(SELECT `BioID`, `BirthCountry`, `BirthCity` FROM Persons)
  AS RESULT(`BioID`, `BirthCountry`, `BirthCity`)
  WHERE(`BirthCountry` IS NOT NULL AND `BirthCountry` != '')
    AND(`BirthCity` IS NULL OR `BirthCity` = '');
```

![Screenschot for alternative answer 8.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/BasketballDB/screenshots/8b.png)

## 9. Which coach(es) Won the most games? Give BiolD and the number Won.

```sql
SELECT `BioID`, `Won` FROM Coaches
  WHERE `Games` IN(SELECT MAX(`Games`) FROM Coaches);
```

![Screenschot for answer 9.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/BasketballDB/screenshots/9.png)

## 10. What percentage of players have Points scored as zero? Call the result column NONSCORERS.

```sql
SELECT SUM(`Points` = 0) / COUNT(*) * 100 AS `NONSCORERS` FROM Players;
```

![Screenschot for answer 10.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/BasketballDB/screenshots/10.png)

## 11. How many teams have Lost more games than they have Won? Call the result column LOSERS.

```sql
SELECT SUM(`Lost` > `Won`) AS `LOSERS` FROM Teams;
```

![Screenschot for answer 11.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/BasketballDB/screenshots/11.png)

## 12. How many teams belong to each ConflD? Call the 2nd column CONFSIZE.

```sql
SELECT `ConfID`, COUNT(*) AS `CONFSIZE`
	  FROM Teams WHERE `ConfID` = 'EC'
  UNION SELECT `ConfID`, COUNT(*) AS `CONFSIZE`
	  FROM Teams WHERE `ConfID` = 'WC';
```

![Screenschot for answer 12.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/BasketballDB/screenshots/12.png)

## 13. How many countries do the persons in this data come from? Call the result column NUMLANDS.

```sql
SELECT `BirthCountry`, COUNT(*) AS `NUMLANDS` FROM Persons
  WHERE `BirthCountry` IS NOT NULL AND `BirthCountry` != ''
  GROUP BY `BirthCountry` ORDER BY `BirthCountry`;
```

![Screenschot for answer 13.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/BasketballDB/screenshots/13.png)

## 14. Which pairs of teams have the exact same record (meaning the same values for Won and the same values for Lost)? Show their Won value and then their Lost value and then the 2 Names, making sure that the 3rd column Name is alphabetically before the 4th column Name so information is not repeated. Call the 3rd column TEAM1 and the 4th column TEAM2.

```sql
SELECT A.`Won`, A.`Lost`, A.`Name` as `TEAM1`, B.`Name` as `TEAM2`
  FROM Teams as A, Teams as B
  WHERE A.`Won` = B.`Won` AND A.`Lost` = B.`Lost`
    AND A.`Name` < B.`Name`;
```

![Screenschot for answer 14.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/BasketballDB/screenshots/14.png)

## 15. For each of the 5 Rankings in Confld "EC", show the average number of games Lost by those teams. Call the 2nd column AVLOSSES.

```sql
SELECT `Ranking`, AVG(`Lost`) AS `AVLOSSES`
  FROM Teams WHERE `ConfID` = 'EC'
  GROUP BY `Ranking` ORDER BY `Ranking`;
```

![Screenschot for answer 15.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/BasketballDB/screenshots/15.png)

## 16. Give a SQL statement to output "INVALID" if any information in any row (tuple) of any relation has invalid data (e.g., the Games value is not equal to Won value plus Lost value). If all the data is valid then it should output an empty table. Call the result column ANYPROBLEMS.

```sql
SELECT 'INVALID' AS `ANYPROBLEMS` FROM Teams
  WHERE `Games` != `Won` + `Lost`;
```

![Screenschot for answer 16.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/BasketballDB/screenshots/16.png)
