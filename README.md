# IIT CS425

Spring 2023 Database Organization at Illinois Tech.

## [MySQL](https://www.w3schools.com/MySQL/mysql_sql.asp)

There are several non-common SQL operators in this handbook.

| Operator | Denotes |
| --- | --- |
| [ ] | Optional |
| \| | Choices |

### SELECT

```sql
SELECT * FROM table1;
```

### WHERE

```sql
SELECT * FROM table1 WHERE condition1;
```

### AND, OR & NOT

```sql
SELECT * FROM table1 WHERE [NOT] condition1 AND|OR condition2 AND|OR ...;
```

### ORDER BY

```sql
SELECT * FROM table1 ORDER BY `column1`, `column2`, ... [ASC|DESC];
```

### INSERT INTO

```sql
INSERT INTO table1[(`column1`, `column2`, ...)]
  VALUES('value1', 'value2', ...);
```

### NULL

```sql
SELECT * FROM table1 WHERE `column1` IS [NOT] NULL;
```

### UPDATE

```sql
UPDATE table1 SET `column1` = 'value1', `column2` = 'value2', ...
  WHERE condition1;
```

### DELETE

```sql
DELETE FROM table1 WHERE condition1;
```

### LIMIT

```sql
SELECT * FROM table1 WHERE condition1 LIMIT number1;
```

### MIN, MAX, COUNT, AVG & SUM

```sql
SELECT MIN|MAX|COUNT|AVG|SUM(`column1`) FROM table1 WHERE condition1;
```

### LIKE

```sql
SELECT * FROM table1 WHERE `column1` LIKE pattern1;
```

#### Wildcards

| Symbol | Description |
| :--- | --- |
| % | 	Represents zero or more characters. |
| _ | Represents a single character. |

### IN

```sql
SELECT * FROM table1 WHERE `column1` IN('value1', 'value2', ...);
SELECT * FROM table1 WHERE `column1` IN(SELECT * FROM table2);
```

### BETWEEN

```sql
SELECT * FROM table1 WHERE `column1` BETWEEN 'value1' AND|OR 'value2';
```

### AS

```sql
SELECT `column1` AS `alias1` FROM table1;
SELECT * FROM table1 AS T;
```

### JOIN

```sql
SELECT * FROM table1 INNER|LEFT|RIGHT|CROSS JOIN table2 ON condition1;
```

#### Join Types

