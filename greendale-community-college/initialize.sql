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
