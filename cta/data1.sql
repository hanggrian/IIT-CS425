DELETE FROM Passengers;
DELETE FROM WagonRegistrations;
DELETE FROM Wagons;
DELETE FROM Trains;
DELETE FROM Locomotives;
DELETE FROM Conductors;
DELETE FROM Stations;
DELETE FROM Tracks;

INSERT INTO Tracks VALUES
  ('Blue'),
  ('Green');

INSERT INTO Stations VALUES
  ('0.1', '0.1', 'Blue', 'Damen', 0),
  ('0.1', '0.2', 'Blue', 'Clark-Lake', 1),
  ('0.1', '0.2', 'Green', 'Clark-Lake', 1),
  ('0.2', '0.2', 'Green', 'Roosevelt', 0);

INSERT INTO Conductors VALUES
  ('Jane', '1991-01-01'),
  ('Michael', '1992-02-02');

INSERT INTO Locomotives VALUES
  ('X1', 1998),
  ('A9', 1980);

INSERT INTO Trains VALUES
  (1, 'Blue', 'X1', 'Jane'),
  (2, 'Green', 'A9', 'Michael');

INSERT INTO Wagons VALUES
  (1, 50),
  (2, 50),
  (3, 50),
  (4, 50);

INSERT INTO WagonRegistrations VALUES
  (1, 1),
  (1, 2),
  (2, 3),
  (2, 4);

INSERT INTO Passengers VALUES
  (NULL, 3.0, 1),
  (NULL, 5.0, 2);
