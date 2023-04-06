INSERT INTO Conductors VALUES
  ('jane', DEFAULT, 'Jane Doe', '1991-01-01', 2023 - 1991, '123'),
  ('john', DEFAULT, 'John Doe', '1992-02-02', 2021 - 1992, '456,789');

INSERT INTO Alerts VALUES
  (NULL, 'Elevator maintenance at Damen.', '2023-03-27', '2023-04-27', 'jane'),
  (NULL, 'Closed today because of tornado.', '2023-03-28', '2023-03-28', 'john');

INSERT INTO Tracks VALUES
  ('Blue'),
  ('Green');

INSERT INTO Stations VALUES
  (41.9100, 87.6780, 'Blue', 'Damen', '60622', NULL, DEFAULT, DEFAULT),
  (41.8858, 87.6316, 'Blue', 'Clark-Lake', '60601', 'Stations located at 3rd floor.', DEFAULT, DEFAULT),
  (41.8858, 87.6316, 'Green', 'Clark-Lake', '60601', 'Stations located at basement.', DEFAULT, DEFAULT),
  (41.8674, 87.6266, 'Green', 'Roosevelt', '60605', NULL, DEFAULT, DEFAULT);

INSERT INTO Locomotives VALUES
  ('0001', 1998),
  ('0002', 1980);

INSERT INTO Wagons VALUES
  ('0001', 40),
  ('0002', 50),
  ('0003', 45),
  ('0004', 55);

INSERT INTO Trains VALUES
  (1, 'Blue', '0001', 'jane'),
  (2, 'Green', '0002', 'john');

INSERT INTO Railcars VALUES
  (1, '0001'),
  (1, '0002'),
  (2, '0003'),
  (2, '0004');

INSERT INTO Passengers VALUES
  (1, 'Michael'),
  (2, 'Mike');

INSERT INTO Passes VALUES
  (1, '2023-03-01', '2023-04-01', 1),
  (2, '2023-03-01', '2023-04-01', 2);

INSERT INTO Trips VALUES
  (DEFAULT, 1, 3.0, 1, 41.9100, 87.6780, 'Blue', 41.8858, 87.6316, 'Green'),
  (DEFAULT, 2, 2.5, 2, 41.9100, 87.6780, 'Blue', 41.8674, 87.6266, 'Green');
