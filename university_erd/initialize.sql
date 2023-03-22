CREATE SCHEMA IF NOT EXISTS UniversityERD;
USE UniversityERD;

DROP TABLE IF EXISTS Sections;
DROP TABLE IF EXISTS TimeSlots;
DROP TABLE IF EXISTS Classrooms;
DROP TABLE IF EXISTS Courses;
DROP TABLE IF EXISTS Students;
DROP TABLE IF EXISTS Instructors;
DROP TABLE IF EXISTS Departments;

-- Departments

CREATE TABLE Departments(
  `dept_name` VARCHAR(50) PRIMARY KEY,
  `building` VARCHAR(50),
  `budget` DECIMAL(15, 2)
);

-- Instructors

CREATE TABLE Instructors(
  `ID` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(50),
  `salary` DECIMAL(15, 2),

  `department_name` VARCHAR(50) NOT NULL,
  CONSTRAINT inst_dept FOREIGN KEY(`department_name`) REFERENCES Departments(`dept_name`)
);

-- Students

CREATE TABLE Students(
  `ID` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(50),
  `tot_cred` FLOAT(2, 2),

  `department_name` VARCHAR(50) NOT NULL,
  `instructor_id` INT,
  CONSTRAINT stud_dept FOREIGN KEY(`department_name`) REFERENCES Departments(`dept_name`),
  CONSTRAINT advisor FOREIGN KEY(`instructor_id`) REFERENCES Instructors(`ID`)
);

-- Courses

CREATE TABLE Courses(
  `course_id` VARCHAR(10) PRIMARY KEY,
  `title` VARCHAR(50),
  `credits` INT(1),

  `department_name` VARCHAR(50) NOT NULL,
  `prereq_id` VARCHAR(10),
  CONSTRAINT course_dept FOREIGN KEY(`department_name`) REFERENCES Departments(`dept_name`),
  CONSTRAINT prereq FOREIGN KEY(`prereq_id`) REFERENCES Courses(`course_id`)
);

-- Classrooms

CREATE TABLE Classrooms(
  `building` VARCHAR(50) NOT NULL,
  `room_number` VARCHAR(5) NOT NULL,
  `capacity` INT(3),
  PRIMARY KEY(`building`, `room_number`)
);

-- TimeSlots

CREATE TABLE TimeSlots(
  `time_slot_id` INT AUTO_INCREMENT PRIMARY KEY,
  `day` INT(1),
  `start_time` TIME,
  `end_time` TIME
);

-- Sections

CREATE TABLE Sections(
  `sec_id` VARCHAR(5) PRIMARY KEY,
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
