# [Project Deliverable 4](https://github.com/hendraanggrian/IIT-CS425/blob/assets/assignments/proj.pdf): CTA

> The last deliverable requires to write a program in any programming language
  SQL supports and implement a variety of SQL commands.

## Problem 1

> The application can either be web or desktop application and must be
  demonstrated on the due date.

![Main preview.](https://github.com/hendraanggrian/IIT-CS425/blob/assets/cta/preview.png)

CTA app is a desktop app that manages train properties, employees & customers.
Powered by JavaFX UI and Kotlin language, this submission also showcase basic
implementation of DAO, a design pattern that binds JVM properties to persistence
data.

### Login

![Login dialog preview.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/cta/preview_login.png)

At the start of use, there is a MySQL login prompt. This dialog remembers user
input by saving it to local preferences. Admittedly not the best choice to store
a password, but security has never been a strong focus of this assignment.

### Installation

- Run a *MySQL Server*.
- Create a new user, or use `root`.
- Create a new empty schema with any name.

### Building

- *MySQL Server*
- *IntelliJ IDEA*
- *JDK 11+*

## Problem 2

> Extra points will be awarded to a group that tests a variety of challenging
  SQL usage (such as set operations, aggregate functions, set membership, set
  comparison, subqueries using WITH clause, etc.).

## Problem 3

> Further, interesting use cases that can be evaluated with sample queries will
  earn extra points.

## Checklist

- Main objectives:
  - [ ] Create desktop application.
  - [x] Create full database diagram.
- SQL commands improvement:
  - [ ] Add restriction/cascading when deleting/updating.
  - [x] Add `CHECK` contraints to restrict bad input.
  - [x] Add nullability check.
  - [x] Add default values.
- Possible schema imporovement:
  - [ ] Support traveling by bus, doesn't add many tables but massively change
    the structure of existing tables.
  - [x] Support membership with weekly and/or monthly payment, potentially adding
    2-3 more tables.