| Inner | Left | Right | Cross |
| --- | --- | --- | --- |
| ![INNER JOIN image.](https://www.w3schools.com/MySQL/img_innerjoin.gif) | ![LEFT JOIN image.](https://www.w3schools.com/MySQL/img_leftjoin.gif) | ![RIGHT JOIN image.](https://www.w3schools.com/MySQL/img_rightjoin.gif) | ![CROSS JOIN image.](https://www.w3schools.com/MySQL/img_crossjoin.png) |

#### Self Join

```sql
SELECT * FROM table1 T1, table1 T2 WHERE condition1;
```

### UNION

```sql
SELECT * FROM table1 UNION [ALL] SELECT * FROM table2;
```

### GROUP BY

```sql
SELECT * FROM table1 WHERE condition1
  GROUP BY `column1`, `column2`, ...
  ORDER BY `column1`, `column2`, ...;
```

### HAVING

```sql
SELECT * FROM table1 GROUP BY `column1`, `column2`, ... HAVING condition1;
```

### EXISTS

```sql
SELECT * FROM table1
  WHERE EXISTS(SELECT `column1` FROM table2 WHERE condition1);
```

### ANY & ALL

```sql
SELECT * FROM table1
  WHERE `column1` = ANY|ALL(SELECT `column1` FROM table2 WHERE condition1);
```

### INSERT INTO SELECT

```sql
INSERT INTO table2[(`column1`, `column2`, ...)]
  SELECT column1, column2, ... FROM table1 WHERE condition1;
```

### CASE

```sql
CASE
  WHEN condition1 THEN 'value1'
  WHEN condition2 THEN 'value2'
  ...
  ELSE 'defaultValue'
END;
```

### IFNULL & COALESCE

```sql
SELECT `UnitPrice` * (`UnitsInStock` + IFNULL|COALESCE(`UnitsOnOrder`, 0))
  FROM Products;
```

### Comments

```sql
-- Hello World
```

### Operators

<table>
<tr><th>Arithmetic</th><th>Bitwise</th></tr>
<tr><td>

| Operator | Description |
| --- | --- |
| + | Add |
| - | Subtract |
| * | Multiply |
| / | Divide |
| % | Modulo |

</td><td>

| Operator | Description |
| --- | --- |
| & | AND |
| \| | OR |
| ^ | Exclusive OR |

</td></tr>
</table>

<table>
<tr><th>Comparison</th><th>Compound</th></tr>
<tr><td>

| Operator | Description |
| --- | --- |
| = | Equal to |
| > | Greater than |
| < | Less than |
| >= | Greater than or equal to |
| <= | Less than or equal to |
| <> | Not equal to |

</td><td>

| Operator | Description |
| --- | --- |
| += | Add equals |
| -= | Subtract equals |
| *= | Multiply equals |
| /= | Divide equals |
| %= | Modulo equals |
| &= | AND equals |
| ^-= | Exclusive AND equals |
| \|*= | OR equals |

</td></tr>
</table>

| Operator | Description |
| --- | --- |
| `ALL` | TRUE if all of the subquery values meet the condition. |
| `AND` | TRUE if all the conditions separated by AND is TRUE. |
| `ANY` | TRUE if any of the subquery values meet the condition. |
| `BETWEEN` | TRUE if the operand is within the range of comparisons. |
| `EXISTS` | TRUE if the subquery returns one or more records. |
| `IN` | TRUE if the operand is equal to one of a list of expressions. |
| `LIKE` | TRUE if the operand matches a pattern. |
| `NOT` | Displays a record if the condition(s) is NOT TRUE. |
| `OR` | TRUE if any of the conditions separated by OR is TRUE. |
| `SOME` | TRUE if any of the subquery values meet the condition. |

### CREATE, DROP DATABASE

```sql
CREATE DATABASE database1;
DROP DATABASE database1;
```

### CREATE, DROP, ALTER TABLE

```sql
CREATE TABLE table1(
  `column1` datatype1 constraint1,
  `column2` datatype2 constraint2,
  ...
);

DROP|TRUNCATE TABLE table1;

ALTER TABLE table1 ADD|MODIFY `column1` datatype1;
ALTER TABLE table1 DROP `column1`;
```

### NOT NULL CONSTRAINT

```sql
CREATE TABLE Persons(
  `ID` INT NOT NULL,
  `LastName` VARCHAR(255) NOT NULL,
  `FirstName` VARCHAR(255) NOT NULL,
  `Age` INT
);

ALTER TABLE Persons ADD|MODIFY `Age` INT NOT NULL;
```

### UNIQUE CONSTRAINT

```sql
CREATE TABLE Persons(
  ...,
  UNIQUE(`ID`)
);
CREATE TABLE Persons(
  ...,
  CONSTRAINT UC_Person UNIQUE(`ID`, `LastName`)
);

-- altering
ALTER TABLE Persons ADD UNIQUE(`ID`);
ALTER TABLE Persons ADD CONSTRAINT UC_Person UNIQUE(`ID`, `LastName`);

-- dropping
ALTER TABLE Persons DROP INDEX UC_Person;
```

### PRIMARY & FOREIGN KEY CONSTRAINT

```sql
CREATE TABLE Orders(
  ...,
  PRIMARY KEY(`OrderID`),
  FOREIGN KEY(`PersonID`) REFERENCES Persons(`PersonID`)
);
CREATE TABLE Orders(
  ...,
  CONSTRAINT PK_Order PRIMARY KEY(`OrderID`),
  CONSTRAINT FK_PersonOrder FOREIGN KEY(`PersonID`)
    REFERENCES Persons(`PersonID`)
);

-- altering
ALTER TABLE Orders ADD PRIMARY KEY(`OrderID`), FOREIGN KEY(`PersonID`);
ALTER TABLE Orders ADD
  CONSTRAINT PK_Orer PRIMARY KEY(`OrderID`),
  CONSTRAINT FK_PersonOrder FOREIGN KEY('PersonID');

-- dropping
ALTER TABLE Persons DROP PRIMARY KEY, FOREIGN KEY(`PersonID`);
```

### CHECK on CREATE TABLE

```sql
CREATE TABLE Persons(
  ...,
  CHECK(`Age` >= 18)
);
CREATE TABLE Persons(
  ...,
  CONSTRAINT CHK_Person CHECK(`Age` >= 18 AND City = 'Sandnes')
);

-- altering
ALTER TABLE Persons ADD CHECK(`Age` >= 18);
ALTER TABLE Persons ADD
  CONSTRAINT CHK_PersonAge CHECK(`Age` >= 18 AND City = 'Sandnes');

-- dropping
ALTER TABLE Persons DROP CHECK CHK_PersonAge;
```

### DEFAULT on CREATE TABLE

```sql
CREATE TABLE Persons(
  `ID` INT NOT NULL,
  `LastName` VARCHAR(255) NOT NULL,
  `FirstName` VARCHAR(255),
  `Age` INT,
  `City` VARCHAR(255) DEFAULT 'Sandnes'
);

-- altering
ALTER TABLE Persons ALTER `City` SET DEFAULT 'Sandnes';

-- dropping
ALTER TABLE Persons ALTER `City` DROP DEFAULT;
```

### CREATE INDEX

```sql
CREATE [UNIQUE] INDEX `index1` ON table1(`column1`, `column2`, ...);

-- dropping
ALTER TABLE DROP INDEX `index1`
```

### AUTO_INCREMENT

```sql
CREATE TABLE Persons(
  `PersonID` INT NOT NULL AUTO_INCREMENT,
  `LastName` VARCHAR(255) NOT NULL,
  `FirstName` VARCHAR(255),
  `Age` INT,
  PRIMARY KEY(`PersonID`)
);

-- altering
ALTER TABLE Persons AUTO_INCREMENT = 100;
```

### Dates

| Data Type | Format |
| --- | --- |
| `DATE` | YYYY-MM-DD |
| `DATETIME` | YYYY-MM-DD HH:mm:ss |
| `TIMESTAMP` | YYYY-MM-DD HH:mm:ss |
| `YEAR` | YYYY or YY |

### Views

```sql
CREATE VIEW view1 AS SELECT `column1`, `column2`, ...
  FROM table1 WHERE condition1;

-- updating
CREATE OR REPLACE VIEW view1 AS SELECT `column1`, `column2`, ...
  FROM table1 WHERE condition1;

-- dropping
DROP VIEW view1;
```
