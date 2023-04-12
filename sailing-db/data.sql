DELETE FROM Reserves;
DELETE FROM Sailors;
DELETE FROM Captains;
DELETE FROM Boats;

INSERT INTO Sailors VALUES
  ('Marx', 23, 8, 52),
  ('Martin', 25, 9, 51),
  ('Adams', 27, 8, 36),
  ('Carrey', 33, 10, 22);

INSERT INTO Captains VALUES
  ('Marx', 23, 8, 52),
  ('Martin', 25, 9, 51),
  ('Adams', 27, 8, 36),
  ('Carrey', 33, 10, 22);

INSERT INTO Boats VALUES
  ('Wayfarer', 109, 120, 'Hout Bay'),
  ('SeaPride', 108, 500, 'Fish Hock'),
  ('Yuppie', 101, 400, 'Hout Bay'),
  ('Joy', 104, 200, 'Hout Bay');

INSERT INTO Reserves VALUES
  (23, 109, '2014-08-01', 120),
  (23, 108, '2014-08-08', 120),
  (25, 101, '2014-08-08', 0),
  (27, 101, '2014-08-09', 100),
  (27, 109, '2014-08-15', 120),
  (33, 109, '2014-09-04', 0),
  (33, 104, '2014-09-11', 0);
