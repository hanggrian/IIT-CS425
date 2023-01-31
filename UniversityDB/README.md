# University Database

> Part of Homework 1.1 and Homework 2.2.

![Main preview.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/UniversityDB/screenshots/main.png)

A desktop app that manages classes, which in turn, manages students, lecturers,
courses, and schedules. Powered by JavaFX UI and Kotlin language, this
submission also showcase basic implementation of DAO, a design pattern that
binds JVM properties to persistence data.

### Login

![Login dialog.](https://github.com/hendraanggrian/IIT-CS425/raw/assets/UniversityDB/screenshots/login.png)

At the start of use, there is a MySQL login prompt. This dialog remembers user
input by saving it to local preferences. Admittedly not the best choice to store
a password, but security has never been a strong focus of this assignment.

## Installation

- Run a *MySQL Server*.
- Create a new user, or use `root`.
- Create a new empty schema with any name.

## Building

- *MySQL Server*
- *IntelliJ IDEA*
- *JDK 11+*
