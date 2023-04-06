INSERT INTO Courses VALUES
  ('CS425', 'Database Organization'),
  ('CS430', 'Introduction to Algorithms'),
  ('CS554', 'Data-Intensive Computing');

INSERT INTO Lecturers VALUES
  (NULL, 'Raicu Ioan', '2023-01-22'),
  (NULL, 'Balekaki Gerald', '2023-01-22'),
  (NULL, 'Yao Lan', '2023-01-22');

INSERT INTO Students VALUES
  (NULL, 'John', '2023-01-22', NULL),
  (NULL, 'Adam', '2023-01-22', NULL),
  (NULL, 'Mark', '2023-01-22', NULL);

INSERT INTO Classes VALUES
  (NULL, 'CS425', 2, '2023-01-09', '2023-05-06'),
  (NULL, 'CS430', 3, '2023-01-09', '2023-05-06'),
  (NULL, 'CS554', 1, '2023-01-09', '2023-05-06');

INSERT INTO Registrations VALUES
  (1, 1, NULL),
  (1, 2, NULL),
  (1, 3, NULL);
INSERT INTO Registrations VALUES
  (2, 1, NULL),
  (2, 2, NULL),
  (2, 3, NULL);
INSERT INTO Registrations VALUES
  (3, 1, NULL),
  (3, 2, NULL),
  (3, 3, NULL);

INSERT INTO Schedules VALUES
  (NULL, 1, 1, '08:35:00', '09:50:00'),
  (NULL, 1, 3, '08:35:00', '09:50:00');
INSERT INTO Schedules VALUES
  (NULL, 2, 2, '15:15:00', '16:30:00'),
  (NULL, 2, 4, '15:15:00', '16:30:00');
INSERT INTO Schedules VALUES
  (NULL, 3, 2, '11:25:00', '12:40:00'),
  (NULL, 3, 4, '11:25:00', '12:40:00');
