<!-- hotfix: KaTeX -->
<!-- https://github.com/yzane/vscode-markdown-pdf/issues/21/ -->
<script type="text/javascript" src="http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>
<script type="text/x-mathjax-config">MathJax.Hub.Config({ tex2jax: { inlineMath: [['$', '$']] }, messageStyle: 'none' });</script>

# Mid exam

> ## Instructions
>
> ### Answer ALL questions.
>
> ### Time allowed: 1 hour.

> ## Database context
>
> Consider the following database schema (__*RateMyProfDB*__) to answer all the
  questions below.
>
> <small>Table: Professor<br>PK: PID</small><br>PID | <br><br>Pname | <br><br>Papers | <br><br>Topic
> --- | --- | --- | ---
> **109** | Steven | 10 | Java
> **110** | Francis | 50 | Databases
> **111** | Daniel | 40 | Java
> **112** | Joy | 20 | Java
>
> <small>Table: Rating<br>PK: SID + PID<br>FK: SID, PID</small><br>SID | <br><br><br>PID | <br><br><br>Score | <br><br><br>Attended
> --- | --- | --- | ---
> **23** | **109** | 6 | 60
> **23** | **110** | 10 | 70
> **25** | **111** | 8 | 40
> **27** | **111** | 9 | 100
> **27** | **109** | 4 | 20
> **33** | **109** | 5 | 80
> **33** | **112** | 1 | 4
>
> <small>Table: Student<br>PK: SID</small><br>SID | <br><br>Sname | <br><br>Uni | <br><br>GPA
> --- | --- | --- | ---
> **23** | Michelle | Illinois Tech | 50.52
> **25** | Tomas | UChi | 20.71
> **27** | Biden | Illinois Tech | 34.66
> **33** | John | UChi | 10.82
>
> <small>Metadata</small><br>Item | <br>Description
> --- | ---
> PID | professor identification number (auto number field)
> Pname | name of Profeesor (not nullable field)
> Papers | number of papers published by professor (nullable field)
> Topic | professor's topic/area of speciality (nullable field)
> SID | student's identification number (non-auto increment field)
> Sname | name of student (not nullable)
> Uni | a university students attend (not nullable)
> GPA | student's grade average point (nullable)
> Score | rating score from student (can only be between 0 and 10), and not nullable.
> Attended | percentage of students that participated in the rating (not nullable)
> PK | primary key
> FK | foreign key

## Relation algebra problem 1

> Write a relational algebra statement to select all professors whose specialty
  is not Java.

$$\sigma_\text{Topic = 'Java'} (\text{Professor})$$

## Relation algebra problem 1

> Write a relational-algebra expression that returns the name and GPA of
  students with a GPA greater than 30 and studies at Illinois Tech.

$$\pi_\text{Sname,GPA} \sigma_\text{GPA > 30} \cap_\text{Uni = 'Illinois Tech'} (\text{Student})$$

## Relation algebra problem 1

> Write a relational-algebra expression to find all information about students
  and ratings, and the students who gave a score less than five.

$$
\sigma_\text{Score > 5} (\text{Student}\bowtie_\text{Student.SID = Rating.SID}\text{Rating}) \\
\text{or} \\
\sigma_\text{Score > 5} \cap_\text{Student.SID = Rating.SID} (\text{Student}\times\text{Rating})
$$

## SQL problem 1

> Write the SQL commands to create the database and the relations shown in the
  diagram above.

```sql
CREATE DATABASE RateMyProfDB;
USE RateMyProfDB;

CREATE TABLE Professor(
  PID INT AUTO_INCREMENT,
  Pname VARCHAR(25) NOT NULL,
  Papers INT,
  Topic VARCHAR(25),
  PRIMARY KEY(PID)
);

CREATE TABLE Student1(
  SID INT,
  Sname VARCHAR(25) NOT NULL,
  Uni VARCHAR(25) NOT NULL,
  GPA DOUBLE,
  PRIMARY KEY(SID)
);

CREATE TABLE Rating1(
  SID INT,
  PID INT,
  Score INT NOT NULL,
  Attended INT NOT NULL,
  PRIMARY KEY(SID, PID),
  FOREIGN KEY(SID) REFERENCES Student1(SID),
  FOREIGN KEY(PID) REFERENCES Professor(PID),
  CHECK(Score BETWEEN 0 AND 10)
);
```

## SQL problem 2

