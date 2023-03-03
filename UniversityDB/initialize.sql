CREATE SCHEMA IF NOT EXISTS UniversityDB;
USE UniversityDB;

DROP TABLE IF EXISTS Registrations;
DROP TABLE IF EXISTS Schedules;
DROP TABLE IF EXISTS Classes;
DROP TABLE IF EXISTS Courses;
DROP TABLE IF EXISTS Schedules;
DROP TABLE IF EXISTS Lecturers;
DROP TABLE IF EXISTS Students;

-- Courses

CREATE TABLE Courses(
  `id` VARCHAR(10) NOT NULL PRIMARY KEY,
  `name` VARCHAR(50) NOT NULL
);
CREATE INDEX Courses_name ON Courses(`name`);

INSERT INTO Courses VALUES('CS425', 'Database Organization');
INSERT INTO Courses VALUES('CS430', 'Introduction to Algorithms');
INSERT INTO Courses VALUES('CS554', 'Data-Intensive Computing');

-- Lecturers

CREATE TABLE Lecturers(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(50) NOT NULL,
  `date_join` DATE NOT NULL
);
CREATE INDEX Lecturers_name ON Lecturers(`name`);

INSERT INTO Lecturers VALUES(NULL, 'Raicu Ioan', '2023-01-22');
INSERT INTO Lecturers VALUES(NULL, 'Balekaki Gerald', '2023-01-22');
INSERT INTO Lecturers VALUES(NULL, 'Yao Lan', '2023-01-22');

-- Students

CREATE TABLE Students(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `name` VARCHAR(50) NOT NULL,
  `date_join` DATE NOT NULL,
  `date_graduate` DATE
);
CREATE INDEX Students_name ON Students(`name`);

INSERT INTO Students VALUES(NULL, 'John', '2023-01-22', NULL);
INSERT INTO Students VALUES(NULL, 'Adam', '2023-01-22', NULL);
INSERT INTO Students VALUES(NULL, 'Mark', '2023-01-22', NULL);

-- Classes

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

INSERT INTO Classes VALUES(NULL, 'CS425', 2, '2023-01-09', '2023-05-06');
INSERT INTO Classes VALUES(NULL, 'CS430', 3, '2023-01-09', '2023-05-06');
INSERT INTO Classes VALUES(NULL, 'CS554', 1, '2023-01-09', '2023-05-06');

-- Registrations

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

INSERT INTO Registrations VALUES(1, 1, NULL);
INSERT INTO Registrations VALUES(1, 2, NULL);
INSERT INTO Registrations VALUES(1, 3, NULL);

INSERT INTO Registrations VALUES(2, 1, NULL);
INSERT INTO Registrations VALUES(2, 2, NULL);
INSERT INTO Registrations VALUES(2, 3, NULL);

INSERT INTO Registrations VALUES(3, 1, NULL);
INSERT INTO Registrations VALUES(3, 2, NULL);
INSERT INTO Registrations VALUES(3, 3, NULL);

-- Schedules

CREATE TABLE Schedules(
  `id` INT AUTO_INCREMENT PRIMARY KEY,
  `class_id` INT NOT NULL,
  `day` INT NOT NULL,
  `time_start` TIME NOT NULL,
  `time_end` TIME NOT NULL,
  CONSTRAINT fk_Schedules_class_id__id FOREIGN KEY(`class_id`) REFERENCES Classes(`id`)
    ON DELETE RESTRICT ON UPDATE RESTRICT
);

INSERT INTO Schedules VALUES(NULL, 1, 1, '08:35:00', '09:50:00');
INSERT INTO Schedules VALUES(NULL, 1, 3, '08:35:00', '09:50:00');

INSERT INTO Schedules VALUES(NULL, 2, 2, '15:15:00', '16:30:00');
INSERT INTO Schedules VALUES(NULL, 2, 4, '15:15:00', '16:30:00');

INSERT INTO Schedules VALUES(NULL, 3, 2, '11:25:00', '12:40:00');
INSERT INTO Schedules VALUES(NULL, 3, 4, '11:25:00', '12:40:00');
