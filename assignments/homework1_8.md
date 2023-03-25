# [Homework 1.8](https://github.com/hendraanggrian/IIT-CS425/raw/assets/assignments/homework1_8.pdf): Greendale Community College

A spreadsheet has been created that stores results of students at Greendale
Community College. This includes annual promotion code REN (excluded), CON (can
continue) or QUA (qualifies), along with information about the courses; their
pre-requisites and lecture periods. You are required to design a relational
database that can represent the information contained in the spreadsheet as
closely and completely as possible.

## Schema

```sql
CREATE SCHEMA IF NOT EXISTS GreendaleCommunityCollege;
USE GreendaleCommunityCollege;

CREATE TABLE Students(
  `Name` VARCHAR(20) PRIMARY KEY,
  `Qualify` CHAR(1) NOT NULL,
  `Promotion Code` VARCHAR(3) NOT NULL,
  `Year` YEAR NOT NULL,
  `Course` VARCHAR(6) NOT NULL,
  `Convenor` VARCHAR(20) NOT NULL,
  `Pre-Requisutes` VARCHAR(20),
  `Periods` VARCHAR(20) NOT NULL,
  `Mark` INT(3),
  `Symbol` VARCHAR(3)
);
```

[View full code](https://github.com/hendraanggrian/IIT-CS425/blob/main/greendale-community-college/initialize.sql)
