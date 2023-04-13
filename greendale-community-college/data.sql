CREATE SCHEMA IF NOT EXISTS GreendaleCommunityCollege;
USE GreendaleCommunityCollege;

DELETE FROM TimeSlots;
DELETE FROM Registrations;
DELETE FROM Courses;
DELETE FROM Students;
DELETE FROM Departments;
DELETE FROM Convenors;

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
