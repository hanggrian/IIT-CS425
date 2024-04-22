# [MySQL database](https://www.w3schools.com/MySQL/mysql_create_db.asp)

## CREATE, DROP DATABASE

```sql
CREATE DATABASE database1;
DROP DATABASE database1;
```

## CREATE, DROP, ALTER TABLE

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

## NOT NULL CONSTRAINT

```sql
CREATE TABLE Persons(
  `ID` INT NOT NULL,
  `LastName` VARCHAR(255) NOT NULL,
  `FirstName` VARCHAR(255) NOT NULL,
  `Age` INT
);

ALTER TABLE Persons ADD|MODIFY `Age` INT NOT NULL;
```

## UNIQUE CONSTRAINT

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

## PRIMARY & FOREIGN KEY CONSTRAINT

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

## CHECK on CREATE TABLE

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

## DEFAULT on CREATE TABLE

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

## CREATE INDEX

```sql
CREATE [UNIQUE] INDEX `index1` ON table1(`column1`, `column2`, ...);

-- dropping
ALTER TABLE DROP INDEX `index1`
```

## AUTO_INCREMENT

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

## Dates

Data Type | Format
--- | ---
`DATE` | YYYY-MM-DD
`DATETIME` | YYYY-MM-DD HH:mm:ss
`TIMESTAMP` | YYYY-MM-DD HH:mm:ss
`YEAR` | YYYY or YY

## Views

```sql
CREATE VIEW view1 AS SELECT `column1`, `column2`, ...
  FROM table1 WHERE condition1;

-- updating
CREATE OR REPLACE VIEW view1 AS SELECT `column1`, `column2`, ...
  FROM table1 WHERE condition1;

-- dropping
DROP VIEW view1;
```
