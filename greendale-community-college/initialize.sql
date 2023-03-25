CREATE SCHEMA IF NOT EXISTS GreendaleCommunityCollege;
USE GreendaleCommunityCollege;

DROP TABLE IF EXISTS Students;

-- Students

CREATE TABLE Students(
  `Name` VARCHAR(20) PRIMARY KEY,
  `Qualify` CHAR(1) NOT NULL,
  `Promotion Code` VARCHAR(3) NOT NULL,
  `Year` YEAR NOT NULL,
  `Course` VARCHAR(6) NOT NULL,
  `Convenor` VARCHAR(20) NOT NULL,
  `Pre-Requisutes` VARCHAR(20),
  `Periods` VARCHAR(20) NOT NULL,
  `Mark` INT(3),
  `Symbol` VARCHAR(3)
);
