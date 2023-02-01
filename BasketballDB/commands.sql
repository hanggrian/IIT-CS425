-- This script only prepare schema and tables. To fill them, use 'Table Data Import Wizard' from
-- MySQLWorkbench with CSV files in `data` directory.

CREATE SCHEMA IF NOT EXISTS BasketballDB;
USE BasketballDB;

DROP TABLE IF EXISTS Coaches;
DROP TABLE IF EXISTS Players;
DROP TABLE IF EXISTS Persons;
DROP TABLE IF EXISTS Teams;

-- Teams

CREATE TABLE Teams(
  `TmID` VARCHAR(3) PRIMARY KEY,
  `ConfID` VARCHAR(2) NOT NULL,
  `Ranking` INT NOT NULL,
  `Playoff` VARCHAR(2),
  `Name` VARCHAR(40) NOT NULL,
  `Won` INT NOT NULL,
  `Lost` INT NOT NULL,
  `Games` INT NOT NULL
);

-- Persons

CREATE TABLE Persons(
  `BioID` VARCHAR(10) PRIMARY KEY,
  `FirstName` VARCHAR(20),
  `LastName` VARCHAR(20) NOT NULL,
  `BirthDate` VARCHAR(10),
  `BirthCity` VARCHAR(40),
  `BirthCountry` VARCHAR(20)
);

-- Players

CREATE TABLE Players(
  `BioID` VARCHAR(10) PRIMARY KEY,
  `TmID` VARCHAR(3) NOT NULL,
  `Played` INT NOT NULL,
  `Started` INT NOT NULL,
  `Minutes` INT NOT NULL,
  `Points` INT NOT NULL,
  `Attempts` INT NOT NULL,
  `Made` INT NOT NULL,
  CONSTRAINT Players_BioID FOREIGN KEY(`BioID`) REFERENCES Persons(`BioID`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT Players_TmID FOREIGN KEY(`TmID`) REFERENCES Teams(`TmID`)
    ON DELETE RESTRICT ON UPDATE RESTRICT
);

-- Coaches

CREATE TABLE Coaches(
  `BioID` VARCHAR(10) PRIMARY KEY,
  `TmID` VARCHAR(3) NOT NULL,
  `Won` INT NOT NULL,
  `Lost` INT NOT NULL,
  `Games` INT NOT NULL,
  `Stint` INT NOT NULL,
  CONSTRAINT Coaches_BioID FOREIGN KEY(`BioID`) REFERENCES Persons(`BioID`)
    ON DELETE RESTRICT ON UPDATE RESTRICT,
  CONSTRAINT Coaches_TmID FOREIGN KEY(`TmID`) REFERENCES Teams(`TmID`)
    ON DELETE RESTRICT ON UPDATE RESTRICT
);
