CREATE SCHEMA `university`;
USE `university`;

/* Generated from Kotlin Exposed. */

-- Courses

CREATE TABLE IF NOT EXISTS Courses (id VARCHAR(10) NOT NULL, `name` VARCHAR(50) NOT NULL);
CREATE INDEX Courses_id ON Courses (id);
CREATE INDEX Courses_name ON Courses (`name`);

INSERT INTO Courses (id, `name`) VALUES ('CS425', 'Database Organization');
INSERT INTO Courses (id, `name`) VALUES ('CS430', 'Introduction to Algorithms');
INSERT INTO Courses (id, `name`) VALUES ('CS554', 'Data-Intensive Computing');

-- Lecturers

CREATE TABLE IF NOT EXISTS Lecturers (id INT AUTO_INCREMENT PRIMARY KEY, `name` VARCHAR(50) NOT NULL, date_join DATE NOT NULL);
CREATE INDEX Lecturers_name ON Lecturers (`name`);

INSERT INTO Lecturers (date_join, `name`) VALUES ('2023-01-22', 'Raicu Ioan');
INSERT INTO Lecturers (date_join, `name`) VALUES ('2023-01-22', 'Balekaki Gerald');
INSERT INTO Lecturers (date_join, `name`) VALUES ('2023-01-22', 'Yao Lan');

-- Students

CREATE TABLE IF NOT EXISTS Students (id INT AUTO_INCREMENT PRIMARY KEY, `name` VARCHAR(50) NOT NULL, date_join DATE NOT NULL, date_graduate DATE NULL);
CREATE INDEX Students_name ON Students (`name`);

INSERT INTO Students (date_join, `name`) VALUES ('2023-01-22', 'John');
INSERT INTO Students (date_join, `name`) VALUES ('2023-01-22', 'Adam');
INSERT INTO Students (date_join, `name`) VALUES ('2023-01-22', 'Mark');

-- Classes

CREATE TABLE IF NOT EXISTS Classes (id INT AUTO_INCREMENT PRIMARY KEY, course_id VARCHAR(10) NOT NULL, lecturer_id INT NOT NULL, date_initial DATE NOT NULL, date_final DATE NOT NULL, CONSTRAINT fk_Classes_course_id__id FOREIGN KEY (course_id) REFERENCES Courses(id) ON DELETE RESTRICT ON UPDATE RESTRICT, CONSTRAINT fk_Classes_lecturer_id__id FOREIGN KEY (lecturer_id) REFERENCES Lecturers(id) ON DELETE RESTRICT ON UPDATE RESTRICT);

INSERT INTO Classes (course_id, date_final, date_initial, lecturer_id) VALUES ('CS425', '2023-05-06', '2023-01-09', 2);
INSERT INTO Classes (course_id, date_final, date_initial, lecturer_id) VALUES ('CS430', '2023-05-06', '2023-01-09', 3);
INSERT INTO Classes (course_id, date_final, date_initial, lecturer_id) VALUES ('CS554', '2023-05-06', '2023-01-09', 1);

-- Registrations

CREATE TABLE IF NOT EXISTS Registrations (id INT AUTO_INCREMENT PRIMARY KEY, class_id INT NOT NULL, student_id INT NOT NULL, grade CHAR(1) NULL, CONSTRAINT fk_Registrations_class_id__id FOREIGN KEY (class_id) REFERENCES Classes(id) ON DELETE RESTRICT ON UPDATE RESTRICT, CONSTRAINT fk_Registrations_student_id__id FOREIGN KEY (student_id) REFERENCES Students(id) ON DELETE RESTRICT ON UPDATE RESTRICT);

INSERT INTO Registrations (class_id, student_id) VALUES (1, 1);
INSERT INTO Registrations (class_id, student_id) VALUES (1, 2);
INSERT INTO Registrations (class_id, student_id) VALUES (1, 3);

INSERT INTO Registrations (class_id, student_id) VALUES (2, 1);
INSERT INTO Registrations (class_id, student_id) VALUES (2, 2);
INSERT INTO Registrations (class_id, student_id) VALUES (2, 3);

INSERT INTO Registrations (class_id, student_id) VALUES (3, 1);
INSERT INTO Registrations (class_id, student_id) VALUES (3, 2);
INSERT INTO Registrations (class_id, student_id) VALUES (3, 3);

-- Schedules

CREATE TABLE IF NOT EXISTS Schedules (id INT AUTO_INCREMENT PRIMARY KEY, class_id INT NOT NULL, `day` INT NOT NULL, time_start TIME NOT NULL, time_end TIME NOT NULL, CONSTRAINT fk_Schedules_class_id__id FOREIGN KEY (class_id) REFERENCES Classes(id) ON DELETE RESTRICT ON UPDATE RESTRICT);

INSERT INTO Schedules (class_id, `day`, time_end, time_start) VALUES (1, 1, '09:50:00', '08:35:00');
INSERT INTO Schedules (class_id, `day`, time_end, time_start) VALUES (1, 3, '09:50:00', '08:35:00');

INSERT INTO Schedules (class_id, `day`, time_end, time_start) VALUES (2, 2, '16:30:00', '15:15:00');
INSERT INTO Schedules (class_id, `day`, time_end, time_start) VALUES (2, 4, '16:30:00', '15:15:00');

INSERT INTO Schedules (class_id, `day`, time_end, time_start) VALUES (3, 2, '12:40:00', '11:25:00');
INSERT INTO Schedules (class_id, `day`, time_end, time_start) VALUES (3, 4, '12:40:00', '11:25:00');