> Return the names of students attending a university other than `Illinois tech`
  or `UChi`.

```sql
SELECT `Sname` FROM Student1
  WHERE `Uni` NOT IN('Illinois Tech', 'UChi');
-- or
SELECT `Sname` FROM Student1
  WHERE `Uni` <> 'Illinois Tech' AND `Uni` <> 'UChi';
-- or
SELECT `Sname` FROM Student1
  WHERE `Sname` NOT IN(SELECT `Sname` FROM Student1
    WHERE `Uni` = 'Illinois Tech' OR `Uni` = 'UChi');
```

## SQL problem 3

> Find the average score given to each professor who has been rated more than
  once.

```sql
SELECT P.`PID`, P.`Pname`, AVG(`Score`)
  FROM Rating1 AS R , Professor AS P
  WHERE R.`PID` = P.`PID`
  GROUP BY `PID`
  HAVING COUNT(*) > 1;
-- or
SELECT `Pname`, AVG(`Score`) FROM Rating1
  NATURAL JOIN Professor GROUP BY `PID` HAVING COUNT(*) > 1;
```

## SQL problem 4

> Get the names of professors along with each score they got and the student
  who gave them that score.

```sql
SELECT P.`Pname`, R.`Score`, S`.Sname`
  FROM Professor AS P, Rating1 AS R, Student1 AS S
  WHERE P.`PID` = R.`PID` and S.`SID` = R.`SID`;
-- or
SELECT P.`Pname`, R.`Score`, S.`Sname`
  FROM Professor AS P
  JOIN Rating1 AS R ON P.`PID` = R.`PID`
  JOIN Student1 AS S ON R.`SID` = S.`SID`;
-- or
SELECT `Pname`, `Score`, `Sname`
  FROM Professor
  NATURAL JOIN Rating1
  NATURAL JOIN Student1;
```

## SQL problem 5

> Update the professor table by increasing the number of papers by one for
  either `steven` or `joy`.

```sql
UPDATE Professor SET `Papers` = `Papers` + 1
  WHERE `Pname` IN('Steven', 'Joy');
-- or
UPDATE Professor SET `Papers` = `Papers` + 1
  WHERE `Pname` = 'Steven' OR `Pname` = 'Joy';
-- or
UPDATE Professor SET `Papers` = (
  CASE
  WHEN `Pname` = 'Steven' OR `Pname` = 'Joy' THEN `Papers` + 1
  ELSE `Papers` + 0
  END);
```

## SQL problem 6

> Which professors have ever scored less than one of prof 111's scores?

```sql
SELECT DISTINCT P.`PID`, P.`Pname` FROM Professor AS P, Rating1 AS R
  WHERE P.`PID` = R.`PID`
    AND `Score` < SOME(SELECT MIN(`Score`) FROM Rating1 WHERE `PID` = 111);
-- or
SELECT DISTINCT P.`PID`, P.`Pname`
  FROM Professor AS P, Rating1 AS R
  WHERE P.`PID` = R.`PID`
    AND `Score` < SOME(SELECT `Score` FROM Rating1 where `PID` = 111);
```

## SQL problem 7

> Write an SQL statement that inserts new rows shown in the table below.
>
> PID | Pname | Papers | Topic
> --- | --- | --- | ---
> 113 | George | 40 | Networks
> 114 | Ethan | 10 | Python
> 115 | Markus | 15 | ICT40

```sql
INSERT INTO Professor(`Pname`, `Papers`, `Topic`) VALUES
  ('George', 40, 'Networks'),
  ('Ethan', 10, 'Python'),
  ('Markus', 15, 'ICT4D');
```

## SQL problem 8

> Using the SQL command below, answer the following questions.
>
> ```sql
> SELECT PID, sum(Score)/count(*)
> FROM Rating
> WHERE Attended > 50
> GROUP BY PID
> HAVING count(*) > 1;
> ```

### Subproblem 8A

> Give the expected output/result in form of a table.

PID | SUM(`Score`) / COUNT(*)
--- | ---
109 | 5.5

### Subproblem 8B

> Provide an explanation of your output.

Considering only majority rating (i.e, `Attended > 50` percent), the query
returns the average score given to each professor (as a effect
of `GROUP BY PID`) who has been rated more than once (filtered by the `HAVING`
Clause). Thus, results in a single row showing `PID` = `109` and `Average`
rating score = `5.5`.
