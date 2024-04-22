# [Homework 1.8](https://github.com/hendraanggrian/IIT-CS425/blob/assets/assignments/hw8.pdf): Greendale Community College

> A spreadsheet has been created that stores results of students at Greendale
  Community College. This includes annual promotion code `REN` (excluded), `CON`
  (can continue) or `QUA` (qualifies), along with information about the courses;
  their pre-requisites and lecture periods. You are required to design a
  relational database that can represent the information contained in the
  spreadsheet as closely and completely as possible.

[View full code](https://github.com/hendraanggrian/IIT-CS425/blob/main/greendale-community-college/initialize.sql)

## Problem 1

> Draw an ER (entity-relationship) diagram for the information in the
  spreadsheet, using the notation employed in this course. If necessary, state
  any assumptions you had to make when modelling this college data, and explain
  any modelling decisions taken.

### ER diagram

![The ER diagram.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/greendale-community-college/er.svg)

[View diagram file](https://github.com/hendraanggrian/IIT-CS425/blob/main/greendale-community-college/er.drawio)

### UML diagram

![The UML diagram.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/greendale-community-college/uml.svg)

[View diagram file](https://github.com/hendraanggrian/IIT-CS425/blob/main/greendale-community-college/uml.drawio)

### SQL initialization

```sql
CREATE SCHEMA IF NOT EXISTS GreendaleCommunityCollege;
USE GreendaleCommunityCollege;

DROP TABLE IF EXISTS TimeSlots;
DROP TABLE IF EXISTS Registrations;
DROP TABLE IF EXISTS Courses;
DROP TABLE IF EXISTS Students;
DROP TABLE IF EXISTS Departments;
DROP TABLE IF EXISTS Convenors;

CREATE TABLE Convenors(
  `conv_id` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(50) NOT NULL
);

CREATE TABLE Departments(
  `dept_name` VARCHAR(50) PRIMARY KEY
);

CREATE TABLE Students(
  `conv_id` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(50) NOT NULL,
  `dept_name` VARCHAR(50) NOT NULL,
  FOREIGN KEY(`dept_name`) REFERENCES Departments(`dept_name`)
);

CREATE TABLE Courses(
  `course_id` VARCHAR(10) PRIMARY KEY,
  `year` YEAR NOT NULL,
  `passing_mark` INT NOT NULL,
  `prereq` VARCHAR(100),
  `conv_id` INT NOT NULL,
  FOREIGN KEY(`conv_id`) REFERENCES Convenors(`conv_id`)
);

CREATE TABLE Registrations(
  `stud_id` INT,
  `conv_id` INT,
  `promo_code` VARCHAR(3) NOT NULL,
  `mark` INT,
  `symbol` VARCHAR(5),
  `qualify` CHAR(1),
  PRIMARY KEY(`stud_id`, `conv_id`)
);

CREATE TABLE TimeSlots(
  `timeslot_id` INT AUTO_INCREMENT PRIMARY KEY,
  `day` INT NOT NULL,
  `time_start` TIME NOT NULL DEFAULT '9:00:00',
  `time_end` TIME NOT NULL DEFAULT '11:00:00',
  `course_id` VARCHAR(10) NOT NULL,
  FOREIGN KEY(`course_id`) REFERENCES Courses(`course_id`)
);
```

[View full code](https://github.com/hendraanggrian/IIT-CS425/blob/main/greendale-community-college/initialize.sql)

## Problem 2

> Represent your ERD as a logical design showing relation schemas of this data.
  Choose 3 or 4 rows from the spreadsheet and show how that data would be stored
  in your database. Briefly describe any design decisions taken and any
  limitations of your schema.

Below are 4 rows selected as dummy data. The current limitation is
`Prerequisites` being a simple column, instead of recursive relationship of
table `Courses`.

Name | Qualify | Promotion Code | Year | Course | Convenor | Periods | Mark | Symbol
--- | --- | --- | --- | --- | --- | --- | --- | ---
Abbot | N | CON | 2019 | BOT202 | Marx | BOT104 or ZOO103 | 5th Daily	54 | 3
Chang | N | CON | 2019 | MAM200 | Bush | MAM100 | 4th Daily | 66 | 2-
Hassan | N | CON | 2019 | CSC117 | Carrey | | 4th or 5th Daily | 60 | 2-
Inggs | N | CON | 2019 | ZOO203 | Lee | BOT104 or ZOO103 | 5th Daily	66 | 2-

### SQL data

```sql
INSERT INTO Convenors VALUES
  (1, 'Marx'),
  (2, 'Bush'),
  (3, 'Carrey'),
  (4, 'Lee');

INSERT INTO Departments VALUES
  ('Robotics'),
  ('Marketing Management'),
  ('Computer Science'),
  ('Zoology');

INSERT INTO Students VALUES
  (1, 'Abbot', 'Robotics'),
  (2, 'Chang', 'Marketing Management'),
  (3, 'Hassan', 'Computer Science'),
  (4, 'Inggs', 'Zoology');

INSERT INTO Courses VALUES
  ('BOT202', '2019', 60, 'BOT104 or ZOO103', 1),
  ('MAM200', '2019', 70, 'MAM100', 2),
  ('CSC117', '2019', 70, '', 3),
  ('ZOO203', '2019', 70, 'BOT104 or ZOO103', 4);

INSERT INTO Registrations VALUES
  (1, 1, 'CON', 54, '3', 'N'),
  (2, 2, 'CON', 66, '2-', 'N'),
  (3, 3, 'CON', 60, '2-', 'N'),
  (4, 4, 'CON', 66, '2-', 'N');

INSERT INTO TimeSlots VALUES
  (NULL, 5, DEFAULT, DEFAULT, 'BOT202'),
  (NULL, 4, DEFAULT, DEFAULT, 'MAM200'),
  (NULL, 4, DEFAULT, DEFAULT, 'CSC117'), (NULL, 5, DEFAULT, DEFAULT, 'CSC117'),
  (NULL, 5, DEFAULT, DEFAULT, 'ZOO203');
```

[View full code](https://github.com/hendraanggrian/IIT-CS425/blob/main/greendale-community-college/data.sql)

## Problem 3

> Give any one functional dependency that holds for this information, and state
  in simple words what your functional dependency tells us about the data.

![The NMF diagram.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/greendale-community-college/nmf.svg)

[View diagram file](https://github.com/hendraanggrian/IIT-CS425/blob/main/greendale-community-college/nmf.drawio)

Dependency diagram above is constructed only from `Students.csv`, not the ER or
UML diagram.

- Partial dependencies
  - **Name &rarr; (Qualify, Promotion Code)** beacause their values are tied to
    PK *Name*.
  - **Course &rarr; (Convenor, Prerequisites)** beacause their
    values are tied to PK *Course*.
- Transitive dependencies
  - **Mark &rarr; (Symbol)** because its value is derived from *Mark* which is
    not a PK.
- Functional dependencies
  - **Name, Course &rarr; (Qualify, Promotion Code, Convenor, Prerequisites,
    Mark, Symbol)** beacuse their values depend on CK *Name + Course*.

## Problem 4

> Is your relation scheme in 1st normal form or not? Give a reason for your
  answer.

> ### [1NF rules](../nmf.md#1nf-rules)
>
> - Each table cell should contain a single value.
> - Each record needs to be unique.

Based on those rules, **the relation scheme is 1NF**.

## Problem 5

> Is your relation scheme in 3rd normal form or not? Give a reason for your
  answer.

> ### [2NF rules](../nmf.md#2nf-rules)
>
> - Single Column Primary Key that does not functionally dependant on any subset
  of candidate key relation.
>
>
> ### [3NF rules](../nmf.md#1nf-rules)
>
> - Has no transitive functional dependencies.

**The relation scheme is not 3NF** because it has transitive functional
dependency **Mark &rarr; (Symbol)**.
