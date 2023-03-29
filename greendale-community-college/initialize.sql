CREATE SCHEMA IF NOT EXISTS GreendaleCommunityCollege;
USE GreendaleCommunityCollege;

DROP TABLE IF EXISTS Students;

-- Students

CREATE TABLE Students(
  `Name` VARCHAR(50),
  `Qualify` CHAR(1) NOT NULL,
  `Promotion Code` VARCHAR(3) NOT NULL,
  `Year` YEAR NOT NULL,
  `Course` VARCHAR(6) NOT NULL,
  `Convenor` VARCHAR(20) NOT NULL,
  `Pre-Requisutes` VARCHAR(20),
  `Periods` VARCHAR(50) NOT NULL,
  `Mark` VARCHAR(3),
  `Symbol` VARCHAR(3)
);
