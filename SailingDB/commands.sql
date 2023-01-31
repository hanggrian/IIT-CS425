CREATE SCHEMA SailingDB;
USE SailingDB;

-- Sailors

CREATE TABLE Sailors(
  `Sname` VARCHAR(20) NOT NULL,
  `SID` INT AUTO_INCREMENT PRIMARY KEY,
  `Rating` INT,
  `Age` INT NOT NULL
);

INSERT INTO Sailors(`Sname`, `SID`, `Rating`, `Age`)
  VALUES('Marx', 23, 8, 52);
INSERT INTO Sailors(`Sname`, `SID`, `Rating`, `Age`)
  VALUES('Martin', 25, 9, 51);
INSERT INTO Sailors(`Sname`, `SID`, `Rating`, `Age`)
  VALUES('Adams', 27, 8, 36);
INSERT INTO Sailors(`Sname`, `SID`, `Rating`, `Age`)
  VALUES('Carrey', 33, 10, 22);

-- Captains

CREATE TABLE Captains(
  `Cname` VARCHAR(20) NOT NULL,
  `CID` INT AUTO_INCREMENT PRIMARY KEY,
  `Rating` INT,
  `Age` INT NOT NULL
);

INSERT INTO Captains(`Cname`, `CID`, `Rating`, `Age`)
  VALUES('Marx', 23, 8, 52);
INSERT INTO Captains(`Cname`, `CID`, `Rating`, `Age`)
  VALUES('Martin', 25, 9, 51);
INSERT INTO Captains(`Cname`, `CID`, `Rating`, `Age`)
  VALUES('Adams', 27, 8, 36);
INSERT INTO Captains(`Cname`, `CID`, `Rating`, `Age`)
  VALUES('Carrey', 33, 10, 22);

-- Boats

CREATE TABLE Boats(
  `Bname` VARCHAR(20) NOT NULL,
  `BID` INT AUTO_INCREMENT PRIMARY KEY,
  `Fee` INT NOT NULL,
  `Location` VARCHAR(20) NOT NULL
);

INSERT INTO Boats(`Bname`, `BID`, `Fee`, `Location`)
  VALUES('Wayfarer', 109, 120, 'Hout Bay');
INSERT INTO Boats(`Bname`, `BID`, `Fee`, `Location`)
  VALUES('SeaPride', 108, 500, 'Fish Hock');
INSERT INTO Boats(`Bname`, `BID`, `Fee`, `Location`)
  VALUES('Yuppie', 101, 400, 'Hout Bay');
INSERT INTO Boats(`Bname`, `BID`, `Fee`, `Location`)
  VALUES('Joy', 104, 200, 'Hout Bay');

-- Reservations

CREATE TABLE Reservations(
  `SID` INT NOT NULL,
  `BID` INT NOT NULL,
  `Day` DATE NOT NULL,
  `Deposit` INT NOT NULL,
  PRIMARY KEY(`SID`, `BID`),
  CONSTRAINT fk_Reservations_SID FOREIGN KEY(`SID`) REFERENCES Sailors(`SID`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT fk_Reservations_BID FOREIGN KEY(`BID`) REFERENCES Boats(`BID`)
    ON DELETE RESTRICT ON UPDATE RESTRICT
);

INSERT INTO Reservations(`SID`, `BID`, `Day`, `Deposit`)
  VALUES(23, 109, '2014-08-01', 120);
INSERT INTO Reservations(`SID`, `BID`, `Day`, `Deposit`)
  VALUES(23, 108, '2014-08-08', 120);
INSERT INTO Reservations(`SID`, `BID`, `Day`, `Deposit`)
  VALUES(25, 101, '2014-08-08', 0);
INSERT INTO Reservations(`SID`, `BID`, `Day`, `Deposit`)
  VALUES(27, 101, '2014-08-09', 100);
INSERT INTO Reservations(`SID`, `BID`, `Day`, `Deposit`)
  VALUES(27, 109, '2014-08-15', 120);
INSERT INTO Reservations(`SID`, `BID`, `Day`, `Deposit`)
  VALUES(33, 109, '2014-09-04', 0);
INSERT INTO Reservations(`SID`, `BID`, `Day`, `Deposit`)
  VALUES(33, 104, '2014-09-11', 0);
