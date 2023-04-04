<!-- hotfix: KaTeX -->
<!-- https://github.com/yzane/vscode-markdown-pdf/issues/21/ -->
<script type="text/javascript" src="http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML"></script>
<script type="text/x-mathjax-config">MathJax.Hub.Config({ tex2jax: { inlineMath: [['$', '$']] }, messageStyle: 'none' });</script>

# Exam 1

> ## Instructions
>
> ### Answer ALL questions.
>
> ### Time allowed: 1 hour.

> ## Database context
>
> Consider the following database schema (__*RateMyProfDB*__) to answer all the
  questions below.
>
> | <small>Table: Professor<br>PK: PID</small><br>PID | <br>Pname | <br>Papers | <br>Topic |
> | --- | --- | --- | --- |
> | **109** | Steven | 10 | Java |
> | **110** | Francis | 50 | Databases |
> | **111** | Daniel | 40 | Java |
> | **112** | Joy | 20 | Java |
>
> | <small>Table: Rating<br>PK: SID + PID<br>FK: SID, PID</small><br>SID | <br>PID | <br>Score | <br>Attended |
> | --- | --- | --- | --- |
> | **23** | **109** | 6 | 60 |
> | **23** | **110** | 10 | 70 |
> | **25** | **111** | 8 | 40 |
> | **27** | **111** | 9 | 100 |
> | **27** | **109** | 4 | 20 |
> | **33** | **109** | 5 | 80 |
> | **33** | **112** | 1 | 4 |
>
> | <small>Table: Student<br>PK: SID</small><br>SID | <br>Sname | <br>Uni | <br>GPA |
> | --- | --- | --- | --- |
> | **23** | Michelle | Illinois Tech | 50.52 |
> | **25** | Tomas | UChi | 20.71 |
> | **27** | Biden | Illinois Tech | 34.66 |
> | **33** | John | UChi | 10.82 |
>
> | <small>Metadata</small><br>Item | <br>Description |
> | --- | --- |
> | PID | professor identification number (auto number field) |
> | Pname | name of Profeesor (not nullable field) |
> | Papers | number of papers published by professor (nullable field) |
> | Topic | professor's topic/area of speciality (nullable field) |
> | SID | student's identification number (non-auto increment field) |
> | Sname | name of student (not nullable) |
> | Uni | a university students attend (not nullable) |
> | GPA | student's grade average point (nullable) |
> | Score | rating score from student (can only be between 0 and 10), and not nullable. |
> | Attended | percentage of students that participated in the rating (not nullable) |
> | PK | primary key |
> | FK | foreign key |

## Relation algebra problem 1

> Write a relational algebra statement to select all professors whose specialty
  is not Java.

## Relation algebra problem 1

> Write a relational-algebra expression that returns the name and GPA of
  students with a GPA greater than 30 and studies at Illinois Tech.

## Relation algebra problem 1

> Write a relational-algebra expression to find all information about students
  and ratings, and the students who gave a score less than five.

## SQL Problem 1
