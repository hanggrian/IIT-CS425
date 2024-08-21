# [Homework 1.6](https://github.com/hanggrian/IIT-CS425/blob/assets/assignments/hw6.pdf): University ER

> ![The ER model.](https://github.com/hanggrian/IIT-CS425/raw/assets/assignments/hw6_1.png)

## Problem 1

> Translate the University ERD and represent fully as relational schemas.

Several notes about this solution:

- `grade` is flagged as **erroneous use of relationship attributes** according
  to [lecture 6th](https://github.com/hanggrian/IIT-CS425/blob/assets/lect6.pdf),
  thus will be ignored.
- The relationship names (`teaches`, `takes`, etc.) in the ER diagram above are
  being used as foreign key contraint names in the SQL table.
- Relationship's total cardinality will make the affected columns `NOT NULL`.
- Weak entity tables will make the affected constraints `ON DELETE CASCADE`,
  according to [this answer](https://stackoverflow.com/a/26448278/1567541/).

### SQL initialization

```sql
CREATE SCHEMA IF NOT EXISTS UniversityER;
USE UniversityER;

DROP TABLE IF EXISTS Sections;
DROP TABLE IF EXISTS TimeSlots;
DROP TABLE IF EXISTS Classrooms;
DROP TABLE IF EXISTS Courses;
DROP TABLE IF EXISTS Students;
DROP TABLE IF EXISTS Instructors;
DROP TABLE IF EXISTS Departments;

CREATE TABLE Departments(
  `dept_name` VARCHAR(50) PRIMARY KEY,
  `building` VARCHAR(50),
  `budget` DECIMAL(15, 2)
);

CREATE TABLE Instructors(
  `ID` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(50),
  `salary` DECIMAL(15, 2),

  `department_name` VARCHAR(50) NOT NULL,
  CONSTRAINT inst_dept FOREIGN KEY(`department_name`) REFERENCES Departments(`dept_name`)
);

CREATE TABLE Students(
  `ID` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(50),
  `tot_cred` FLOAT(2, 2),

  `department_name` VARCHAR(50) NOT NULL,
  `instructor_id` INT,
  CONSTRAINT stud_dept FOREIGN KEY(`department_name`) REFERENCES Departments(`dept_name`),
  CONSTRAINT advisor FOREIGN KEY(`instructor_id`) REFERENCES Instructors(`ID`)
);

CREATE TABLE Courses(
  `course_id` VARCHAR(10) PRIMARY KEY,
  `title` VARCHAR(50),
  `credits` INT(1),

  `department_name` VARCHAR(50) NOT NULL,
  `prereq_id` VARCHAR(10),
  CONSTRAINT course_dept FOREIGN KEY(`department_name`) REFERENCES Departments(`dept_name`),
  CONSTRAINT prereq FOREIGN KEY(`prereq_id`) REFERENCES Courses(`course_id`)
);

CREATE TABLE Classrooms(
  `building` VARCHAR(50) NOT NULL,
  `room_number` VARCHAR(5) NOT NULL,
  `capacity` INT(3),
  PRIMARY KEY(`building`, `room_number`)
);

CREATE TABLE TimeSlots(
  `time_slot_id` INT AUTO_INCREMENT PRIMARY KEY,
  `day` INT(1),
  `start_time` TIME,
  `end_time` TIME
);

CREATE TABLE Sections(
  `sec_id` VARCHAR(5),
  `semester` INT(1),
  `year` YEAR,

  `instructor_id` INT NOT NULL,
  `student_id` INT,
  `course_id` VARCHAR(10) NOT NULL,
  `building` VARCHAR(50) NOT NULL,
  `room_number` VARCHAR(5) NOT NULL,
  `time_slot_id` INT NOT NULL,
  CONSTRAINT teaches FOREIGN KEY(`instructor_id`) REFERENCES Instructors(`ID`)
    ON DELETE CASCADE,
  CONSTRAINT takes FOREIGN KEY(`student_id`) REFERENCES Students(`ID`)
    ON DELETE CASCADE,
  CONSTRAINT sec_course FOREIGN KEY(`course_id`) REFERENCES Courses(`course_id`)
    ON DELETE CASCADE,
  CONSTRAINT sec_time_slot FOREIGN KEY(`building`, `room_number`)
    REFERENCES Classrooms(`building`, `room_number`)
    ON DELETE CASCADE,
  CONSTRAINT sec_class FOREIGN KEY(`time_slot_id`) REFERENCES TimeSlots(`time_slot_id`)
    ON DELETE CASCADE
);
```

[View full code](https://github.com/hanggrian/IIT-CS425/blob/main/university-er/initialize.sql)
