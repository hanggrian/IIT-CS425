CREATE SCHEMA IF NOT EXISTS UniversityDB;
USE UniversityDB;

DROP TABLE IF EXISTS Registrations;
DROP TABLE IF EXISTS Schedules;
DROP TABLE IF EXISTS Classes;
DROP TABLE IF EXISTS Courses;
DROP TABLE IF EXISTS Schedules;
DROP TABLE IF EXISTS Lecturers;
DROP TABLE IF EXISTS Students;

CREATE TABLE Courses(
  `id` VARCHAR(10) PRIMARY KEY,
  `name` VARCHAR(50) NOT NULL
);
CREATE INDEX Courses_name ON Courses(`name`);

CREATE TABLE Lecturers(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(50) NOT NULL,
  `date_join` DATE NOT NULL
);
CREATE INDEX Lecturers_name ON Lecturers(`name`);

CREATE TABLE Students(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(50) NOT NULL,
  `date_join` DATE NOT NULL,
  `date_graduate` DATE
);
CREATE INDEX Students_name ON Students(`name`);

CREATE TABLE Classes(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `course_id` VARCHAR(10) NOT NULL,
  `lecturer_id` INT NOT NULL,
  `date_initial` DATE NOT NULL,
  `date_final` DATE NOT NULL,
  CONSTRAINT Classes_course_id FOREIGN KEY(`course_id`) REFERENCES Courses(`id`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT Classes_lecturer_id FOREIGN KEY(`lecturer_id`) REFERENCES Lecturers(`id`)
    ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE Registrations(
  `class_id` INT NOT NULL,
  `student_id` INT NOT NULL,
  `grade` CHAR(1),
  PRIMARY KEY(`class_id`, `student_id`),
  CONSTRAINT Registrations_class_id FOREIGN KEY(`class_id`) REFERENCES Classes(`id`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT Registrations_student_id FOREIGN KEY(`student_id`) REFERENCES Students(`id`)
    ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE Schedules(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `class_id` INT NOT NULL,
  `day` INT NOT NULL,
  `time_start` TIME NOT NULL,
  `time_end` TIME NOT NULL,
  CONSTRAINT fk_Schedules_class_id__id FOREIGN KEY(`class_id`) REFERENCES Classes(`id`)
    ON DELETE RESTRICT ON UPDATE RESTRICT
);
